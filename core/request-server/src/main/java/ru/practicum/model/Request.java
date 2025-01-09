package ru.practicum.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.practicum.enums.Status;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "requests")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @NotNull
    @Column
    LocalDateTime created;

    @NotNull
    @Column
    Long eventId;

    @NotNull
    @Column
    Long requesterId;

    @NotNull
    @Column
    @Enumerated(value = EnumType.STRING)
    Status status;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Request request = (Request) o;
        return Objects.equals(id, request.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
