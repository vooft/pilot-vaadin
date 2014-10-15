package com.vooft.pilot;


import com.vaadin.addon.touchkit.ui.NavigationManager;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Widgetset;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.*;

/**
 * The UI's "main" class
 */
@Theme("pilot")
@Widgetset("com.vooft.pilot.gwt.AppWidgetSet")
public class MyTouchKitUI extends UI {
    NavigationManager navigationManager;

    @Override
    protected void init(VaadinRequest request) {
        navigationManager = new NavigationManager();
        //navigator = new Navigator(this, navigationManager);

        Page.getCurrent().setUriFragment("");

        setContent(navigationManager);
        navigationManager.navigateTo(new MenuHierarchyView(navigationManager));
    }

    public NavigationManager getNavigationManager() {
        return navigationManager;
    }
}