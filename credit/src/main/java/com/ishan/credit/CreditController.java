package com.ishan.credit;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CreditController {

    @GetMapping("/credit")
    public String credit() {
        return "✅ Credit Service Reached!";
    }
}
