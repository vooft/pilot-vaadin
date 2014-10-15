package com.vooft.pilot;

import com.vaadin.addon.touchkit.ui.NavigationButton;
import com.vaadin.addon.touchkit.ui.NavigationButton.NavigationButtonClickEvent;
import com.vaadin.addon.touchkit.ui.NavigationButton.NavigationButtonClickListener;
import com.vaadin.addon.touchkit.ui.NavigationManager;
import com.vaadin.addon.touchkit.ui.NavigationView;
import com.vaadin.addon.touchkit.ui.VerticalComponentGroup;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.*;
import com.vooft.pilot.carslist.CarsGridView;
import com.vooft.pilot.misc.ContactsView;
import com.vooft.pilot.misc.CreditBuyView;
import com.vooft.pilot.misc.InfoView;
import com.vooft.pilot.misc.RedemptionRentView;

/**
 * Displays accounts, mailboxes, message list hierarchically
 */
public class MenuHierarchyView extends NavigationView {

    private static final long serialVersionUID = 1L;

    private NavigationManager nav;

    public MenuHierarchyView(final NavigationManager nav) {
        this.nav = nav;

        setCaption("Прокат авто без залога");
        setWidth("100%");
        setHeight("100%");

        CssLayout root = new CssLayout();
        //root.setWidth("100%");
        root.addStyleName("pilot-root");
        //root.setMargin(true);

        Embedded logo = new Embedded(null, new ThemeResource("img/pilot-auto.png"));
        logo.addStyleName("pilot-logo");
        logo.setWidth("100%");

        root.addComponent(logo);

        Label classLabel = new Label("Класс авто");
        classLabel.addStyleName("pilot-menu-title");
        root.addComponent(classLabel);

        root.addComponent(createPilotMenu(nav));

        Label infoLabel = new Label("Информация");
        infoLabel.addStyleName("pilot-menu-title");
        root.addComponent(infoLabel);

        root.addComponent(createBuyMenu(nav));

        Label toolsLabel = new Label("Покупка авто");
        toolsLabel.addStyleName("pilot-menu-title");
        root.addComponent(toolsLabel);

        root.addComponent(createToolsMenu(nav));

        setContent(root);
    }

    private Component createPilotMenu(final NavigationManager nav) {
        VerticalComponentGroup vl = new VerticalComponentGroup();
        vl.addStyleName("pilot-menu-layout");

        final NavigationButton economyButton = new NavigationButton("Эконом");
        economyButton.addStyleName("pill");
        economyButton.addStyleName("pilot-menu-item");
        economyButton.setDescription("до 3000");
        vl.addComponent(economyButton);
        economyButton.addClickListener(new NavigationButtonClickListener() {
            @Override
            public void buttonClick(NavigationButtonClickEvent event) {
                CarsGridView screen = new CarsGridView(nav, 0, 3000);
                screen.setCaption(economyButton.getCaption());
                nav.navigateTo(screen);
            }
        });
        economyButton.setIcon(new ThemeResource("graphics/budget.png"));

        final NavigationButton middleButton = new NavigationButton("Средний");
        middleButton.addStyleName("pill");
        middleButton.addStyleName("pilot-menu-item");
        middleButton.setDescription("до 6000");
        vl.addComponent(middleButton);
        middleButton.addClickListener(new NavigationButtonClickListener() {
            @Override
            public void buttonClick(NavigationButtonClickEvent event) {
                CarsGridView screen = new CarsGridView(nav, 3000, 6000);
                screen.setCaption(middleButton.getCaption());
                nav.navigateTo(screen);
            }
        });
        middleButton.setIcon(new ThemeResource("graphics/middle.png"));

        final NavigationButton businessButton = new NavigationButton("Бизнес");
        businessButton.addStyleName("pill");
        businessButton.addStyleName("pilot-menu-item");
        businessButton.setDescription("до 9000");
        vl.addComponent(businessButton);
        businessButton.addClickListener(new NavigationButtonClickListener() {
            @Override
            public void buttonClick(NavigationButtonClickEvent event) {
                CarsGridView screen = new CarsGridView(nav, 6000, 9000);
                screen.setCaption(businessButton.getCaption());
                nav.navigateTo(screen);
            }
        });
        businessButton.setIcon(new ThemeResource("graphics/business.png"));

        final NavigationButton luxuryButton = new NavigationButton("Люкс");
        luxuryButton.addStyleName("pill");
        luxuryButton.addStyleName("pilot-menu-item");
        luxuryButton.setDescription("от 9000");
        vl.addComponent(luxuryButton);
        luxuryButton.addClickListener(new NavigationButtonClickListener() {
            @Override
            public void buttonClick(NavigationButtonClickEvent event) {
                CarsGridView screen = new CarsGridView(nav, 9000, 100000);
                screen.setCaption(luxuryButton.getCaption());
                nav.navigateTo(screen);
            }
        });
        luxuryButton.setIcon(new ThemeResource("graphics/luxury.png"));

        return vl;
    }

    private VerticalComponentGroup createBuyMenu(final NavigationManager nav) {
        VerticalComponentGroup vl = new VerticalComponentGroup();
        vl.addStyleName("pilot-menu-layout");

        final NavigationButton infoButton = new NavigationButton("Условия");
        infoButton.addStyleName("pill");
        infoButton.addStyleName("pilot-menu-item");
        vl.addComponent(infoButton);
        infoButton.addClickListener(new NavigationButtonClickListener() {
            @Override
            public void buttonClick(NavigationButtonClickEvent event) {
                nav.navigateTo(new InfoView());
            }
        });
        infoButton.setIcon(new ThemeResource("graphics/rules.png"));

        final NavigationButton contactsButton = new NavigationButton("Контакты");
        contactsButton.addStyleName("pill");
        contactsButton.addStyleName("pilot-menu-item");
        vl.addComponent(contactsButton);
        contactsButton.addClickListener(new NavigationButtonClickListener() {
            @Override
            public void buttonClick(NavigationButtonClickEvent event) {
                nav.navigateTo(new ContactsView());
            }
        });
        contactsButton.setIcon(new ThemeResource("graphics/contacts.png"));

        return vl;
    }

    private VerticalComponentGroup createToolsMenu(final NavigationManager nav) {
        VerticalComponentGroup vl = new VerticalComponentGroup();
        vl.addStyleName("pilot-menu-layout");

        final NavigationButton contactsButton = new NavigationButton("Аренда с выкупом");
        contactsButton.addStyleName("pill");
        contactsButton.addStyleName("pilot-menu-item");
        contactsButton.setDescription("расчет");
        vl.addComponent(contactsButton);
        contactsButton.addClickListener(new NavigationButtonClickListener() {
            @Override
            public void buttonClick(NavigationButtonClickEvent event) {
                nav.navigateTo(new RedemptionRentView());
            }
        });
        contactsButton.setIcon(new ThemeResource("graphics/redemp.png"));

        final NavigationButton infoButton = new NavigationButton("Рассрочка");
        infoButton.addStyleName("pill");
        infoButton.addStyleName("pilot-menu-item");
        vl.addComponent(infoButton);
        infoButton.addClickListener(new NavigationButtonClickListener() {
            @Override
            public void buttonClick(NavigationButtonClickEvent event) {
                nav.navigateTo(new CreditBuyView());
            }
        });
        infoButton.setIcon(new ThemeResource("graphics/credit.png"));

        return vl;
    }
}
