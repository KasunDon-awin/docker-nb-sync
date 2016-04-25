/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kasundon.docker.nb.sync;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;
import org.netbeans.api.project.Project;
import org.openide.filesystems.FileObject;
import org.openide.util.Exceptions;

/**
 *
 * @author kasun.don
 */
public class SyncPreferences {

    protected static final String REMOTE_HOST = "remotehost";
    protected static final String REMOTE_PORT = "remoteport";
    protected static final String CONTAINER_NAME = "containername";
    protected static final String APPLICATION_PATH = "applicationpath";

    public static final String PRJ_DIR = "nbproject"; //NOI18N
    public static final String PROPERTIES = "dockersync.properties"; //NOI18N

    protected static final String ENABLE = "enable";

    private Project project;

    public SyncPreferences(Project project) {
        this.project = project;
    }

    public boolean isEnabled() {
        return Boolean.parseBoolean(getProperty(ENABLE));
    }

    public void setEnable(Boolean syncMode) {
        setProperty(ENABLE, syncMode.toString());
    }

    public String getRemoteHost() {
        return getProperty(REMOTE_HOST);
    }

    public void setRemoteHost(String remoteHost) {
        setProperty(REMOTE_HOST, remoteHost);
    }

    public String getRemotePort() {
        return getProperty(REMOTE_PORT);
    }

    public void setRemotePort(String remotePort) {
        setProperty(REMOTE_PORT, remotePort);
    }

    public String getContainerName() {
        return getProperty(CONTAINER_NAME);
    }

    public void setContainerName(String containerName) {
        setProperty(CONTAINER_NAME, containerName);
    }

    public String getApplicationPath() {
        return getProperty(APPLICATION_PATH);
    }

    public void setApplicationPath(String applicationPath) {
        setProperty(APPLICATION_PATH, applicationPath);
    }

    public String getProperty(String propName) {
        String val = null;

        if (hasProperties(project)) {
            try {
                Properties props = new Properties();
                InputStream is = getPropertyFileInputStream();
                props.load(is);
                val = props.getProperty(propName);
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return val;
    }

    public void setProperty(String propName, String value) {
        if (hasProperties(project)) {
            Properties props = new Properties();

            OutputStream os;

            try {
                InputStream is = getPropertyFileInputStream();
                props.load(is);
                is.close();
                props.setProperty(propName, value);
                os = getPropertyFileOutputStream();
                props.store(os, value);

                os.flush();
                os.close();
            } catch (IOException ex) {
                Exceptions.printStackTrace(ex);
            }

        }
    }

    public FileObject getPropertyFile() {
        return project.getProjectDirectory().getFileObject(PRJ_DIR).getFileObject(PROPERTIES);

    }

    public InputStream getPropertyFileInputStream() throws FileNotFoundException {
        return getPropertyFile().getInputStream();
    }

    public OutputStream getPropertyFileOutputStream() throws IOException {
        return getPropertyFile().getOutputStream();
    }

    public static boolean hasProperties(Project project) {
        FileObject buildGWT = project.getProjectDirectory().getFileObject(PRJ_DIR
                + "/" + PROPERTIES);

        return buildGWT != null;
    }

    public static void createPropertyFile(Project project) throws IOException {
        File file = new File(getPropertyFilePath(project));

        if (!file.exists()) {
            file.createNewFile();
        }
    }

    public static String getPropertyFilePath(Project project) {
        return project.getProjectDirectory().getPath() + "/" + PRJ_DIR + "/" + PROPERTIES;
    }

    public static SyncPreferences getInstance(Project project) {
        return new SyncPreferences(project);
    }
}
