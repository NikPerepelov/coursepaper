package com.example.Entities;

import javax.persistence.*;

@Entity
@Table(name = "users")
public class transactions
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long trans_id;

    @Column (name = "customer_id")
    private long customer_id;

    @Column (name = "tr_datetime")
    private String tr_datetime;

    @Column (name = "mcc_code")
    private long mcc_code;

    @Column (name = "tr_type")
    private long tr_type;

    @Column (name = "amount")
    private double amount;

    @Column (name = "term_id")
    private String term_id;



    public transactions(long trans_id, long customer_id, String tr_datetime, long mcc_code, long tr_type, double amount, String term_id) {
        this.trans_id = trans_id;
        this.customer_id = customer_id;
        this.tr_datetime = tr_datetime;
        this.mcc_code = mcc_code;
        this.tr_type = tr_type;
        this.amount = amount;
        this.term_id = term_id;
    }

    public transactions() {

    }


    @Override
    public String toString() {
        return "Transactions { " +
                "customer_id = " + customer_id +
                ", tr_datetime = '" + tr_datetime + '\'' +
                ", mcc_code = " + mcc_code +
                ", tr_type = " + tr_type +
                ", amount = " + amount +
                ", term_id = '" + term_id + '\'' +
                '}';
    }

    public void setTrans_id(long customer_id) {
        this.trans_id = customer_id;
    }
    public Long getTrans_id() {
        return trans_id;
    }

    public void setCustomer_id(long customer_id) {
        this.customer_id = customer_id;
    }
    public Long getCustomer_id() {
        return customer_id;
    }

    public void setTr_datetime(String tr_datetime) {
        this.tr_datetime = tr_datetime;
    }
    public String getTr_datetime() {
        return tr_datetime ;
    }

    public void setMcc_code(long mcc_code) {
        this.mcc_code = mcc_code;
    }
    public long getMcc_code() {
        return mcc_code;
    }

    public void setTr_type(long tr_type) {
        this.tr_type = tr_type;
    }
    public long getTr_type() {
        return tr_type;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
    public double getAmount() {
        return amount;
    }

    public void setTerm_id(String term_id) {
        this.term_id = term_id;
    }
    public String getTerm_id() {
        return term_id;
    }
}
