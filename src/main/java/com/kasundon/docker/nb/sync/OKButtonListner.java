/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kasundon.docker.nb.sync;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import org.openide.util.Exceptions;

/**
 *
 * @author kasun.don
 */
public class OKButtonListner implements ActionListener{

    private ProjectPanel panel;

    public OKButtonListner(ProjectPanel panel) {
        this.panel = panel;
    }
        
    
    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            SyncPreferences.createPropertyFile(panel.getProject());
            SyncPreferences prefs = SyncPreferences.getInstance(panel.getProject());
            
            prefs.setEnable(panel.getEnable().isSelected());
            prefs.setRemoteHost(panel.getRemoteHost().getText());
            prefs.setRemotePort(panel.getRemotePort().getText());
            prefs.setContainerName(panel.getContainerName().getText());
            prefs.setApplicationPath(panel.getRemotePath().getText());
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
        }
        
    }
    
}
