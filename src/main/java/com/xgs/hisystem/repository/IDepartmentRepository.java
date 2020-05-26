package com.xgs.hisystem.repository;

import com.xgs.hisystem.pojo.entity.DepartmentEntity;

import java.util.List;

/**
 * @author XueGuiSheng
 * @date 2020/3/23
 * @description:
 */
public interface IDepartmentRepository extends BaseRepository<DepartmentEntity>{

    DepartmentEntity findByNameAndAddress(String name,String address);
    List<DepartmentEntity> findAll();
}
