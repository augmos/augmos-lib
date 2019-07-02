package com.augmos.lib.databind.impl;

import com.augmos.lib.databind.SimpleDatabase;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.regex.Pattern;

public final class FirebaseJacksonDatabase implements SimpleDatabase {

    private static final Logger LOG = LoggerFactory.getLogger(FirebaseJacksonDatabase.class);
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper()
//            .registerModule()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    private static final Pattern GET_FORMAT = Pattern.compile("\\w+:\\w+(\\/\\w+:\\w+)*");

    private final Firestore firestore;



    public FirebaseJacksonDatabase(final Firestore firestore) {
        this.firestore = firestore;
    }

    @Override
    public <T> String send(
            final String path,
            final T obj
    ) {
        //TODO


        return null;
    }

    /**
     * Get a specified document from Firebase as specified type
     *
     * @param path : path to the needed document, formatted as follows:
     *             collection:document(/collection:document)*
     * @param clazz : class to cast the document to
     * @param <T>
     * @return
     */
    @Override
    public <T> Optional<T> get(
            final String path,
            final Class<T> clazz
    ) {
        if (!GET_FORMAT.matcher(path).matches()) {
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
            try {
                //TODO remove
                LOG.info(doc.getData().toString());

                final T output = OBJECT_MAPPER.readValue(doc.getData().toString(), clazz);
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

}
