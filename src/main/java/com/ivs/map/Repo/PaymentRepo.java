package com.ivs.map.Repo;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ivs.map.Model.Payment;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepo extends JpaRepository<Payment,Integer >{

}
