package com.ogiraffers.gorilla.employee.service;

import com.ogiraffers.gorilla.employee.dao.EmployeeMapper;
import com.ogiraffers.gorilla.employee.dto.InsertEmpDTO;
import com.ogiraffers.gorilla.employee.dto.SelectEmployeeDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    private final EmployeeMapper mapper;

    public EmployeeService(EmployeeMapper mapper) {
        this.mapper = mapper;
    }

    public List<SelectEmployeeDTO> selectAll(){

        List<SelectEmployeeDTO> empList = mapper.selectAll();

        return empList;
    }

    public int registEmployee(InsertEmpDTO insertEmpDTO){

        int result = 0;
        try{
            result = mapper.registEmployee(insertEmpDTO);
        }catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }



}
