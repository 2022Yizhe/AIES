package com.neuswp.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class Teacher {
    private Integer id;
    private String username;
    private String name;
    private String sex;
    private LocalDate birthday;
    private String phone;
    private String education;
    private String motto;
}
