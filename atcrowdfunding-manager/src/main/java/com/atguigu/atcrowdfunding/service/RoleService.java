package com.atguigu.atcrowdfunding.service;

import com.atguigu.atcrowdfunding.bean.TRole;
import com.github.pagehelper.PageInfo;

import java.util.Map;

public interface RoleService {
    PageInfo<TRole> listPage(Map<String, Object> paramMap);

    void saveRole(TRole role);

    TRole getRoleById(Integer id);

    void updateRole(TRole role);

    void deleteRoleById(Integer id);

    void deleteBatch(String ids);
}
