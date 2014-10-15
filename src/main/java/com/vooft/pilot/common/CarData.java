package com.vooft.pilot.common;

import com.vaadin.server.Resource;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: vooft
 * Date: 07.05.13
 * Time: 18:33
 * To change this template use File | Settings | File Templates.
 */
public class CarData implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer mId = 0;
    private String mCarName = "";
    private Integer mCarPrice = 0;
    private Boolean mFree;
    private Map<String, String> mUrlParams = new HashMap<String, String>();
    private String mEngine = "";
    private String mDrive = "";
    private String mBody = "";
    private String mCabin = "";
    private String mBrand = "";
    private Resource mTitlePicture;
    private Resource mBigPicture;
    private List<Resource> mImages = null;
    private Integer mTitleWidth;

    public CarData(int id, String carName) {
        mId = id;
        mCarName = carName;

        mUrlParams.put("cars_id", mId.toString());
    }

    public String getCarName() {
        return mCarName;
    }

    public void setCarName(String carName) {
        mCarName = carName;
    }

    public int getCarPrice() {
        return mCarPrice;
    }

    public void setCarPrice(int carPrice) {
        mCarPrice = carPrice;
    }

    public int getId() {
        return mId;
    }

    public boolean isFree() {
        return mFree;
    }

    public void setIsFree(boolean is_free) {
        mFree = is_free;
    }

    public String getEngine() {
        return mEngine;
    }

    public void setEngine(String engine) {
        mEngine = engine;
    }

    public String getDrive() {
        return mDrive;
    }

    public void setDrive(String drive) {
        mDrive = drive;
    }

    public String getBody() {
        return mBody;
    }

    public void setBody(String body) {
        mBody = body;
    }

    public String getCabin() {
        return mCabin;
    }

    public void setCabin(String cabin) {
        mCabin = cabin;
    }

    public Resource getTitlePic() {
        return mTitlePicture;
    }

    public void setTitlePic(Resource resource) {
        mTitlePicture = resource;
    }

    public Resource getBigTitlePic() {
        return mBigPicture;
    }

    public void setBigTitlePic(Resource resource) {
        mBigPicture = resource;
    }

    public String getImageUrl() {
        return String.format("http://pilot-auto.net:1613/mobile/?proto=2&action=get_photo&cars_id=%d", mId);
    }

    public String getBrand() {
        return mBrand;
    }

    public void setBrand(String mBrand) {
        this.mBrand = mBrand.trim();
    }

    public List<Resource> getImages() {
        return mImages;
    }

    public void loadImages() {
        mImages = CarsSql.getImages(getId());
    }

    public void setTitleWidth(int width) {
        mTitleWidth = width;
    }

    public int getTitleWidth() {
        return mTitleWidth;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CarData carData = (CarData) o;

        if (!mId.equals(carData.mId)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return mId.hashCode();
    }
}

