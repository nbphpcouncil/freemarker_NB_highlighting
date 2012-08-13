/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ftl.lexer;

import javax.swing.JOptionPane;
import org.ftl.FMParserTokenManager;
import org.ftl.SimpleCharStream;
import org.ftl.Token;
import org.netbeans.spi.lexer.Lexer;
import org.netbeans.spi.lexer.LexerRestartInfo;

class SJLexer implements Lexer<SJTokenId> {

    private LexerRestartInfo<SJTokenId> info;
    private FMParserTokenManager javaParserTokenManager;

    SJLexer(LexerRestartInfo<SJTokenId> info) {
        this.info = info;
        SimpleCharStream stream = new SimpleCharStream(info.input());
        javaParserTokenManager = new FMParserTokenManager(stream);
    }

    @Override
    public org.netbeans.api.lexer.Token<SJTokenId> nextToken() {
        Token token = javaParserTokenManager.getNextToken();
        System.out.println("TOKEN:              "+token.toString() + "/" + token.kind);
        if (info.input().readLength() < 1) {
            return null;
        }
        return info.tokenFactory().createToken(SJLanguageHierarchy.getToken(token.kind));
    }

    @Override   
    public Object state() {
        return null;
    }

    @Override
    public void release() {
    }

}