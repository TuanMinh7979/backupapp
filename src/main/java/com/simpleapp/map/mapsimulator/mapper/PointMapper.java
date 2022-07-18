package com.simpleapp.map.mapsimulator.mapper;

import com.simpleapp.map.mapsimulator.dto.PointDto;
import com.simpleapp.map.mapsimulator.pojo.Point;
import org.springframework.stereotype.Component;

@Component
public class PointMapper {
    public PointDto toDto(Point model) {

        PointDto pointDto = new PointDto();
        pointDto.setId(model.getId());
        pointDto.setLat(model.getLat());
        pointDto.setLon(model.getLon());
        return pointDto;
    }
}
