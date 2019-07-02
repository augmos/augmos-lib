package com.augmos.lib.databind;

import com.augmos.lib.databind.beans.Exercise;
import com.augmos.lib.databind.beans.RenderedExercise;
import com.augmos.lib.databind.beans.Template;
import com.augmos.lib.databind.impl.FreemarkerRenderer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Optional;

public class Main {

    public static void main(final String[] args) throws Exception {
//        final Exercise exercise;
//        try (final InputStream is = Main.class.getClass().getResourceAsStream("/exercises/exec.json")) {
//            exercise = new ObjectMapper().readValue(is, Exercise.class);
//        }
//
//        final Template template;
//        try (final InputStream is = Main.class.getClass().getResourceAsStream("/templates/lineq.json")) {
//            template = new ObjectMapper().readValue(is, Template.class);
//        }
//
//        final Optional<RenderedExercise> re = new FreemarkerRenderer().render(template, exercise);
//        System.out.println(new ObjectMapper().writeValueAsString(re.get()));
    }

}
