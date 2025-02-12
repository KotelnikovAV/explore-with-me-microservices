package ru.eventlink.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SubComment {
    @NotNull
    Long authorId;
    @NotNull
    Long targetUserId;
    @NotBlank
    String text;
    @NotNull
    LocalDateTime created;
    Set<Long> likesId;

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof SubComment that)) return false;
        return Objects.equals(authorId, that.authorId) &&
                Objects.equals(targetUserId, that.targetUserId) &&
                Objects.equals(created, that.created);
    }

    @Override
    public int hashCode() {
        return Objects.hash(authorId, targetUserId, created);
    }
}
