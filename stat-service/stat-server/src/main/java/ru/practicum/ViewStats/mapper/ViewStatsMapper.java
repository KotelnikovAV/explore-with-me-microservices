package ru.practicum.ViewStats.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import ru.practicum.ViewStats.model.ViewStats;
import ru.practicum.ViewStatsDto;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ViewStatsMapper {
    List<ViewStatsDto> listViewStatsToListViewStatsDto(List<ViewStats> viewStats);
}
