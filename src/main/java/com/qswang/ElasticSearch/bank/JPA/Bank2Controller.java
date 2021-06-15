package com.qswang.ElasticSearch.bank.JPA;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("elasticsearchjpa")
public class Bank2Controller {

    @Autowired
    private BankRepository bankRepository;

    // http://localhost:8080/elasticsearchjpa/id/1
    @RequestMapping("id/{account}")
    public String SearchById(@PathVariable(value="account") String userAccount){
        System.out.println(userAccount);
        Optional<Account> optional = bankRepository.findById(Long.parseLong(userAccount));
        return optional.get().toString();
    }

    // http://localhost:8080/elasticsearchjpa/age/32
    @RequestMapping("age/{age}")
    public String SearchByAge(@PathVariable(value="age") String age){
        Page<Account> list = bankRepository.findByAge(Long.parseLong(age), PageRequest.of(0,10));
        return list.getContent().toString();
    }

    // http://localhost:8080/elasticsearchjpa/name?firstname=Nanette&&lastname=Bates
    @RequestMapping("name")
    public String SearchByName(@RequestParam(value="firstname",required = true)String firstname,@RequestParam(value="lastname",required = true)String lastname){
        Page<Account> list = bankRepository.findByFirstnameAndLastname(firstname,lastname,PageRequest.of(0,10));
        return list.getContent().toString();
    }

    // http://localhost:8080/elasticsearchjpa/address/bristo
    //                                                  like bristol
    @RequestMapping("address/{addr}")
    public String SearchByAddress(@PathVariable(value="addr") String address){
        List<Account> list = bankRepository.findByAddressLike(address);
        return list.toString();
    }


    // http://localhost:8080/elasticsearchjpa/agerange?from=31&&to=32
    // from and to are included
    @RequestMapping("agerange")
    public String SearchByAgerange(@RequestParam(value="from",required = true) String from,@RequestParam(value="to",required = true) String to){
        Page<Account> list = bankRepository.findByAgeBetween(Long.parseLong(from),Long.parseLong(to),PageRequest.of(0,10));
        return list.getContent().toString();
    }

}
