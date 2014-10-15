package com.vooft.pilot.carsingle;

import com.vaadin.server.Resource;
import com.vaadin.ui.*;
import com.vaadin.ui.Button.*;
import com.vooft.pilot.common.CarData;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: vooft
 * Date: 28.05.13
 * Time: 21:57
 * To change this template use File | Settings | File Templates.
 */
public class CarGalleryLayout extends CssLayout {
    private Button prevButton;
    private Button nextButton;
    private Embedded picture;
    private int index = 0;
    private CarData carData;

    public CarGalleryLayout(CarData cd) {
        this.carData = cd;
        List<Resource> images = carData.getImages();

        CssLayout root = this;
        //root.setWidth(width, UNITS_PIXELS);
        //root.setHeight(width, UNITS_PIXELS);
        //root.setMargin(true);

        HorizontalLayout hl = new HorizontalLayout();

        prevButton = new Button("<");
        prevButton.addClickListener(new ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
                prevImage();
            }
        });

        hl.addComponent(prevButton);

        picture = new Embedded();
        picture.setSource(images.get(0));
        picture.setWidth("100%");
        picture.setImmediate(true);

        hl.addComponent(picture);

        nextButton = new Button(">");
        nextButton.addClickListener(new ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
                nextImage();
            }
        });

        hl.addComponent(nextButton);
        hl.setExpandRatio(picture, 1.0f);

        hl.setComponentAlignment(prevButton, Alignment.MIDDLE_CENTER);
        hl.setComponentAlignment(nextButton, Alignment.MIDDLE_CENTER);

        hl.setWidth("100%");

        addComponent(hl);
    }

    private void prevImage() {
        List<Resource> images = carData.getImages();

        index++;
        if(index>=images.size())
            index = 0;

        picture.setSource(images.get(index));
    }

    private void nextImage() {
        List<Resource> images = carData.getImages();

        index--;
        if(index<=0)
            index = images.size()-1;

        picture.setSource(images.get(index));
    }
}
