package com.atguigu.atcrowdfunding.service.impl;

import com.atguigu.atcrowdfunding.bean.TMenu;
import com.atguigu.atcrowdfunding.dao.TMenuMapper;
import com.atguigu.atcrowdfunding.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MenuServiceImpl implements MenuService {

    @Autowired
    TMenuMapper menuMapper;

    @Override
    public List<TMenu> listAllMenus() {
        // 只存放父菜单
        List<TMenu> parentList = new ArrayList<TMenu>();
        Map<Integer, TMenu> cache = new HashMap<Integer, TMenu>();
        // 存放所有菜单
        List<TMenu> allList = menuMapper.selectByExample(null);
        // 迭代，找到所有父菜单
        for (TMenu menu : allList) {
            Integer pid = menu.getPid();
            if (pid == 0) {
                parentList.add(menu);
                cache.put(menu.getId(), menu);
            }
        }
        // 迭代，组合父子关系
        for (TMenu menu : allList) {
            Integer pid = menu.getPid();
            if (pid != 0) {
                // 外键关联主键：通过孩子的pid找他的父亲
                TMenu parent = cache.get(pid);
                parent.getChildren().add(menu);
            }
        }
        return parentList;
    }

    @Override
    public List<TMenu> listAllMenusTree() {
        return menuMapper.selectByExample(null);
    }
}
