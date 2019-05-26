package com.augmos.analyzer.mathml.parser;

//TODO @Ripper
public class SyntaxTree {

    private final String token;
    private final SyntaxTree left;
    private final SyntaxTree right;

    public SyntaxTree(
            final String token,
            final SyntaxTree left,
            final SyntaxTree right
    ) {
        this.token = token;
        this.left = left;
        this.right = right;
    }

    public String getToken() {
        return token;
    }

    public SyntaxTree getLeft() {
        return left;
    }

    public SyntaxTree getRight() {
        return right;
    }

}
