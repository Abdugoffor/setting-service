package uz.sud.setting.modules.language.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "languages")
public class LanguageEntity extends PanacheEntity {

    @Column(nullable = false, unique = true)
    public String name;

    @Column
    public String description;

    @Column(nullable = false)
    public Boolean main = false;

    @OneToMany(
        mappedBy = "language",
        cascade = CascadeType.ALL,
        orphanRemoval = true,
        fetch = FetchType.LAZY
    )
    public List<LanguageContentEntity> contents = new ArrayList<>();
}
