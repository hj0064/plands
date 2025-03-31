package com.plands.backend.controller;

import com.plands.backend.domain.Sample;
import com.plands.backend.service.SampleServcie;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sample")
@CrossOrigin(origins = "http://localhost:8082") // 프론트엔드와 CORS 해결
public class SampleController {
    private final SampleServcie sampleServcie;

    public SampleController(SampleServcie sampleServcie) {
        this.sampleServcie = sampleServcie;
    }

    @GetMapping
    public List<Sample> getUsers() {
        return sampleServcie.getAllUsers();
    }
}
