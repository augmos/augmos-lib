package com.augmos.analyzer;

import java.util.List;

public interface Parser<T> {

    List<T> parse(String content) throws AnalyzerException;

}
