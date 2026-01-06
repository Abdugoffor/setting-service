package uz.sud.setting.modules.category.repository;


import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import uz.sud.setting.modules.category.entity.Category;

@ApplicationScoped
public class CategoryRepository implements PanacheRepository<Category> {
    
}
