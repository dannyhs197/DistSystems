package com.rest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public enum CarDAO {
	
	instance;
	
	Connection con;
	
	private CarDAO() {
		try {
			Class.forName("org.hsqldb.JDBCDriver");
			
			con = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/oneDB;ifexists=true", "SA", "Passw0rd");
		} catch (ClassNotFoundException | SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	public List<Car> getCars() {
		List<Car> cars = new ArrayList<Car>();
		try {
			//Currently throwing a null connection error
			System.out.println(con);
			PreparedStatement statement = con.prepareStatement("SELECT * FROM Car");
			ResultSet rs = statement.executeQuery();
			
			while(rs.next()) {
				int id = rs.getInt("ID");
				String brand = rs.getString("Brand");
				String model = rs.getString("Model");
				int doors = rs.getInt("Doors");
				
				cars.add(new Car(id, brand, model, doors));
			}
			
			rs.close();
			statement.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return cars;
	}
	
	public Car getCar(int id) {
		Car car = new Car();
		
		try {
			PreparedStatement statement = con.prepareStatement("SELECT * FROM Car WHERE ID = ?");
			statement.setInt(1, id);
			ResultSet rs = statement.executeQuery();
			
			while(rs.next()) {
				car = new Car(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4));
			}
			
			statement.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return car;
	}
	
	public void insertCar(int id, String brand, String model, int doors) {
		try {
			PreparedStatement statement = con.prepareStatement("INSERT INTO Car (ID, Brand, Model, Doors) VALUES (?, ?, ?, ?)");
			statement.setInt(1, id);
			statement.setString(2, brand);
			statement.setString(3, model);
			statement.setInt(4, doors);
			statement.executeUpdate();
			statement.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void updateCar(int id, String brand, String model, int doors) {
		try {
			PreparedStatement statement = con.prepareStatement("UPDATE Car SET Brand = ?, Model = ?, Doors = ? WHERE ID = ?");
			statement.setString(1, brand);
			statement.setString(2, model);
			statement.setInt(3, doors);
			statement.setInt(4, id);
			statement.executeUpdate();
			statement.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void deleteCar(int id) {
		try {
			PreparedStatement statement = con.prepareStatement("DELETE FROM Car WHERE ID = ?");
			statement.setInt(1, id);
			statement.executeUpdate();
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
