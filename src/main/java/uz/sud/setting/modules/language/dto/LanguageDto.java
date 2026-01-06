package uz.sud.setting.modules.language.dto;

import uz.sud.setting.modules.language.entity.LanguageEntity;

public class LanguageDto {
    public static class Response {
        public Long id;
        public String name;        // Masalan "O'zbek", "Русский", "English"
        public String description;
        public Boolean main;       // Asosiy tilmi?

        public static Response fromEntity(LanguageEntity entity) {
            if (entity == null) return null;
            Response r = new Response();
            r.id = entity.id;
            r.name = entity.name;
            r.description = entity.description;
            r.main = entity.main;
            return r;
        }
    }
}
