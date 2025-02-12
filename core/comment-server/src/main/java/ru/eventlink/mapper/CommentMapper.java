package ru.eventlink.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import ru.eventlink.dto.comment.RequestCommentDto;
import ru.eventlink.model.Comment;
import ru.eventlink.model.SubComment;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CommentMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "created", ignore = true)
    @Mapping(target = "likesId", ignore = true)
    @Mapping(target = "subComments", ignore = true)
    Comment requestCommentDtoToComment(RequestCommentDto requestCommentDto);

    @Mapping(target = "created", ignore = true)
    @Mapping(target = "likesId", ignore = true)
    SubComment requestCommentDtoToSubComment(RequestCommentDto requestCommentDto);
}
