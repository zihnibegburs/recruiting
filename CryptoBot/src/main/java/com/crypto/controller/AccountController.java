package com.crypto.controller;

import com.crypto.service.JobScratcherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController("/recruiter")
public class AccountController {

    @Autowired
    JobScratcherService jobScratcherService;

    @GetMapping("/")
    public void jobs() {
        jobScratcherService.scratch();
    }

}
