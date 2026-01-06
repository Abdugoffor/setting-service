package uz.sud.setting.modules.language.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import uz.sud.setting.modules.language.entity.LanguageEntity;

@ApplicationScoped
public class LanguageRepository implements PanacheRepository<LanguageEntity> {

    public LanguageEntity findMain() {
        return find("main", true).firstResult();
    }
}
