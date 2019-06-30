package com.augmos.lib.databind.beans;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public final class RenderedExecrise extends Exercise {

    private final String renderedInput;
    private final List<String> renderedOutput;
    private final List<String> renderedSolution;

    private RenderedExecrise(
            UUID id,
            String name,
            Template template,
            Map<String, String> params
    ) {
        super(id, name, template, params);
    }
}
