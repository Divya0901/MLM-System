package com.dg.MLMSystem.Controller;

import com.dg.MLMSystem.Service.CommissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/commissions")
public class CommissionController {

    @Autowired
    private CommissionService commissionService;

    @GetMapping("/calculate")
    public ResponseEntity<String> calculateCommissions() {
        commissionService.calculateCommissions();
        return ResponseEntity.ok("Commissions calculated successfully");
    }
}

