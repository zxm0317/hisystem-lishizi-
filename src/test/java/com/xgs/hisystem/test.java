package com.xgs.hisystem;

import com.xgs.hisystem.pojo.vo.RoleVO;
import com.xgs.hisystem.service.IAdminService;
import com.xgs.hisystem.service.impl.AdminServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class test {

    @Autowired
    IAdminService iAdminService;

    @Test
    public void creatRole(){
        RoleVO roleVO =new RoleVO();
        roleVO.setRolename("patients");
        roleVO.setDesciption("在线病人");
        roleVO.setRoleValue(8);
        iAdminService.createRole(roleVO);
    }
}
