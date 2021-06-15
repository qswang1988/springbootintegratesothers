package com.qswang.ElasticSearch.bank.JPA;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;

import java.io.Serializable;

@Data
@Document(indexName = "bank",type = "account")
public class Account implements Serializable{
    private static final long serialVersionUID = -5087658155687251393L;

    @Id
    private long account_number;


    private long balance;
    private String firstname;
    private String lastname;



    private long age;
    private String gender;
    private String address;
    private String employer;
    private String email;
    private String city;
    private String state;


}
