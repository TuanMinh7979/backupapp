package com.ivs.map.Repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ivs.map.Model.Account;
import com.ivs.map.Model.Customer;
import com.ivs.map.Model.Driver;
import com.ivs.map.Model.State;
import com.ivs.map.Model.Trip;
@Repository
public interface TripRepo extends JpaRepository<Trip, Long> {

	//List<Trip> findByDriver(Driver dr);
	@Query(value = "SELECT * FROM Trip WHERE phone=?1",
			countQuery = "SELECT count(*) FROM Trip WHERE phone=?1",
			nativeQuery = true)
	Page<Trip> findByDriver(String phone, Pageable pageable);
	//List<Trip> findByCustomer(Customer customer);
	@Query(value = "SELECT * FROM Trip WHERE cus_phone=?1",
			countQuery = "SELECT count(*) FROM Trip WHERE cus_phone=?1",
			nativeQuery = true)
	Page<Trip> findByCustomer(String cus_phone, Pageable pageable);
	List<Trip> findByDriverAndState(Driver dr, State st);
	List<Trip> findByState(State st);
	List<Trip> findByCustomerOrderByTripIdDesc(Customer customer);

	List<Trip> findByCustomerAndState(Customer customer, State st);

}