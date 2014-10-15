package com.vooft.pilot.misc;

import com.vaadin.addon.touchkit.ui.NavigationView;
import com.vaadin.addon.touchkit.ui.VerticalComponentGroup;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.Page;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.*;
import com.vaadin.ui.Button.*;
import com.vooft.pilot.common.UriListener;

/**
 * Created with IntelliJ IDEA.
 * User: vooft
 * Date: 25.05.13
 * Time: 20:57
 * To change this template use File | Settings | File Templates.
 */
public class ContactsView extends NavigationView {
    private static final String SIZE_STRING = "32px";

    public ContactsView() {
        setCaption("Контакты");
    }

    @Override
    public void attach() {
        VerticalComponentGroup root = new VerticalComponentGroup();
        root.setWidth("100%");

        { // телефоны
            HorizontalLayout hl = new HorizontalLayout();
            hl.setSpacing(true);
            hl.setWidth("100%");

            ThemeResource tr = new ThemeResource("graphics/call_big.png");
            Embedded e = new Embedded(null, tr);
            e.setWidth(SIZE_STRING);
            e.setHeight(SIZE_STRING);
            hl.addComponent(e);

            VerticalLayout vl = new VerticalLayout();
            vl.setWidth("100%");

            Link phone1 = new Link("+7 (495) 790-57-30", new ExternalResource("tel:+74957905730"));
            phone1.setWidth("100%");
            vl.addComponent(phone1);

            Link phone2 = new Link("+7 (499) 999-57-30", new ExternalResource("tel:+74999995730"));
            phone2.setWidth("100%");
            vl.addComponent(phone2);

            Link phone3 = new Link("+7 (800) 700-57-30", new ExternalResource("tel:+78007005730"));
            phone3.setWidth("100%");
            vl.addComponent(phone3);

            hl.addComponent(vl);

            hl.setExpandRatio(vl, 1.0f);

            root.addComponent(hl);
        }

        {
            HorizontalLayout hl = new HorizontalLayout();
            hl.setSpacing(true);
            hl.setWidth("100%");

            ThemeResource tr = new ThemeResource("graphics/call_big.png");
            Embedded e = new Embedded(null, tr);
            e.setWidth(SIZE_STRING);
            e.setHeight(SIZE_STRING);
            hl.addComponent(e);

            VerticalLayout vl = new VerticalLayout();
            vl.setWidth("100%");

            Label l = new Label("Тех. поддержка (только по техническим вопросам, круглосуточно)");
            l.setWidth("100%");
            vl.addComponent(l);

            Link phone1 = new Link("+7 (495) 649-50-20", new ExternalResource("tel:+74956495020"));
            phone1.setWidth("100%");
            vl.addComponent(phone1);

            Link phone2 = new Link("+7 (926) 014-10-24", new ExternalResource("tel:+79260141024"));
            phone2.setWidth("100%");
            vl.addComponent(phone2);

            hl.addComponent(vl);

            hl.setExpandRatio(vl, 1.0f);

            root.addComponent(hl);
        }

        {
            HorizontalLayout hl = new HorizontalLayout();
            hl.setSpacing(true);
            hl.setWidth("100%");

            ThemeResource tr = new ThemeResource("graphics/mail_big.png");
            Embedded e = new Embedded(null, tr);
            e.setWidth(SIZE_STRING);
            e.setHeight(SIZE_STRING);
            hl.addComponent(e);

            Link mail = new Link("info@pilot-auto.su", new ExternalResource("mailto:info@pilot-auto.su"));
            mail.setWidth("100%");

            hl.addComponent(mail);

            hl.setExpandRatio(mail, 1.0f);

            root.addComponent(hl);
        }

        {
            VerticalLayout vl = new VerticalLayout();
            vl.setWidth("100%");

            Link mapsLink2 = new Link(null, new ExternalResource("https://maps.google.com/maps?q=55.74298739737297,+37.5596686080133+(%D0%9F%D0%B8%D0%BB%D0%BE%D1%82-%D0%90%D0%B2%D1%82%D0%BE)&hl=ru&ie=UTF8&sll=55.742987,37.559669&sspn=0.010484,0.031629&t=m&z=15&iwloc=A"));
            mapsLink2.setIcon(new ThemeResource("img/map.png"));
            mapsLink2.setWidth("100%");
            mapsLink2.setImmediate(true);
            vl.addComponent(mapsLink2);

            Button linkButton = new Button("Найти нас");
            linkButton.setImmediate(true);
            linkButton.setWidth("100%");
            linkButton.addClickListener(new ClickListener() {
                @Override
                public void buttonClick(ClickEvent event) {
                    Page.getCurrent().open("https://maps.google.com/maps?q=55.74298739737297,+37.5596686080133+(%D0%9F%D0%B8%D0%BB%D0%BE%D1%82-%D0%90%D0%B2%D1%82%D0%BE)&hl=ru&ie=UTF8&sll=55.742987,37.559669&sspn=0.010484,0.031629&t=m&z=15&iwloc=A", "_blank");
                }
            });
            vl.addComponent(linkButton);

            root.addComponent(vl);
        }

        setContent(root);

        Page.getCurrent().addUriFragmentChangedListener(new UriListener("contacts"));
    }
}
