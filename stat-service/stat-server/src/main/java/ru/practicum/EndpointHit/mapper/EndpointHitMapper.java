package ru.practicum.EndpointHit.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import ru.practicum.EndpointHit.model.EndpointHit;
import ru.practicum.EndpointHitDto;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface EndpointHitMapper {
    EndpointHit endpointHitDtoToEndpointHit(EndpointHitDto endpointHitDto);

    EndpointHitDto endpointHitToEndpointHitDto(EndpointHit endpointHit);
}
