package uz.sud.setting.modules.address.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "address_translations", 
       uniqueConstraints = @UniqueConstraint(columnNames = {"address_id", "lang_id"}))
public class AddressTranslationEntity extends PanacheEntity{
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id", nullable = false)
    public AddressEntity address;

    @Column(name = "lang_id", nullable = false)
    public Long langId;

    @Column(name = "title", nullable = false)
    public String title;
}
