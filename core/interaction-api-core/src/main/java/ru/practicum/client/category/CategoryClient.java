package ru.practicum.client.category;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.category.CategoryDto;
import ru.practicum.dto.category.NewCategoryDto;
import ru.practicum.dto.category.UpdateCategoryDto;
import ru.practicum.fallback.category.CategoryFallback;

import java.util.List;

@FeignClient(name = "category", fallback = CategoryFallback.class)
public interface CategoryClient {
    @PostMapping("/api/v1/admin/categories")
    @ResponseStatus(HttpStatus.CREATED)
    CategoryDto addCategory(@RequestBody @Valid NewCategoryDto newCategoryDto);

    @DeleteMapping("/api/v1/admin/categories/{catId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteCategory(@PathVariable long catId);

    @PatchMapping("/api/v1/admin/categories/{catId}")
    CategoryDto updateCategory(@PathVariable long catId, @RequestBody @Valid UpdateCategoryDto dto);

    @GetMapping("/api/v1/categories")
    List<CategoryDto> getAllCategories(@RequestParam(defaultValue = "0")
                                       @PositiveOrZero Integer from,
                                       @RequestParam(defaultValue = "10")
                                       @Positive Integer size);

    @GetMapping("/api/v1/categories/{catId}")
    CategoryDto getCategory(@PathVariable Long catId);
}