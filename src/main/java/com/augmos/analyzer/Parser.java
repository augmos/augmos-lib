package com.augmos.analyzer;

public interface Parser<T> {

    T parse(String content) throws AnalyzerException;

}
