package com.ishan.admin;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
public class AdminController {

    @GetMapping("/")
    public String admin() {
        return "🛠️ Admin Service Reached!";
    }

    @GetMapping("/status")
    public String status() {
        return "✅ Admin Service is UP!";
    }

    @GetMapping("/dashboard")
    public String dashboard() {
        return "📊 Welcome to the Admin Dashboard!";
    }
}
