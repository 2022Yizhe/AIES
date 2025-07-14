package com.neuswp.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student implements Serializable {
    private Integer id;
    private String username;
    private String name;
    private String sex;
    private LocalDate birthday;
    private String phone;
    private Integer class_id;
    private String motto;
}