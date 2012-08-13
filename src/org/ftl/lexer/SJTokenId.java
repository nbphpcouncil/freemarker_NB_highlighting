/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ftl.lexer;

import org.netbeans.api.lexer.Language;
import org.netbeans.api.lexer.TokenId;

public class SJTokenId implements TokenId {

    private final String name;
    private final String primaryCategory;
    private final int id;

    public SJTokenId(
            String name,
            String primaryCategory,
            int id) {
        this.name = name;
        this.primaryCategory = primaryCategory;
        this.id = id;
    }

    public static Language<SJTokenId> getLanguage() {
        return new SJLanguageHierarchy().language();
    }

    @Override
    public String primaryCategory() {
        return primaryCategory;
    }

    @Override
    public int ordinal() {
        return id;
    }

    @Override
    public String name() {
        return name;
    }
}