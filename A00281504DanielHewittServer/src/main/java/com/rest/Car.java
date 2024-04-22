package com.rest;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "car")
@XmlType(propOrder = {"id", "brand", "model", "doors"})
public class Car {
	
	private int id;
	private String brand;
	private String model;
	private int doors;
	
	public Car() {}
	
	public Car(int id, String brand, String model, int doors) {
		this.id = id;
		this.brand = brand;
		this.model = model;
		this.doors = doors;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public int getDoors() {
		return doors;
	}

	public void setDoors(int doors) {
		this.doors = doors;
	}
}
