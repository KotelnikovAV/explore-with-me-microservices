package ru.eventlink.category.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import ru.eventlink.category.model.Category;
import ru.eventlink.dto.category.CategoryDto;
import ru.eventlink.dto.category.NewCategoryDto;
import ru.eventlink.dto.category.UpdateCategoryDto;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CategoryMapper {
    Category newCategoryDtoToCategory(NewCategoryDto newCategoryDto);

    CategoryDto categoryToCategoryDto(Category category);

    Category updateCategoryDtoToCategory(UpdateCategoryDto updateCategoryDto);

    List<CategoryDto> listCategoryToListCategoryDto(List<Category> categoryList);
}
