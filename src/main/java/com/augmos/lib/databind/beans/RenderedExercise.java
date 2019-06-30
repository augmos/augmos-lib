package com.augmos.lib.databind.beans;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;

public final class RenderedExercise extends Exercise {

    //jackson keys
    public static final String RENDERED_TEXT_KEY = "renderedText";
    public static final String RENDERED_MATHML_KEY = "renderedMathml";
    public static final String RENDERED_SOLUTION_KEY = "renderedSolution";
    public static final String RENDERED_ANSWERS_KEY = "renderedAnswers";

    //instance fields

    //rendered templates
    private final String renderedText;
    private final String renderedMathml;
    private final List<String> renderedSolution;
    private final Map<String, String> renderedAnswers;

    @JsonCreator
    public RenderedExercise(
            @JsonProperty(ID_KEY) final long id,
            @JsonProperty(NAME_KEY) final String name,
            @JsonProperty(TEMPLATE_KEY) final Template template,
            @JsonProperty(PARAMS_KEY) final Map<String, String> params,
            @JsonProperty(RENDERED_TEXT_KEY) final String renderedText,
            @JsonProperty(RENDERED_MATHML_KEY) final String renderedMathml,
            @JsonProperty(RENDERED_SOLUTION_KEY) final List<String> renderedSolution,
            @JsonProperty(RENDERED_ANSWERS_KEY) final Map<String, String> renderedAnswers
    ) {
        super(id, name, template, params);

        this.renderedText = renderedText;
        this.renderedMathml = renderedMathml;
        this.renderedSolution = renderedSolution;
        this.renderedAnswers = renderedAnswers;
    }

    public RenderedExercise(
            final Exercise exercise,
            final String renderedText,
            final String renderedMathml,
            final List<String> renderedSolution,
            final Map<String, String> renderedAnswers
    ) {
        this(exercise.getId(), exercise.getName(), exercise.getTemplate(), exercise.getParams(), renderedText, renderedMathml, renderedSolution, renderedAnswers);
    }

    @JsonProperty(RENDERED_TEXT_KEY)
    public String getRenderedText() {
        return renderedText;
    }

    @JsonProperty(RENDERED_MATHML_KEY)
    public String getRenderedMathml() {
        return renderedMathml;
    }

    @JsonProperty(RENDERED_SOLUTION_KEY)
    public List<String> getRenderedSolution() {
        return renderedSolution;
    }

    @JsonProperty(RENDERED_ANSWERS_KEY)
    public Map<String, String> getRenderedAnswers() {
        return renderedAnswers;
    }
}
