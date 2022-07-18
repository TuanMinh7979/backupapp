package com.simpleapp.map.mapsimulator.util;


import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.CSVWriter;
import lombok.Getter;
import lombok.Setter;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.springframework.stereotype.Component;


import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Getter
@Setter
@Component
public class OSMToCSV {


    public void toCSVData(String inputOsmXmlFilePath, String outputGraphDataCsvPath) {
        try {
            SAXReader reader = new SAXReader();
            Document document = reader.read(new File(inputOsmXmlFilePath));

            List<Node> osmNodeNodes = document.selectNodes("//node");
            List<Node> osmWayNodes = document.selectNodes("//way");

            setUpNodes(osmNodeNodes, outputGraphDataCsvPath);
            setUpEdges(osmNodeNodes, osmWayNodes, outputGraphDataCsvPath);
            System.out.println("Create data csv file successfully!");


        } catch (DocumentException e) {
            e.printStackTrace();
        }

    }


    private void setUpNodes(List<Node> osmNodeNodes, String outputGraphDataCsvPath) {
        try {
            CSVWriter writer = new CSVWriter(new FileWriter(outputGraphDataCsvPath));
            for (int i = 0; i < osmNodeNodes.size(); i++) {
                Node node = osmNodeNodes.get(i);
                List<String> nodeList = new ArrayList<>();
                nodeList.add(String.valueOf(i));
                nodeList.add(node.valueOf("@id"));
                nodeList.add(node.valueOf("@lat"));
                nodeList.add(node.valueOf("@lon"));
                nodeList.add(" ");

                String[] array = nodeList.toArray(new String[0]);
                writer.writeNext(array);
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //
    private void setUpEdges(List<Node> osmNodeNodes, List<Node> osmWayNodes, String outputGraphDataCsvPath) {

        HashMap<Long, Integer> nodeIdIdxMap = new HashMap<>();
        for (int i = 0; i < osmNodeNodes.size(); i++) {
            Node node = osmNodeNodes.get(i);
            nodeIdIdxMap.put(Long.valueOf(node.valueOf("@id")), i);
        }
        try {
            CSVReader csvReader = new CSVReaderBuilder(new FileReader(outputGraphDataCsvPath)).build();
            List<String[]> csvBody = csvReader.readAll();
            CSVWriter writer = new CSVWriter(new FileWriter(outputGraphDataCsvPath), ',');

            for (Node wayi : osmWayNodes) {
                if (wayi.hasContent()) {
                    List<Node> wayPointNodes = wayi.selectNodes("nd");
                    for (int i = 0; i < wayPointNodes.size() - 1; i++) {
                        Node node1 = wayPointNodes.get(i);
                        int node1MapIdx = nodeIdIdxMap.get(Long.valueOf(node1.valueOf("@ref")));

                        Node node2 = wayPointNodes.get(i + 1);
                        int node2MapIdx = nodeIdIdxMap.get(Long.valueOf(node2.valueOf("@ref")));

                        Node node1OsmNode = osmNodeNodes.get(node1MapIdx);
                        Node node2OsmNode = osmNodeNodes.get(node2MapIdx);
                        Double weight = getWeight(
                                node1OsmNode.valueOf("@lat"),
                                node1OsmNode.valueOf("@lon"),
                                node2OsmNode.valueOf("@lat"),
                                node2OsmNode.valueOf("@lon"));
                        //read file :
                        String oldNode1FileEdges = csvBody.get(node1MapIdx)[4];

                        String oldNode2FileEdges = csvBody.get(node2MapIdx)[4];

                        String add1 = " " + Long.valueOf(node2.valueOf("@ref")) + ":" + weight;
                        String add2 = " " + Long.valueOf(node1.valueOf("@ref")) + ":" + weight;
                        oldNode1FileEdges += add1;
                        oldNode2FileEdges += add2;

                        //write to csv file
                        csvBody.get(node1MapIdx)[4] = oldNode1FileEdges.trim();
                        csvBody.get(node2MapIdx)[4] = oldNode2FileEdges.trim();


                    }
                }
            }
            writer.writeAll(csvBody);
            writer.flush();
            writer.close();

            csvReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    public Double getWeight(String srcLat, String srcLon, String desLat, String desLon) {

        double startLat = Double.parseDouble(srcLat);
        double startLon = Double.parseDouble(srcLon);

        double endLat = Double.parseDouble(desLat);
        double endLon = Double.parseDouble(desLon);

        return Haversine.distance(startLat, startLon, endLat, endLon);
    }


//    public static void main(String[] args) {
//
//        OSMToCSV osmToCSV = new OSMToCSV("testInpuFile", "testOutputFile");
//        osmToCSV.toCSVData();
//
//    }


}