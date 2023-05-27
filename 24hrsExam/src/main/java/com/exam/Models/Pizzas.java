package com.exam.Models;

public class Pizzas {
	
	private String pizza_id;
	private String name;
	private String ingredients;
	private String size;
	private double price = 0;
	private String type;
	public String getPizza_id() {
		return pizza_id;
	}
	public void setPizza_id(String pizza_id) {
		this.pizza_id = pizza_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIngredients() {
		return ingredients;
	}
	public void setIngredients(String ingredients) {
		this.ingredients = ingredients;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	
	
}
