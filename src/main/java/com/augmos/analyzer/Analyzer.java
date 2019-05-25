package com.augmos.analyzer;

public interface Analyzer {

    AnalyzerChannel openChannel();

    void terminateChannel(AnalyzerChannel channel);

}
