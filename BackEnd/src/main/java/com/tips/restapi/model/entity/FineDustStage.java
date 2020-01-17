package com.tips.restapi.model.entity;

import lombok.Data;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Table;
import javax.persistence.Id;

@Data
public class FineDustStage implements Serializable
{
    private String _returntype;
    private String cograde;
    private String covalue;
    private String dataterm;
    private String datatime;
    private String khaigrade;
    private String khaivalue;
    private String mangname;
    private String no2grade;
    private String no2value;
    private String numofrows;
    private String o3grade;
    private String o3value;
    private String pageno;
    private String pm10grade;
    private String pm10grade1h;
    private String pm10value;
    private String pm10value24;
    private String pm25grade;
    private String pm25grade1h;
    private String pm25value;
    private String pm25value24;
    private String resultcode;
    private String resultmsg;
    private String rnum;
    private String servicekey;
    private String sidoname;
    private String so2grade;
    private String so2value;
    private String stationcode;
    private String stationname;
    private String totalcount;
    private String ver;
}
