package com.vooft.pilot.common;

import java.io.*;
import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * User: vooft
 * Date: 30.05.13
 * Time: 2:13
 * To change this template use File | Settings | File Templates.
 */
public class PropertyHelper {
    static private final Properties property;

    static private final String RESOURCE_FILE = "pilot.properties";
    static {
        property = getProperty();
    }
    /**
     * Считывает настройки из настроечного файла
     */
    static private final Properties getProperty() {

        //URL dbpropertiesURL = ImportProperty.class.getResource(RESOURCE_FILE);
        Properties properties = new Properties();
        BufferedInputStream bis = null;
        try {
            File f = new File(RESOURCE_FILE);
            System.out.println(f.getAbsolutePath());
            if(f.exists()==false)
                f.createNewFile();

            bis = new BufferedInputStream(new FileInputStream(RESOURCE_FILE));
            properties.load(bis);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (Exception ee) {

                }
            }
        }
        return properties;
    }
    static public String getValue(String paramName) {
        String returnIt = property.getProperty(paramName);
        return returnIt;
    }
    static public void setProperty(String propertName, String propertyValue) {
        property.setProperty(propertName, propertyValue);
    }
    /**
     * Сохраняет настройки
     */
    static public void save() {
        BufferedOutputStream bos = null;
        try {
            bos = new BufferedOutputStream(new FileOutputStream(RESOURCE_FILE));
            property.store(bos, "");
        } catch (Exception e) {
            //StIzdelLogger.getLogger().log(Level.SEVERE, e.getMessage(), e);
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
}
