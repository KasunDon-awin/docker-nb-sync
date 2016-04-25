/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kasundon.docker.nb.sync;

import java.io.IOException;
import org.netbeans.api.project.FileOwnerQuery;
import org.netbeans.api.project.ui.OpenProjects;
import org.openide.filesystems.FileAttributeEvent;
import org.openide.filesystems.FileChangeListener;
import org.openide.filesystems.FileEvent;
import org.openide.filesystems.FileRenameEvent;
import org.openide.filesystems.FileUtil;
import org.openide.modules.ModuleInstall;
import org.openide.util.Exceptions;
import org.openide.windows.WindowManager;

public class Installer extends ModuleInstall {

    @Override
    public void restored() {
        WindowManager.getDefault().invokeWhenUIReady(new Runnable() {
            @Override
            public void run() {

                FileUtil.addFileChangeListener(new FileChangeListener() {

                    @Override
                    public void fileFolderCreated(FileEvent fe) {
                        if (OpenProjects.getDefault().isProjectOpen(FileOwnerQuery.getOwner(fe.getFile()))) {
                            System.out.println("created" + fe.getFile().getPath());
                        }

                        System.out.println("created" + fe.getFile().getPath());
                    }

                    @Override
                    public void fileDataCreated(FileEvent fe) {
                        System.out.println("data" + fe.getFile().getPath());
                    }

                    @Override
                    public void fileChanged(FileEvent fe) {
                        try {
                            DockerSync.sync("changed", fe.getFile());
                        } catch (IOException ex) {
                            Exceptions.printStackTrace(ex);
                        }
                    }

                    @Override
                    public void fileDeleted(FileEvent fe) {
                        System.out.println("deleted" + fe.getFile().getPath());
                    }

                    @Override
                    public void fileRenamed(FileRenameEvent fre) {
                        System.out.println("renamed" + fre.getFile().getPath());
                    }

                    @Override
                    public void fileAttributeChanged(FileAttributeEvent fae) {
                        System.out.println("attr changed" + fae.getFile().getPath());
                    }
                });
            }
        });
    }

}
