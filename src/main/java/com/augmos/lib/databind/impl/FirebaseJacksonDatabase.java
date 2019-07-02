package com.augmos.lib.databind.impl;

import com.augmos.lib.databind.SimpleDatabase;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.regex.Pattern;

public final class FirebaseJacksonDatabase implements SimpleDatabase {

    private static final Logger LOG = LoggerFactory.getLogger(FirebaseJacksonDatabase.class);
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper()
//            .registerModule()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    private static final Pattern PATH_FORMAT_PATTERN = Pattern.compile("\\w+:\\w+(\\/\\w+:\\w+)*");

    private final Firestore firestore;

    public FirebaseJacksonDatabase(final Firestore firestore) {
        this.firestore = firestore;
    }

    /**
     * Send an object to Firestore at specified path
     *
     * @param path path to the needed document, formatted as follows:
     *             collection:document(/collection:document)*
     * @param obj the object to be sent
     * @param <T> generic of class type
     * @return false if something went wrong, true if everything went right
     */
    @Override
    public <T> boolean send(
            final String path,
            final T obj
    ) {
        final Optional<DocumentReference> docRef = docPath(path);
        if (!docRef.isPresent())
            return false;

        final String json;
        try {
            json = OBJECT_MAPPER.writeValueAsString(obj);
        } catch (final IOException e) {
            LOG.warn("Could not convert object to json: {}.", e.getMessage());
            return false;
        }

        final TypeReference<Map<String, Object>> typeRef = new TypeReference<Map<String, Object>>() {};
        final Map<String, Object> somap;
        try {
            somap = OBJECT_MAPPER.readValue(json, typeRef);
        } catch (final IOException e) {
            LOG.warn("Could not convert json to object map: {}.", e.getMessage());
            return false;
        }

        final ApiFuture<WriteResult> wrfuture = docRef.get().set(somap);

        //TODO timeout, fail condition
        try {
            LOG.info("Write to Firebase completed, timestamp: {}.", wrfuture.get().getUpdateTime());
            return true;
        } catch (final ExecutionException | InterruptedException e) {
            LOG.info("Write to Firebase was not confirmed due to an exception in WriteResult: {}.", e.getMessage());
            return false;
        }
    }

    /**
     * Get a specified document from Firestore as specified type
     *
     * @param path path to the needed document, formatted as follows:
     *             collection:document(/collection:document)*
     * @param clazz class to cast the document to
     * @param <T> generic of class type
     * @return an empty Optional if something went wrong. An Optional containing the requested object if everything went right
     */
    @Override
    public <T> Optional<T> get(
            final String path,
            final Class<T> clazz
    ) {
        if (!PATH_FORMAT_PATTERN.matcher(path).matches()) {
            LOG.info("Query {} does not match the input format.");
        } else {
            LOG.debug("Getting {} from Firebase...", path);
        }

        final String[] tokens = path.split("\\/");
        String[] colDoc = tokens[0].split(":");
        DocumentReference docRef = firestore.collection(colDoc[0]).document(colDoc[1]);

        for (int i = 1; i < tokens.length - 1; ++i) {
            colDoc = tokens[i].split(":");
            docRef = docRef.collection(colDoc[0]).document(colDoc[1]);
        }

        final ApiFuture<DocumentSnapshot> af = docRef.get();

        final DocumentSnapshot doc;

        try {
            doc = af.get();
        } catch (final InterruptedException | ExecutionException e) {
            LOG.warn("Fetch of document {} failed: {}.", path, e.getMessage());
            return Optional.empty();
        }

        if (doc.exists()) {
            LOG.debug("Fetch of document {} successful.");
            try {                final T output = OBJECT_MAPPER.readValue(doc.getData().toString(), clazz);
                return Optional.of(output);
            } catch (final IOException e) {
                LOG.warn("Deserialization of {} has failed: {}.", path, e.getMessage());
                return Optional.empty();
            }

        } else {
            LOG.warn("The document {} is empty or does not exist.");
            return Optional.empty();
        }

    }

    private final Optional<DocumentReference> docPath(final String path) {
        if (!PATH_FORMAT_PATTERN.matcher(path).matches()) {
            LOG.info("Provided path \"{}\" does not match the input format.", path);
            return Optional.empty();
        } else {
            LOG.debug("Getting \"{}\" from Firebase...", path);
        }

        final String[] tokens = path.split("\\/|:");
        CollectionReference colRef = firestore.collection(tokens[0]);
        DocumentReference docRef = colRef.document(tokens[1]);
        boolean atDoc = false;

        for (int i = 2; i < tokens.length; ++i) {
            if (atDoc) {
                docRef = colRef.document(tokens[i]);
                atDoc = false;
            } else {
                colRef = docRef.collection(tokens[i]);
                atDoc = true;
            }
        }

        return Optional.of(docRef);
    }

}
