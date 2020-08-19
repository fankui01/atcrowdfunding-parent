package com.atguigu.atcrowdfunding.controller;

import com.atguigu.atcrowdfunding.LoginException;
import com.atguigu.atcrowdfunding.bean.TAdmin;
import com.atguigu.atcrowdfunding.bean.TMenu;
import com.atguigu.atcrowdfunding.service.AdminService;
import com.atguigu.atcrowdfunding.service.MenuService;
import com.atguigu.atcrowdfunding.util.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class DispatcherController {

    @Autowired
    AdminService adminService;

    @Autowired
    MenuService menuService;

    @RequestMapping("/logout")
    public String logout(HttpSession session) {
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/login.jsp";
    }

    @RequestMapping("/main")
    public String main(HttpSession session) {
        // 查询所有菜单，显示菜单
        // 集合中只需要返回所有的父菜单
        List<TMenu> parentMenuList = (List<TMenu>) session.getAttribute("parentMenuList");
        if (parentMenuList == null) {
            parentMenuList = menuService.listAllMenus();
            session.setAttribute("parentMenuList", parentMenuList);
        }
        return "main";
    }

    @RequestMapping("/login")
    public String login(String loginacct, String userpswd, HttpSession session, Model model) {
        try {
            TAdmin admin = adminService.getAdminByLogin(loginacct, userpswd);
            session.setAttribute(Const.LOGIN_ADMIN, admin);
            // 转发，路径不变，会出现表单重复提交问题
            return "redirect:/main";
        } catch (LoginException e) {
            e.printStackTrace();
            model.addAttribute("message", e.getMessage());
            return "forward:/login.jsp";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("message", "系统出现问题");
            return "forward:/login.jsp";
        }
    }
}
