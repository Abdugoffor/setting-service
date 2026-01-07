package uz.sud.setting.modules.address.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import uz.sud.setting.modules.address.dto.AddressTranslationDto;
import uz.sud.setting.modules.address.entity.AddressTranslationEntity;
import uz.sud.setting.modules.address.repository.AddressTranslationRepository;

@ApplicationScoped
public class AddressTranslationService {

    private final AddressTranslationRepository repository;

    public AddressTranslationService(AddressTranslationRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public Long create(AddressTranslationDto.Create dto) {
        var address = repository.getEntityManager()
                .find(uz.sud.setting.modules.address.entity.AddressEntity.class, dto.addressId);

        if (address == null) {
            throw new NotFoundException("Address not found. id=" + dto.addressId);
        }

        AddressTranslationEntity entity = new AddressTranslationEntity();
        entity.address = address;
        entity.langId = dto.languageId;
        entity.title = dto.title;

        repository.persist(entity);
        return entity.id;
    }

    @Transactional
    public Long update(Long addressId, Long translationId, AddressTranslationDto.Update dto) {
        AddressTranslationEntity entity = repository.findByAddressIdAndId(addressId, translationId)
                .orElseThrow(() -> new NotFoundException(
                "AddressTranslation not found. addressId="
                + addressId + ", id=" + translationId
        ));
        entity.title = dto.title;
        return entity.id;
    }
}
