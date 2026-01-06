package uz.sud.setting.modules.language.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import io.quarkus.panache.common.Page;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import uz.sud.setting.modules.language.dto.LanguageContentDto;
import uz.sud.setting.modules.language.entity.LanguageContentEntity;
import uz.sud.setting.modules.language.entity.LanguageEntity;
import uz.sud.setting.modules.language.repository.LanguageContentRepository;
import uz.sud.setting.modules.language.repository.LanguageRepository;

@ApplicationScoped
public class LanguageContentService {

    private final LanguageRepository languageRepository;
    private final LanguageContentRepository repository;

    public LanguageContentService(LanguageContentRepository repository, LanguageRepository languageRepository) {
        this.repository = repository;
        this.languageRepository = languageRepository;
    }

    @Transactional
    public List<LanguageContentDto.Response> upsertMultiple(LanguageContentDto.Update req) {
        if (req.contents == null || req.contents.isEmpty()) {
            // throw new RuntimeException("Contents cannot be empty");
            return List.of();
        }

        String category = req.category != null ? req.category : "GLOBAL";
        List<LanguageContentDto.Response> responses = new ArrayList<>();
        for (LanguageContentDto.Update.ContentItem item : req.contents) {
            if (languageRepository.findById(item.languageId) == null) {
                throw new RuntimeException("Language not found: " + item.languageId);
            }
            LanguageContentEntity entity = repository.find(
                    "key = ?1 and category = ?2 and languageId = ?3",
                    req.key, category, item.languageId
            ).firstResult();
            if (entity == null) {
                entity = new LanguageContentEntity();
            }
            entity.key = req.key;
            entity.value = item.value;
            entity.category = category;
            entity.languageId = item.languageId;
            repository.persist(entity);
            responses.add(LanguageContentDto.Response.fromEntity(entity));
        }
        return responses;
        // if (req.contents == null || req.contents.isEmpty()) {
        //     // Bo‘sh bo‘lsa bo‘sh ro‘yxat qaytariladi
        //     return List.of();
        // }

        // List<LanguageContentDto.Response> responses = new ArrayList<>();
        // String category = req.category != null ? req.category : "GLOBAL";
        // for (LanguageContentDto.Update.ContentItem item : req.contents) {
        //     // 1️⃣ Language mavjudligini tekshirish
        //     LanguageEntity lang = languageRepository.findById(item.languageId);
        //     if (lang == null) {
        //         // Language topilmasa, shunchaki o‘tib ketamiz
        //         continue;
        //     }
        //     // 2️⃣ Mavjud contentni topish
        //     LanguageContentEntity entity = repository.find(
        //             "key = ?1 and category = ?2 and languageId = ?3",
        //             req.key, category, item.languageId
        //     ).firstResult();
        //     if (entity == null) {
        //         entity = new LanguageContentEntity();
        //         entity.key = req.key;
        //         entity.category = category;
        //         entity.languageId = item.languageId;
        //     }
        //     // 3️⃣ Value yangilash
        //     entity.value = item.value;
        //     // 4️⃣ Saqlash
        //     repository.persist(entity);
        //     responses.add(LanguageContentDto.Response.fromEntity(entity));
        // }
        // return responses;
    }

    public LanguageContentDto.ByKeyResponse getByKey(String key) {
        if (key == null || key.isBlank()) {

            LanguageContentDto.ByKeyResponse resp = new LanguageContentDto.ByKeyResponse();
            resp.contents = List.of();
            resp.key = key;
            resp.category = null;
            return resp;
        }

        List<LanguageContentEntity> entities = repository.list("key", key);

        if (entities.isEmpty()) {

            LanguageContentDto.ByKeyResponse resp = new LanguageContentDto.ByKeyResponse();
            resp.contents = List.of();
            resp.key = key;
            resp.category = null;
            return resp;
        }

        return LanguageContentDto.ByKeyResponse.fromEntities(entities);
    }

    public Map<String, String> getByLanguageId(Long languageId) {
        return repository.list("languageId", languageId)
                .stream()
                .collect(Collectors.toMap(
                        e -> e.key,
                        e -> e.value,
                        (oldV, newV) -> newV
                ));
    }

    @Transactional
    public void deleteByKey(String key) {
        LanguageContentEntity entity = repository.find("key", key).firstResult();
        if (entity == null) {
            throw new RuntimeException("Content not found: " + key);
        }
        repository.delete(entity);
    }

    public List<LanguageContentDto.Response> findAll(String key, String category, Long languageId, String value) {
        PanacheQueryWrapper wrapper = new PanacheQueryWrapper(key, category, languageId, value);
        List<LanguageContentEntity> list = wrapper.getQuery(repository).list();
        return list.stream().map(LanguageContentDto.Response::fromEntity).toList();
    }

    // Pagination example
    public List<LanguageContentDto.Response> getPageByLanguageId(Long languageId, String key, String value, int page, int size) {
        PanacheQueryWrapper wrapper = new PanacheQueryWrapper(key, null, languageId, value);
        List<LanguageContentEntity> list = wrapper.getQuery(repository).page(Page.of(page, size)).list();
        return list.stream().map(LanguageContentDto.Response::fromEntity).toList();
    }

    // Inner helper class for dynamic query filtering
    private static class PanacheQueryWrapper {

        private final String key;
        private final String category;
        private final Long languageId;
        private final String value;

        public PanacheQueryWrapper(String key, String category, Long languageId, String value) {
            this.key = key;
            this.category = category;
            this.languageId = languageId;
            this.value = value;
        }

        public io.quarkus.hibernate.orm.panache.PanacheQuery<LanguageContentEntity> getQuery(LanguageContentRepository repo) {
            StringBuilder query = new StringBuilder("1 = 1");
            List<Object> params = new ArrayList<>();

            if (key != null && !key.isEmpty()) {
                query.append(" and lower(key) like ?").append(params.size() + 1);
                params.add("%" + key.toLowerCase() + "%");
            }
            if (category != null && !category.isEmpty()) {
                query.append(" and category = ?").append(params.size() + 1);
                params.add(category);
            }
            if (languageId != null) {
                query.append(" and languageId = ?").append(params.size() + 1);
                params.add(languageId);
            }
            if (value != null && !value.isEmpty()) {
                query.append(" and lower(value) like ?").append(params.size() + 1);
                params.add("%" + value.toLowerCase() + "%");
            }

            return repo.find(query.toString(), params.toArray());
        }
    }
}
