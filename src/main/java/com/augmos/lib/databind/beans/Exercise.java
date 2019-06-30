package com.augmos.lib.databind.beans;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;
import java.util.UUID;

public class Exercise {

    public static final String ID_KEY = "id";
    public static final String NAME_KEY = "name";
    public static final String TEMPLATE_KEY = "template";
    public static final String PARAMS_KEY = "params";

    private final UUID id;
    private final String name;
    private final Template template;
    private final Map<String, String> params;

    @JsonCreator
    public Exercise(
            @JsonProperty(ID_KEY) final UUID id,
            @JsonProperty(NAME_KEY) final String name,
            @JsonProperty(TEMPLATE_KEY) final Template template,
            @JsonProperty(PARAMS_KEY) final Map<String, String> params
    ) {
        this.id = id;
        this.name = name;
        this.template = template;
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

    @JsonProperty(TEMPLATE_KEY)
    public Template getTemplate() {
        return template;
    }

    @JsonProperty(PARAMS_KEY)
    public Map<String, String> getParams() {
        return params;
    }

    public String render() {
        String render = template.getTemplate();

        for (final String p : template.getParams()) {
            if (params.containsKey(p))
                render = render.replaceAll(p, params.get(p));
        }

        return render;
    }
}
