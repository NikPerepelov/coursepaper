package com.example.Entities;

import javax.persistence.*;

@Entity
@Table(name = "tr_types")
public class tr_types
{
    @Id
    private long tr_type;

    @Column(name = "tr_description")
    private String tr_description;

    public tr_types() {
    }

    public tr_types(long tr_type, String tr_description) {
        this.tr_type = tr_type;
        this.tr_description = tr_description;
    }

    public Long getTr_type() {
        return tr_type;
    }

    public void setTr_type(Long tr_type) {
        this.tr_type = tr_type;
    }

    public String getTr_description() {
        return tr_description;
    }

    public void setTr_description(String tr_description) {
        this.tr_description = tr_description;
    }

    @Override
    public String toString() {
        return "tr_types{" +
                "tr_type=" + tr_type +
                ", tr_description='" + tr_description + '\'' +
                '}';
    }
}
