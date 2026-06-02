package com.example.controller;

import com.example.dto.DtoEmployee;
import org.springframework.http.ResponseEntity;

public interface IRestEmployeeController {

    public DtoEmployee getEmployeeById(Long id);
}
