package com.kingsley.groupon.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.List;

/**
 * class name : Groupon
 * author : Kingsley
 * created date : on 2017/6/21 15:54
 * file change date : on 2017/6/21 15:54
 * version: 1.0
 */

public class CityBean {
    private String status;
    private List<String> cities;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<String> getCities() {
        return cities;
    }

    public void setCities(List<String> cities) {
        this.cities = cities;
    }

    @Override
    public String toString() {
        return "CityBean{" +
                "status='" + status + '\'' +
                ", cities=" + cities +
                '}';
    }
    @DatabaseTable
    public static class CityNameBean{
        @DatabaseField(id = true)
        private String cityName;
        @DatabaseField
        private String cityPinYinName;
        @DatabaseField
        private String cityLetter;

        public String getCityName() {
            return cityName;
        }

        public void setCityName(String cityName) {
            this.cityName = cityName;
        }

        public String getCityLetter() {
            return cityLetter;
        }

        public void setCityLetter(String cityLetter) {
            this.cityLetter = cityLetter;
        }

        public String getCityPinYinName() {
            return cityPinYinName;
        }

        public void setCityPinYinName(String cityPinYinName) {
            this.cityPinYinName = cityPinYinName;
        }

        @Override
        public String toString() {
            return "CityNameBean{" +
                    "cityName='" + cityName + '\'' +
                    ", cityPinYinName='" + cityPinYinName + '\'' +
                    ", cityLetter='" + cityLetter + '\'' +
                    '}';
        }
    }
}
