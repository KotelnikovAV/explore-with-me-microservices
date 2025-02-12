package ru.eventlink.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.eventlink.dto.comment.CommentDto;
import ru.eventlink.dto.comment.CommentUserDto;
import ru.eventlink.dto.comment.RequestCommentDto;
import ru.eventlink.dto.comment.SubCommentDto;
import ru.eventlink.mapper.CommentMapper;
import ru.eventlink.repository.CommentRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;

    @Override
    public List<CommentDto> findAllCommentsByEventId(Long eventId) {
        return List.of();
    }

    @Override
    public List<CommentUserDto> findAllCommentsByUserId(Long userId) {
        return List.of();
    }

    @Override
    public CommentDto addComment(Long eventId, RequestCommentDto commentDto) {
        return null;
    }

    @Override
    public CommentDto updateComment(RequestCommentDto commentDto) {
        return null;
    }

    @Override
    public SubCommentDto addSubComment(Long eventId, String commentId, RequestCommentDto commentDto) {
        return null;
    }
}
