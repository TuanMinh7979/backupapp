package com.simple.simpleapp.Repo;

import com.simple.simpleapp.Model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepo extends JpaRepository<Customer, String>{

}
