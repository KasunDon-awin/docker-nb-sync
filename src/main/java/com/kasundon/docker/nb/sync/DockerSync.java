/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kasundon.docker.nb.sync;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.InspectContainerResponse;
import com.github.dockerjava.core.DockerClientBuilder;
import com.github.dockerjava.core.DockerClientConfig;
import java.io.IOException;
import javax.swing.JOptionPane;
import org.netbeans.api.project.FileOwnerQuery;
import org.netbeans.api.project.Project;
import org.netbeans.api.project.ui.OpenProjects;
import org.openide.filesystems.FileObject;

/**
 *
 * @author kasun.don
 */
public class DockerSync {

    private static DockerClient client;

    public static void sync(String event, FileObject fo) throws IOException {
        Project project = FileOwnerQuery.getOwner(fo);
        
        if (SyncPreferences.getPropertyFilePath(project).equals(fo.getPath())) {
            return;
        }
        
        SyncPreferences prefs = SyncPreferences.getInstance(project);
        
        if (prefs.isEnabled() && OpenProjects.getDefault().isProjectOpen(project)) {
            client = new DockerSync().getInstance("tcp://" + prefs.getRemoteHost() + ":" + prefs.getRemotePort());
            InspectContainerResponse inspectContainer = client.inspectContainerCmd(prefs.getContainerName())
                    .exec();
            if (inspectContainer.getState().getRunning()) {
                client.copyArchiveToContainerCmd(inspectContainer.getId())
                        .withHostResource(fo.getPath())
                        .withRemotePath(prefs.getApplicationPath()).exec();
            } else {
                JOptionPane.showMessageDialog(null, prefs.getContainerName() + " : Looks like cotainer is not running.");
            }
        }
    }

    public DockerClient getInstance(String serverUrl) {
        if (client == null) {
            DockerClientConfig config = DockerClientConfig.createDefaultConfigBuilder()
                    .withDockerHost(serverUrl)
                    .withDockerTlsVerify(false)
                    .build();

            client = DockerClientBuilder.getInstance(config)
                    .build();
        }

        return client;
    }
}
