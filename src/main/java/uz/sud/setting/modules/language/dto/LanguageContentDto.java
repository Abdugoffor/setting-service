package uz.sud.setting.modules.language.dto;

import java.util.List;

public class LanguageContentDto {

    public static class Create {
        public String key;
        public String value;
        public String category;
        public Long languageId;
    }

    public static class Update {
        public String key;
        public String category;
        public List<ContentItem> contents;

        public static class ContentItem {
            public Long languageId;
            public String value;
        }
    }

    public static class Response {
        public Long id;
        public String key;
        public String value;
        public String category;
        public Long languageId;
        public LanguageDto.Response language; // Language DTO

        public static Response fromEntity(uz.sud.setting.modules.language.entity.LanguageContentEntity entity) {
            Response r = new Response();
            r.id = entity.id;
            r.key = entity.key;
            r.value = entity.value;
            r.category = entity.category;
            r.languageId = entity.languageId;
            if (entity.language != null) {
                r.language = LanguageDto.Response.fromEntity(entity.language);
            }
            return r;
        }
    }

    public static class ByKeyResponse {
        public String key;
        public String category;
        public List<ByKeyContentItem> contents;

        public static class ByKeyContentItem {
            public Long id;
            public String value;
            public LanguageDto.Response language;
        }

        public static ByKeyResponse fromEntities(List<uz.sud.setting.modules.language.entity.LanguageContentEntity> entities) {
            if (entities.isEmpty()) throw new RuntimeException("No content found for the given key");
            
            ByKeyResponse response = new ByKeyResponse();
            
            response.key = entities.get(0).key;
            
            response.category = entities.get(0).category;
            
            response.contents = entities.stream().map(e -> {
                ByKeyContentItem item = new ByKeyContentItem();
                item.id = e.id;
                item.value = e.value;
                if (e.language != null) {
                    item.language = LanguageDto.Response.fromEntity(e.language);
                }
                return item;
            }).toList();
            return response;
        }
    }
}
