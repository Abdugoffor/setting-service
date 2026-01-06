package uz.sud.setting.modules.address.repository;

import java.util.List;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import uz.sud.setting.modules.address.entity.AddressEntity;

@ApplicationScoped
public class AddressRepository implements PanacheRepository<AddressEntity>{
    public AddressEntity findByIdWithTranslation(Long id, Long langId) {
        return find("SELECT a FROM AddressEntity a JOIN FETCH a.translations t WHERE a.id = ?1 AND t.langId = ?2", id, langId)
                .firstResult();
    }

    // Simple search with translations
    public List<AddressEntity> searchAddresses(Long parentId, String title, Long languageId) {
        String jpql = "SELECT DISTINCT a FROM AddressEntity a " +
                      "LEFT JOIN FETCH a.translations t " +
                      "WHERE (:parentId IS NULL OR a.parentId = :parentId) " +
                    //   "AND (:title IS NULL OR LOWER(t.title) LIKE LOWER(CONCAT('%', :title, '%'))) " +
                    (title != null && !title.isEmpty() ? "AND LOWER(t.title) LIKE LOWER(CONCAT('%', :title, '%')) " : "") +
                      "AND (:languageId IS NULL OR t.langId = :languageId)";
        var query = getEntityManager().createQuery(jpql, AddressEntity.class)
                .setParameter("parentId", parentId)
                .setParameter("languageId", languageId);

        if (title != null && !title.isEmpty()) {
            query.setParameter("title", title);
        }

        return query.getResultList();
    }

    // Pagination
    public List<AddressEntity> pageAddresses(Long parentId, String title, Long languageId, int offset, int limit) {
        String jpql = "SELECT DISTINCT a FROM AddressEntity a " +
                      "LEFT JOIN FETCH a.translations t " +
                      "WHERE (:parentId IS NULL OR a.parentId = :parentId) " +
                    (title != null && !title.isEmpty() ? "AND LOWER(t.title) LIKE LOWER(CONCAT('%', :title, '%')) " : "") +
                      "AND (:languageId IS NULL OR t.langId = :languageId)";

        var query = getEntityManager().createQuery(jpql, AddressEntity.class)
                .setParameter("parentId", parentId)
                .setParameter("languageId", languageId)
                .setFirstResult(offset)
                .setMaxResults(limit);

        if (title != null && !title.isEmpty()) {
            query.setParameter("title", title);
        }

        return query.getResultList();
    }

    public Long countAddresses(Long parentId, String title, Long languageId) {
        String jpql = "SELECT COUNT(DISTINCT a) FROM AddressEntity a " +
                      "LEFT JOIN a.translations t " +
                      "WHERE (:parentId IS NULL OR a.parentId = :parentId) " +
                      "AND (:title IS NULL OR LOWER(t.title) LIKE LOWER(CONCAT('%', :title, '%'))) " +
                      "AND (:languageId IS NULL OR t.langId = :languageId)";
        return getEntityManager().createQuery(jpql, Long.class)
                .setParameter("parentId", parentId)
                .setParameter("title", title)
                .setParameter("languageId", languageId)
                .getSingleResult();
    }
}
