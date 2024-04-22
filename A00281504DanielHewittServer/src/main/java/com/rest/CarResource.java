package com.rest;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/cars")
public class CarResource {

	@GET
	@Produces({MediaType.APPLICATION_XML})
	public List<Car> getCars(){
		return CarDAO.instance.getCars();
	}
	
	@GET
	@Path("/{carId}")
	@Produces({MediaType.APPLICATION_XML})
	public Car getCar(@PathParam("carId") int id){
		return CarDAO.instance.getCar(id);
	}
	
	@POST
	@Path("/insert/{carId}/{carBrand}/{carModel}/{carDoors}")
	@Consumes(MediaType.APPLICATION_XML)
	public void insertCar(@PathParam("carId") int id, @PathParam("carBrand") String brand, @PathParam("carModel") String model, @PathParam("carDoors") int doors) {
		CarDAO.instance.insertCar(id, brand, model, doors);
	}
	
	@PUT
	@Path("/update/{carId}/{carBrand}/{carModel}/{carDoors}")
	@Consumes(MediaType.APPLICATION_XML)
	public void updateCar(@PathParam("carId") int id, @PathParam("carBrand") String brand, @PathParam("carModel") String model, @PathParam("carDoors") int doors) {
		CarDAO.instance.updateCar(id, brand, model, doors);
	}
	
	@DELETE
	@Path("/{carId}")
	public void deleteCar(@PathParam("carId") int id) {
		CarDAO.instance.deleteCar(id);
	}
}
