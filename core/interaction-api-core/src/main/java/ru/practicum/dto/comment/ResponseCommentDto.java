package ru.practicum.dto.comment;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.practicum.utility.Constants;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ResponseCommentDto {
    @NotNull
    Long id;
    @NotNull
    Long eventId;
    @NotNull
    Long authorId;
    @NotBlank
    String text;
    @NotNull
    List<ResponseCommentDto> subComments;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.DATE_TIME_FORMAT)
    @NotNull
    LocalDateTime created;
    Long likeCount;

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof ResponseCommentDto that)) return false;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
