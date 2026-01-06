package uz.sud.setting.modules.category.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import uz.sud.setting.modules.category.dto.CategoryCreateDTO;
import uz.sud.setting.modules.category.dto.CategoryMapper;
import uz.sud.setting.modules.category.dto.CategoryResponseDTO;
import uz.sud.setting.modules.category.dto.CategorySearchDTO;
import uz.sud.setting.modules.category.dto.CategoryUpdateDTO;
import uz.sud.setting.modules.category.entity.Category;
import uz.sud.setting.modules.category.repository.CategoryRepository;

@ApplicationScoped
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Transactional
    public CategoryResponseDTO create(CategoryCreateDTO dto) {
        Category category = new Category();
        category.title = dto.title;
        category.description = dto.description;
        category.isActive = dto.isActive != null ? dto.isActive : true;

        category.persist();
        return CategoryMapper.toDTO(category);
    }

    @Transactional
    public CategoryResponseDTO update(Long id, CategoryUpdateDTO dto) {
        Category category = categoryRepository.findById(id);
        if (category == null) {
            throw new NotFoundException("Category topilmadi");
        }

        category.title = dto.title;
        category.description = dto.description;
        
        if (dto.isActive != null) {
            category.isActive = dto.isActive;
        }

        return CategoryMapper.toDTO(category);
    }

    public CategoryResponseDTO getById(Long id) {
        Category category = categoryRepository.findById(id);
        if (category == null) {
            throw new NotFoundException("Category topilmadi");
        }
        return CategoryMapper.toDTO(category);
    }

    @Transactional
    public void delete(Long id) {
        boolean deleted = categoryRepository.deleteById(id);
        if (!deleted) {
            throw new NotFoundException("Category topilmadi");
        }
    }

    public List<CategoryResponseDTO> search(CategorySearchDTO dto) {

        StringBuilder query = new StringBuilder("1=1");
        Map<String, Object> params = new HashMap<>();

        if (dto.title != null && !dto.title.isBlank()) {
            query.append(" and lower(title) like :title");
            params.put("title", "%" + dto.title.toLowerCase() + "%");
        }

        if (dto.isActive != null) {
            query.append(" and isActive = :isActive");
            params.put("isActive", dto.isActive);
        }

        PanacheQuery<Category> panacheQuery =
                categoryRepository.find(query.toString(), params)
                        .page(dto.page, dto.size);

        return panacheQuery.list()
                .stream()
                .map(CategoryMapper::toDTO)
                .collect(Collectors.toList());
    }
}
