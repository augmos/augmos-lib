package com.augmos.analyzer.mathml.parser;

import com.augmos.analyzer.AnalyzerException;
import com.augmos.analyzer.Parser;

import java.util.List;

public final class MathMLParser implements Parser<SyntaxTree> {

    /**
     * Turns a String containing a MathML representation of mathematical expressions into a List of SyntaxTrees
     * No semantic checks, only syntax (2+2=5 is ok)
     * Chained equations are split to make those binary (a=b=c becomes a=b and b=c)
     *
     * Example 2+2=5, (2+2)*2=8
     * MathML input: "<math xmlns='http://www.w3.org/1998/Math/MathML'> <mtable columnalign='left'> <mtr> <mtd> <mn> 2 </mn> <mo> + </mo> <mn> 2 </mn> <mo> = </mo> <mn> 5 </mn> </mtd> </mtr> <mtr> <mtd> <mfenced> <mrow> <mn> 2 </mn> <mo> + </mo> <mn> 2 </mn> </mrow> </mfenced> <mo> &#x00B7; <!-- middle dot --> </mo> <mn> 2 </mn> <mo> = </mo> <mn> 8 </mn> </mtd> </mtr> </mtable> </math> "
     * SyntaxTrees:
     *     =            =
     *    / \          / \
     *   +   5        *   8
     *  / \          / \
     * 2   2        +   2
     *             / \
     *            2   2
     *
     * For more information on MathML: https://www.w3.org/TR/2014/REC-MathML3-20140410/
     * For an example of InteractiveInk MathML output: https://webdemo.myscript.com/views/math/index.html
     *
     * @param content a String containing a MathML representation of mathematical expression
     * @return sorted List of resulting syntax trees
     * @throws AnalyzerException if the input does not conform to xml
     */

    @Override
    public List<SyntaxTree> parse(final String content) throws AnalyzerException {
        //TODO @Ripper

        return null;
    }
}
