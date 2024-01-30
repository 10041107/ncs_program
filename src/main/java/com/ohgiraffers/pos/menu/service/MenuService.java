package com.ohgiraffers.pos.menu.service;

import com.ohgiraffers.pos.menu.dto.MenuDTO;
import com.ohgiraffers.pos.menu.exception.NotInsertNameException;
import com.ohgiraffers.pos.menu.model.MenuDAO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class MenuService {

    private MenuDAO menuDAO;

    public MenuService(MenuDAO menuDAO) {
        this.menuDAO = menuDAO;
    }

    public List<MenuDTO> selectAllMenu() {  /*select 진행순서 3번*/
        List<MenuDTO> menus = menuDAO.selectAllMenu();
        if(Objects.isNull(menus)){              // 반환된 결과 없음     쿼리문에서 에러 발생 체크
            System.out.println("exeception menus가 없네요 ");
        }
        // if(menus.size <=0   새로운 db 일수 있음 에러는 아님
        return menus;
    }


    public MenuDTO selectOneMenu(MenuDTO menuDTO) {
        MenuDTO menus = menuDAO.selectOneMenu(menuDTO);
        return menus;
    }


    public int regist(MenuDTO menuDTO) throws NotInsertNameException {
        List<MenuDTO> names = menuDAO.selectAllName();
        List<String> name = names.stream().map(e->e.getName()).collect(Collectors.toCollection(ArrayList::new)); // 리스트 형 변환하기
        if(name.contains(menuDTO.getName())){                                          // 메뉴이름 중복된거 등록하지 못하게 하는 과정 쿼리문과 연결해서 만들기
            throw new NotInsertNameException("");
        }

        if (menuDTO.getName().isEmpty()){
            throw new NotInsertNameException("");
        }
        if(menuDTO.getPrice()<=0){
            return 0;
        }else {
            int menus = menuDAO.regist(menuDTO);  // insert 쿼리문을 진행시킴 - 결과값 1 반환 진행순서 3번
            return menus;
        }
    }

    public int update(MenuDTO menuDTO) {
        int menus = menuDAO.update(menuDTO);
        return menus;
    }


    public int delete(MenuDTO menuDTO) {
        int menus = menuDAO.delete(menuDTO);
        return menus;
    }


}
