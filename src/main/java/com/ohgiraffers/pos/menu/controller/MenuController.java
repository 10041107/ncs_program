package com.ohgiraffers.pos.menu.controller;

import com.ohgiraffers.pos.menu.dto.MenuDTO;
import com.ohgiraffers.pos.menu.exception.NotInsertNameException;
import com.ohgiraffers.pos.menu.service.MenuService;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

@Controller
@RequestMapping("/menus/*")
@SessionAttributes("id")
public class MenuController extends HttpServlet {

    @Autowired
    private MenuService menuService;

    HttpSession session;

    @GetMapping("menus")   /*select 진행순서 2번 -- service로 보냄 */
    public ModelAndView selectAllMenu(ModelAndView mv){
        List<MenuDTO> menus = menuService.selectAllMenu();          // 쿼리에서는 문제 없으나 그 이후 에러
        // service 에서 담아온 쿼리문 값을 menus 에 넣어줌  진행순서 4번
/*        if (Objects.isNull(menus)){
            System.out.println("exception 대체");
        }*/
        mv.addObject("menus",menus);   // view로 보낼 내용 설정
        mv.setViewName("menus/allMenus");          // view 경로 설정

        return mv;  // 보냄
    }
    @PostMapping("login")
    public String login(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");
        String id = req.getParameter("id");
        String pwd = req.getParameter("pwd");
        session = req.getSession();
        session.setMaxInactiveInterval(60*3);
        session.setAttribute("id",id);
        session.setAttribute("pwd",pwd);
        return "menucontrol";
    }
    @GetMapping("logout")
    public String logout(HttpServletRequest req, HttpServletResponse resp){
        session.invalidate();
        return "menus/logout";
    }

    @GetMapping("onemenu")
    public ModelAndView OneMenu(ModelAndView mv){
        mv.setViewName("menus/onemenu");
        return mv;
    }
    @GetMapping("onemenuaction")
    public ModelAndView selectOneMenu(ModelAndView mv, MenuDTO menuDTO){
        MenuDTO menus = menuService.selectOneMenu(menuDTO);
        mv.addObject("menus",menus);
        mv.setViewName("menus/allMenus");
        return mv;
    }



    @GetMapping("regist")               /// get 매핑이 전달된 location.href 값을 html 문으로 보내주는 역할을 하는듯
    public void insert(){}
    @PostMapping("regist")  /*regist 진행순서 2번 */
    public ModelAndView insertMenu(ModelAndView mv,MenuDTO menuDTO) throws NotInsertNameException {
        int regist = menuService.regist(menuDTO);  // service로 보내 결과값 받아옴
        if (regist <=0){
            mv.addObject("message", "가격은 음수일 수 없습니다.");
            mv.setViewName("/error/errorMessage");
        }else {
            mv.setViewName("/menus/returnMessage");  // view 경로 설정
        }
        return mv;
    }

    @GetMapping("update")
    public void update(){}

    @PostMapping("update")
    public ModelAndView updateMenu(ModelAndView mv, MenuDTO menuDTO){
        int update = menuService.update(menuDTO);
        if(update == 0){
            mv.addObject("message", "업데이트 실패");
            mv.setViewName("/error/errorMessage");
        }else {
            mv.setViewName("/menus/returnMessage");
        }

        return mv;
    }

    @GetMapping("delete")
    public void delete(){}

    @PostMapping("delete")
    public ModelAndView deleteMenu(ModelAndView mv, MenuDTO menuDTO){
        int delete = menuService.delete(menuDTO);
        if(delete == 0) {
            mv.addObject("message", "삭제 실패");
            mv.setViewName("/error/errorMessage");

        }else{ mv.setViewName("/menus/returnMessage"); }


        return mv;
    }



}
