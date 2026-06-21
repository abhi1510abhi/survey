package com.example.survey.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.survey.entity.Users;

public interface UserRespository extends JpaRepository<Users, Integer> {
    
}
