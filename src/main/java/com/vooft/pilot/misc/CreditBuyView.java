package com.vooft.pilot.misc;

import com.vaadin.addon.touchkit.ui.NavigationView;
import com.vaadin.addon.touchkit.ui.VerticalComponentGroup;
import com.vaadin.server.Page;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.*;
import com.vooft.pilot.common.UriListener;

/**
 * Created with IntelliJ IDEA.
 * User: vooft
 * Date: 30.05.13
 * Time: 14:33
 * To change this template use File | Settings | File Templates.
 */
public class CreditBuyView extends NavigationView {
    public CreditBuyView() {
        setCaption("Рассрочка");
    }

    @Override
    public void attach() {
        super.attach();

        CssLayout root = new CssLayout();
        root.setWidth("100%");
        //root.setMargin(true);

        VerticalComponentGroup vl = new VerticalComponentGroup();

        Label l = new Label("\t\t\t\t<p><b>Предлагаем Вам программу выкупа автомобилей из парка компании.</b><br /></p><p>&nbsp;</p><p>Для оформления договора аренды с правом выкупа Вам не потребуется заниматься сбором справок о доходах и прочих документов, а процедура оформления не займет более 15 минут, и Вы сразу уезжаете на автомобиле.</p><p><b>По вопросам оформления автомобилей обращайтесь по тел. +7 (929) 939-31-21 </b><br /></p><p>\n" +
                "<b><br />\n" +
                "Программа распространяется на следующие автомобили:\n" +
                "\n" +
                "\n" +
                "</b></p><p>&nbsp;</p><table cellpadding=\"10\" cellspacing=\"0\" width=\"100%\"><tbody><tr>\n" +
                "\n" +
                "<td><b><center>Автомобиль</center></b></td>\n" +
                "\n" +
                "<td><b><center>Характеристики</center></b></td>\n" +
                "<td><b><center>Фото авто</center></b></td>\n" +
                "<td><b><center>Сумма первоначального взноса</center></b></td>\n" +
                "<td><b><center>Сумма ежемесячного платежа (12 месяцев)</center></b></td>\n" +
                "</tr><tr>\n" +
                "<td><b>Mini Cooper S</b></td><td>2007г., 1.6 л. turbo,175 л.с., АКПП, цвет синий, пробег 90 тыс. км</td><td><a href=\"prokat-mini-cooper\" mce_href=\"prokat-mini-cooper\">Mini Cooper S</a></td><td align=\"center\"><b>162 500р.</b></td><td align=\"center\"><b>40 625р.</b></td></tr><tr><td><b>Audi A6</b></td><td>2007г., 2.0 л., 170 л.с., АКПП, цвет черный, пробег 140 тыс. км</td><td><a href=\"prokat-audi-a6\">Audi A6</a></td><td align=\"center\"><b>180 000р.</b></td><td align=\"center\"><b>45 000р.</b></td></tr><tr>\n" +
                "<td><b>Volkswagen Touareg</b></td><td>2004 г., 3.2 л., 230 л.с., АКПП, цвет темно-синий, пробег 120 тыс. км</td><td><a href=\"prokat-volkswagen-touareg\">Volkswagen Touareg</a></td><td align=\"center\"><b>182 500р.</b></td><td align=\"center\"><b>45 625р.</b></td></tr><tr>\n" +
                "<td><b>Porshe Cayenne S</b></td><td>2004г., 4.5 л., 320 л.с., АКПП, цвет белый, пробег 120 тыс. км </td><td><a href=\"http://pilot-auto.su/avto-okna-/rassrochka-foto-kayen.html\" mce_href=\"http://pilot-auto.su/avto-okna-/rassrochka-foto-kayen.html\">Porshe Cayenne S</a> </td><td align=\"center\"><b>187 500р.</b></td><td align=\"center\"><b>46 875р.</b></td></tr><tr>\n" +
                "<td><b>BMW 325</b></td><td>2008г., 2.5 л., 218 л.с., АКПП, цвет белый, пробег 100 тыс. км</td><td><a href=\"prokat-bmw-325\" mce_href=\"prokat-bmw-325\">BMW 325</a> </td><td align=\"center\"><b>200 000р.</b></td><td align=\"center\"><b>50 000р.</b></td></tr><tr>\n" +
                "<td><b>BMW 520</b></td><td>2008г., 2.0 л., 170 л.с. АКПП, цвет черный, пробег 130 тыс. км</td><td><a href=\"prokat-bmw-5-e60\">BMW 520 E60</a> <br /></td><td align=\"center\"><b>212 500р.</b></td><td align=\"center\"><b>53 125р.</b></td></tr><tr>\n" +
                "<td><b>BMW 645</b></td><td>2004г., 4.4 л., 325 л.с., АКПП, цвет белый, пробег 115 тыс. км</td><td><a href=\"http://pilot-auto.su/avto-okna-/rassrochka-foto-bmv-645.html\" mce_href=\"http://pilot-auto.su/avto-okna-/rassrochka-foto-bmv-645.html\">BMW 645</a> </td><td align=\"center\"><b>212 500р.</b></td><td align=\"center\"><b>53 125р.</b></td></tr><tr>\n" +
                "<td><b>Audi A5</b></td><td>2007г., 3.2 л., 265 л.с., АКПП, цвет белый, пробег 80 тыс. км</td><td><a href=\"prokat-audi-a5-coupe\">Audi A5</a></td><td align=\"center\"><b>237 500р.</b></td><td align=\"center\"><b>59 375р.</b></td></tr><tr>\n" +
                "<td><b>Audi A5</b></td><td>2007 г., 3.2 л., 265 л.с., АКПП, цвет черный, пробег 80 тыс. км</td><td><a href=\"prokat-audi-a5-coupe\">Audi A5</a></td><td align=\"center\"><b>237 500р.</b></td><td align=\"center\"><b>59 375р.</b></td></tr><tr><td><b>Land Rover Range Rover Sport</b></td><td>2005г., 4.2 л. компрессор, 390 л.с., АКПП, цвет оранжевый, пробег 130 тыс. км</td><td><a href=\"prokat-land-rover-range-rover-sport\">Range Rover Sport</a></td><td align=\"center\"><b>250 000р.</b></td><td align=\"center\"><b>62 500р.</b></td></tr><tr>\n" +
                "<td><b>BMW X5</b></td><td>2006г., 3.0 л., 272 л.с., АКПП, цвет темно-синий, пробег 130 тыс. км</td><td><a href=\"prokat-bmw-x5-e70\">BMW X5 E70</a> </td><td align=\"center\"><b>275 000р.</b></td><td align=\"center\"><b>68 750р.</b></td></tr><tr><td colspan=\"1\"><b>Mercedes S320</b></td><td colspan=\"1\">2006г., 3.2 л., 275 л.с., АКПП, цвет белый, пробег 150 тыс.км <br /></td><td colspan=\"1\"><a href=\"prokat-mercedes-benz-s350-w221\">Mercedes-Benz S320 W221</a></td><td colspan=\"1\" align=\"center\"><b>&nbsp;275 000р.</b></td><td colspan=\"1\" align=\"center\"><b>&nbsp;68 750р.</b></td></tr><tr><td colspan=\"1\"><b>Lexus LS460</b></td><td colspan=\"1\">2006г., 4.6 л., 381 л.с., АКПП, цвет синий, пробег 90 тыс.км <br /></td><td colspan=\"1\"><a href=\"http://pilot-auto.su/avto-okna-/rassrochka-foto-leksus-ls460.html\" mce_href=\"http://pilot-auto.su/avto-okna-/rassrochka-foto-leksus-ls460.html\">Lexus LS460</a> </td><td colspan=\"1\" align=\"center\"><b>&nbsp;287 500р.</b></td><td colspan=\"1\" align=\"center\"><b>&nbsp;71 875р.</b></td></tr><tr><td colspan=\"1\"><b>Mercedes S320 AMG</b></td><td colspan=\"1\">2007г., 3.2 л., 235 л.с., АКПП, цвет черный, дизель, пробег 115 тыс.км <br /></td><td colspan=\"1\"><a href=\"prokat-mercedes-benz-s350-w221\">Mercedes-Benz S320 W221</a></td><td colspan=\"1\" align=\"center\"><b>&nbsp;325 000р.</b></td><td colspan=\"1\" align=\"center\"><b>&nbsp;81 250р.</b></td></tr><tr><td colspan=\"1\"><b>Chevrolet Camaro</b></td><td colspan=\"1\">2009г., 3.2 л., 306 л.с., АКПП, цвет желтый, пробег 32 тыс.км</td><td colspan=\"1\"><a href=\"prokat-chevrolet-camaro\">Chevrolet Camaro</a> <br /></td><td colspan=\"1\" align=\"center\"><b>&nbsp;400 000р.</b></td><td colspan=\"1\" align=\"center\"><b>&nbsp;100 000р.</b></td></tr></tbody></table>\n" +
                "\n" +
                "\n" +
                "<hr>\n" +
                "<p><b>По вопросам оформления автомобилей обращайтесь по тел. +7 </b><b><b>(929) 939-31-21</b>&nbsp; </b></p><p><b>Примечания:</b><br />\n" +
                "1. Первоначальный взнос оплачивается при заключении договора аренды с последующим выкупом. 1-й платеж должен быть сделан не позднее 14 календарных дней после первоначального взноса, далее платежи совершаются раз в месяц.<br />\n" +
                "2. Расчет не включает дополнительные затраты на страхование, ТО, оформление автомобиля в ГИБДД, установку и обслуживание противоугонных систем, транспортный налог и проч.<br />\n" +
                "3. Возможен вариант с разбивкой дополнительных затрат и включением их в ежемесячные платежи. <br /></p>\n" +
                "\n" +
                "\t", ContentMode.HTML);
        l.setWidth("100%");

        vl.addComponent(l);

        root.addComponent(vl);

        setContent(root);

        Page.getCurrent().addUriFragmentChangedListener(new UriListener("credit"));
    }
}
