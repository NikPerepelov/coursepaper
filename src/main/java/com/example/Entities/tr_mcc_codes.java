package com.example.Entities;

import javax.persistence.*;

@Entity
@Table(name = "tr_mcc_codes")
public class tr_mcc_codes
{
    @Id
    private long mcc_code;

    @Column(name = "mcc_description")
    private String mcc_description;

    public tr_mcc_codes() {
    }

    public tr_mcc_codes(long mcc_code, String mcc_description) {
        this.mcc_code = mcc_code;
        this.mcc_description = mcc_description;
    }


    public String getMcc_description() {
        return mcc_description;
    }

    public void setMcc_description(String mcc_description) {
        this.mcc_description = mcc_description;
    }

    public Long getMcc_code() {
        return mcc_code;
    }

    public void setMcc_code(Long mcc_code) {
        this.mcc_code = mcc_code;
    }

    @Override
    public String toString() {
        return "tr_mcc_codes{" +
                "mcc_code=" + mcc_code +
                ", mcc_description='" + mcc_description + '\'' +
                '}';
    }
}
