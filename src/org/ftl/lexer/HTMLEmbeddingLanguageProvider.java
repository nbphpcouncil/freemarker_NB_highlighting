/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ftl.lexer;

import java.util.Set;
import javax.swing.JOptionPane;
import org.netbeans.api.editor.mimelookup.MimeLookup;
import org.netbeans.api.html.lexer.HTMLTokenId;
import org.netbeans.api.lexer.InputAttributes;
import org.netbeans.api.lexer.Language;
import org.netbeans.api.lexer.LanguagePath;
import org.netbeans.api.lexer.Token;
import org.netbeans.spi.lexer.LanguageEmbedding;
import org.netbeans.spi.lexer.LanguageProvider;
import org.openide.util.lookup.ServiceProvider;

@ServiceProvider(service = LanguageProvider.class)
public class HTMLEmbeddingLanguageProvider extends LanguageProvider {

    private Language embeddedLanguage;

    @Override
    public Language<?> findLanguage(String mimeType) {
        return HTMLTokenId.language();
    }

    @Override
    public LanguageEmbedding<?> findLanguageEmbedding(Token<?> token, 
        LanguagePath languagePath, InputAttributes inputAttributes) {
        initLanguage();
        if (languagePath.mimePath().equals("text/html")) {
            if (token.id().name().equals("TEXT")) {
                return LanguageEmbedding.create(embeddedLanguage, 0, 0, false);
            }
        }
        return null;
    }

    private void initLanguage() {
        embeddedLanguage = MimeLookup.getLookup("text/x-ftl").lookup(Language.class);
        if (embeddedLanguage == null) {
            throw new NullPointerException("Can't find language for embedding");
        }
    }
    
}