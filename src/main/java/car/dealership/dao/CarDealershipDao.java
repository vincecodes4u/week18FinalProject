package car.dealership.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import car.dealership.entity.CarDealership;

public interface CarDealershipDao extends JpaRepository<CarDealership, Long> {

}
