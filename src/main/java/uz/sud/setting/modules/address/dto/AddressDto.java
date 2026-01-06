package uz.sud.setting.modules.address.dto;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class AddressDto {
    public static class Create {
        @NotNull(message = "soatoId bo'sh bo'lishi mumkin emas")
        public Long soatoId;

        @NotNull(message = "parentId bo'sh bo'lishi mumkin emas")
        public Long parentId;

        @NotNull(message = "languageId bo'sh bo'lishi mumkin emas")
        public Long languageId;

        @NotBlank(message = "title bo'sh bo'lishi mumkin emas")
        public String title;
    }

    public static class Response {
        public Long id;
        public Long soatoId;
        public Long parentId;
        public List<Translation> translations;

        public static class Translation {
            public Long id;
            public Long langId;
            public String title;
        }
    }
}
