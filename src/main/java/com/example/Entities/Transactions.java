package com.example.Entities;

import javax.persistence.*;

@Entity
@Table(name = "transactions_cut")
public class Transactions
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long trans_id;

    @ManyToOne(fetch = FetchType.LAZY)
    @Column (name = "customer_id")
    private GenderTrain customer_id;

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

    @JoinColumn(name = "tr_mcc_codes")
    private long tr_mcc_codes;

    public Transactions(long trans_id, long customer_id, String tr_datetime, long mcc_code, long tr_type, double amount, String term_id) {
        this.trans_id = trans_id;
        this.tr_datetime = tr_datetime;
        this.mcc_code = mcc_code;
        this.tr_type = tr_type;
        this.amount = amount;
        this.term_id = term_id;
    }

    public Transactions() {

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

    public long getTrans_id() {
        return trans_id;
    }

    public GenderTrain getCustomer_id() {
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

    public long getTr_mcc_codes() {
        return tr_mcc_codes;
    }
    public void setTr_mcc_codes() {
        this.tr_mcc_codes = tr_mcc_codes;
    }
}
