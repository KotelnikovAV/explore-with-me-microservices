package ru.eventlink.event.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import ru.eventlink.dto.event.EventFullDto;
import ru.eventlink.dto.event.EventShortDto;
import ru.eventlink.dto.event.NewEventDto;
import ru.eventlink.event.model.Event;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface EventMapper {

    @Mapping(target = "category", expression = "java(null)")
    Event newEventDtoToEvent(NewEventDto newEventDto);

    EventFullDto eventToEventFullDto(Event event);

    List<EventShortDto> listEventToListEventShortDto(List<Event> events);

    List<EventFullDto> listEventToListEventFullDto(List<Event> events);
}
