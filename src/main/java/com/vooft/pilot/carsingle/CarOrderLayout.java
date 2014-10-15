package com.vooft.pilot.carsingle;

import com.vaadin.addon.touchkit.ui.VerticalComponentGroup;
import com.vaadin.shared.ui.datefield.Resolution;
import com.vaadin.ui.*;
import com.vooft.pilot.common.CarData;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: vooft
 * Date: 24.05.13
 * Time: 18:24
 * To change this template use File | Settings | File Templates.
 */
public class CarOrderLayout extends CssLayout {
    private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    private CarData carData;
    private final TextField nameField;
    private final TextField phoneField;
    private final PopupDateField fromDateField;
    private final PopupDateField toDateField;

    public CarOrderLayout(CarData carData) {
        this.carData = carData;

        CssLayout root = this;
        root.setWidth("100%");
        //root.setMargin(true);

        {
            VerticalComponentGroup vl = new VerticalComponentGroup();
            vl.addStyleName("pilot-car-single-order-layout");

            nameField = new TextField("Имя");
            nameField.addStyleName("pilot-car-single-order-name");
            nameField.setRequired(true);
            nameField.setWidth("100%");
            vl.addComponent(nameField);

            phoneField = new TextField("Телефон");
            phoneField.addStyleName("pilot-car-single-order-phone");
            phoneField.setRequired(true);
            phoneField.setWidth("100%");
            vl.addComponent(phoneField);

            fromDateField = new PopupDateField("С");
            fromDateField.addStyleName("pilot-car-single-order-from");
            fromDateField.setRequired(true);
            fromDateField.setResolution(Resolution.DAY);
            fromDateField.setValue(new Date());
            fromDateField.setWidth("100%");
            vl.addComponent(fromDateField);

            toDateField = new PopupDateField("По");
            toDateField.addStyleName("pilot-car-single-order-to");
            toDateField.setRequired(true);
            toDateField.setResolution(Resolution.DAY);
            toDateField.setValue(new Date());
            toDateField.setWidth("100%");
            vl.addComponent(toDateField);

            root.addComponent(vl);
        }
    }

    public Map<String, String> createData() {
        // http://pilot-auto.net:1613/mobile/?proto=2&action=make_order&fromd=2013-01-01&car_name=abcd&phone=1&tod=2013-01-01&tariff=abcd&client=fsa

        Map<String, String> result = new HashMap<String, String>();

        result.put("car_name", carData.getCarName());
        result.put("cars_id", String.valueOf(carData.getId()));
        result.put("fromd", SIMPLE_DATE_FORMAT.format((Date) fromDateField.getValue()));
        result.put("tod", SIMPLE_DATE_FORMAT.format((Date) fromDateField.getValue()));
        result.put("client", (String) nameField.getValue());
        result.put("phone", (String) phoneField.getValue());
        result.put("tariff", "Тариф");

        return result;
    }
}
