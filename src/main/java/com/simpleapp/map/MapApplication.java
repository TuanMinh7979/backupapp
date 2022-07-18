package com.simpleapp.map;

import com.simpleapp.map.mapsimulator.MapController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class MapApplication implements CommandLineRunner {
    @Autowired
    private MapController mapController;

    public static void main(String[] args) {
        SpringApplication.run(MapApplication.class, args);
    }


    @Override
    public void run(String... args) throws Exception {
        mapController.hdleSetupGraph();
    }


}
