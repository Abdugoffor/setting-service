package uz.sud.setting.modules.category.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CategoryUpdateDTO {
    @NotBlank(message = "Title bosh bolishi mumkin emas")
    @Size(max = 255)
    public String title;

    @NotBlank(message = "Description bosh bolishi mumkin emas")
    @Size(max = 1000)
    public String description;

    @NotNull(message = "Status bosh bolishi mumkin emas")
    @JsonProperty("is_active") // JSONdagi nomni bog‘lash
    public Boolean isActive; // bu yerda @NotNull ishlatildi
}
