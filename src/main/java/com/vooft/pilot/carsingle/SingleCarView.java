package com.vooft.pilot.carsingle;

import com.vaadin.addon.touchkit.ui.NavigationBar;
import com.vaadin.addon.touchkit.ui.NavigationView;
import com.vaadin.server.Page;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.*;
import com.vaadin.ui.Button.*;
import com.vooft.pilot.common.CarData;
import com.vooft.pilot.common.MailHelper;
import com.vooft.pilot.common.UriListener;
import org.vaadin.jouni.animator.AnimatorProxy;
import org.vaadin.jouni.animator.shared.AnimType;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: vooft
 * Date: 23.05.13
 * Time: 22:16
 * To change this template use File | Settings | File Templates.
 */
public class SingleCarView extends NavigationView implements ClickListener {

    private CarOrderLayout carOrderLayout;
    private CarData carData;

    private static final String PERCENT_PROPERTY = "PERCENT_PROPERTY";
    private int currentPosition;
    private List<CarData> carDataList;

    public SingleCarView(List<CarData> carDataList, int currentPosition) {
        this.carDataList = carDataList;
        initComponents(currentPosition);

        Page.getCurrent().addUriFragmentChangedListener(new UriListener("single"));
    }

    private void initComponents(int currentPosition) {
        this.carData = carDataList.get(currentPosition);
        this.currentPosition = currentPosition;

        setCaption(carData.getCarName());

        CssLayout root = new CssLayout();
        root.addStyleName("pilot-car-single-root");
        final AnimatorProxy proxy = new AnimatorProxy();
        root.addComponent(proxy);

        final CssLayout mainLayout = new CssLayout();

        CarView cv = new CarView(this, carData);
        cv.setWidth("100%");
        mainLayout.addComponent(cv);

        carOrderLayout = new CarOrderLayout(carData);
        mainLayout.addComponent(carOrderLayout);
        carOrderLayout.setVisible(false);

        {
            final Button orderButton = new Button("Заказать");
            orderButton.addClickListener(new ClickListener() {
                @Override
                public void buttonClick(ClickEvent event) {
                    carOrderLayout.setVisible(true);
                    proxy.animate(carOrderLayout, AnimType.FADE_IN).setDuration(500);
                    orderButton.removeClickListener(this);
                    orderButton.addClickListener(SingleCarView.this);
                }
            });
            orderButton.addStyleName("pilot-order-button");
            mainLayout.addComponent(orderButton);
        }

        setToolbar(createToolbar());

        root.addComponent(mainLayout);

        proxy.animate(mainLayout, AnimType.FADE_IN).setDuration(500);

        setContent(root);
    }

    private Component createToolbar() {
        final NavigationBar toolbar = new NavigationBar();
        toolbar.addStyleName("pilot-car-single-toolbar");

        /*HorizontalComponentGroup hl = new HorizontalComponentGroup();
        hl.setMargin(false);

        final Button galleryButton = new Button();
        galleryButton.setIcon(new ThemeResource("graphics/pic.png"));
        galleryButton.addListener(new ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
                Popover popover = new Popover();

                if(carData.getImages()==null)
                    carData.loadImages(getApplication());

                popover.setContent(new CarGalleryLayout(carData));
                popover.setWidth("100%");
                popover.setClosable(true);
                popover.showRelativeTo(galleryButton);
            }
        });

        hl.addComponent(galleryButton);

        toolbar.setRightComponent(hl);*/

        Button nextCar = new Button();
        nextCar.addStyleName("pilot-car-single-next-button");
        nextCar.setIcon(new ThemeResource("graphics/next.png"));
        nextCar.addClickListener(new ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
                int index = getNextIndex(currentPosition);
                initComponents(index);
            }
        });

        Button prevCar = new Button();
        prevCar.addStyleName("pilot-car-single-prev-button");
        prevCar.setIcon(new ThemeResource("graphics/prev.png"));
        prevCar.addClickListener(new ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
                int index = getPreviousIndex(currentPosition);
                initComponents(index);
            }
        });

        toolbar.setLeftComponent(prevCar);
        toolbar.setRightComponent(nextCar);

        toolbar.setCaption(String.format("%d/%d", currentPosition+1, carDataList.size()));

        return toolbar;
    }

    private int getPreviousIndex(int current) {
        int prevViewIndex = currentPosition - 1;
        if (prevViewIndex < 0) {
            prevViewIndex = carDataList.size() - 1;
        }

        return prevViewIndex;
    }

    private int getNextIndex(int current) {
        int nextViewIndex = currentPosition + 1;
        if (nextViewIndex >= carDataList.size()) {
            nextViewIndex = 0;
        }

        return nextViewIndex;
    }

    @Override
    public void buttonClick(ClickEvent event) {
        Map<String, String> params = carOrderLayout.createData();
        try {
            String response = MailHelper.makeHttpRequest("make_order", params);

            if(response.startsWith("[true]")) {
               Notification.show("Спасибо за заказ, с вами свяжутся в ближайшее время");
                return;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        Notification.show("К сожалению, произошла ошибка при заказе, попробуйте чуть позже");
    }
}
