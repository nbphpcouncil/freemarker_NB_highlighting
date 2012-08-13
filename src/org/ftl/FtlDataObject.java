/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ftl;

import java.io.IOException;
import org.netbeans.api.templates.TemplateRegistration;
import org.openide.WizardDescriptor;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.MIMEResolver;
import org.openide.loaders.DataObject;
import org.openide.loaders.DataObjectExistsException;
import org.openide.loaders.MultiDataObject;
import org.openide.loaders.MultiFileLoader;
import org.openide.text.DataEditorSupport;
import org.openide.util.NbBundle.Messages;

@Messages({
    "LBL_Ftl_LOADER=Files of Ftl"
})
@MIMEResolver.ExtensionRegistration(
    displayName = "#LBL_Ftl_LOADER",
mimeType = "text/x-ftl",
extension = {"ftl"})
@DataObject.Registration(
    mimeType = "text/x-ftl",
iconBase = "org/ftl/dot.png",
displayName = "#LBL_Ftl_LOADER",
position = 300)
@ActionReferences({
    @ActionReference(
        path = "Loaders/text/x-ftl/Actions",
    id =
    @ActionID(category = "System", id = "org.openide.actions.OpenAction"),
    position = 100,
    separatorAfter = 200),
    @ActionReference(
        path = "Loaders/text/x-ftl/Actions",
    id =
    @ActionID(category = "Edit", id = "org.openide.actions.CutAction"),
    position = 300),
    @ActionReference(
        path = "Loaders/text/x-ftl/Actions",
    id =
    @ActionID(category = "Edit", id = "org.openide.actions.CopyAction"),
    position = 400,
    separatorAfter = 500),
    @ActionReference(
        path = "Loaders/text/x-ftl/Actions",
    id =
    @ActionID(category = "Edit", id = "org.openide.actions.DeleteAction"),
    position = 600),
    @ActionReference(
        path = "Loaders/text/x-ftl/Actions",
    id =
    @ActionID(category = "System", id = "org.openide.actions.RenameAction"),
    position = 700,
    separatorAfter = 800),
    @ActionReference(
        path = "Loaders/text/x-ftl/Actions",
    id =
    @ActionID(category = "System", id = "org.openide.actions.SaveAsTemplateAction"),
    position = 900,
    separatorAfter = 1000),
    @ActionReference(
        path = "Loaders/text/x-ftl/Actions",
    id =
    @ActionID(category = "System", id = "org.openide.actions.FileSystemAction"),
    position = 1100,
    separatorAfter = 1200),
    @ActionReference(
        path = "Loaders/text/x-ftl/Actions",
    id =
    @ActionID(category = "System", id = "org.openide.actions.ToolsAction"),
    position = 1300),
    @ActionReference(
        path = "Loaders/text/x-ftl/Actions",
    id =
    @ActionID(category = "System", id = "org.openide.actions.PropertiesAction"),
    position = 1400)
})
public class FtlDataObject extends MultiDataObject {

    public FtlDataObject(FileObject pf, MultiFileLoader loader) throws DataObjectExistsException, IOException {
        super(pf, loader);
        registerEditor("text/x-ftl", false);
        getLookup().lookup(DataEditorSupport.class).setMIMEType("text/html");
    }

    @Override
    protected int associateLookup() {
        return 1;
    }

    @TemplateRegistration(folder = "Other", content = "FtlTemplate.ftl")
    public static WizardDescriptor.InstantiatingIterator templateIterator() {
        return null;
    }
    
}
