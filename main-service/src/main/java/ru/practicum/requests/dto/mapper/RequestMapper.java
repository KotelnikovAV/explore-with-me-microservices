package ru.practicum.requests.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import ru.practicum.requests.dto.EventRequestStatusUpdateResultDto;
import ru.practicum.requests.dto.ParticipationRequestDto;
import ru.practicum.requests.model.Request;
import ru.practicum.requests.model.Status;

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
