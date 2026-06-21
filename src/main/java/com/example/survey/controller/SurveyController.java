package com.example.survey.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.survey.dto.UserDto;
import com.example.survey.entity.Users;
import com.example.survey.service.SurveyService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;





@RestController
@RequestMapping("/survey")
public class SurveyController {
    @Autowired
    private SurveyService surveyService;


    @PostMapping("/submit")
    public Users submitSurvey(@RequestBody UserDto userdto) {
        return surveyService.createSurvey(userdto);
    }

    
    @GetMapping("/all")
    public List<Users> getAllSurvey() {
        return surveyService.getAllSurveys();
    }

    @GetMapping("/id") // http://localhost:8023/survey/id?surveyId=2
    public Users getSurveyById(@RequestParam int surveyId) {
        return surveyService.getSurveyById(surveyId);
    }
    

    @GetMapping("/id/{id}") // http://localhost:8023/survey/id/2
    public Users getSurveyById2(@PathVariable int id) {
        return surveyService.getSurveyById(id);
    }
    


}
