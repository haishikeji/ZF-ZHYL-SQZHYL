package com.px.modulars.device.entity;

/**
 *  获取返回的经纬度
 */
public class GsmRsp {

    /** 经度 */
    private String longitude;

    /** 纬度 */
    private String latitude;

    private String address;
    private String date;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "GsmRsp{" +
                "longitude='" + longitude + '\'' +
                ", latitude='" + latitude + '\'' +
                ", address='" + address + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
