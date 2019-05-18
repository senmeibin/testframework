package com.bpms.cmn.entity.ext;

import com.bpms.cmn.entity.City;

import javax.persistence.Entity;
import javax.persistence.Table;

//注：防止共通模块与业务模块Entity同名问题，在共通模块的Entity中加入带前缀的name属性
@Entity(name = "CmnCity")
@Table(name = "cmn_city")
public class CityExt extends City {
    private static final long serialVersionUID = -20160413172542802L;
}