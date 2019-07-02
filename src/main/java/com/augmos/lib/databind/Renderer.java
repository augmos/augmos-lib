package com.augmos.lib.databind;

import com.augmos.lib.databind.beans.Exercise;
import com.augmos.lib.databind.beans.RenderedExercise;
import com.augmos.lib.databind.beans.Template;

import java.util.Optional;

public interface Renderer {

    public Optional<RenderedExercise> render(Template template, Exercise exercise);

}
