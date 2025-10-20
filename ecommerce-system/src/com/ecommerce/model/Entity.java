package com.ecommerce.model;

import java.time.LocalDate;
import java.util.Objects;

public abstract class Entity {

    protected LocalDate createdDate;

    protected Entity(LocalDate createdDate) {
        this.createdDate = createdDate != null ? createdDate : LocalDate.now();
    }

    protected Entity() {
        this(LocalDate.now());
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    protected void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    @Override
    public abstract boolean equals(Object obj);

    @Override
    public abstract int hashCode();

    @Override
    public abstract String toString();
}
