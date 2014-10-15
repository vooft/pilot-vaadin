package com.vooft.pilot.carslist;

import com.vaadin.addon.touchkit.ui.VerticalComponentGroup;
import com.vaadin.event.LayoutEvents.LayoutClickListener;
import com.vaadin.ui.*;
import com.vooft.pilot.common.CarData;

public class CarItem extends VerticalComponentGroup {
    private CarData cd = null;
    private VerticalLayout root = new VerticalLayout();

    public CarItem(CarData cd) {
        this.cd = cd;

        root.setSpacing(true);
        //setMargin(true, false, false, false);

        Embedded e = new Embedded("", cd.getTitlePic());
        e.addStyleName("pilot-car-image-title");
        root.addComponent(e);

        Label carNameLabel = new Label(cd.getCarName());
        carNameLabel.addStyleName("pilot-car-name");
        //carNameLabel.setWidth("100%");
        //carNameLabel.setHeight("36px");
        root.addComponent(carNameLabel);

        Label priceLabel = new Label(String.format("%d р/с", cd.getCarPrice()));
        priceLabel.addStyleName("pilot-car-price");
        root.addComponent(priceLabel);

        addStyleName(cd.isFree() ? "pilot-car-free" : "pilot-car-busy");
        addComponent(root);

        root.setWidth("100%");
    }

    public void addLayoutClickListener(LayoutClickListener listener) {
        root.addLayoutClickListener(listener);
    }
}
