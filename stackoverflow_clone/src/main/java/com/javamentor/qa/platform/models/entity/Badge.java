package com.javamentor.qa.platform.models.entity;

import com.javamentor.qa.platform.models.entity.chat.Chat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "badges")
public class Badge implements Serializable {

    @Id
    @GeneratedValue(generator = "Badge_seq")
    private Long id;

    @Column(name = "badge_name")
    private String badgeName;

    @Column(name = "reputations_for_merit")
    private Integer reputationForMerit;

    @Column
    private String description;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Chat)) return false;
        Badge badge = (Badge) o;
        return id != null &&
                id.equals(badge.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
