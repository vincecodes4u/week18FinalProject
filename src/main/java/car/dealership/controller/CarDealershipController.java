package car.dealership.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import car.dealership.controller.model.CarDealershipData;
import car.dealership.controller.model.CarDealershipData.CarDealershipCustomer;
import car.dealership.controller.model.CarDealershipData.CarDealershipEmployee;
import car.dealership.service.CarDealershipService;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class CarDealershipController {
	@Autowired
	private CarDealershipService carDealershipService;

	@PostMapping("/car_dealership")
	@ResponseStatus(code = HttpStatus.CREATED)
    public CarDealershipData createCarDealership(@RequestBody CarDealershipData carDealershipData) {
	log.info("Creating car dealership {}", carDealershipData);
	
	return carDealershipService.saveCarDealership(carDealershipData);
	}
	
	@PutMapping("/car_dealership/{carDealershipId}")
	public CarDealershipData updateCarDealership(@PathVariable Long carDealershipId, @RequestBody CarDealershipData carDealershipData) {
		carDealershipData.setCarDealershipId(carDealershipId);
		log.info("Updating car dealership {}", carDealershipData);
		return carDealershipService.saveCarDealership(carDealershipData);
	}
	
	@PostMapping("/car_dealership/{carDealershipId}/employee")
	@ResponseStatus(code = HttpStatus.CREATED)
	public CarDealershipEmployee insertCarDealershipEmployee(@PathVariable Long carDealershipId,
			@RequestBody CarDealershipEmployee carDealershipEmployee) {
		log.info("Creating employee () for cardealership with ID = ()", carDealershipEmployee.getEmployeeId(), carDealershipId);
		return carDealershipService.saveEmployee(carDealershipId, carDealershipEmployee);
	}
	
	@PutMapping("/car_dealership/{carDealershipId}/employee")
	public CarDealershipEmployee updateCarDealershipEmployee(@PathVariable Long carDealershipId,
			@RequestBody CarDealershipEmployee carDealershipEmployee) {
		log.info("Updating employee () for cardealership with ID = ()", carDealershipEmployee.getEmployeeId(), carDealershipId);
		return carDealershipService.saveEmployee(carDealershipId, carDealershipEmployee);
	}
	
	@PostMapping("/car_dealership/{carDealershipId}/customer")
	@ResponseStatus(code = HttpStatus.CREATED)
	public CarDealershipCustomer insertCarDealershipCustomer(@PathVariable Long carDealershipId, @RequestBody CarDealershipCustomer carDealershipCustomer) {
		log.info("Creating customer {} for car dealership with ID = {}", carDealershipCustomer.getCustomerId(), carDealershipId);
		return carDealershipService.saveCustomer(carDealershipId, carDealershipCustomer);
	}
	
	@PutMapping("/car_dealership/{carDealershipId}/customer")
	public CarDealershipCustomer updateCarDealershipCustomer(@PathVariable Long carDealershipId, @RequestBody CarDealershipCustomer carDealershipCustomer) {
		log.info("Updating customer {} for car dealership with ID = ()", carDealershipCustomer.getCustomerId(), carDealershipId);
		return carDealershipService.saveCustomer(carDealershipId, carDealershipCustomer);
	}
	
	@GetMapping("/car_dealership")
	public List<CarDealershipData> ListAllCarDealerships(){
		log.info("Listing all car dealerships");
		return carDealershipService.retrieveAllCarDealerships();
}
	
	@GetMapping("/car_dealership/{carDealershipId}")
	public CarDealershipData getCarDealershipById(@PathVariable Long carDealershipId) {
		log.info("Retrieving car dealership with ID=()", carDealershipId);
		return carDealershipService.retrieveCarDealershipById(carDealershipId);
	}
	
	@DeleteMapping("/car_dealership/{carDealershipId}")
	public Map<String, String> deleteCarDealershipById(@PathVariable Long carDealershipId) {
		log.info("Recieved request to delete car dealership with ID: " + carDealershipId + ".");
		carDealershipService.deleteCarDealershipById(carDealershipId);
		return Map.of("Message", "Car Dealership with ID = " + carDealershipId + " was deleted successfully");
	}
}





