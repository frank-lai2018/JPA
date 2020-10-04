package com.frank.test;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.frank.dao.RoleDao;
import com.frank.dao.UserDao;
import com.frank.domain.Role;
import com.frank.domain.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class ManyToManyTest {

    @Autowired
    private UserDao userDao;
    @Autowired
    private RoleDao roleDao;

    /**
     * 保存一個用戶，保存一個角色
     *
     * 多對多放棄維護權：被動的一方放棄
     */
    @Test
    @Transactional
    @Rollback(false)
    public void testAdd() {
        User user = new User();
        user.setUserName("小李");

        Role role = new Role();
        role.setRoleName("java程序員");

        //配置用戶到角色關係，可以對中間表中的數據進行維護 1-1
        user.getRoles().add(role);

        //配置角色到用戶的關係，可以對中間表的數據進行維護 1-1
        role.getUsers().add(user);

        userDao.save(user);
        roleDao.save(role);
    }


    //測試級聯添加（保存一個用戶的同時保存用戶的關聯角色）
    @Test
    @Transactional
    @Rollback(false)
    public void testCasCadeAdd() {
        User user = new User();
        user.setUserName("小李");

        Role role = new Role();
        role.setRoleName("java程序員");

        //配置用戶到角色關係，可以對中間表中的數據進行維護 1-1
        user.getRoles().add(role);

        //配置角色到用戶的關係，可以對中間表的數據進行維護 1-1
        role.getUsers().add(user);

        userDao.save(user);
    }

    /**
     * 案例：刪除id為1的用戶，同時刪除他的關聯物件
     */
    @Test
    @Transactional
    @Rollback(false)
    public void testCasCadeRemove() {
        //查詢1號用戶
        Optional<User> optional = userDao.findById(1l);
        User user = optional.get();
        //刪除1號用戶
        userDao.delete(user);

    }
}