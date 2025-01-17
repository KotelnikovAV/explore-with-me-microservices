package ru.practicum.event.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import ru.practicum.dto.event.RecommendationsDto;
import ru.practicum.ewm.stats.proto.RecommendedEventProto;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface RecommendationsMapper {
    RecommendationsDto recommendedEventProtoToRecommendationsDto(RecommendedEventProto recommendedEventProto);

    List<RecommendationsDto> listRecommendedEventProtoToListRecommendationsDto(List<RecommendedEventProto> recommendedEventProto);
}
