package com.atguigu.atcrowdfunding.controller;

import com.atguigu.atcrowdfunding.bean.TMenu;
import com.atguigu.atcrowdfunding.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class MenuController {

    @Autowired
    MenuService menuService;

    @RequestMapping("/menu/index")
    public String index() {
        return "menu/index";
    }

    @ResponseBody
    @RequestMapping("/menu/loadTree")
    public List<TMenu> loadTree() {
        return menuService.listAllMenusTree();
    }
}
