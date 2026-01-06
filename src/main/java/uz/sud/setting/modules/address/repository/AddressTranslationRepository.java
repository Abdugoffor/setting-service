package uz.sud.setting.modules.address.repository;

import java.util.Optional;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import uz.sud.setting.modules.address.entity.AddressTranslationEntity;

@ApplicationScoped
public class AddressTranslationRepository implements PanacheRepository<AddressTranslationEntity>{
    public Optional<AddressTranslationEntity> findByAddressIdAndId(Long addressId, Long id) {
        return find("address.id = ?1 AND id = ?2", addressId, id).firstResultOptional();
    }
}
