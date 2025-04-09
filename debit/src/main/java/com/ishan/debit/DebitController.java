package com.ishan.debit;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DebitController {

    @GetMapping("/debit")
    public String debit() {
        return "💸 Debit Service Reached!";
    }
}
