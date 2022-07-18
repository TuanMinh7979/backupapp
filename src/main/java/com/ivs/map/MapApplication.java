package com.ivs.map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.ivs.map.mapsimulator.MapController;


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
