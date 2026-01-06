package uz.sud.setting.modules.language.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import uz.sud.setting.modules.language.entity.LanguageContentEntity;

@ApplicationScoped
public class LanguageContentRepository implements PanacheRepository<LanguageContentEntity>{
    
}
