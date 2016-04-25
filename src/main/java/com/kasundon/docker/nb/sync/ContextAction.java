/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kasundon.docker.nb.sync;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import org.netbeans.api.project.Project;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.filesystems.FileObject;
import org.openide.loaders.DataObject;
import org.openide.util.Exceptions;
import org.openide.util.Lookup;
import org.openide.util.NbBundle;
import org.openide.util.Utilities;

/**
 *
 * @author kasun.don
 */
@ActionID(category = "File", id = "com.kasundon.docker.nb.sync.ContextAction")
@ActionRegistration(displayName = "#DockerSyncOption")
@ActionReferences({
    @ActionReference(path = "Loaders/folder/any/Actions", position = 750),
    @ActionReference(path = "Loaders/image/png-gif-jpeg-bmp/Actions", position = 750),
    @ActionReference(path = "Loaders/content/unknown/Actions", position = 750),
    @ActionReference(path = "Loaders/text/html/Actions", position = 750),
    @ActionReference(path = "Loaders/text/x-css/Actions", position = 750),
    @ActionReference(path = "Loaders/text/sh/Actions", position = 750),
    @ActionReference(path = "Loaders/text/x-jsp/Actions", position = 750),
    @ActionReference(path = "Loaders/text/x-java/Actions", position = 750),
    @ActionReference(path = "Loaders/text/x-jsp-servlet/Actions", position = 750),
    @ActionReference(path = "Loaders/text/x-tag/Actions", position = 750),
    @ActionReference(path = "Loaders/text/x-sql/Actions", position = 750),
    @ActionReference(path = "Loaders/text/x-php5/Actions", position = 750),
    @ActionReference(path = "Loaders/text/x-properties/Actions", position = 750),
    @ActionReference(path = "Loaders/text/XML/Actions", position = 750),
    @ActionReference(path = "Loaders/application/x-class-file/Actions", position = 750),
    @ActionReference(path = "Loaders/application/x-java-archive/Actions", position = 750),
    @ActionReference(path = "Loaders/application/xml/Actions", position = 750),
    @ActionReference(path = "Loaders/application/xml-dtd/Actions", position = 750),
    @ActionReference(path = "Loaders/application/xml-external-parsed-entity/Actions", position = 750),
    @ActionReference(path = "Loaders/text/xml-xml-xsl/Actions", position = 750),
    @ActionReference(path = "Loaders/application/x-xml/Actions", position = 750),
    @ActionReference(path = "Loaders/text/xml-external-parsed-entity/Actions", position = 750),
    @ActionReference(path = "Loaders/text/*/Actions", position = 750),
    @ActionReference(path = "Loaders/application/xhtml+xml", position = 750),
    @ActionReference(path = "Loaders/application/pdf/Actions", position = 750),
    @ActionReference(path = "Loaders/text/javascript/Actions", position = 750)
})
@NbBundle.Messages("DockerSyncOption=Upload to docker container")
public class ContextAction implements ActionListener {
    
    DataObject context;

    public ContextAction(DataObject context) {
        this.context = context;
    }
        
    
    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            Lookup lookup = Utilities.actionsGlobalContext();
            Project project = lookup.lookup(Project.class);
            FileObject projectDir = project.getProjectDirectory();
            DockerSync.sync("changed", projectDir);
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
        }
    }
    
}
