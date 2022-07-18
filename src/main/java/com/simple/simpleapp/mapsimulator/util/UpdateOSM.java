package com.simple.simpleapp.mapsimulator.util;

import com.simple.simpleapp.mapsimulator.PlaceRepo;
import com.simple.simpleapp.Model.Place;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.CSVWriter;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.net.*;
import java.util.*;

@Slf4j

@Getter
@Setter

@Component
@RequiredArgsConstructor
public class UpdateOSM {


    private List<Node> osmPointNodes = new ArrayList<>();
    private Map<Long, Integer> pointIdIdxMap = new HashMap<>();

    private List<Node> osmWayNodes = new ArrayList<>();
    private Map<Long, Integer> wayIdIdxMap = new HashMap<>();

    private List<InsertData> insertDatas = new ArrayList<>();

    private final PlaceRepo placeRepo;


    public void updateOsmXmlData(String osmFilePath) {
        System.out.println("UPDATE OSM XML FILE:");
        setupPlaceLocation(osmFilePath);
        addPlaceNodeToWay(osmFilePath);
        System.out.println("Update osm xml file data successfully!");
    }


    private void setupPlaceLocation(String osmXmlFilePathToUpdate) {
        System.out.println(">>>SETUP PLACE LOCATION");
        try {

            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

            InputStream is = new FileInputStream(osmXmlFilePathToUpdate);

            DocumentBuilder db = dbf.newDocumentBuilder();

            Document doc = db.parse(is);


            NodeList osmPointNodeList = doc.getElementsByTagName("node");


            //reuse
            //will del
//            StringBuilder sb = new StringBuilder();
            //will del
            //reuse
            System.out.println("Node list size: " + osmPointNodeList.getLength());
            for (int i = 0; i < osmPointNodeList.getLength(); i++) {
                Node nodei = osmPointNodeList.item(i);
                if (nodei.hasChildNodes() && nodei.getChildNodes().getLength() > 3) {
                    String oldlat = nodei.getAttributes().getNamedItem("lat").getTextContent();
                    String oldlong = nodei.getAttributes().getNamedItem("lon").getTextContent();
                    String urlStr = "http://router.project-osrm.org/nearest/v1/driving/" + oldlong + "," + oldlat + "?number=1";
                    System.out.println(">>>>Url get waypoint location: " + urlStr);
                    URL url = null;
                    try {
                        url = new URL(urlStr);

                        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                        conn.setRequestMethod("GET");
                        conn.connect();
                        StringBuilder jsonStrBuilder = new StringBuilder();
                        if (conn.getResponseCode() == 200) {
                            Scanner scan = new Scanner(url.openStream());
                            while (scan.hasNext()) {
                                String temp = scan.nextLine();
                                jsonStrBuilder.append(temp);
                            }
                        }

                        String jsonStr = jsonStrBuilder.toString();

                        // GET DATA
                        JSONObject rsJsonObj = new JSONObject(jsonStr);

                        JSONArray waypoints = (JSONArray) rsJsonObj.get("waypoints");
                        JSONObject nearestWaypoint = (JSONObject) waypoints.get(0);

                        JSONArray nwNodes = (JSONArray) nearestWaypoint.get("nodes");

                        //data
                        Long dataNbNodeId = null;
                        ///data
                        for (int j = 0; j < nwNodes.length(); j++) {
                            Long curNbNodeId = Long.valueOf(String.valueOf(nwNodes.get(j)));
                            if (curNbNodeId > 0) {
                                dataNbNodeId = curNbNodeId;
                                break;
                            }
                        }

                        Long addNodeId = Long.parseLong(nodei.getAttributes().getNamedItem("id").getTextContent());
                        String dataStreetName = String.valueOf(nearestWaypoint.get("name"));
                        dataStreetName = dataStreetName.isEmpty() ? "nullname" : dataStreetName;
                        insertDatas.add(new InsertData(addNodeId, dataNbNodeId, dataStreetName));

                        JSONArray location = (JSONArray) nearestWaypoint.get("location");
                        //data
                        String datalong = String.valueOf(location.get(0));
                        String datalat = String.valueOf(location.get(1));
                        ///data
                        //GET DATA


                        //MODIFY LOCATION

                        nodei.getAttributes().getNamedItem("lat").setTextContent(datalat);
                        nodei.getAttributes().getNamedItem("lon").setTextContent(datalong);

                        //MODIFY LOCATION


                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (ProtocolException e) {
                        e.printStackTrace();
                    }
                }
            }

            Transformer tf = TransformerFactory.newInstance().newTransformer();
            tf.setOutputProperty(OutputKeys.INDENT, "yes");
            tf.setOutputProperty(OutputKeys.STANDALONE, "no");

            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(osmXmlFilePathToUpdate);

            tf.transform(source, result);
            System.out.println(">>>Setup place location successfully");


        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }

    }

    private void addPlaceNodeToWay(String osmXmlFilePathToUpdate) {
        //setup resource
        System.out.println(">>>ADD PLACE NODE TO WAY");
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

            InputStream is = new FileInputStream(osmXmlFilePathToUpdate);

            DocumentBuilder db = dbf.newDocumentBuilder();

            Document doc = db.parse(is);

            NodeList osmPointNodeList = doc.getElementsByTagName("node");

            for (int i = 0; i < osmPointNodeList.getLength(); i++) {
                Node curPointNode = osmPointNodeList.item(i);

                this.osmPointNodes.add(curPointNode);
                Long curWayNodeId = Long.parseLong(curPointNode.getAttributes().getNamedItem("id").getTextContent());
                this.pointIdIdxMap.put(curWayNodeId, i);
            }


            NodeList osmWayNodeList = doc.getElementsByTagName("way");

            for (int i = 0; i < osmWayNodeList.getLength(); i++) {
                Node curWayNode = osmWayNodeList.item(i);

                this.osmWayNodes.add(curWayNode);
                Long curWayNodeId = Long.parseLong(curWayNode.getAttributes().getNamedItem("id").getTextContent());
                this.wayIdIdxMap.put(curWayNodeId, i);
            }

            //ADD NODEID TO WAY
            for (InsertData pairI : insertDatas) {

                Long nbNodeId = pairI.getNbNodeId();
                Long addNodeId = pairI.getAddNodeId();
                String streetName = pairI.getStreetName();

                Long wayId = getWayId(nbNodeId, streetName);
                if (wayId == null) {
                    log.warn(">>>Neighbor " + nbNodeId + " of node " + addNodeId + " is not in any way");
                    continue;
                }

                System.out.println("way id is " + wayId);
                Integer wayIdx = wayIdIdxMap.get(wayId);
                System.out.println("wayidx " + wayIdx);
                if (wayIdx == null) {
                    log.warn(">>>Way idx is null");
                    continue;
                }
                Node wayNode = osmWayNodes.get(wayIdx);
                NodeList wayChildNodeList = wayNode.getChildNodes();


                List<Node> ndNodeList = new ArrayList<>();


                List<Double> wayLonList = new ArrayList<>();
                for (int k = 0; k < wayChildNodeList.getLength(); k++) {
                    Node curWayChildNode = wayChildNodeList.item(k);
                    if (curWayChildNode.getNodeName().equals("nd")) {
                        ndNodeList.add(curWayChildNode);
                        Long curWayChildNodeId = Long.parseLong(curWayChildNode.getAttributes().getNamedItem("ref").getTextContent());
                        wayLonList.add(Double.parseDouble(osmPointNodes.get(pointIdIdxMap.get(curWayChildNodeId)).getAttributes().getNamedItem("lon").getTextContent()));
                    }
                }


                Double firstLong = wayLonList.get(0);
                Double secondLong = wayLonList.get(1);

                Node addPointNode = osmPointNodes.get(pointIdIdxMap.get(addNodeId));
                Double addNodeLong = Double.parseDouble(addPointNode.getAttributes().getNamedItem("lon").getTextContent());

                Element nd = doc.createElement("nd");
                nd.setAttribute("ref", String.valueOf(addNodeId));
                nd.setAttribute("lon", String.valueOf(addNodeLong));
                wayLonList.add(addNodeLong);

                if (firstLong > secondLong) {
                    //decrease
                    Collections.sort(wayLonList, Comparator.reverseOrder());

                } else {
                    //increase
                    Collections.sort(wayLonList);

                }
                int addedEleIdx = wayLonList.indexOf(addNodeLong);

                if (addedEleIdx < (wayLonList.size() - 1)) {

                    Node posNode = ndNodeList.get(addedEleIdx);
                    wayNode.insertBefore(nd, posNode);
                } else if (addedEleIdx == (wayLonList.size() - 1)) {

                    wayNode.appendChild(nd);
                }

            }
            Transformer tf = TransformerFactory.newInstance().newTransformer();
            tf.setOutputProperty(OutputKeys.INDENT, "yes");
            tf.setOutputProperty(OutputKeys.STANDALONE, "no");
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(osmXmlFilePathToUpdate);
            tf.transform(source, result);
            System.out.println(">>>Add new place node to way successfully");

        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
    }


    public void createPlaceDBDataCSV(String outputPlaceDBDataCsvFilePath) {
        try {
            CSVWriter writer = new CSVWriter(new FileWriter(outputPlaceDBDataCsvFilePath));
            System.out.println(">>>SET DB DATA");
            for (InsertData insData : insertDatas) {
                Long nodeId = insData.getAddNodeId();

                Node savedOsmNode = osmPointNodes.get(pointIdIdxMap.get(nodeId));
                NodeList savedOsmNodeTagChilds = savedOsmNode.getChildNodes();

                String placeName = "none";

                for (int i = savedOsmNodeTagChilds.getLength() - 1; i >= 0; i--) {

                    Node tagNode = savedOsmNodeTagChilds.item(i);
                    if (tagNode.getNodeName().equals("tag")
                            && tagNode.getAttributes().getNamedItem("k").getTextContent().equals("name")) {
                        placeName = tagNode.getAttributes().getNamedItem("v").getTextContent();
                        break;
                    }

                }

                if (placeName.equals("none")) continue;
//                place.setNodeId(nodeId);
//                place.setName(placeName);
                System.out.println("Place node Id: " + nodeId + " place name: " + placeName);
                String[] array = new String[]{String.valueOf(nodeId), placeName};
                writer.writeNext(array);
            }
            writer.close();
            System.out.println("Setup place db data to csv file success fully");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public void insertPlaceToDB(String csvPlaceDbPath) {
        placeRepo.deleteAll();
        try {
            CSVReader csvReader = new CSVReaderBuilder(new FileReader(csvPlaceDbPath)).build();
            List<String[]> csvBody = csvReader.readAll();

            for (String[] placei : csvBody) {
                System.out.println("insert nodeId: " + placei[0] + " _ " + "place name: " + placei[1]);
                try {
                    placeRepo.save(new Place(Long.parseLong(placei[0]), placei[1]));
                } catch (Exception e) {

                    e.printStackTrace();
                    Random r = new Random();
                    placeRepo.save(new Place(Long.parseLong(placei[0]), String.valueOf(placei[1] + r.nextInt(100))));
                }
            }
            csvReader.close();
        } catch (FileNotFoundException e) {

        } catch (IOException e) {

            e.printStackTrace();
        }

        System.out.println(">>>Insert Node data to db successfully");
    }


    public Long getWayId(Long nbNodeId, String streetName) {
        String urlStr = "https://api.openstreetmap.org/api/0.6/node/" + nbNodeId + "/ways";
        URL url = null;

        try {
            url = new URL(urlStr);
            URLConnection conn = url.openConnection();
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(conn.getInputStream());

            NodeList osmWayNodes = doc.getElementsByTagName("way");
            String wayIdStr = "";


            System.out.println(">>>>>num of way from " + nbNodeId + " is " + osmWayNodes.getLength());

            if (osmWayNodes.getLength() == 0) return null;
            for (int i = 0; i < osmWayNodes.getLength(); i++) {
                Node wayNodei = osmWayNodes.item(i);
                NodeList wayNodeiTagChilds = wayNodei.getChildNodes();

                for (int j = wayNodeiTagChilds.getLength() - 1; j >= 0; j--) {

                    Node childNode = wayNodeiTagChilds.item(j);

                    if (childNode.getNodeName().equals("tag")) {

                        System.out.println(childNode.getNodeName() + "INFO:" + j + "  streetname" + streetName + " ---key: " + childNode.getAttributes().getNamedItem("k").getTextContent()
                                + "  ---value : " + childNode.getAttributes().getNamedItem("v").getTextContent());


                        if (!streetName.equals("nullname")
                                && childNode.getAttributes().getNamedItem("k").getTextContent().equals("name")
                                && (childNode.getAttributes().getNamedItem("v").getTextContent().equals(streetName)
                                || streetName.indexOf(childNode.getAttributes().getNamedItem("v").getTextContent()) != -1)) {
                            wayIdStr = wayNodei.getAttributes().getNamedItem("id").getTextContent();
                            System.out.println(">>>>>Way id is " + wayIdStr + " in case 1");
                            return Long.parseLong(wayIdStr);

                        }

                        if (streetName.equals("nullname") && !childNode.getAttributes().getNamedItem("k").getTextContent().equals("name")) {
                            wayIdStr = wayNodei.getAttributes().getNamedItem("id").getTextContent();
                            System.out.println(">>>>>Way id is " + wayIdStr + " in case 2");
                            return Long.parseLong(wayIdStr);


                        }


                    }


                }

            }
            //if after all way from this node that do not return any time throw exception
//            if (wayIdStr.isEmpty()) throw new RuntimeException("Way id is not found");
            System.out.println("No way found and return null");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
        return null;

    }
}