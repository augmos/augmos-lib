package com.augmos.lib.databind;

public interface Exchanger {

    public <T> String send(T obj);

    public <T> T receive(String path);

}
