package com.vooft.pilot.misc;

import com.vaadin.addon.touchkit.ui.NavigationView;
import com.vaadin.addon.touchkit.ui.NumberField;
import com.vaadin.addon.touchkit.ui.Switch;
import com.vaadin.addon.touchkit.ui.VerticalComponentGroup;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.server.Page;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.*;
import com.vaadin.ui.Button.*;
import com.vooft.pilot.common.DataHelper;
import com.vooft.pilot.common.UriListener;

/**
 * Created with IntelliJ IDEA.
 * User: vooft
 * Date: 30.05.13
 * Time: 14:36
 * To change this template use File | Settings | File Templates.
 */
public class RedemptionRentView extends NavigationView {

    private NumberField sumField;
    private NumberField engineField;
    private ListSelect firstPaymentSelect;
    private Switch kaskoSwitch;
    private Switch osagoSwitch;
    private Switch nalogSwitch;
    private Switch to1Switch;
    private Switch to2Switch;
    private Switch resinSwitch;
    private Switch safetySwitch;
    private ListSelect periodSelect;
    private Label resultSum;
    private Label firstPaymentValue;

    public RedemptionRentView() {
        setCaption("Аренда с выкупом");
        setWidth("100%");
        setHeight("100%");

        final CssLayout root = new CssLayout();
        root.setWidth("100%");
        //root.setMargin(true);

        {
            VerticalComponentGroup vl = new VerticalComponentGroup();
            Label l = new Label("Компания «Пилот-Авто» оказывает услуги по прокату автомобилей с правом выкупа. Такой вид аренды предполагает временное использование транспортного средства с последующим переходом его в собственность арендатора. Данная услуга не является формой кредитования, приобретения в рассрочку или лизинга. В связи с этим сравнивать переплату и процентную ставку при прокате с выкупом и при получении кредита на автомобиль неверно.");
            l.setWidth("100%");
            vl.addComponent(l);
            root.addComponent(vl);
        }

        {
            Label l = new Label("<h2>Преимущества аренды с правом выкупа</h2>\n" +
                    "<ul>\n" +
                    "<li><b>Гибкий график платежей.</b> Сделка по аренде автомобиля с правом выкупа проводится максимально быстро и не требует привлечения больших финансовых средств. Вы можете сформировать оптимальный график платежей с фиксированной суммой выплаты. Также предоставляется возможность досрочного выкупа автомобиля.\n" +
                    "</li><li><b>Экономия финансов.</b> Обычно в сумму первоначального платежа включаются затраты на обслуживание транспортного средства (прохождение ТО, монтаж систем безопасности, сезонный комплект резины) и оформление автомобиля (страхование по ОСАГО и КАСКО, транспортный налог).\n" +
                    "</li><li><b>Экономия времени.</b> Для оформления выкупа транспортного средства Вам не потребуются справки о доходах и прочие документы. Если автомобиль выкупается из автопарка компании, то Вы получите его в срок до 3 дней. Вы будете избавлены от необходимости заниматься вопросами страхования, возить автомобиль на техосмотр, тратить время на прохождение ТО и подбор резины по сезону.\n" +
                    "</li><li><b>Возможность выбора.</b> Разонравился выкупаемый автомобиль, и Вы хотите сменить его на другую модель из парка компании? В этом случае договор можно перезаключить. При этом выплаченная Вами сумма также переводится на новый договор.\n" +
                    "</li><li><b>Гибкие условия.</b> Для оформления договора Вам не требуется предоставлять справки о доходах, иметь прописку в Москве или области. \n" +
                    "</li>\n" +
                    "</ul>\n" +
                    "<h2>Условия договора аренды с правом выкупа</h2>\n" +
                    "<p><b>Срок договора.</b> Максимальный срок выполнения договора составляет два года. Вы можете сами определять оптимальный период (от года до двух). При этом у Вас остается возможность досрочного исполнения договора.\n" +
                    "</p><p><b>График платежей.</b> Вы можете самостоятельно выбрать удобное расписание арендных платежей за Ваш автомобиль – еженедельно, ежемесячно и т.д. Платежи вносятся на основании составленного графика в течение всего срока договора.\n" +
                    "</p><p><b>Страхование.</b> Вы получаете автомобиль, который уже поставлен на учет и застрахован по ОСАГО и КАСКО.\n" +
                    "</p>\n" +
                    "<h2>Требования к клиентам</h2>\n" +
                    "<ul>\n" +
                    "<li>Желающий взять в аренду автомобиль с правом выкупа проходит только проверку службой безопасности компании. Справки с места работы не требуются.\n" +
                    "Официальный стаж вождения должен составлять минимум 1 год.\n" +
                    "</li><li>Прописка в Московской области не является обязательным условием, но при ее наличии возможен вариант заключения договора без первоначального взноса. Если Вы прописаны в ином регионе, то будут рассмотрены варианты с первоначальным взносом.\n" +
                    "</li><li>Автомобиль большую часть времени будет эксплуатироваться в Москве. Ограничений по пробегу нет.\n" +
                    "</li>\n" +
                    "</ul>\n" +
                    "<h2>Порядок приобретения автомобиля</h2>\n" +
                    "<p><b>Предварительное обсуждение.</b> Вначале Вы подбираете автомобиль из представленных в каталоге, либо согласовываете нужную Вам модель с компанией. После этого рассчитывается примерная стоимость сделки и Вы предоставляете свои анкетные данные на проверку нашей службе безопасности. После одобрения заключается договор аренды с правом выкупа.\n" +
                    "</p><p><b>Заключение договора аренды автомобиля с правом выкупа.</b> В документ вносятся согласованные условия сделки. График платежей и акт приема-передачи автомобиля фиксируется в приложении. Если автомобиль привозится по Вашему заказу, то сумма первоначального платежа составляет не менее 15 %. В этом случае взнос за первый месяц оплачивается в день подписания договора.\n" +
                    "</p><p><b>Передача автомобиля в аренду.</b> Со страховой компанией согласовываются условия ОСАГО и КАСКО, и Вы получаете автомобиль. Если техника приобретается из автопарка нашей компании, то передача происходит в день заключения договора. Если автомобиль приобретается под заказ, то Вы получаете его в течение 3-х дней.\n" +
                    "</p><p><b>Передача автомобиля в собственность.</b> По завершении выплат арендной и выкупной стоимости авто, оформляется переход права собственности, и Вы становитесь полноправным владельцем.\n" +
                    "</p>\n", ContentMode.HTML);
            l.setWidth("100%");
            final VerticalComponentGroup vl = new VerticalComponentGroup();
            vl.addComponent(l);

            final HorizontalLayout hl = new HorizontalLayout();
            hl.setMargin(true);
            hl.setWidth("100%");

            final Button rulesButton = new Button("Показать условия");
            rulesButton.setWidth("100%");
            rulesButton.addClickListener(new ClickListener() {
                @Override
                public void buttonClick(ClickEvent event) {
                    vl.setVisible(!vl.isVisible());
                    root.removeComponent(hl);
                }
            });

            hl.addComponent(rulesButton);

            root.addComponent(hl);
            root.addComponent(vl);
            vl.setVisible(false);
        }

        root.addComponent(createForm());

        HorizontalLayout buttonsGroup = new HorizontalLayout();
        buttonsGroup.setWidth("100%");
        buttonsGroup.setMargin(true);

        Button calcButton = new Button("Рассчитать", new ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
                calc();
            }
        });
        calcButton.setWidth("100%");
        buttonsGroup.addComponent(calcButton);

        root.addComponent(buttonsGroup);

        setContent(root);

        Page.getCurrent().addUriFragmentChangedListener(new UriListener("redemp"));
    }

    private Component createForm() {
        VerticalComponentGroup vl = new VerticalComponentGroup();
        vl.setWidth("100%");

        {
            sumField = new NumberField("Стоимость авто, руб.");
            //sumField.setWidth("90%");
            sumField.setMaxLength(6);
            vl.addComponent(sumField);

            sumField.addStyleName("textfield-border");
        }

        {
            engineField = new NumberField("Мощность двигателя, л.с.");
            //engineField.setWidth("90%");
            engineField.setMaxLength(3);
            vl.addComponent(engineField);

            engineField.addStyleName("textfield-border");
        }

        {
            final IndexedContainer container = DataHelper.createFirstPaymentContainer();

            firstPaymentSelect = new ListSelect("Первоначальный взнос*", container);
            firstPaymentSelect.setNullSelectionAllowed(false);
            firstPaymentSelect.setImmediate(true);

            firstPaymentSelect.setValue(firstPaymentSelect.getItemIds().iterator().next());
            vl.addComponent(firstPaymentSelect);
        }

        {
            kaskoSwitch = new Switch("Каско");
            kaskoSwitch.setValue(false);
            vl.addComponent(kaskoSwitch);
        }

        {
            osagoSwitch = new Switch("ОСАГО");
            osagoSwitch.setValue(false);
            vl.addComponent(osagoSwitch);
        }

        {
            nalogSwitch = new Switch("Транспортный налог");
            nalogSwitch.setValue(false);
            vl.addComponent(nalogSwitch);
        }

        {
            to1Switch = new Switch("ТО-1");
            to1Switch.setValue(false);
            vl.addComponent(to1Switch);
        }

        {
            to2Switch = new Switch("ТО-2");
            to2Switch.setValue(false);
            vl.addComponent(to2Switch);
        }

        {
            resinSwitch = new Switch("Комплект сезонной резины");
            resinSwitch.setValue(false);
            vl.addComponent(resinSwitch);
        }

        {
            safetySwitch = new Switch("Комплект безопасности");
            safetySwitch.setValue(false);
            vl.addComponent(safetySwitch);
        }

        {
            final IndexedContainer container = DataHelper.createPeriodContainer();

            periodSelect = new ListSelect("В рассрочку на", container);
            periodSelect.setNullSelectionAllowed(false);
            periodSelect.setImmediate(true);

            periodSelect.setValue(periodSelect.getItemIds().iterator().next());
            vl.addComponent(periodSelect);
        }

        {
            firstPaymentValue = new Label();
            firstPaymentValue.setCaption("Первый взнос");
            vl.addComponent(firstPaymentValue);
        }

        {
            resultSum = new Label();
            resultSum.setCaption("Итого");
            vl.addComponent(resultSum);
        }

        return vl;
    }

    private void calc() {
        { // проверка суммы
            String sumStr = sumField.getValue().toString().trim();
            int i = 0;
            try {
                i = Integer.valueOf(sumStr);
            } catch (Exception e) { }

            if(i<100000 || i>10000000) { // менее 100к и более 10 млн
                Notification.show("Введите корректную сумму");
                return;
            }
        }

        { // проверка суммы
            String engineStr = engineField.getValue().toString().trim();
            int i = 0;
            try {
                i = Integer.valueOf(engineStr);
            } catch (Exception e) { }

            if(i<10 || i>999) {
                Notification.show("Введите корректную мощность");
                return;
            }
        }

        double sum = Double.valueOf(sumField.getValue().toString().trim());
        double result = sum;

        if((Boolean) kaskoSwitch.getValue()) {
            result += sum*0.1;
        }

        if((Boolean) osagoSwitch.getValue()) {
            result += 10000;
        }

        if((Boolean) nalogSwitch.getValue()) {
            int p = Integer.valueOf(engineField.getValue().toString());
            int k = 0;

            if(p<100)
                k = 7;
            else if(p<125)
                k = 20;
            else if(p<150)
                k = 30;
            else if(p<175)
                k = 38;
            else if(p<200)
                k = 45;
            else if(p<225)
                k = 60;
            else if(p<250)
                k = 75;
            else
                k = 150;

            result += k*p;
        }

        if((Boolean) to1Switch.getValue()) {
            result += 15000;
        }

        if((Boolean) to2Switch.getValue()) {
            result += 30000;
        }

        if((Boolean) resinSwitch.getValue()) {
            result += 30000;
        }

        if((Boolean) safetySwitch.getValue()) {
            result += 25000;
        }

        Integer period = (Integer) periodSelect.getItem(periodSelect.getValue()).getItemProperty(DataHelper.SINGLE_VALUE_PROPERTY).getValue();
        Integer firstPayPercent = (Integer) firstPaymentSelect.getItem(firstPaymentSelect.getValue()).getItemProperty(DataHelper.SINGLE_VALUE_PROPERTY).getValue();

        double koeff = 0;

        if(period==12) {
            switch (firstPayPercent) {
                case 0: koeff = 1.8;
                    break;
                case 15: koeff = 1.6;
                    break;
                case 25: koeff = 1.4;
                    break;
            }
        } else if(period==24) {
            switch (firstPayPercent) {
                case 0: koeff = 2.3;
                    break;
                case 15: koeff = 2;
                    break;
                case 25: koeff = 1.8;
                    break;
            }
        }

        result = result * koeff;

        int firstPay = (int) Math.floor(result*firstPayPercent/100);

        firstPaymentValue.setValue(String.format("%d руб", firstPay));

        int monthly = (int) Math.round((result-firstPay)/period);
        resultSum.setValue(String.format("%d руб/мес", monthly));
    }
}
