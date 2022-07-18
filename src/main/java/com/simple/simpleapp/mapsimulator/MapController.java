package com.simple.simpleapp.mapsimulator;

import com.simple.simpleapp.mapsimulator.util.OSMToCSV;
import com.simple.simpleapp.mapsimulator.algo.searchalgo.DijkstraSearchAlgo;
import com.simple.simpleapp.mapsimulator.dto.PointDto;
import com.simple.simpleapp.mapsimulator.pojo.AdjListGraph;
import com.simple.simpleapp.mapsimulator.pojo.Point;
import com.simple.simpleapp.mapsimulator.util.UpdateOSM;
import com.simple.simpleapp.mapsimulator.util.UploadUtil;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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




}