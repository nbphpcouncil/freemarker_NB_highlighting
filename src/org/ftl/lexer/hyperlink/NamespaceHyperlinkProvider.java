/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ftl.lexer.hyperlink;

import java.io.File;
import java.util.EnumSet;
import java.util.Set;
import javax.swing.text.Document;
import org.ftl.FMParserConstants;
import org.ftl.lexer.SJTokenId;
import org.netbeans.api.editor.mimelookup.MimeRegistration;
import org.netbeans.api.lexer.Language;
import org.netbeans.api.lexer.Token;
import org.netbeans.api.lexer.TokenHierarchy;
import org.netbeans.api.lexer.TokenSequence;
import org.netbeans.lib.editor.hyperlink.spi.HyperlinkProviderExt;
import org.netbeans.lib.editor.hyperlink.spi.HyperlinkType;
import org.openide.awt.StatusDisplayer;
import org.openide.cookies.OpenCookie;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.loaders.DataObject;
import org.openide.loaders.DataObjectNotFoundException;
import org.openide.util.Exceptions;

@MimeRegistration(mimeType = "text/x-ftl", service = HyperlinkProviderExt.class)
public class NamespaceHyperlinkProvider implements HyperlinkProviderExt {

    private int startOffset, endOffset;
    private String path;

    @Override
    public Set<HyperlinkType> getSupportedHyperlinkTypes() {
        return EnumSet.of(HyperlinkType.GO_TO_DECLARATION);
    }

    @Override
    public boolean isHyperlinkPoint(Document doc, int offset, HyperlinkType type) {
        return getHyperlinkSpan(doc, offset, type) != null;
    }

    @Override
    public int[] getHyperlinkSpan(Document doc, int offset, HyperlinkType type) {
        return getIdentifierSpan(doc, offset);
    }

    @Override
    public String getTooltipText(Document doc, int offset, HyperlinkType type) {
//        String text = null;
//        try {
////            int idx = doc.getText(startOffset, endOffset - startOffset).lastIndexOf("/");
////            text = doc.getText(startOffset, endOffset - startOffset).substring(idx);
//        } catch (BadLocationException ex) {
//            Exceptions.printStackTrace(ex);
//        }

//        return "<html>Click to open <b>" + matchToken.text().toString() + "</b></html>";
        return "<html>Click to open " + path + "</html>";
    }

    @Override
    public void performClickAction(Document doc, int offset, HyperlinkType ht) {
        FileObject fo = getFileObject(doc);
        String pathToFileToOpen = fo.getParent().getPath() + path;
        File fileToOpen = FileUtil.normalizeFile(new File(pathToFileToOpen));
        if (fileToOpen.exists()) {
            try {
                FileObject foToOpen = FileUtil.toFileObject(fileToOpen);
                DataObject.find(foToOpen).getLookup().lookup(OpenCookie.class).open();
            } catch (DataObjectNotFoundException ex) {
                Exceptions.printStackTrace(ex);
            }
        } else {
            StatusDisplayer.getDefault().setStatusText(fileToOpen.getPath() + " doesn't exist!");
        }
    }

    private static FileObject getFileObject(Document doc) {
        DataObject od = (DataObject) doc.getProperty(Document.StreamDescriptionProperty);
        return od != null ? od.getPrimaryFile() : null;
    }

    private int[] getIdentifierSpan(Document doc, int offset) {
        TokenHierarchy<?> th = TokenHierarchy.get(doc);
        TokenSequence htmlTs = th.tokenSequence(Language.find("text/html"));
        if (htmlTs == null || !htmlTs.moveNext() && !htmlTs.movePrevious()) {
            return null;
        }
        TokenSequence<SJTokenId> ts = htmlTs.embedded();
        if (ts == null) {
            return null;
        }
        ts.move(offset);
        if (!ts.moveNext() && !ts.movePrevious()) {
            return null;
        }
        Token t = ts.token();
        if (t.id().name().equals("ID")) {
            Token idToken = ts.token();
            startOffset = ts.offset() - 1;
            endOffset = ts.offset() + t.length();
            //Check that the previous token was a call statement,
            //otherwise we don't want our id hyperlinked:
            ts.movePrevious();
            Token prevToken = ts.token();
            if (prevToken.id().name().equals("UNIFIED_CALL")) {
                idToken = idToken;
                if (getIdBelongingToMatchingAs(ts, idToken, startOffset, endOffset)) {
                    return new int[]{startOffset, endOffset};
                }
            } else {
                return null;
            }
        }
        return null;
    }

    private boolean getIdBelongingToMatchingAs(TokenSequence ts, Token t, int start, int end) {
        for (int i = 0; i < ts.tokenCount(); i++) {
            ts.moveIndex(i);
            ts.moveNext();
            if (ts.token() != null && ts.token().id().ordinal() == FMParserConstants.AS) {
                ts.moveNext();//this is for the space between visible tokens
                ts.moveNext();//this gets us to the value of the "as" token
                Token asValueToken = ts.token();
                if (asValueToken.text().toString().equals(t.text().toString())) {
                    ts.movePrevious();
                    ts.movePrevious();
                    ts.movePrevious();
                    ts.movePrevious();
                    path = ts.token().text().toString().replaceAll("\"", "");
//                    JOptionPane.showMessageDi\alog(null, ts.token().text());
                    return true;
                }
            }
        }
        return false;
    }
}
