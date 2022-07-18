package com.simpleapp.map.Repo;
import com.simpleapp.map.Model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepo extends JpaRepository<Payment,Integer >{

}
