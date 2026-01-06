package uz.sud.setting.modules.language.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(
    name = "language_contents",
    indexes = @Index(
        name = "idx_lang_key_cat",
        columnList = "language_id,content_key,category",
        unique = true
    )
)
public class LanguageContentEntity extends PanacheEntity {

    @Column(name = "content_key", nullable = false)
    public String key;

    @Column(name = "content_value", nullable = false)
    public String value;

    @Column(nullable = false)
    public String category = "GLOBAL";

    @Column(name = "language_id", nullable = false)
    public Long languageId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "language_id", insertable = false, updatable = false)
    public LanguageEntity language;
}
