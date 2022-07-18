package com.simple.simpleapp.mapsimulator.mapper;

import com.simple.simpleapp.mapsimulator.dto.PointDto;
import com.simple.simpleapp.mapsimulator.pojo.Point;
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
