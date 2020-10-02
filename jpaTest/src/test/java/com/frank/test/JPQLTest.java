package com.frank.test;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import org.junit.Test;

import com.frank.utils.JpaUtils;

public class JPQLTest {
	/**
     * 查詢全部
     * jqpl：from cn.itcast.domain.Customer
     * sql：SELECT * FROM cst_customer
     */
    @Test
    public void testFindAll() {
        //1.獲取entityManager物件
        EntityManager em = JpaUtils.getEntityManager();
        //2.開啟事務
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        //3.查詢全部
        String jpql = "from Customer ";
        Query query = em.createQuery(jpql);//創建Query查詢物件，query物件才是執行jqpl的物件

        //發送查詢，並封裝結果集
        List list = query.getResultList();

        System.out.print(list);

        //4.提交事務
        tx.commit();
        //5.釋放資源
        em.close();
    }
    
    /**
     * 排序查詢： 倒序查詢全部客戶（根據id倒序）
     * sql：SELECT * FROM cst_customer ORDER BY cust_id DESC
     * jpql：from Customer order by custId desc
     *
     * 進行jpql查詢
     * 1.創建query查詢物件
     * 2.對參數進行賦值
     * 3.查詢，並得到返回結果
     */
    @Test
    public void testOrders() {
        //1.獲取entityManager物件
        EntityManager em = JpaUtils.getEntityManager();
        //2.開啟事務
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        //3.查詢全部
        String jpql = "from Customer order by custId desc";
        Query query = em.createQuery(jpql);//創建Query查詢物件，query物件才是執行jqpl的物件

        //發送查詢，並封裝結果集
        List list = query.getResultList();

        for (Object obj : list) {
            System.out.println(obj);
        }

        //4.提交事務
        tx.commit();
        //5.釋放資源
        em.close();
    }


    /**
     * 使用jpql查詢，統計客戶的總數
     * sql：SELECT COUNT(cust_id) FROM cst_customer
     * jpql：select count(custId) from Customer
     */
    @Test
    public void testCount() {
        //1.獲取entityManager物件
        EntityManager em = JpaUtils.getEntityManager();
        //2.開啟事務
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        //3.查詢全部
        //i.根據jpql語句創建Query查詢物件
        String jpql = "select count(custId) from Customer";
        Query query = em.createQuery(jpql);
        //ii.對參數賦值
        //iii.發送查詢，並封裝結果

        /**
         * getResultList ： 直接將查詢結果封裝為list集合
         * getSingleResult : 得到唯一的結果集
         */
        Object result = query.getSingleResult();

        System.out.println(result);

        //4.提交事務
        tx.commit();
        //5.釋放資源
        em.close();
    }


    /**
     * 分頁查詢
     * sql：select * from cst_customer limit 0,2
     * jqpl : from Customer
     */
    @Test
    public void testPaged() {
        //1.獲取entityManager物件
        EntityManager em = JpaUtils.getEntityManager();
        //2.開啟事務
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        //3.查詢全部
        //i.根據jpql語句創建Query查詢物件
        String jpql = "from Customer";
        Query query = em.createQuery(jpql);
        //ii.對參數賦值 -- 分頁參數
        //起始索引
        query.setFirstResult(0);
        //每頁查詢的條數
        query.setMaxResults(2);

        //iii.發送查詢，並封裝結果

        /**
         * getResultList ： 直接將查詢結果封裝為list集合
         * getSingleResult : 得到唯一的結果集
         */
        List list = query.getResultList();

        for(Object obj : list) {
            System.out.println(obj);
        }

        //4.提交事務
        tx.commit();
        //5.釋放資源
        em.close();
    }


    /**
     * 條件查詢
     * 案例：查詢客戶名稱以‘it’開頭的客戶
     * sql：SELECT * FROM cst_customer WHERE cust_name LIKE ?
     * jpql : from Customer where custName like ?
     */
    @Test
    public void testCondition() {
        //1.獲取entityManager物件
        EntityManager em = JpaUtils.getEntityManager();
        //2.開啟事務
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        //3.查詢全部
        //i.根據jpql語句創建Query查詢物件
        String jpql = "from Customer where custName like ?1 ";
        Query query = em.createQuery(jpql);
        //ii.對參數賦值 -- 佔位符參數
        //第一個參數：佔位符的索引位置（從1開始），第二個參數：取值
        query.setParameter(1,"it%");

        //iii.發送查詢，並封裝結果

        /**
         * getResultList ： 直接將查詢結果封裝為list集合
         * getSingleResult : 得到唯一的結果集
         */
        List list = query.getResultList();

        System.out.println(list);

        //4.提交事務
        tx.commit();
        //5.釋放資源
        em.close();
    }
}
