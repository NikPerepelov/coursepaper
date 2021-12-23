package com.example.Entities;

import javax.persistence.*;

@Entity
@Table(name = "gender_train")
public class gender_train
{
    @Id
    private long customer_id;

    @Column(name = "gender")
    private byte gender;

    public gender_train()
    {

    }

    public gender_train(long customer_id, byte gender)
    {
        this.customer_id = customer_id;
        this.gender = gender;
    }


    public long getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(byte customer_id) {
        this.customer_id = customer_id;
    }

    public byte getGender() {
        return gender;
    }

    public void setGender(byte gender) {
        this.gender = gender;
    }

    @Override
    public String toString() {
        return "gender_train{" +
                "customer_id=" + customer_id +
                ", gender=" + gender +
                '}';
    }
}
