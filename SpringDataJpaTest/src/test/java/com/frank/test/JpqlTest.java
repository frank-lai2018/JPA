package com.frank.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.frank.dao.CustomerDAO;
import com.frank.domain.Customer;

import java.util.Arrays;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class) //聲明spring提供的單元測試環境
@ContextConfiguration(locations = "classpath:applicationContext.xml")//指定spring容器的配置信息
public class JpqlTest {
    @Autowired
    private CustomerDAO customerDao;

    @Test
    public void testFindJPQL() {
        Customer customer = customerDao.findJpql("測試測試");
        System.out.println(customer);
    }


    @Test
    public void testFindCustNameAndId() {
       // Customer customer = customerDao.findCustNameAndId("IT",1l);
        Customer customer = customerDao.findCustNameAndId(1l,"測試測試");
        System.out.println(customer);
    }

    /**
     * 測試jpql的更新操作
     * * springDataJpa中使用jpql完成 更新/刪除操作
     * * 需要手動添加事務的支持
     * * 默認會執行結束之後，回滾事務
     * @Rollback : 設置是否自動回滾
     * false | true
     */
    @Test
    @Transactional //添加事務的支持
    @Rollback(value = false)
    public void testUpdateCustomer() {
        customerDao.updateCustomer(1L,"哈哈哈哈哈");
    }

    //測試sql查詢
    @Test
    public void testFindSql() {
        List<Object[]> list = customerDao.findSql("IT%");
        for(Object [] obj : list) {
            System.out.println(Arrays.toString(obj));
        }
    }


    //測試方法命名規則的查詢
    @Test
    public void testNaming() {
        Customer customer = customerDao.findByCustName("IT");
        System.out.println(customer);
    }


    //測試方法命名規則的查詢
    @Test
    public void testFindByCustNameLike() {
        List<Customer> list = customerDao.findByCustNameLike("IT%");
        for (Customer customer : list) {
            System.out.println(customer);
        }
    }


    //測試方法命名規則的查詢
    @Test
    public void testFindByCustNameLikeAndCustIndustry() {
        Customer customer = customerDao.findByCustNameLikeAndCustIndustry("IT1%", "ITIT");
        System.out.println(customer);
    }
}
