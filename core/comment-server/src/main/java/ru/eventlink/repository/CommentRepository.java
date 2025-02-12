package ru.eventlink.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import ru.eventlink.model.Comment;

public interface CommentRepository extends MongoRepository<Comment, ObjectId> {
}
