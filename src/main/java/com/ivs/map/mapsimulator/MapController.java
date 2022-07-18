package com.ivs.map.mapsimulator;

import com.ivs.map.mapsimulator.algo.searchalgo.DijkstraSearchAlgo;
import com.ivs.map.mapsimulator.dto.PointDto;
import com.ivs.map.mapsimulator.pojo.AdjListGraph;
import com.ivs.map.mapsimulator.pojo.Point;
import com.ivs.map.mapsimulator.util.OSMToCSV;
import com.ivs.map.mapsimulator.util.UpdateOSM;
import com.ivs.map.mapsimulator.util.UploadUtil;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("map")
@RequiredArgsConstructor
@Setter
@Getter

public class MapController {


    private final DijkstraSearchAlgo dijkstraSearchAlgo;
    private final AdjListGraph adjListGraph;
    private final UploadUtil uploadUtil;
    private final UpdateOSM updateOSM;
    private final OSMToCSV osmToCSV;
    private final PlaceRepo placeRepo;

    private final String PROJECT_ROOT = System.getProperty("user.dir");

    private String graphPath = PROJECT_ROOT + "/src/main/webapp/mapsimulator/graphcsv/default.csv";


    @RequestMapping("")
    public String index(Model model
    ) {

        Map<Long, Point> data = adjListGraph.getData();
        List<Long> keyList = data.keySet().stream().collect(Collectors.toList());
        Long middleKey = keyList.get(data.size() / 2);
        Point middleItem = data.get(middleKey);


        model.addAttribute("longitude", middleItem.getLon());
        model.addAttribute("latitude", middleItem.getLat());
        return "/mapsimulator/map";
    }


    @PostMapping("setup")
    public String addUpdateAndCreateDbCsvDataFromXml(@RequestParam("file") MultipartFile file) {
        File savedFile = uploadUtil.handelUploadFile(file);
        String osmPath = savedFile.getPath();
        updateOSM.updateOsmXmlData(osmPath);
        //for database

        String newPlaceDbCsvPath = PROJECT_ROOT + "/src/main/webapp/mapsimulator/dbcsv/" + file.getOriginalFilename().substring(0, file.getOriginalFilename().indexOf(".")) + ".csv";
        updateOSM.createPlaceDBDataCSV(newPlaceDbCsvPath);

        String newGraphDataCsvPath = newPlaceDbCsvPath.replaceFirst("dbcsv", "graphcsv");
        osmToCSV.toCSVData(osmPath, newGraphDataCsvPath);

        return "redirect:/map/setup";
    }

    @GetMapping("/setup/setup-csvdata/{dataFileName}")
    @ResponseBody
    public ResponseEntity<String> changeGraphMap(@PathVariable String dataFileName) {
        File graphCsvPath = new File(PROJECT_ROOT + "/src/main/webapp/mapsimulator/graphcsv/" + dataFileName + ".csv");
        if (!graphCsvPath.exists()) return new ResponseEntity<>("failed", HttpStatus.BAD_REQUEST);
        setGraphPath(PROJECT_ROOT + "/src/main/webapp/mapsimulator/graphcsv/" + dataFileName + ".csv");

        File dbCsvPath = new File(PROJECT_ROOT + "/src/main/webapp/mapsimulator/dbcsv/" + dataFileName + ".csv");
        if (!dbCsvPath.exists()) return new ResponseEntity<>("failed", HttpStatus.BAD_REQUEST);
        updateOSM.insertPlaceToDB(PROJECT_ROOT + "/src/main/webapp/mapsimulator/dbcsv/" + dataFileName + ".csv");
        hdleSetupGraph();
        return new ResponseEntity<>("success", HttpStatus.OK);
    }


    public void hdleSetupGraph() {
        adjListGraph.setupGraph(graphPath);
//        System.out.println(">>>Setup csv to graph success");

    }


    //map/api/search
    @GetMapping("api/search")
    @ResponseBody
    public List<PointDto> getShortestPath(
            @RequestParam(value = "source") String srcStr,
            @RequestParam(value = "destination") String desStr) {
        Long srcNodeId = placeRepo.getNodeIdByName(srcStr);
        Long desNodeId = placeRepo.getNodeIdByName(desStr);
        System.out.println("SOURCE NODE ID: " + srcNodeId);
        System.out.println("DESTINATION NODE ID: " + desNodeId);


        return dijkstraSearchAlgo.findShortestPath(srcNodeId, desNodeId);
    }


    //Ajax
    //map/ajax/place-search-data
    @GetMapping("ajax/place-search-data")
    @ResponseBody
    public List<String> getPlaceByAddressStr(@RequestParam("term") String kw) {

        return placeRepo.getAddresssByKw(kw).stream().map(address -> address.trim()).collect(Collectors.toList());
    }

    @GetMapping(value = "/customer")
    public String showCreateTripPage(Model model) {
        Map<Long, Point> data = adjListGraph.getData();
        List<Long> keyList = data.keySet().stream().collect(Collectors.toList());
        Long middleKey = keyList.get(data.size() / 2);
        Point middleItem = data.get(middleKey);

        model.addAttribute("longitude", middleItem.getLon());
        model.addAttribute("latitude", middleItem.getLat());
        return "/mapsimulator/customer-map";
    }

    @GetMapping("/setup/delete-csvdata/{dataFileName}")
    @ResponseBody
    public ResponseEntity<String> deleteMap(@PathVariable String dataFileName) {
        File graphCsvPath = new File(PROJECT_ROOT + "/src/main/webapp/mapsimulator/graphcsv/" + dataFileName + ".csv");
        if (graphCsvPath.exists())
            graphCsvPath.delete();

        File dbCsvPath = new File(PROJECT_ROOT + "/src/main/webapp/mapsimulator/dbcsv/" + dataFileName + ".csv");
        if (dbCsvPath.exists()) dbCsvPath.delete();

        File graphXmlPath = new File(PROJECT_ROOT + "/src/main/webapp/mapsimulator/osm/" + dataFileName + ".xml");
        if (graphXmlPath.exists())
            graphXmlPath.delete();
        File graphOsmPath = new File(PROJECT_ROOT + "/src/main/webapp/mapsimulator/osm/" + dataFileName + ".osm");
        if (graphOsmPath.exists())
            graphOsmPath.delete();
        return new ResponseEntity<>("delete map success", HttpStatus.OK);
    }

    @RequestMapping("maptest")
    public String indexTest(Model model
    ) {
        return "/mapsimulator/maptest";
    }



}