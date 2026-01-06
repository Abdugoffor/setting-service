package uz.sud.setting.modules.language.dto;

import uz.sud.setting.modules.language.entity.LanguageEntity;

public class LanguageMapper {
    public static LanguageResponseDTO toDTO(LanguageEntity entity) {
        LanguageResponseDTO dto = new LanguageResponseDTO();
        dto.id = entity.id;
        dto.name = entity.name;
        dto.description = entity.description;
        dto.main = entity.main;
        return dto;
    }
}
