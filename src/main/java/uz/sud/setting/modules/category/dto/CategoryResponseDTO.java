package uz.sud.setting.modules.category.dto;

import java.time.LocalDateTime;

public class CategoryResponseDTO {
    public Long id;
    public String title;
    public String description;
    public Boolean isActive;
    public LocalDateTime createdAt;
    public LocalDateTime updatedAt;
}
