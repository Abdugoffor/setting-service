package uz.sud.setting.modules.language.dto;

import jakarta.validation.constraints.NotBlank;

public class LanguageCreateDTO {
    @NotBlank
    public String name;
    public String description;
    public Boolean main;
}
