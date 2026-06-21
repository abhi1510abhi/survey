package com.example.survey.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity


@Data
@NoArgsConstructor
@AllArgsConstructor
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int id;
    public String first_name;
    public String last_name;
    public String email;
    public String dob;
    public String gender;
    public Long created_dt;
    public Integer created_by;
    public Long updated_dt;
    public Integer updated_by;
}
