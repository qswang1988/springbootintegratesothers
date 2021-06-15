package com.qswang.ElasticSearch.bank.JPA;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface BankRepository extends ElasticsearchRepository<Account,Long> {
    Page<Account> findByAge(Long age, Pageable pageable);
    Page<Account> findByFirstnameAndLastname(String firstname,String lastname, Pageable pageable);
    List<Account> findByAddressLike(String address);
    Page<Account> findByAgeBetween(Long From,Long To, Pageable pageable);
}
