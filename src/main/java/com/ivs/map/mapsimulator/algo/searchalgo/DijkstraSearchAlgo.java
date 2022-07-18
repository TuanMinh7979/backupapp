package com.ivs.map.mapsimulator.algo.searchalgo;


import com.ivs.map.mapsimulator.dto.PointDto;
import com.ivs.map.mapsimulator.mapper.PointMapper;
import com.ivs.map.mapsimulator.pojo.AdjListGraph;
import com.ivs.map.mapsimulator.pojo.Edge;
import com.ivs.map.mapsimulator.pojo.Point;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@RequiredArgsConstructor
public class DijkstraSearchAlgo extends AbstractSearchAlgo {

    private final AdjListGraph adjListGraph;
    private final PointMapper pointMapper;
    private Map<Long, Point> data = new HashMap<>();


    public List<PointDto> findShortestPath(Long srcNodeId, Long targetId) {

        data = adjListGraph.getData();



        Map<Long, Boolean> visitedNodeMarks = new HashMap<>();
        List<PointDto> visitedNodes = new ArrayList<>();


        PointDto srcPointDto = pointMapper.toDto(data.get(srcNodeId));


        srcPointDto.setDistance(0.0);

        PriorityQueue<PointDto> priorityQueue = new PriorityQueue<>();
        priorityQueue.add(srcPointDto);

        visitedNodeMarks.put(srcPointDto.getId(), true);
        visitedNodes.add(srcPointDto);


        while (!priorityQueue.isEmpty()) {
            PointDto curPointDto = priorityQueue.poll();

            //add
            Point curItem = data.get(curPointDto.getId());
            //add
            List<Edge> curEdges = curItem.getEdges();

            for (Edge edgei : curEdges) {
                long neighBourId = edgei.getNeighBourId();
                PointDto neighBouri = pointMapper.toDto(data.get(neighBourId));

                if (visitedNodeMarks.get(neighBouri.getId()) == null || visitedNodeMarks.get(neighBouri.getId()) == false) {

                    Double newDis = curPointDto.getDistance() + edgei.getWeight();

                    if (newDis < neighBouri.getDistance()) {
                        priorityQueue.remove(neighBouri);
                        neighBouri.setDistance(newDis);
                        neighBouri.setPrev(curPointDto);

                        priorityQueue.add(neighBouri);
                    }
                }

            }
            visitedNodeMarks.put(curPointDto.getId(), true);
            visitedNodes.add(curPointDto);


        }


        PointDto target = null;
        for (PointDto rsi : visitedNodes) {
            if (rsi.getId().equals(targetId)) {
                target = rsi;
                break;
            }
        }
        List<PointDto> path = new ArrayList<>();
        for (PointDto node = target; node != null; node = node.getPrev()) {
            path.add(node);
        }
        Collections.reverse(path);
        return path;


    }


}