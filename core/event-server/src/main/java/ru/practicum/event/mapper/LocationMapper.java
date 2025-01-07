package ru.practicum.event.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import ru.practicum.dto.event.LocationDto;
import ru.practicum.event.model.Location;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface LocationMapper {

    Location locationDtoToLocation(LocationDto locationDto);

    LocationDto locationToLocationDto(Location location);
}
