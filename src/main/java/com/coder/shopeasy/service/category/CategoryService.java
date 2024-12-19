package com.coder.shopeasy.service.category;

import com.coder.shopeasy.exceptions.ALreadyExistsException;
import com.coder.shopeasy.exceptions.ProductNotFoundException;
import com.coder.shopeasy.exceptions.ResourceNotFoundException;
import com.coder.shopeasy.model.Category;
import com.coder.shopeasy.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService implements ICategoryService{
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category Not Found!"));
    }

    @Override
    public Category getCategoryByName(String name) {
        return categoryRepository.findByName(name);
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category addCategory(Category category) {
        return Optional.of(category).filter(a->!categoryRepository.existsByName(a.getName())).map(categoryRepository::save)
                .orElseThrow(() -> new ALreadyExistsException("Category Already Exists = "+category.getName()));
    }

    @Override
    public Category updateCategory(Category newCategory, Long id) {
        return Optional.ofNullable(getCategoryById(id)).map(oldCategory -> {
            oldCategory.setName(newCategory.getName());
            return categoryRepository.save(oldCategory);
        }).orElseThrow(() -> new ResourceNotFoundException("Category Not Found!"));
    }

    @Override
    public void deleteCategoryById(Long id) {
        categoryRepository.findById(id).
                ifPresentOrElse(categoryRepository::delete,
                        ()->{throw new ProductNotFoundException("Category Not Found!");});
    }
}
