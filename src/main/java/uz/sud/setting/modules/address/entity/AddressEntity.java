package uz.sud.setting.modules.address.entity;

import java.util.ArrayList;
import java.util.List;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "addresses")
public class AddressEntity  extends PanacheEntity{
    @Column(name = "soato_id")
    public Long soatoId;

    @Column(name = "parent_id")
    public Long parentId;

    @OneToMany(mappedBy = "address", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    public List<AddressTranslationEntity> translations = new ArrayList<>();
}
