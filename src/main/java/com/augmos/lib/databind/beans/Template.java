package com.augmos.lib.databind.beans;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public final class Template {

    public static final String ID_KEY = "id";
    public static final String NAME_KEY = "name";
    public static final String DESCRIPTION_KEY = "description";
    public static final String TEMPLATE_KEY = "template";
    public static final String PARAMS_KEY = "params";
    public static final String SOLUTION_PATH_KEY = "solutionPath";
    public static final String SOLUTION_VALUES_KEY = "solutionValues";

    private final UUID id;
    private final String name;
    private final String description;
    private final String template;
    private final List<String> params;

    private final List<String> solutionPath;
    private final Map<String, String> solutionValues;

    @JsonCreator
    public Template(
            @JsonProperty(ID_KEY) final UUID id,
            @JsonProperty(NAME_KEY) final String name,
            @JsonProperty(DESCRIPTION_KEY) final String description,
            @JsonProperty(TEMPLATE_KEY) final String template,
            @JsonProperty(PARAMS_KEY) final List<String> params,
            @JsonProperty(SOLUTION_PATH_KEY) final List<String> solutionPath,
            @JsonProperty(SOLUTION_VALUES_KEY) final Map<String, String> solutionValues
    ) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.template = template;
        this.solutionPath = solutionPath;
        this.solutionValues = solutionValues;
        this.params = params;
    }

    @JsonProperty(ID_KEY)
    public UUID getId() {
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

    @JsonProperty(TEMPLATE_KEY)
    public String getTemplate() {
        return template;
    }

    @JsonProperty(SOLUTION_PATH_KEY)
    public List<String> getSolutionPath() {
        return solutionPath;
    }

    @JsonProperty(SOLUTION_VALUES_KEY)
    public Map<String, String> getSolutionValues() {
        return solutionValues;
    }

    @JsonProperty(PARAMS_KEY)
    public List<String> getParams() {
        return params;
    }

}
