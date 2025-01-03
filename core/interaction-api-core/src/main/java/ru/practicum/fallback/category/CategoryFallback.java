package ru.practicum.fallback.category;

import ru.practicum.client.category.CategoryClient;
import ru.practicum.dto.category.CategoryDto;
import ru.practicum.dto.category.NewCategoryDto;
import ru.practicum.dto.category.UpdateCategoryDto;
import ru.practicum.exception.ServerUnavailableException;

import java.util.List;

public class CategoryFallback implements CategoryClient {
    @Override
    public CategoryDto addCategory(NewCategoryDto newCategoryDto) {
        throw new ServerUnavailableException("Endpoint /api/v1/admin/categories method POST is unavailable");
    }

    @Override
    public void deleteCategory(long catId) {
        throw new ServerUnavailableException("Endpoint /api/v1/admin/categories/{catId} method DELETE is unavailable");
    }

    @Override
    public CategoryDto updateCategory(long catId, UpdateCategoryDto dto) {
        throw new ServerUnavailableException("Endpoint /api/v1/admin/categories/{catId} method PATCH is unavailable");
    }

    @Override
    public List<CategoryDto> getAllCategories(Integer from, Integer size) {
        throw new ServerUnavailableException("Endpoint /api/v1/categories method GET is unavailable");
    }

    @Override
    public CategoryDto getCategory(Long catId) {
        throw new ServerUnavailableException("Endpoint /api/v1/categories/{catId} method GET is unavailable");
    }
}
