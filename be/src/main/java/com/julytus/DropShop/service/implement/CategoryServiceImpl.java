package com.julytus.DropShop.service.implement;

import com.julytus.DropShop.dto.request.CategoryRequest;
import com.julytus.DropShop.dto.response.CategoryResponse;
import com.julytus.DropShop.dto.response.PageResponse;
import com.julytus.DropShop.exception.AppException;
import com.julytus.DropShop.exception.ErrorCode;
import com.julytus.DropShop.mapper.CategoryResponseMapper;
import com.julytus.DropShop.model.Category;
import com.julytus.DropShop.repository.CategoryRepository;
import com.julytus.DropShop.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Override
    public CategoryResponse createCategory(CategoryRequest request) {
        if (categoryRepository.existsByName(request.getName())) {
            throw new AppException(ErrorCode.NAME_EXISTED);
        }

        return CategoryResponseMapper.fromCategory(
                categoryRepository.save(
                Category.builder()
                        .name( request.getName() )
                        .description(request.getDescription())
                        .build())
        );
    }

    @Override
    public CategoryResponse updateCategory(CategoryRequest request, String id) {
        Category category = getById(id);
        category.setName(request.getName());
        category.setDescription(request.getDescription());
        return CategoryResponseMapper.fromCategory(
                categoryRepository.save(category)
        );
    }

    @Override
    public void deleteCategory(String id) {
        Category category = getById(id);
        categoryRepository.delete(category);
    }

    @Override
    public Category getById(String id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_FOUND));
    }

    @Override
    public PageResponse<CategoryResponse> getAll(int page, int limit) {
        Pageable pageable = PageRequest.of(page - 1, limit);
        Page<Category> categories= categoryRepository.findAll(pageable);

        return PageResponse.<CategoryResponse>builder()
                .currentPage(page)
                .pageSize(categories.getSize())
                .totalPages(categories.getTotalPages())
                .totalElements(categories.getTotalElements())
                .data(CategoryResponseMapper.fromPageCategory(categories))
                .build();
    }
}
