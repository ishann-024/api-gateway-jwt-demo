package com.ishan.debit;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
public class DebitController {

    @GetMapping("/")
    public String debit() {
        return " Debit Service Reached!";
    }

    @GetMapping("/transactions")
    public String transactions() {
        return " Debit Transactions List";
    }
}

