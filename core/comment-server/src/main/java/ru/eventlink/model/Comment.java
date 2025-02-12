package ru.eventlink.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.util.ProxyUtils;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Document(collation = "comments")
public class Comment {
    @Id
    ObjectId id;
    @NotNull(message = "eventId must not be null")
    @Indexed
    Long eventId;
    @NotNull(message = "authorId must not be null")
    Long authorId;
    @NotBlank(message = "text must not be blank")
    String text;
    Set<SubComment> subComments;
    @NotNull(message = "created must not be null")
    LocalDateTime created;
    Set<Long> likesId;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || ProxyUtils.getUserClass(this) != ProxyUtils.getUserClass(o))
            return false;
        Comment comment = (Comment) o;
        return getId() != null && Objects.equals(getId(), comment.getId());
    }

    @Override
    public final int hashCode() {
        return ProxyUtils.getUserClass(this).hashCode();
    }
}
