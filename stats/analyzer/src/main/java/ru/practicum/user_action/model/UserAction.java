package ru.practicum.user_action.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Entity
@Table(name = "user-action")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserAction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @NotNull
    @Column
    Long userId;

    @NotNull
    @Column
    Long eventId;

    @NotNull
    @Column
    @Enumerated(value = EnumType.STRING)
    ActionType actionType;

    @NotNull
    @Column
    LocalDateTime actionDate;
}
