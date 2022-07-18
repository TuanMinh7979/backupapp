package com.simple.simpleapp.Repo;
import com.simple.simpleapp.Model.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface DriverRepo extends JpaRepository<Driver,String > {

}
