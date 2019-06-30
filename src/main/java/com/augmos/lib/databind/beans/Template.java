package com.augmos.lib.databind.beans;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public final class Template {

    // logger
    private static final Logger LOG = LoggerFactory.getLogger(Template.class);

    // jackson keys
    public static final String ID_KEY = "id";
    public static final String NAME_KEY = "name";
    public static final String DESCRIPTION_KEY = "description";
    public static final String TEXT_KEY = "text";
    public static final String MATHML_KEY = "mathml";
    public static final String SOLUTION_KEY = "solution";
    public static final String ANSWERS_KEY = "answers";
    public static final String PARAMS_KEY = "params";

    // instance fields

    // info fields
    private final long id;
    private final String name;
    private final String description;

    // freemarker templates
    private final String text;
    private final String mathml;
    private final List<String> solution;
    private final Map<String, String> answers;

    // requested params
    private final List<String> params;

    @JsonCreator
    public Template(
            @JsonProperty(ID_KEY) final long id,
            @JsonProperty(NAME_KEY) final String name,
            @JsonProperty(DESCRIPTION_KEY) final String description,
            @JsonProperty(TEXT_KEY) final String text,
            @JsonProperty(MATHML_KEY) final String mathml,
            @JsonProperty(SOLUTION_KEY) final List<String> solution,
            @JsonProperty(ANSWERS_KEY) final Map<String, String> answers,
            @JsonProperty(PARAMS_KEY) final List<String> params
    ) throws IOException {
        this.id = id;
        this.name = name;
        this.description = description;
        this.text = text;
        this.mathml = mathml;
        this.solution = solution;
        this.answers = answers;
        this.params = params;
    }

    @JsonProperty(ID_KEY)
    public long getId() {
        return id;
    }

    @JsonProperty(NAME_KEY)
    public String getName() {
        return name;
    }

    @JsonProperty(DESCRIPTION_KEY)
    public String getDescription() {
        return description;
    }

    @JsonProperty(TEXT_KEY)
    public String getText() {
        return text;
    }

    @JsonProperty(MATHML_KEY)
    public String getMathml() {
        return mathml;
    }

    @JsonProperty(SOLUTION_KEY)
    public List<String> getSolution() {
        return solution;
    }

    @JsonProperty(ANSWERS_KEY)
    public Map<String, String> getAnswers() {
        return answers;
    }

    @JsonProperty(PARAMS_KEY)
    public List<String> getParams() {
        return params;
    }

}
