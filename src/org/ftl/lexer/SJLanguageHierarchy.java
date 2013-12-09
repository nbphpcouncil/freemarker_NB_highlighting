package org.ftl.lexer;

import java.util.*;
import org.ftl.FreeMarkerUtils;
import org.netbeans.spi.lexer.LanguageHierarchy;
import org.netbeans.spi.lexer.Lexer;
import org.netbeans.spi.lexer.LexerRestartInfo;

public class SJLanguageHierarchy extends LanguageHierarchy<SJTokenId> {

    private static List<SJTokenId> tokens;
    private static Map<Integer, SJTokenId> idToToken;

    private static void init() {
        
        tokens = FreeMarkerUtils.getTokensList();
        idToToken = new HashMap<Integer, SJTokenId> ();
        for (SJTokenId token : tokens)
            idToToken.put (token.ordinal (), token);

    }

    static synchronized SJTokenId getToken(int id) {
        if (idToToken == null) {
            init();
        }
        return idToToken.get(id);
    }

    @Override
    protected synchronized Collection<SJTokenId> createTokenIds() {
        if (tokens == null) {
            init();
        }
        return tokens;
    }

    @Override
    protected synchronized Lexer<SJTokenId> createLexer(LexerRestartInfo<SJTokenId> info) {
        return new SJLexer(info);
    }

    @Override
    protected String mimeType() {
        return "text/x-ftl";
    }
}