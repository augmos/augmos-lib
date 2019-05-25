package com.augmos.analyzer;

public interface AnalyzerChannel {

    void set(String content);

    void update(String newSubstring, int pos, int replacedLength);

    void terminate();

    String getContent();

    boolean blockUntilProcessed(int timeoutMs);

    AnalysisResult getResult();

}
