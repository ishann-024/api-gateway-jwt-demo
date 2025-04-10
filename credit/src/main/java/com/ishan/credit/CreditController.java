package com.ishan.credit;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
public class CreditController {

    @GetMapping("/")
    public String credit() {
        return " Credit Service Reached!";
    }

    @GetMapping("/balance")
    public String getBalance() {
        return " Credit Balance: ₹10,000";
    }
}
