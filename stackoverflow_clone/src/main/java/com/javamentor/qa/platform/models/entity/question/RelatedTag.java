package com.javamentor.qa.platform.models.entity.question;

import com.javamentor.qa.platform.models.entity.chat.Chat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "related_tag")
public class RelatedTag implements Serializable {

    @Id
    @GeneratedValue(generator = "RelatedTag_seq")
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "main_tag")
    private Tag mainTag;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "child_tag")
    private Tag childTag;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Chat)) return false;
        RelatedTag relatedTag = (RelatedTag) o;
        return id != null &&
                id.equals(relatedTag.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
