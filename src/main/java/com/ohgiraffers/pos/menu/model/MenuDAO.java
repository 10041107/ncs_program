package com.ohgiraffers.pos.menu.model;

import com.ohgiraffers.pos.menu.dto.MenuDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MenuDAO {
    List<MenuDTO> selectAllMenu();
    MenuDTO selectOneMenu(MenuDTO menuDTO);

    int regist(MenuDTO menuDTO);

    int update(MenuDTO menuDTO);

    int delete(MenuDTO menuDTO);


    List<MenuDTO> selectAllName();
}
