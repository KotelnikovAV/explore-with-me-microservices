package ru.practicum.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.practicum.enums.StatusLike;

import java.time.LocalDateTime;

@Entity
@Table(name = "likes")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Like {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @NotNull
    @Column
    Long eventId;

    @NotNull
    @Column
    Long userId;

    @NotNull
    @Column
    @Enumerated(value = EnumType.STRING)
    StatusLike status;

    @NotNull
    @Column
    LocalDateTime created;
}
