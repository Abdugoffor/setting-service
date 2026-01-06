package uz.sud.setting.modules.address.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class AddressTranslationDto {
    public static class Create {
        @NotNull(message = "addressId bo'sh bo'lishi mumkin emas")
        public Long addressId;

        @NotNull(message = "languageId bo'sh bo'lishi mumkin emas")
        public Long languageId;

        @NotBlank(message = "title bo'sh bo'lishi mumkin emas")
        public String title;
    }

    public static class Update {
        @NotBlank(message = "title bo'sh bo'lishi mumkin emas")
        public String title;
    }
}
