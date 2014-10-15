package com.vooft.pilot.common;

import com.vaadin.data.util.IndexedContainer;

/**
 * Created with IntelliJ IDEA.
 * User: vooft
 * Date: 29.05.13
 * Time: 0:49
 * To change this template use File | Settings | File Templates.
 */
public class DataHelper {
    public static final Object SINGLE_VALUE_PROPERTY = "VALUE";

    public static IndexedContainer createTariffs() {
        IndexedContainer result = new IndexedContainer();
        //result.addContainerProperty(NAME_PROPERTY, String.class, "");
        result.addContainerProperty(SINGLE_VALUE_PROPERTY, Integer.class, 0);

        result.addItem("1-2 дня").getItemProperty(SINGLE_VALUE_PROPERTY).setValue(0);
        result.addItem("3-14 дней, -10%").getItemProperty(SINGLE_VALUE_PROPERTY).setValue(10);
        result.addItem("15-30 дней, -15%").getItemProperty(SINGLE_VALUE_PROPERTY).setValue(15);
        result.addItem("Свыше 30 дней, -25%").getItemProperty(SINGLE_VALUE_PROPERTY).setValue(25);
        result.addItem("Выходного дня, -12%").getItemProperty(SINGLE_VALUE_PROPERTY).setValue(12);
        result.addItem("Рабочая неделя, -13%").getItemProperty(SINGLE_VALUE_PROPERTY).setValue(13);

        return result;
    }

    public static IndexedContainer createFirstPaymentContainer() {
        IndexedContainer result = new IndexedContainer();
        result.addContainerProperty(SINGLE_VALUE_PROPERTY, Integer.class, 0);

        result.addItem("0%").getItemProperty(SINGLE_VALUE_PROPERTY).setValue(0);
        result.addItem("15%").getItemProperty(SINGLE_VALUE_PROPERTY).setValue(15);
        result.addItem("25%").getItemProperty(SINGLE_VALUE_PROPERTY).setValue(25);

        return result;
    }

    public static IndexedContainer createPeriodContainer() {
        IndexedContainer result = new IndexedContainer();
        result.addContainerProperty(SINGLE_VALUE_PROPERTY, Integer.class, 0);

        result.addItem("1 год").getItemProperty(SINGLE_VALUE_PROPERTY).setValue(12);
        result.addItem("2 года").getItemProperty(SINGLE_VALUE_PROPERTY).setValue(24);

        return result;
    }
}
