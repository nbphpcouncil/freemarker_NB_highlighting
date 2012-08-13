/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ftl.lexer.hyperlink;

import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import javax.swing.JOptionPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import org.ftl.lexer.SJLanguage;
import org.ftl.lexer.SJLanguageHierarchy;
import org.ftl.lexer.SJTokenId;
import org.netbeans.api.editor.mimelookup.MimeRegistration;
import org.netbeans.api.lexer.Language;
import org.netbeans.api.lexer.Token;
import org.netbeans.api.lexer.TokenHierarchy;
import org.netbeans.api.lexer.TokenSequence;
import org.netbeans.lib.editor.hyperlink.spi.HyperlinkProviderExt;
import org.netbeans.lib.editor.hyperlink.spi.HyperlinkType;
import org.openide.awt.StatusDisplayer;
import org.openide.cookies.LineCookie;
import org.openide.filesystems.FileObject;
import org.openide.loaders.DataObject;
import org.openide.loaders.DataObjectNotFoundException;
import org.openide.text.Line;
import org.openide.util.Exceptions;

@MimeRegistration(mimeType = "text/x-html", service = HyperlinkProviderExt.class)
public class AssignHyperlinkProvider implements HyperlinkProviderExt {

    private int literalStartOffset, literalEndOffset;
    private int lineNumber;

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
        String text = null;
        try {
            text = doc.getText(literalStartOffset, literalEndOffset - literalStartOffset);
        } catch (BadLocationException ex) {
            Exceptions.printStackTrace(ex);
        }
        return "Click to jump to declaration";
    }

    @Override
    public void performClickAction(Document doc, int offset, HyperlinkType ht) {
        try {
            FileObject fo = getFileObject(doc);
            LineCookie lc = DataObject.find(fo).getLookup().lookup(LineCookie.class);
            Line line = lc.getLineSet().getOriginal(lineNumber);
            line.show(Line.ShowOpenType.OPEN, Line.ShowVisibilityType.FRONT);
        } catch (DataObjectNotFoundException ex) {
            Exceptions.printStackTrace(ex);
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
        
//        JOptionPane.showMessageDialog(null, t.id().name());

        if (t.id().name().equals("ID") || t.id().name().equals("PRINTABLE_CHARS")) {
                                                

            int start = ts.offset();
            int end = ts.offset() + t.length();
            getCurrentLineNumber(doc, t);
            if (getDeclaration(ts, t, start, end)) {
                ts.move(offset);
                ts.movePrevious();
                if (!ts.token().id().name().equals("ASSIGN")) {
                    return new int[]{literalStartOffset, literalEndOffset};
                }
            }
        }
        return null;
    }

    private void getCurrentLineNumber(Document doc, Token t) {
        //Get the current line number:
        FileObject fo = getFileObject(doc);
        LineCookie lc;
        try {
            lc = DataObject.find(fo).getLookup().lookup(LineCookie.class);
            List<? extends Line> lines = lc.getLineSet().getLines();
            for (Line line : lines) {
                if (line.getText().contains("<#assign") && line.getText().contains(t.text().toString())) {
                    lineNumber = line.getLineNumber();
                }
            }
        } catch (DataObjectNotFoundException ex) {
            Exceptions.printStackTrace(ex);
        }
    }

    private boolean getDeclaration(TokenSequence ts, Token t, int start, int end) {
        for (int i = 0; i < ts.tokenCount(); i++) {
            ts.moveIndex(i);
            ts.moveNext();
            if (ts.token() != null && ts.token().id().name().equals("ASSIGN")) {
                //Get the next token, which is the matching ID:
                ts.moveNext();
                Token assignLiteral = ts.token();
                if (assignLiteral.text().toString().equals(t.text().toString())) {
                    literalStartOffset = start;
                    literalEndOffset = end;
                    StatusDisplayer.getDefault().setStatusText(assignLiteral.text().toString() + "/" + literalStartOffset + "/" + literalEndOffset);
                    return true;
                }
            }
        }
        return false;
    }
    
}
