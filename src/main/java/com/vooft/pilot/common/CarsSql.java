package com.vooft.pilot.common;


import com.vaadin.server.Page;
import com.vaadin.server.Resource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: vooft
 * Date: 08.05.13
 * Time: 22:17
 * To change this template use File | Settings | File Templates.
 */
public class CarsSql {
    private static Connection getConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        try {
            return DriverManager.getConnection("jdbc:mysql://localhost/rentcar","root", "testpass");
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        return null;
    }

    public static List<CarData> getCars(int minSum, int maxSum) {
        int bigWidth = 512;

        bigWidth = Page.getCurrent().getBrowserWindowWidth();
        System.out.println("Screen width: " + bigWidth);

        int maxWidth = (int) Math.floor((double) bigWidth/3.5);

        if(maxWidth>512)
            maxWidth = 512;

        bigWidth = (int) Math.floor(bigWidth*0.8);
        if(bigWidth>512)
            bigWidth = 512;

        List<CarData> result = new ArrayList<CarData>();

        try {
            Connection conn = getConnection();
            Statement st = conn.createStatement();

            String query = "SELECT c.id, c.cname as car_name, c.price, c.brand,\n" +
                    "\t\t\t(case when c.car_name like '%fake%' then 0 else cf.is_free end) as is_free,coalesce(ct.cabin, '') as cabin, coalesce(ct.body_type, '') as body,\n" +
                    "\t\t\tcoalesce(ct.drive, '') as drive,\n" +
                    "\t\t\tcoalesce(CAST(ct.motor_power AS CHAR), '0') as motor_power, coalesce(CAST(ct.motor_value AS CHAR), '0') as motor_value,\n" +
                    "\t\t\t(SELECT cs.id FROM car_rent_car_scan cs WHERE cs.cars_id=c.id AND cs.doctype='photo_test_title') as image_id,\n" +
                    "\t\t\t(SELECT cs.blob FROM car_rent_car_scan cs WHERE cs.cars_id=c.id AND cs.doctype='photo_test_title') as img\n" +
                    "\t\t\tFROM car_rent_car_price c\n" +
                    "\t\t\tINNER JOIN car_rent_car_tech ct ON c.id=ct.id\n" +
                    "\t\t\tINNER JOIN car_rent_car_free cf ON c.id=cf.id\n" +
                    "\t\t\tWHERE c.archive=0 AND c.car_name NOT LIKE '%skip%' AND c.price>=" + minSum + " AND c.price<" + maxSum + " \n" +
                    "\t\t\tORDER BY price, car_name";

            ResultSet rs = st.executeQuery(query);

            while(rs.next()) {
                CarData data = new CarData(rs.getInt("id"), rs.getString("car_name"));
                data.setCarPrice(Integer.parseInt(rs.getString("price")));
                data.setIsFree(rs.getInt("is_free")==1);

                data.setDrive(rs.getString("drive"));

                data.setBody(rs.getString("body"));

                data.setCabin(rs.getString("cabin"));

                data.setBrand(rs.getString("brand"));

                data.setEngine(String.format("%s л.с., %s л.", rs.getString("motor_power"), rs.getString("motor_value")));
                data.setTitleWidth(maxWidth);

                int image_id = rs.getInt("image_id");
                Resource res = null;
                if(ImageHelper.hasThumbnail(image_id, maxWidth))
                    res = ImageHelper.getImageResource(image_id, maxWidth);
                else {
                    byte img[] = rs.getBytes("img");
                    if(img==null) {
                        res = ImageHelper.createStubResource();
                    } else {
                        res = ImageHelper.getImageResource(image_id, maxWidth, img);
                    }
                }

                Resource bigRes = null;
                if(ImageHelper.hasThumbnail(image_id, bigWidth))
                    bigRes = ImageHelper.getImageResource(image_id, bigWidth);
                else {
                    byte img[] = rs.getBytes("img");
                    if(img==null) {
                        bigRes = ImageHelper.createStubResource();
                    } else {
                        bigRes = ImageHelper.getImageResource(image_id, bigWidth, img);
                    }
                }

                data.setTitlePic(res);
                data.setBigTitlePic(bigRes);

                result.add(data);
            }

            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        return result;
    }

    public static List<Resource> getImages(int cars_id) {
        int maxWidth = 512;

        List<Resource> result = new ArrayList<Resource>();
        try {
            Connection conn = getConnection();
            Statement st = conn.createStatement();

            ResultSet rs = st.executeQuery("SELECT id, `blob` as img FROM car_rent_car_scan\n" +
                    "\t\t\tWHERE cars_id=" + cars_id + " AND doctype LIKE 'photo_%' AND doctype<>'photo_test_title' ORDER BY doctype");
            while(rs.next()) {
                int id = rs.getInt("id");

                Resource res = null;
                if(ImageHelper.hasThumbnail(id, maxWidth))
                    res = ImageHelper.getImageResource(id, maxWidth);
                else {
                    byte img[] = rs.getBytes("img");
                    if(img==null) {
                        res = ImageHelper.createStubResource();
                    } else {
                        res = ImageHelper.getImageResource(id, maxWidth, img);
                    }
                }

                result.add(res);
            }

            if(result.size()==0)
                result.add(ImageHelper.createStubResource());

            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        return result;
    }

    public static void main(String argc[]) {
        System.out.println(System.nanoTime());
        getCars(0, 3000);
        System.out.println(System.nanoTime());
    }
}
