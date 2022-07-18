package com.simpleapp.map.Repo;
import com.simpleapp.map.Model.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface DriverRepo extends JpaRepository<Driver,String > {

}
