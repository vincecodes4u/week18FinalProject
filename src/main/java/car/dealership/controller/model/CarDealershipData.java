package car.dealership.controller.model;

import java.util.HashSet;
import java.util.Set;

import car.dealership.entity.CarDealership;
import car.dealership.entity.Customer;
import car.dealership.entity.Employee;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CarDealershipData {
	private Long carDealershipId;
	private String carDealershipName;
	private String carDealershipAddress;
	private String carDealershipCity;
	private String carDealershipState;
	private String carDealershipZip;
	private String carDealershipPhone;
	private Set<CarDealershipCustomer> customers = new HashSet<>();
	private Set<CarDealershipEmployee> employees = new HashSet<>();
	
	public CarDealershipData(CarDealership carDealership) {
		carDealershipId = carDealership.getCarDealershipId();
		carDealershipName = carDealership.getCarDealershipName();
		carDealershipAddress = carDealership.getCarDealershipAddress();
		carDealershipCity = carDealership.getCarDealershipCity();
		carDealershipState = carDealership.getCarDealershipState();
		carDealershipZip = carDealership.getCarDealershipZip();
		carDealershipPhone = carDealership.getCarDealershipPhone();
		
		for (Customer customer : carDealership.getCustomers()) {
			
			customers.add(new CarDealershipCustomer(customer));
		}
		for (Employee employee : carDealership.getEmployees()) {
		
			employees.add(new CarDealershipEmployee(employee));
		}
	}
	
	@Data
	@NoArgsConstructor 
	public static class CarDealershipEmployee {
		private Long employeeId;
		private String employeeFirstName;
		private String employeeLastName;
		private String employeePhone;
		private String employeeJobTitle;
		
		public CarDealershipEmployee(Employee employee) {
			employeeId = employee.getEmployeeId();
			employeeFirstName = employee.getEmployeeFirstName();
			employeeLastName = employee.getEmployeeLastName();
			employeePhone = employee.getEmployeePhone();
			employeeJobTitle = employee.getEmployeeJobTitle();	
		}
	}
	
	@Data
	@NoArgsConstructor
	public static class CarDealershipCustomer {
		
		private Long customerId;
		private String customerFirstName;
		private String customerLastName;
		private String customerEmail;
		
		public CarDealershipCustomer(Customer customer) {
			customerId = customer.getCustomerId();
			customerFirstName = customer.getCustomerFirstName();
			customerLastName = customer.getCustomerLastName();
			customerEmail = customer.getCustomerEmail();
			}
		}		
	}	

