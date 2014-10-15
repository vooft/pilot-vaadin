package com.vooft.pilot.carsingle;

import com.vaadin.addon.touchkit.ui.Popover;
import com.vaadin.addon.touchkit.ui.VerticalComponentGroup;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.*;
import com.vaadin.ui.Button.*;
import com.vooft.pilot.common.CarData;
import com.vooft.pilot.common.DataHelper;

/**
* Created with IntelliJ IDEA.
* User: vooft
* Date: 29.05.13
* Time: 1:14
* To change this template use File | Settings | File Templates.
*/
class CarView extends CssLayout {
    public static final String TARIFF_FORMAT = "%d руб/сут";
    private SingleCarView carView;
    private final Label tariffPrice;
    private CarData carData;

    public CarView(SingleCarView carView, final CarData carData) {
        this.carView = carView;
        this.carData = carData;

        //setMargin(true);

        VerticalComponentGroup dataGroup = new VerticalComponentGroup();
        dataGroup.addStyleName("pilot-car-single-datalayout");

        {
            Label l = new Label("");
            l.addStyleName("pilot-car-single-carname");
            l.setWidth(100.0f, Unit.PERCENTAGE);
            l.setCaption(carData.getCarName());
            dataGroup.addComponent(l);
        }

        {
            Embedded e = new Embedded(null, carData.getBigTitlePic());
            e.addStyleName("pilot-car-single-image");
            e.setWidth("100%");
            dataGroup.addComponent(e);
        }

        {
            Label l = new Label(String.format("%d руб/сут", carData.getCarPrice()));
            l.addStyleName("pilot-car-single-price");
            l.setCaption("Базовая цена:");
            dataGroup.addComponent(l);
        }

        {
            tariffPrice = new Label(String.format(TARIFF_FORMAT, carData.getCarPrice()));
            tariffPrice.addStyleName("pilot-car-single-price-tariff");
            tariffPrice.setCaption("Цена с учетом тарифа:");
            dataGroup.addComponent(tariffPrice);
        }

        {
            final IndexedContainer container = DataHelper.createTariffs();

            ListSelect tariffSelect = new ListSelect("Тариф", container);
            tariffSelect.setNullSelectionAllowed(false);
            tariffSelect.setImmediate(true);

            tariffSelect.setValue(tariffSelect.getItemIds().iterator().next());

            tariffSelect.addValueChangeListener(new Property.ValueChangeListener() {
                @Override
                public void valueChange(ValueChangeEvent event) {
                    Item i = container.getItem(event.getProperty().getValue());
                    Integer discount = (Integer) i.getItemProperty(DataHelper.SINGLE_VALUE_PROPERTY).getValue();

                    double price = carData.getCarPrice();
                    price = price*(100-discount)/100;
                    tariffPrice.setValue(String.format(TARIFF_FORMAT, Math.round(price)));
                }
            });

            tariffSelect.addStyleName("pilot-car-single-tariff");

            dataGroup.addComponent(tariffSelect);
        }

        {
            HorizontalLayout hl = new HorizontalLayout();
            hl.setMargin(false);

            final Button galleryButton = new Button();
            galleryButton.setIcon(new ThemeResource("graphics/pic.png"));
            galleryButton.addClickListener(new ClickListener() {
                @Override
                public void buttonClick(ClickEvent event) {
                    Popover popover = new Popover();

                    if (carData.getImages() == null)
                        carData.loadImages();

                    popover.setContent(new CarGalleryLayout(carData));
                    popover.setWidth("100%");
                    popover.setClosable(true);
                    popover.showRelativeTo(galleryButton);
                }
            });

            hl.addComponent(galleryButton);

            final Button specButton = new Button();
            specButton.setIcon(new ThemeResource("graphics/spec.png"));
            specButton.addClickListener(new ClickListener() {
                @Override
                public void buttonClick(ClickEvent event) {
                    Popover popover = new Popover();

                    popover.setContent(new TechSpecLayout(carData));
                    popover.setClosable(true);
                    popover.showRelativeTo(specButton);
                    //popover.setHeight("200px");
                    popover.setWidth("50%");
                }
            });
            hl.addComponent(specButton);

            carView.setRightComponent(hl);
        }

        addComponent(dataGroup);
    }

    @Override
    public void attach() {
        super.attach();
    }

    private class TechSpecLayout extends VerticalComponentGroup {
        public TechSpecLayout(CarData cd) {
            setCaption("Тех. характеристики");

            {
                Label l = new Label(String.format("%s", cd.getEngine()));
                l.setCaption("Двигатель");
                l.setWidth("100%");
                addComponent(l);
            }

            {
                Label l = new Label(String.format("%s", cd.getDrive()));
                l.setCaption("Привод");
                l.setWidth("100%");
                addComponent(l);
            }

            {
                Label l = new Label(String.format("%s", cd.getBody()));
                l.setCaption("Кузов");
                l.setWidth("100%");
                addComponent(l);
            }

            {
                Label l = new Label(String.format("%s", cd.getCabin()));
                l.setCaption("Салон");
                l.setWidth("100%");
                addComponent(l);
            }

            //setSizeUndefined();
            setWidth("100%");
        }
    }
}
