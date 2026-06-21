package com.example.survey.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class UserDto {
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
