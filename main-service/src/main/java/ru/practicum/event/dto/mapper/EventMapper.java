package ru.practicum.event.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import ru.practicum.event.dto.EventFullDto;
import ru.practicum.event.dto.EventShortDto;
import ru.practicum.event.dto.NewEventDto;
import ru.practicum.event.model.Event;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface EventMapper {

    @Mapping(target = "category", expression = "java(null)")
    Event newEventDtoToEvent(NewEventDto newEventDto);

    EventFullDto eventToEventFullDto(Event event);

    List<EventShortDto> listEventToListEventShortDto(List<Event> events);

    List<EventFullDto> listEventToListEventFullDto(List<Event> events);
}
