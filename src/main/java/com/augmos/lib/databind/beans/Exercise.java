package com.augmos.lib.databind.beans;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public class Exercise {

    // jackson keys
    public static final String ID_KEY = "id";
    public static final String NAME_KEY = "name";
    public static final String TEMPLATE_KEY = "template";
    public static final String PARAMS_KEY = "params";

    // instance fields
    private final long id;
    private final String name;
    private final long template;
    private final Map<String, String> params;

    @JsonCreator
    public Exercise(
            @JsonProperty(ID_KEY) final long id,
            @JsonProperty(NAME_KEY) final String name,
            @JsonProperty(TEMPLATE_KEY) final long template,
            @JsonProperty(PARAMS_KEY) final Map<String, String> params
    ) {
        this.id = id;
        this.name = name;
        this.template = template;
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

    @JsonProperty(TEMPLATE_KEY)
    public long getTemplate() {
        return template;
    }

    @JsonProperty(PARAMS_KEY)
    public Map<String, String> getParams() {
        return params;
    }

}
