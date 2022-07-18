package com.simple.simpleapp.mapsimulator;

import com.simple.simpleapp.Model.Place;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlaceRepo extends JpaRepository<Place, Long> {
    @Query("select  name from Place where name like :kw% ")
    List<String> getAddresssByKw(@Param("kw") String kw);


    @Query("select nodeId from Place where name = :inpName")
    Long getNodeIdByName(@Param("inpName") String inpName);

    String getNameByNodeId(long node_id);



}
