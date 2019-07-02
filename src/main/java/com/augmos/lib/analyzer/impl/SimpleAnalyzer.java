package com.augmos.lib.analyzer.impl;

import com.augmos.lib.analyzer.AnalyzedExercise;
import com.augmos.lib.analyzer.Analyzer;
import com.augmos.lib.databind.beans.RenderedExercise;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.regex.Pattern;

public final class SimpleAnalyzer implements Analyzer<RenderedExercise> {

    // logger
    private static final Logger LOG = LoggerFactory.getLogger(SimpleAnalyzer.class);


    // special answer keys
    private static final String CALCULATION_KEY = "%calc%";

    // list of all special answer keys
    private static final LinkedList<String> SPECIAL_ANSWER_KEYS = new LinkedList<>(
            Arrays.asList(new String[] {
                    CALCULATION_KEY
            })
    );

    // matches all whitespaces that are outside of XML-tags (I'm a fucking genius)
    private static final Pattern NON_XML_WHITESPACES = Pattern.compile("[\\s]+(?![^><]*>)");

    // matches a string tagged as mathml
    private static final Pattern MATHML_PATTERN = Pattern.compile("<math xmlns='http:\\/\\/www.w3.org\\/1998\\/Math\\/MathML'>.+<\\/math>");
    // patterns to match the mathml headers
    private static final Pattern MATHML_OPEN_TAG = Pattern.compile("<math xmlns='http:\\/\\/www.w3.org\\/1998\\/Math\\/MathML'>");
    private static final Pattern MATHML_CLOSE_TAG = Pattern.compile("<\\/math>");

    // matches multiline content
    private static final Pattern MULTILINE_PATTERN = Pattern.compile("<mtable columnalign='left'>.+<\\/mtable>");
    // matches multiline tags
    private static final Pattern MULTILINE_OPEN_TAG = Pattern.compile("<mtable columnalign='left'>");
    private static final Pattern MULTILINE_CLOSE_TAG = Pattern.compile("<\\/mtable>");

    // matches valid multiline content
    private static final Pattern VALID_MULTILINE = Pattern.compile("(<mtr><mtd>.*<\\/mtd><\\/mtr>)+");

    //matches empty strings between lines
    private static final String MULTILINE_SPLIT = "(?<=(<\\/mtd><\\/mtr>))(?=(<mtr><mtd>))";

    @Override
    public AnalyzedExercise analyze(
            final long id,
            final long student,
            final RenderedExercise exercise,
            final String mathml,
            final String jiix
    ) {
        // remove all whitespaces that are not within tags
        final String denseMathml = NON_XML_WHITESPACES.matcher(mathml).replaceAll("");

        // check for mathml tags
        if (!MATHML_PATTERN.matcher(denseMathml).matches()) {
            //TODO exit
        }

        // remove mathml tags
        final String content = MATHML_OPEN_TAG.matcher(
                MATHML_CLOSE_TAG.matcher(denseMathml).replaceAll("")
        ).replaceFirst("");

        // check if multiline
        if (MULTILINE_PATTERN.matcher(content).matches()) {
            // multiline
            // remove multiline tags
            final String multiline = MULTILINE_OPEN_TAG.matcher(
                    MULTILINE_CLOSE_TAG.matcher(content).replaceAll("")
            ).replaceFirst("");

            // check if valid multiline
            if (!VALID_MULTILINE.matcher(multiline).matches()) {
                //TODO exit
            }

            // split lines
            final LinkedList<String> lines = new LinkedList<>(
                    Arrays.asList(multiline.split(MULTILINE_SPLIT))
            );

            // ANALYSIS STARTS HERE
            int present = 0;

            for (final String line : lines) {
                if (exercise.getRenderedSolution().contains(line)) {
                    present++;
                }
            }

            final LinkedList<String> answers = new LinkedList<>();
            boolean correct = true;
            for (final String key : exercise.getRenderedAnswers().keySet()) {

                // check for special key-value pairs
                if (SPECIAL_ANSWER_KEYS.contains(key)) {
                    correct = correct && handleSpecialKeys(key, exercise.getRenderedAnswers().get(key), lines);
                } else {
                    correct = correct &&
                            lines.contains(String.format("%s<mo>=</mo>%s", key, exercise.getRenderedAnswers().get(key)));
                }

            }

            return new AnalyzedExercise(id, student, exercise.getId(), correct, exercise.getRenderedSolution().size(), present, denseMathml, jiix);

        } else {
            // single line
            // ANALYSIS STARTS HERE
            int present;
            if (exercise.getRenderedSolution().contains(content)) {
                present = 1;
            } else {
                present = 0;
            }

            boolean correct;
            if (exercise.getRenderedAnswers().size() == 1) {

            } else {
                correct = false;
            }


            return null;
        }

    }


    private boolean handleSpecialKeys(
            final String key,
            final String value,
            final LinkedList<String> lines
    ) {
        switch (key) {
            case CALCULATION_KEY : {
                return Pattern.compile(String.format("(.*<mo>=<\\/mo>%s)|(%s)", value, value))
                        .matcher(lines.getLast())
                        .matches();
            }

            default : {
                LOG.warn("Something went very wrong - special key handler did not recognize the key. Check SPECIAL_ANSWER_KEYS.");
                return false;
            }
        }
    }

}
