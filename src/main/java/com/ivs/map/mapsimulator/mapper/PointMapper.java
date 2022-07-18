package com.ivs.map.mapsimulator.mapper;

import com.ivs.map.mapsimulator.dto.PointDto;
import lombok.Getter;
import lombok.Setter;
import com.ivs.map.mapsimulator.pojo.Point;
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
