package com.example.service;

import com.example.dto.DtoEmployee;
import org.springframework.http.ResponseEntity;

public interface IEmployeeService {

    DtoEmployee findEmployeeById(Long id);

}
