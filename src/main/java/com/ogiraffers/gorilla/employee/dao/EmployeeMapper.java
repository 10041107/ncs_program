package com.ogiraffers.gorilla.employee.dao;

import com.ogiraffers.gorilla.employee.dto.InsertEmpDTO;
import com.ogiraffers.gorilla.employee.dto.SelectEmployeeDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EmployeeMapper {

    List<SelectEmployeeDTO> selectAll();

    int registEmployee(InsertEmpDTO insertEmpDTO);
}
