package uz.sud.setting.modules.category.entity;

import java.time.LocalDateTime;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "category")
public class Category extends PanacheEntity {
    @NotBlank
    @Column(nullable = false, length = 255)
    public String title;

    @Column(length = 1000)
    public String description;


    @Column(name = "is_active", nullable = false)
    public Boolean isActive;

    @Column(name = "created_at", nullable = false, updatable = false)
    public LocalDateTime createdAt;

    @Column(name = "updated_at")
    public LocalDateTime updatedAt;

    /* ===== Lifecycle ===== */
    @SuppressWarnings("unused")
    @PrePersist
    void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = createdAt;
    }

    @SuppressWarnings("unused")
    @PreUpdate
    void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
