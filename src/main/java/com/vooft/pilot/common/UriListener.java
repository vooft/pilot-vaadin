package com.vooft.pilot.common;

import com.vaadin.addon.touchkit.ui.NavigationManager;
import com.vaadin.server.Page;
import com.vaadin.server.Page.UriFragmentChangedEvent;
import com.vaadin.server.Page.UriFragmentChangedListener;
import com.vaadin.ui.*;
import com.vooft.pilot.MyTouchKitUI;

/**
 * Created with IntelliJ IDEA.
 * User: vooft
 * Date: 24.06.13
 * Time: 22:42
 * To change this template use File | Settings | File Templates.
 */
public class UriListener implements UriFragmentChangedListener {
    private String prevFragment;

    public UriListener(String uri) {
        prevFragment = Page.getCurrent().getUriFragment()==null ? "" : Page.getCurrent().getUriFragment();
        Page.getCurrent().setUriFragment(uri);
    }

    @Override
    public void uriFragmentChanged(UriFragmentChangedEvent event) {
        if(event.getUriFragment().equals(prevFragment)) {
            Page.getCurrent().removeUriFragmentChangedListener(this);

            MyTouchKitUI ui = (MyTouchKitUI) UI.getCurrent();
            NavigationManager nav = ui.getNavigationManager();
            nav.navigateBack();
        }
    }
}
