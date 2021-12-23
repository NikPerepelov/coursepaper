package com.example.Entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tr_mcc_codes")
public class tr_mcc_codes
{
    @Id
    private long mcc_code;

    @Column(name = "mcc_description")
    private String mcc_description;

    @OneToMany(mappedBy = "tr_mcc_codes", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)  //orphanRemoval - удаление всех эл. с одним мсс кодом, при удалении мсс кода
    List<transactions> transactions;


    public tr_mcc_codes() {
    }

    public tr_mcc_codes(long mcc_code, String mcc_description) {
        this.mcc_code = mcc_code;
        this.mcc_description = mcc_description;
        transactions = new ArrayList<>();
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
