package ru.practicum.event.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.practicum.category.model.Category;
import ru.practicum.enums.State;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "events")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @NotBlank
    @Column
    String annotation;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    Category category;

    @NotNull
    @Column
    LocalDateTime createdOn;

    @NotBlank
    @Column
    String description;

    @NotNull
    @Column
    LocalDateTime eventDate;

    @NotNull
    @Column
    Long initiatorId;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "location_id")
    Location location;

    @NotNull
    @Column
    Boolean paid;

    @NotNull
    @Column
    Long participantLimit;

    @NotNull
    @Column
    LocalDateTime publishedOn;

    @NotNull
    @Column
    Boolean requestModeration;

    @NotNull
    @Column
    @Enumerated(value = EnumType.STRING)
    State state;

    @NotBlank
    @Column
    String title;

    @Column(name = "confirmed_requests")
    Long confirmedRequests;

    @NotNull
    @Column
    Long likes;

    @Transient
    Double rating;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return Objects.equals(id, event.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
