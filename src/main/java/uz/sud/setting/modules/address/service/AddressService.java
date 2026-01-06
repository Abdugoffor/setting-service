package uz.sud.setting.modules.address.service;

import java.util.ArrayList;
import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import uz.sud.setting.modules.address.dto.AddressDto;
import uz.sud.setting.modules.address.dto.AddressPageDto;
import uz.sud.setting.modules.address.dto.AddressParamsDto;
import uz.sud.setting.modules.address.entity.AddressEntity;
import uz.sud.setting.modules.address.entity.AddressTranslationEntity;
import uz.sud.setting.modules.address.repository.AddressRepository;

@ApplicationScoped
public class AddressService {
    private final AddressRepository addressRepository;

    public AddressService(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    public List<AddressPageDto> searchAddress(AddressParamsDto params) {
        List<AddressEntity> entities = addressRepository.searchAddresses(
                params.parentId,
                params.title,
                params.languageId
        );

        List<AddressPageDto> results = new ArrayList<>();
        for (AddressEntity e : entities) {
            for (AddressTranslationEntity t : e.translations) {
                if (params.languageId == null || t.langId.equals(params.languageId)) {
                    AddressPageDto dto = new AddressPageDto();
                    dto.id = e.id;
                    dto.soatoId = e.soatoId;
                    dto.parentId = e.parentId;
                    dto.languageId = t.langId;
                    dto.translationId = t.id;
                    dto.title = t.title;
                    results.add(dto);
                }
            }
        }

        return results;
    }

    public List<AddressPageDto> pageAddresses(AddressParamsDto params, int page, int size) {
        int offset = page * size;
        List<AddressEntity> entities = addressRepository.pageAddresses(
                params.parentId,
                params.title,
                params.languageId,
                offset,
                size
        );
        
        List<AddressPageDto> results = new ArrayList<>();
        for (AddressEntity e : entities) {
            for (AddressTranslationEntity t : e.translations) {
                if (params.languageId == null || t.langId.equals(params.languageId)) {
                    AddressPageDto dto = new AddressPageDto();
                    dto.id = e.id;
                    dto.soatoId = e.soatoId;
                    dto.parentId = e.parentId;
                    dto.languageId = t.langId;
                    dto.translationId = t.id;
                    dto.title = t.title;
                    results.add(dto);
                }
            }
        }

        return results;
    }

    public long countAddresses(AddressParamsDto params) {
        return addressRepository.countAddresses(
                params.parentId,
                params.title,
                params.languageId
        );
    }
    
    public AddressDto.Response findOne(Long id, Long languageId) {
        AddressEntity entity = addressRepository.findByIdWithTranslation(id, languageId);
        if (entity == null) return null;
        return toResponse(entity);
    }

    @Transactional
    public AddressDto.Response create(AddressDto.Create dto) {
        AddressEntity entity = new AddressEntity();
        entity.soatoId = dto.soatoId;
        entity.parentId = dto.parentId;

        AddressTranslationEntity translation = new AddressTranslationEntity();
        translation.address = entity;
        translation.langId = dto.languageId;
        translation.title = dto.title;

        entity.translations.add(translation);

        addressRepository.persist(entity);

        return toResponse(entity);
    }

    private AddressDto.Response toResponse(AddressEntity entity) {
        AddressDto.Response response = new AddressDto.Response();
        response.id = entity.id;
        response.soatoId = entity.soatoId;
        response.parentId = entity.parentId;
        response.translations = new ArrayList<>();

        for (AddressTranslationEntity t : entity.translations) {
            AddressDto.Response.Translation tr = new AddressDto.Response.Translation();
            tr.id = t.id;
            tr.langId = t.langId;
            tr.title = t.title;
            response.translations.add(tr);
        }

        return response;
    }
}
