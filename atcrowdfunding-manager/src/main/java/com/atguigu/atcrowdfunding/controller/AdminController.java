package com.atguigu.atcrowdfunding.controller;

import com.atguigu.atcrowdfunding.bean.TAdmin;
import com.atguigu.atcrowdfunding.service.AdminService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Map;

@Controller
public class AdminController {

    @Autowired
    AdminService adminService;

    @RequestMapping("/admin/index")
    public String index(@RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum, @RequestParam(value = "pageSize", required = false, defaultValue = "2") Integer pageSize, @RequestParam(value = "condition", required = false, defaultValue = "") String condition, Model model) {
        // 1.开启分页插件功能
        PageHelper.startPage(pageNum, pageSize);
        // 2.通过业务层调用Dao层获取分页数据，并封装成分页对象返回
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("condition", condition);
        PageInfo<TAdmin> page = adminService.listPage(paramMap);
        // 3.数据存储
        model.addAttribute("page", page);
        // 4.跳转页面
        return "admin/index";
    }

    @RequestMapping("/admin/toAdd")
    public String toAdd() {
        return "admin/add";
    }

    @RequestMapping("/admin/doAdd")
    public String doAdd(TAdmin admin) {
        adminService.saveAdmin(admin);
        // 保存数据后，重定向到分页查询页面
        // 添加后跳转到最后一页，根据分页合理化实现：指定一个不可能存在的页，分页组件就会抛异常，并处理异常，去到最后一页。
        return "redirect:/admin/index?pageNum=" + Integer.MAX_VALUE;
    }

    @RequestMapping("/admin/toUpdate")
    public String toUpdate(Integer id, Model model) {
        TAdmin admin = adminService.getAdminById(id);
        model.addAttribute("admin", admin);
        return "admin/update";
    }

    @RequestMapping("/admin/doUpdate")
    public String doUpdate(TAdmin admin, Integer pageNum) {
        adminService.updateAdmin(admin);
        return "redirect:/admin/index?pageNum=" + pageNum;
    }

    @RequestMapping("/admin/doDelete")
    public String doDelete(Integer id, Integer pageNum) {
        adminService.deleteAdminById(id);
        return "redirect:/admin/index?pageNum=" + pageNum;
    }

    @RequestMapping("/admin/deleteBatch")
    public String deleteBatch(String ids, Integer pageNum) {
        adminService.deleteBatch(ids);
        return "redirect:/admin/index?pageNum=" + pageNum;
    }
}
