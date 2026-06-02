package com.example.service.impl;

import com.example.dto.DtoDepartment;
import com.example.models.Department;
import com.example.dto.DtoEmployee;
import com.example.models.Employee;
import com.example.repository.IEmployeeRepository;
import com.example.service.IEmployeeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EmployeeServiceImpl implements IEmployeeService {

    @Autowired
    private IEmployeeRepository employeeRepository;

    @Override
    public DtoEmployee findEmployeeById(Long id) {
        Optional<Employee> optional = employeeRepository.findById(id);
        
        if (optional.isPresent()) {
            Employee employee = optional.get();
            DtoEmployee dtoEmployee = new DtoEmployee();
            DtoDepartment dtoDepartment = new DtoDepartment();
            Department department = employee.getDepartment();

            BeanUtils.copyProperties(employee, dtoEmployee);
            if (department != null) {
                BeanUtils.copyProperties(department, dtoDepartment);
                dtoEmployee.setDtoDepartment(dtoDepartment);
            }

            return dtoEmployee;
        }
        
        return null;
    }
}
