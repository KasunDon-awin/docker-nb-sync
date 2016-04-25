/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kasundon.docker.nb.sync;

import javax.swing.JComponent;
import org.netbeans.api.project.Project;
import org.netbeans.spi.project.ui.support.ProjectCustomizer;
import org.openide.util.Lookup;
import org.openide.util.NbBundle;

/**
 *
 * @author kasun.don
 */
public class PanelController implements ProjectCustomizer.CompositeCategoryProvider {

    private ProjectPanel panel;
    private ProjectCustomizer.Category category;
    Project project;

    public ProjectPanel getPanel() {
        if (panel == null) {
            panel = new ProjectPanel();
        }

        load();

        return panel;
    }

    @Override
    @NbBundle.Messages({"title=docker-sync"})
    public ProjectCustomizer.Category createCategory(Lookup lkp) {
        project = lkp.lookup(Project.class);
        getPanel().setProject(project);

        category = ProjectCustomizer.Category.create(Bundle.title(), Bundle.title(), null);
        category.setOkButtonListener(new OKButtonListner(getPanel()));

        return category;
    }

    @Override
    public JComponent createComponent(ProjectCustomizer.Category ctgr, Lookup lkp) {
        return getPanel();
    }

    @ProjectCustomizer.CompositeCategoryProvider.Registration(projectType = "org-netbeans-modules-php-project")
    public static PanelController createMyDemoConfigurationTab() {
        return new PanelController();
    }

    void load() {
        SyncPreferences prefs = SyncPreferences.getInstance(project);

        panel.getEnable().setSelected(prefs.isEnabled());
        panel.enableComponents();

        panel.getRemoteHost().setText(prefs.getRemoteHost());
        panel.getRemotePort().setText(prefs.getRemotePort());
        panel.getContainerName().setText(prefs.getContainerName());
        panel.getRemotePath().setText(prefs.getApplicationPath());
    }
}
