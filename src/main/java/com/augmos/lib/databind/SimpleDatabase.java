package com.augmos.lib.databind;

import java.util.Optional;

public interface SimpleDatabase {

    public <T> String send(String path, T obj);

    public <T> Optional<T> get(String path, Class<T> clazz);

}
