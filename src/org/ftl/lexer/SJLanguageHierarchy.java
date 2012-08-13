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

        
//        tokens = Arrays.asList(new SJTokenId[]{
//            
////            new SJTokenId("DEFAULT", "keyword", 0),
////                    new SJTokenId("NODIRECTIVE", "keyword", 1),
////                    new SJTokenId("FM_EXPRESSION", "keyword", 2),
////                    new SJTokenId("IN_PAREN", "keyword", 3),
////                    new SJTokenId("NAMED_PARAMETER_EXPRESSION", "keyword", 4),
////                    new SJTokenId("EXPRESSION_COMMENT", "keyword", 5),
////                    new SJTokenId("NO_SPACE_EXPRESSION", "keyword", 6),
////                    new SJTokenId("NO_PARSE", "keyword", 7),
////            
//                    new SJTokenId("EOF", "0", 0),
//                    new SJTokenId("BLANK", "1", 1),
//                    new SJTokenId("START_TAG", "2", 2),
//                    new SJTokenId("END_TAG", "3", 3),
//                    new SJTokenId("CLOSE_TAG1", "4", 4),
//                    new SJTokenId("CLOSE_TAG2", "5", 5),
//                    new SJTokenId("ATTEMPT", "6", 6),
//                    new SJTokenId("RECOVER", "7", 7),
//                    new SJTokenId("IF", "8", 8),
//                    new SJTokenId("ELSE_IF", "9", 9),
//                    new SJTokenId("LIST", "10", 10),
//                    new SJTokenId("FOREACH", "11", 11),
//                    new SJTokenId("SWITCH", "12", 12),
//                    new SJTokenId("CASE", "13", 13),
//                    new SJTokenId("ASSIGN", "14", 14),
//                    new SJTokenId("GLOBALASSIGN", "15", 15),
//                    new SJTokenId("LOCALASSIGN", "16", 16),
//                    new SJTokenId("_INCLUDE", "17", 17),
//                    new SJTokenId("IMPORT", "18", 18),
//                    new SJTokenId("FUNCTION", "19", 19),
//                    new SJTokenId("MACRO", "20", 20),
//                    new SJTokenId("TRANSFORM", "21", 21),
//                    new SJTokenId("VISIT", "22", 22),
//                    new SJTokenId("STOP", "23", 23),
//                    new SJTokenId("RETURN", "24", 24),
//                    new SJTokenId("CALL", "25", 25),
//                    new SJTokenId("SETTING", "26", 26),
//                    new SJTokenId("COMPRESS", "27", 27),
//                    new SJTokenId("COMMENT", "28", 28),
//                    new SJTokenId("TERSE_COMMENT", "29", 29),
//                    new SJTokenId("NOPARSE", "30", 30),
//                    new SJTokenId("END_IF", "31", 31),
//                    new SJTokenId("END_LIST", "32", 32),
//                    new SJTokenId("END_RECOVER", "33", 33),
//                    new SJTokenId("END_ATTEMPT", "34", 34),
//                    new SJTokenId("END_FOREACH", "35", 35),
//                    new SJTokenId("END_LOCAL", "36", 36),
//                    new SJTokenId("END_GLOBAL", "37", 37),
//                    new SJTokenId("END_ASSIGN", "38", 38),
//                    new SJTokenId("END_FUNCTION", "39", 39),
//                    new SJTokenId("END_MACRO", "40", 40),
//                    new SJTokenId("END_COMPRESS", "41", 41),
//                    new SJTokenId("END_TRANSFORM", "keyword", 42),
//                    new SJTokenId("END_SWITCH", "keyword", 43),
//                    new SJTokenId("ELSE", "keyword", 44),
//                    new SJTokenId("BREAK", "keyword", 45),
//                    new SJTokenId("SIMPLE_RETURN", "keyword", 46),
//                    new SJTokenId("HALT", "keyword", 47),
//                    new SJTokenId("FLUSH", "keyword", 48),
//                    new SJTokenId("TRIM", "keyword", 49),
//                    new SJTokenId("LTRIM", "keyword", 50),
//                    new SJTokenId("RTRIM", "keyword", 51),
//                    new SJTokenId("NOTRIM", "keyword", 52),
//                    new SJTokenId("DEFAUL", "keyword", 53),
//                    new SJTokenId("SIMPLE_NESTED", "keyword", 54),
//                    new SJTokenId("NESTED", "keyword", 55),
//                    new SJTokenId("SIMPLE_RECURSE", "keyword", 56),
//                    new SJTokenId("RECURSE", "keyword", 57),
//                    new SJTokenId("FALLBACK", "keyword", 58),
//                    new SJTokenId("ESCAPE", "if", 59),
//                    new SJTokenId("END_ESCAPE", "if", 60),
//                    new SJTokenId("NOESCAPE", "if", 61),
//                    new SJTokenId("END_NOESCAPE", "if", 62),
//                    new SJTokenId("UNIFIED_CALL", "call", 63),
//                    new SJTokenId("UNIFIED_CALL_END", "call", 64),
//                    new SJTokenId("FTL_HEADER", "keyword", 65),
//                    new SJTokenId("TRIVIAL_FTL_HEADER", "keyword", 66),
//                    new SJTokenId("UNKNOWN_DIRECTIVE", "keyword", 67),
//                    new SJTokenId("WHITESPACE", "keyword", 68),
//                    new SJTokenId("PRINTABLE_CHARS", "id", 69),
//                    new SJTokenId("FALSE_ALERT", "keyword", 70),
//                    new SJTokenId("OUTPUT_ESCAPE ", "brace", 71),
//                    new SJTokenId("NUMERICAL_ESCAPE ", "keyword", 72),
//                    new SJTokenId("73 ", "keyword", 73),
//                    new SJTokenId("74 ", "keyword", 74),
//                    new SJTokenId("75 ", "keyword", 75),
//                    new SJTokenId("76   ", "backbracket", 76),
//                    new SJTokenId("77 ", "keyword", 77),
//                    new SJTokenId("78 ", "keyword", 78),
//                    new SJTokenId("79 ", "keyword", 79),
//                    new SJTokenId("ESCAPED_CHAR", "keyword", 80),
//                    new SJTokenId("STRING_LITERAL", "literal", 81),
//                    new SJTokenId("RAW_STRING", "raw", 82),
//                    new SJTokenId("FALSE", "keyword", 83),
//                    new SJTokenId("TRUE", "keyword", 84),
//                    new SJTokenId("INTEGER", "keyword", 85),
//                    new SJTokenId("DECIMAL ", "keyword", 86),
//                    new SJTokenId("DOT ", "keyword", 87),
//                    new SJTokenId("DOT_DOT ", "keyword", 88),
//                    new SJTokenId("BUILT_IN", "keyword", 89),
//                    new SJTokenId("EXISTS ", "keyword", 90),
//                    new SJTokenId("EQUALS", "equals", 91),
//                    new SJTokenId("DOUBLE_EQUALS", "keyword", 92),
//                    new SJTokenId("NOT_EQUALS ", "keyword", 93),
//                    new SJTokenId("LESS_THAN", "keyword", 94),
//                    new SJTokenId("LESS_THAN_EQUALS ", "keyword", 95),
//                    new SJTokenId("ESCAPED_GT ", "keyword", 96),
//                    new SJTokenId("ESCAPED_GTE ", "keyword", 97),
//                    new SJTokenId("PLUS", "keyword", 98),
//                    new SJTokenId("MINUS", "keyword", 99),
//                    new SJTokenId("TIMES", "keyword", 100),
//                    new SJTokenId("DOUBLE_STAR", "keyword", 101),
//                    new SJTokenId("ELLIPSIS", "keyword", 102),
//                    new SJTokenId("DIVIDE", "keyword", 103),
//                    new SJTokenId("PERCENT", "keyword", 104),
//                    new SJTokenId("AND", "keyword", 105),
//                    new SJTokenId("OR ", "keyword", 106),
//                    new SJTokenId("EXCLAM ", "keyword", 107),
//                    new SJTokenId("COMMA", "keyword", 108),
//                    new SJTokenId("SEMICOLON", "keyword", 109),
//                    new SJTokenId("COLON", "keyword", 110),
//                    new SJTokenId("OPEN_BRACKET ", "brace", 111),
//                    new SJTokenId("CLOSE_BRACKET", "brace", 112),
//                    new SJTokenId("OPEN_PAREN", "brace", 113),
//                    new SJTokenId("CLOSE_PAREN", "brace", 114),
//                    new SJTokenId("OPEN_BRACE ", "brace", 115),
//                    new SJTokenId("CLOSE_BRACE", "brace", 116),
//                    new SJTokenId("IN ", "keyword", 117),
//                    new SJTokenId("AS ", "as", 118),
//                    new SJTokenId("USING", "keyword", 119),
//                    new SJTokenId("ID", "120", 120),
//                    new SJTokenId("LETTER", "121", 121),
//                    new SJTokenId("DIGIT", "122", 122),
//                    new SJTokenId("DIRECTIVE_END", "123", 123),
//                    new SJTokenId("EMPTY_DIRECTIVE_END", "124", 124),
//                    new SJTokenId("NATURAL_GT", "125", 125),
//                    new SJTokenId("NATURAL_GTE", "126", 126),
//                    new SJTokenId("TERMINATING_WHITESPACE", "127", 127),
//                    new SJTokenId("TERMINATING_EXCLAM", "128", 128),
//                    new SJTokenId("TERSE_COMMENT_END", "129", 129),
//                    new SJTokenId("MAYBE_END", "130", 130),
//                    new SJTokenId("KEEP_GOING ", "131", 131),
//                    new SJTokenId("LONE_LESS_THAN_OR_DASH", "132", 132),
//                    
//        });
//        idToToken = new HashMap<Integer, SJTokenId>();
//        for (SJTokenId token : tokens) {
//            idToToken.put(token.ordinal(), token);
//        }
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