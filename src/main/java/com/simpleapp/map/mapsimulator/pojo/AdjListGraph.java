package com.simpleapp.map.mapsimulator.pojo;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

@Component
@Getter
@Setter

public class AdjListGraph {
    //nodeid => index in list
    private Map<Long, Point> data = new HashMap<>();

//    private List<AdjListGraphItem> data = new ArrayList<>();


    public void setupGraph(String csvFilePath) {
        try {
            CSVReader csvReader = new CSVReaderBuilder(new FileReader(csvFilePath))
                    .build();
            List<String[]> csvBody = csvReader.readAll();
//            System.out.println("Data size: " + csvBody.size());
            if (!data.isEmpty()) {
                System.out.println("data not empty " + data.size());
                data = new HashMap<>();
            }


            for (String[] csvLine : csvBody) {
                Point point = new Point();
                point.setId(Long.valueOf(csvLine[1]));
                point.setLat(csvLine[2]);
                point.setLon(csvLine[3]);


                String fileEdgesStr = csvLine[4];
                List<Edge> edgesData = new ArrayList<>();
                List<String> fileEdges = new ArrayList<>(Arrays.asList(fileEdgesStr.split(" ")));
                for (String edgei : fileEdges) {
                    String[] edgePair = edgei.split(":");
                    Edge newEdge = new Edge();
                    newEdge.setNeighBourId(Long.valueOf(edgePair[0]));
                    newEdge.setWeight(Double.valueOf(edgePair[1]));
                    edgesData.add(newEdge);
                }
                point.setEdges(edgesData);


                data.put(Long.valueOf(csvLine[1]), point);

            }
            csvReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}

