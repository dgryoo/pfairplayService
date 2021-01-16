package com.example.pfairplayservice.controller;

import com.example.pfairplayservice.jpa.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TeamController {

    @Autowired
    private TeamRepository teamRepository;

}
