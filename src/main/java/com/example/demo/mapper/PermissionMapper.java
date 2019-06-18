package com.example.demo.mapper;

import com.example.demo.entity.Permission;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author ：acan
 * @date ：Created in 2019-06-12 10:13
 * @description：permission mapper
 * @modified By：
 * @version: $
 */

@Repository
public interface PermissionMapper {
    List<Permission> findPermissionByUserId(Integer id);
    List<Permission> findAll();
}
