package ru.practicum.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import ru.practicum.dto.requests.EventRequestStatusUpdateResultDto;
import ru.practicum.dto.requests.ParticipationRequestDto;
import ru.practicum.enums.Status;
import ru.practicum.model.request.Request;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface RequestMapper {
    @Mapping(target = "event", source = "event.id")
    @Mapping(target = "requester", source = "requester.id")
    ParticipationRequestDto requestToParticipationRequestDto(Request request);

    List<ParticipationRequestDto> listRequestToListParticipationRequestDto(List<Request> request);


    default List<ParticipationRequestDto> getConfirmedRequests(List<Request> request) {
        return request.stream()
                .filter(r -> r.getStatus() == Status.CONFIRMED)
                .map(this::requestToParticipationRequestDto)
                .toList();
    }


    default List<ParticipationRequestDto> getRejectedRequests(List<Request> request) {
        return request.stream()
                .filter(r -> r.getStatus() == Status.REJECTED)
                .map(this::requestToParticipationRequestDto)
                .toList();
    }

    @Mapping(target = "confirmedRequests", expression = "java(getConfirmedRequests(requests))")
    @Mapping(target = "rejectedRequests", expression = "java(getRejectedRequests(requests))")
    EventRequestStatusUpdateResultDto toEventRequestStatusResult(Integer dummy, List<Request> requests);
}
