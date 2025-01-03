package ru.practicum.client.compilation;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.compilation.CompilationDto;
import ru.practicum.dto.compilation.NewCompilationDto;
import ru.practicum.dto.compilation.UpdateCompilationRequest;
import ru.practicum.fallback.compilation.CompilationFallback;

import java.util.List;

@FeignClient(name = "compilation", fallback = CompilationFallback.class)
public interface CompilationClient {
    @PostMapping("/api/v1/admin/compilations")
    @ResponseStatus(HttpStatus.CREATED)
    CompilationDto addCompilation(@Valid @RequestBody NewCompilationDto compilationDto);

    @PatchMapping("/api/v1/admin/compilations/{compId}")
    CompilationDto updateCompilation(@PathVariable Long compId,
                                     @Valid @RequestBody UpdateCompilationRequest request);

    @DeleteMapping("/api/v1/admin/compilations/{compId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteCompilation(@PathVariable Long compId);

    @GetMapping("/api/v1/compilations")
    List<CompilationDto> getAllCompilations(@RequestParam(required = false) Boolean pinned,
                                            @RequestParam(defaultValue = "0") @PositiveOrZero Integer from,
                                            @RequestParam(defaultValue = "10") @Positive Integer size);

    @GetMapping("/api/v1/compilations/{compId}")
    CompilationDto getCompilationById(@PathVariable Long compId);
}
