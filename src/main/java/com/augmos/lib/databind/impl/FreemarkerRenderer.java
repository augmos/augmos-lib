package com.augmos.lib.databind.impl;

import com.augmos.lib.databind.Renderer;
import com.augmos.lib.databind.beans.Exercise;
import com.augmos.lib.databind.beans.RenderedExercise;
import com.augmos.lib.databind.beans.Template;
import freemarker.template.TemplateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.StringWriter;
import java.util.*;

public final class FreemarkerRenderer implements Renderer {

    // logger
    private static final Logger LOG = LoggerFactory.getLogger(FreemarkerRenderer.class);

    @Override
    public Optional<RenderedExercise> render(
            final Template template,
            final Exercise exercise
    ) {
        //check if exercise has all params
        for (final String p : template.getParams()) {
            if (!exercise.getParams().containsKey(p))
                return Optional.empty();
        }

        final StringWriter writer = new StringWriter();

        //render text
        try {
            final freemarker.template.Template textTemplate = new freemarker.template.Template(
                    String.format("%x-name", template.getId()),
                    template.getText(),
                    null);
            textTemplate.process(exercise.getParams(), writer);
        } catch (final IOException | TemplateException e) {
            LOG.warn("Text template rendering failed. (tid={}, eid={], msg={}", template.getId(), exercise.getId(), e.getMessage());
            return Optional.empty();
        }

        final String renderedText = writer.toString();

        //render mathml
        writer.getBuffer().setLength(0);
        try {
            final freemarker.template.Template mathmlTemplate = new freemarker.template.Template(
                    String.format("%x-mathml", template.getId()),
                    template.getMathml(),
                    null);
            mathmlTemplate.process(exercise.getParams(), writer);
        } catch (final IOException | TemplateException e) {
            LOG.warn("Mathml template rendering failed. (tid={}, eid={], msg={}", template.getId(), exercise.getId(), e.getMessage());
            return Optional.empty();
        }

        final String renderedMathml = writer.toString();

        //render solution
        final List<String> renderedSolution = new LinkedList<>();
        int count = 0;
        for (final String s : template.getSolution()) {
            writer.getBuffer().setLength(0);
            try {
                final freemarker.template.Template solutionTemplate = new freemarker.template.Template(
                        String.format("%x-solution-%d", template.getId(), count++),
                        s,
                        null
                );
                solutionTemplate.process(exercise.getParams(), writer);
                renderedSolution.add(writer.toString());

            } catch (final IOException | TemplateException e) {
                LOG.warn("Solution template rendering failed. (tid={}, eid={], msg={}", template.getId(), exercise.getId(), e.getMessage());
                return Optional.empty();
            }
        }

        //render answer
        final Map<String, String> renderedAnswer = new HashMap<>();
        count = 0;
        for (final String k : template.getAnswers().keySet()) {
            writer.getBuffer().setLength(0);
            try {
                final freemarker.template.Template answerKeyTemplate = new freemarker.template.Template(
                        String.format("%x-answer-%d", template.getId(), count),
                        k,
                        null
                );
                answerKeyTemplate.process(exercise.getParams(), writer);
                final String key = writer.toString();

                writer.getBuffer().setLength(0);

                final freemarker.template.Template answerValueTemplate = new freemarker.template.Template(
                        String.format("%x-answer-%d", template.getId(), count),
                        template.getAnswers().get(k),
                        null
                );
                answerValueTemplate.process(exercise.getParams(), writer);
                final String value = writer.toString();
                renderedAnswer.put(key, value);
            } catch (final IOException | TemplateException e) {
                LOG.warn("Answers template rendering failed. (tid={}, eid={], msg={}", template.getId(), exercise.getId(), e.getMessage());
                return Optional.empty();
            }
        }

        return Optional.of(
                new RenderedExercise(
                        exercise,
                        renderedText,
                        renderedMathml,
                        renderedSolution,
                        renderedAnswer));
    }

}
