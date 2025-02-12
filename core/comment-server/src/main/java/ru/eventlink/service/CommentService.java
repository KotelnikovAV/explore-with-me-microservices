package ru.eventlink.service;

import ru.eventlink.dto.comment.CommentDto;
import ru.eventlink.dto.comment.CommentUserDto;
import ru.eventlink.dto.comment.RequestCommentDto;
import ru.eventlink.dto.comment.SubCommentDto;

import java.util.List;

public interface CommentService {
    List<CommentDto> findAllCommentsByEventId(Long eventId);

    List<CommentUserDto> findAllCommentsByUserId(Long userId);

    CommentDto addComment(Long eventId, RequestCommentDto commentDto);

    CommentDto updateComment(RequestCommentDto commentDto);

    SubCommentDto addSubComment(Long eventId, String commentId, RequestCommentDto commentDto);
}
