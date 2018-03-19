package com.example.demo.model;

import java.util.Date;

import org.springframework.data.annotation.Id;

public class Display {
	
	@Id
	private String code;
	private String name;

	private String group_no;
	private String group_name;
	
	private Float current_price;
	private Float base_price;
	private String ratio;
	
	//private String color;
	
	private Integer level1;
	private Integer level2;
	private Integer level3;
	private Integer level4;
	private Integer level5;
	
	private String color = "";
	
	private Integer oncolor = 0;

	public Integer getOncolor() {
		return oncolor;
	}


	public void setOncolor(Integer oncolor) {
		this.oncolor = oncolor;
	}


	public String getColor() {
		return color;
	}


	public void setColor(String color) {
		this.color = color;
	}

	public Display() {
		// TODO Auto-generated constructor stub
	}


	public String getCode() {
		return code;
	}


	public void setCode(String code) {
		this.code = code;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getGroup_no() {
		return group_no;
	}


	public void setGroup_no(String group_no) {
		this.group_no = group_no;
	}


	public String getGroup_name() {
		return group_name;
	}


	public void setGroup_name(String group_name) {
		this.group_name = group_name;
	}


	public float getCurrent_price() {
		return current_price;
	}


	public void setCurrent_price(Float current_price) {
		this.current_price = current_price;
	}


	public float getBase_price() {
		return base_price;
	}


	public void setBase_price(Float base_price) {
		this.base_price = base_price;
	}


	public String getRatio() {
		return ratio;
	}


	public void setRatio(String ratio) {
		this.ratio = ratio;
	}

	public float getLevel1() {
		return level1;
	}


	public void setLevel1(Integer level1) {
		this.level1 = level1;
	}


	public float getLevel2() {
		return level2;
	}


	public void setLevel2(Integer level2) {
		this.level2 = level2;
	}


	public float getLevel3() {
		return level3;
	}


	public void setLevel3(Integer level3) {
		this.level3 = level3;
	}


	public float getLevel4() {
		return level4;
	}


	public void setLevel4(Integer level4) {
		this.level4 = level4;
	}


	public float getLevel5() {
		return level5;
	}


	public void setLevel5(Integer level5) {
		this.level5 = level5;
	}
}
