package com.helmes.worker_reg.entities;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "sector_hierarchy")
public class SectorHierarchy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long relationshipId;
    private Long parentId;
    @NotNull
    private Long childId;

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Long getChildId() {
        return childId;
    }

    public void setChildId(Long childId) {
        this.childId = childId;
    }

    public Long getRelationshipId() {
        return relationshipId;
    }

    public void setRelationshipId(Long relationshipId) {
        this.relationshipId = relationshipId;
    }
}
