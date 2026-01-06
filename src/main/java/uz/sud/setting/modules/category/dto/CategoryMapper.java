package uz.sud.setting.modules.category.dto;

import java.util.List;
import java.util.stream.Collectors;

import uz.sud.setting.modules.category.entity.Category;

public class CategoryMapper {
    private CategoryMapper() {
        
    }
    public static CategoryResponseDTO toDTO(Category entity) {
        if (entity == null) {
            return null;
        }

        CategoryResponseDTO dto = new CategoryResponseDTO();
        dto.id = entity.id;
        dto.title = entity.title;
        dto.description = entity.description;
        dto.isActive = entity.isActive;
        dto.createdAt = entity.createdAt;
        dto.updatedAt = entity.updatedAt;

        return dto;
    }

    public static List<CategoryResponseDTO> toDTOList(List<Category> entities) {
        return entities.stream()
                .map(CategoryMapper::toDTO)
                .collect(Collectors.toList());
    }
}
