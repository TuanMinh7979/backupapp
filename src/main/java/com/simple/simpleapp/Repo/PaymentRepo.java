package com.simple.simpleapp.Repo;
import com.simple.simpleapp.Model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepo extends JpaRepository<Payment,Integer >{

}
