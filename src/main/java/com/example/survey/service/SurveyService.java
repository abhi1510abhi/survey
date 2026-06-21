package com.example.survey.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.survey.dto.UserDto;
import com.example.survey.entity.Users;
import com.example.survey.repository.UserRespository;

@Service
public class SurveyService {
     @Autowired
     private UserRespository userRepository;

    
    // public SurveyService(UserRespository userRepository) {
    //     this.userRepository = userRepository;
    // }

    public Users createSurvey(UserDto userdto) {

        Users u = new Users();
        u.setFirst_name(userdto.getFirst_name());
        u.setLast_name(userdto.getLast_name());
        u.setEmail(userdto.getEmail());
        u.setDob(userdto.getDob());
        u.setGender(userdto.getGender());
        long currentTime = System.currentTimeMillis() / 1000L;
        u.setCreated_dt(currentTime);
        u.setUpdated_dt(currentTime);
        return userRepository.save(u);
    }

    public List<Users> getAllSurveys() {
        return userRepository.findAll();
    }

    public Users getSurveyById(int surveyId) {
        return userRepository.findById(surveyId).get();
    }


}
