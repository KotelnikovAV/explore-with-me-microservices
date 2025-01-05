package ru.practicum.repository.category;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.model.category.Category;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findCategoriesByNameContainingIgnoreCase(String name);
}
