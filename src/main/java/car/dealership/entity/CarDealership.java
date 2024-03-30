package car.dealership.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;


@Entity
@Data
public class CarDealership {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long carDealershipId;
	private String carDealershipName;
	private String carDealershipAddress;
	private String carDealershipCity;
	private String carDealershipState;
	private String carDealershipZip;
	private String carDealershipPhone;
	
//one to many relationships
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@OneToMany(mappedBy = "carDealership", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Employee> employees = new HashSet<>();
	
	

	
//many to many relationship	
	
//	@ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
//	private Set<CarManufacturer> carmanufacturers = new HashSet<>();
	
//	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
//	private Set<Car> cars = new HashSet<>();
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@ManyToMany(cascade = CascadeType.PERSIST)
	@JoinTable(name = "car_dealership_customer", joinColumns = @JoinColumn(name = "car_dealership_id"),
	inverseJoinColumns = @JoinColumn(name = "customer_id"))
	private Set<Customer> customers = new HashSet<>();


}
