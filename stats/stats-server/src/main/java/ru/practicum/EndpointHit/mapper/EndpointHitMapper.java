package ru.practicum.EndpointHit.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import ru.practicum.EndpointHit.model.EndpointHit;
import ru.practicum.dto.EndpointHitDto;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface EndpointHitMapper {
    @Mapping(target = "id", ignore = true)
    EndpointHit endpointHitDtoToEndpointHit(EndpointHitDto endpointHitDto);

    EndpointHitDto endpointHitToEndpointHitDto(EndpointHit endpointHit);
}
