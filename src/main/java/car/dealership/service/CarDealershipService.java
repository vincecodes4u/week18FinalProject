package car.dealership.service;

import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import car.dealership.controller.model.CarDealershipData;
import car.dealership.controller.model.CarDealershipData.CarDealershipCustomer;
import car.dealership.controller.model.CarDealershipData.CarDealershipEmployee;
import car.dealership.dao.CarDealershipDao;
import car.dealership.dao.CustomerDao;
import car.dealership.dao.EmployeeDao;
import car.dealership.entity.CarDealership;
import car.dealership.entity.Customer;
import car.dealership.entity.Employee;
@Service
public class CarDealershipService {

	@Autowired
	private CarDealershipDao carDealershipDao;
	
	@Autowired
	private EmployeeDao employeeDao;
	
	@Autowired
	private CustomerDao customerDao;
	
	@Transactional(readOnly = false)
	public CarDealershipData saveCarDealership(CarDealershipData carDealershipData) {
		CarDealership carDealership = findOrCreateCarDealership(carDealershipData.getCarDealershipId());
		copyCarDealershipFields(carDealership, carDealershipData);
		CarDealership dbCarDealership = carDealershipDao.save(carDealership);
		return new CarDealershipData(dbCarDealership);
	}
	private void copyCarDealershipFields(CarDealership carDealership, CarDealershipData carDealershipData) {
		carDealership.setCarDealershipId(carDealershipData.getCarDealershipId());
		carDealership.setCarDealershipName(carDealershipData.getCarDealershipName());
		carDealership.setCarDealershipAddress(carDealershipData.getCarDealershipAddress());
		carDealership.setCarDealershipCity(carDealershipData.getCarDealershipCity());
		carDealership.setCarDealershipState(carDealershipData.getCarDealershipState());
		carDealership.setCarDealershipZip(carDealershipData.getCarDealershipZip());
		carDealership.setCarDealershipPhone(carDealershipData.getCarDealershipPhone());
	}
	
	private CarDealership findOrCreateCarDealership(Long carDealershipId) {
		CarDealership carDealership;
		
		if(Objects.isNull(carDealershipId)) {
			carDealership = new CarDealership();
		} else {
			carDealership = findCarDealershipById(carDealershipId);
		}
		return carDealership;
	}
	private CarDealership findCarDealershipById(Long carDealershipId) {
		return carDealershipDao.findById(carDealershipId).orElseThrow(() -> new NoSuchElementException("Car Dealership with Id=" + carDealershipId + " does not exist"));
	}
	
	@Transactional(readOnly = false)
	public CarDealershipEmployee saveEmployee(Long carDealershipId, CarDealershipEmployee carDealershipEmployee) {
		CarDealership carDealership = findCarDealershipById(carDealershipId);
		
		Employee employee = findOrCreateEmployee(carDealershipId, carDealershipEmployee.getEmployeeId());
		copyEmployeeFields(employee, carDealershipEmployee);
		
		employee.setCarDealership(carDealership);
		carDealership.getEmployees().add(employee);
		
		return new CarDealershipEmployee (employeeDao.save(employee));
	}
	
	private void copyEmployeeFields(Employee employee, CarDealershipEmployee carDealershipEmployee) {
		employee.setEmployeeId(carDealershipEmployee.getEmployeeId());
		employee.setEmployeeFirstName(carDealershipEmployee.getEmployeeFirstName());
		employee.setEmployeeLastName(carDealershipEmployee.getEmployeeLastName());
		employee.setEmployeePhone(carDealershipEmployee.getEmployeePhone());
		employee.setEmployeeJobTitle(employee.getEmployeeJobTitle());
		}
	
	private Employee findOrCreateEmployee(Long carDealershipId, Long employeeId) {
		if(Objects.isNull(employeeId)) {
			return new Employee();
		}else {
			return findEmployeeById(carDealershipId, employeeId);
		}
	}
	
	private Employee findEmployeeById(Long carDealershipId, Long employeeId) {
		Employee employee = employeeDao.findById(employeeId)
				.orElseThrow(() -> new NoSuchElementException("Employee with ID=" + employeeId + " was not found."));
		if(employee.getCarDealership().getCarDealershipId() != carDealershipId ) {
			throw new IllegalArgumentException(
					"Employee with ID =" + employeeId + " does not work at car dealership with ID= " + carDealershipId);
			}
		return employee;
	}
	
	@Transactional(readOnly = false)
	public CarDealershipCustomer saveCustomer(Long carDealershipId, CarDealershipCustomer carDealershipCustomer) {
		CarDealership carDealership = findCarDealershipById(carDealershipId);
		Customer customer = findOrCreateCustomer(carDealershipId,carDealershipCustomer.getCustomerId());
		
		copyCustomerFields(customer, carDealershipCustomer);
		customer.getCarDealerships().add(carDealership);
		carDealership.getCustomers().add(customer);
		return new CarDealershipCustomer (customerDao.save(customer));
	}
	
	private void copyCustomerFields(Customer customer, CarDealershipCustomer carDealershipCustomer) {
		customer.setCustomerId(carDealershipCustomer.getCustomerId());
		customer.setCustomerFirstName(carDealershipCustomer.getCustomerFirstName());
		customer.setCustomerLastName(carDealershipCustomer.getCustomerLastName());
		customer.setCustomerEmail(carDealershipCustomer.getCustomerEmail());
	}
	
	private Customer findOrCreateCustomer(Long carDealershipId, Long customerId) {
		if(Objects.isNull(customerId)) {
			return new Customer();
		}else {
			return findCustomerById(carDealershipId, customerId);
		}
	}
	
	private Customer findCustomerById(Long carDealershipId, Long customerId) {
		Customer customer = customerDao.findById(customerId)
				.orElseThrow(() -> new NoSuchElementException("Customer with ID=" + customerId + " was not found"));
		boolean found = false;
		
		for (CarDealership carDealership: customer.getCarDealerships()) {
			if (carDealership.getCarDealershipId() == carDealershipId) {
				found = true;
				break;
			}
		}
	if(!found) {
		throw new IllegalArgumentException(
				"Customer with ID=" + customerId + " does not shop at pet store with ID=" + carDealershipId);
	}
	return customer;	
	}
	
	@Transactional(readOnly = true)
	public List<CarDealershipData> retrieveAllCarDealerships() {
		List<CarDealershipData> carDealershipData = new LinkedList<>();
		  
		for (CarDealership carDealership: carDealershipDao.findAll()) {
			CarDealershipData psd = new CarDealershipData(carDealership);
			
			psd.getCustomers().clear();
			psd.getEmployees().clear();
			carDealershipData.add(psd);
		}
	return carDealershipData;
	}
	
	@Transactional(readOnly = true)
	public CarDealershipData retrieveCarDealershipById(Long carDealershipId) {
		return new CarDealershipData(findCarDealershipById(carDealershipId));	
		}
	public void deleteCarDealershipById(Long carDealershipId) {
		CarDealership carDealership = findCarDealershipById(carDealershipId);
		
		carDealershipDao.delete(carDealership);
		}
} //END
