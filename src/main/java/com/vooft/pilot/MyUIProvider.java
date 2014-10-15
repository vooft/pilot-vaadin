package com.vooft.pilot;

import com.vaadin.server.UIClassSelectionEvent;
import com.vaadin.server.UIProvider;
import com.vaadin.ui.*;

/**
 * Created with IntelliJ IDEA.
 * User: vooft
 * Date: 24.06.13
 * Time: 22:52
 * To change this template use File | Settings | File Templates.
 */
public class MyUIProvider extends UIProvider {

    @Override
    public Class<? extends UI> getUIClass(UIClassSelectionEvent event) {
        return MyTouchKitUI.class;
    }
}
