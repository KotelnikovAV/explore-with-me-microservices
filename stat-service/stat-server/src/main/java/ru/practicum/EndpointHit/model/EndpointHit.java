package ru.practicum.EndpointHit.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "statistics")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class EndpointHit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column
    private String app;

    @NotBlank
    @Column
    private String uri;

    @NotBlank
    @Column
    private String ip;

    @NotNull
    @Column
    private LocalDateTime timestamp;
}