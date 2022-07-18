package com.ivs.map.Repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ivs.map.Model.Customer;

@Repository
public interface CustomerRepo extends JpaRepository<Customer, String>{

}
