package uz.sud.setting.modules.language.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;
import uz.sud.setting.modules.language.dto.LanguageCreateDTO;
import uz.sud.setting.modules.language.dto.LanguageMapper;
import uz.sud.setting.modules.language.dto.LanguageResponseDTO;
import uz.sud.setting.modules.language.dto.LanguageUpdateDTO;
import uz.sud.setting.modules.language.entity.LanguageEntity;
import uz.sud.setting.modules.language.repository.LanguageRepository;

@ApplicationScoped
public class LanguageService {

    private final LanguageRepository repository;

    public LanguageService(LanguageRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public LanguageResponseDTO create(LanguageCreateDTO dto) {
        LanguageEntity existing = repository.findByName(dto.name);
        if (existing != null) {

            return LanguageMapper.toDTO(existing);
        }

        if (Boolean.TRUE.equals(dto.main)) {
            unsetMain();
        }

        LanguageEntity entity = new LanguageEntity();
        entity.name = dto.name;
        entity.description = dto.description;
        entity.main = dto.main != null && dto.main;

        repository.persist(entity);
        return LanguageMapper.toDTO(entity);
    }

    public LanguageResponseDTO getMain() {
        LanguageEntity entity = repository.findMain();

        if (entity == null) {
            entity = repository.findAll().firstResult();
        }

        return entity == null ? null : LanguageMapper.toDTO(entity);
    }

    public List<LanguageResponseDTO> search(String name, String description, String search) {

        StringBuilder jpql = new StringBuilder("1=1");
        Map<String, Object> params = new HashMap<>();

        if (name != null && !name.isBlank()) {
            jpql.append(" AND lower(name) LIKE :name");
            params.put("name", "%" + name.toLowerCase() + "%");
        }

        if (description != null && !description.isBlank()) {
            jpql.append(" AND lower(description) LIKE :description");
            params.put("description", "%" + description.toLowerCase() + "%");
        }

        if (search != null && !search.isBlank()) {
            jpql.append(" AND (lower(name) LIKE :search OR lower(description) LIKE :search)");
            params.put("search", "%" + search.toLowerCase() + "%");
        }

        PanacheQuery<LanguageEntity> query
                = repository.find(jpql.toString(), Sort.by("id").descending(), params);

        return query.list()
                .stream()
                .map(LanguageMapper::toDTO)
                .toList();
    }

    public List<LanguageResponseDTO> page(int page, int size) {
        return repository.findAll()
                .page(page, size)
                .list()
                .stream()
                .map(LanguageMapper::toDTO)
                .toList();
    }

    @Transactional
    public LanguageResponseDTO update(Long id, LanguageUpdateDTO dto) {
        LanguageEntity entity = repository.findById(id);
        if (entity == null) {
            throw new NotFoundException("Language topilmadi");
        }

        if (dto.main != null && dto.main) {
            unsetMain();
        }

        if (dto.name != null) {

            LanguageEntity found = repository.findByName(dto.name);

            if (found != null && !found.id.equals(id)) {
                throw new BadRequestException("Bunday name allaqachon mavjud");
            }

            entity.name = dto.name;
        }

        if (dto.description != null) {
            entity.description = dto.description;
        }
        if (dto.main != null) {
            entity.main = dto.main;
        }

        return LanguageMapper.toDTO(entity);
    }

    @Transactional
    public void delete(Long id) {
        LanguageEntity entity = repository.findById(id);
        if (entity == null) {
            throw new NotFoundException("Language topilmadi");
        }
        repository.delete(entity);
    }

    @Transactional
    void unsetMain() {
        LanguageEntity current = repository.findMain();
        if (current != null) {
            current.main = false;
        }
    }
}
