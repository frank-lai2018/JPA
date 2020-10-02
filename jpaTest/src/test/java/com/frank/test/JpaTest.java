package com.frank.test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.junit.Test;

import com.frank.domain.Customer;
import com.frank.utils.JpaUtils;

public class JpaTest {

	/**
	     * 測試jpa的保存
	     * 案例：保存一個客戶到數據庫中
     * Jpa的操作步驟
     * 1.加載配置文件創建工廠（實體管理器工廠）物件
     * 2.通過實體管理器工廠獲取實體管理器
     * 3.獲取事務物件，開啟事務
     * 4.完成增刪改查操作
     * 5.提交事務（回滾事務）
     * 6.釋放資源
     */
	@Test
	public void testSave() {
		//1.加載配置文件創建工廠（實體管理器工廠）物件
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("myJpa");
		
		//2.通過實體管理器工廠獲取實體管理器
		EntityManager entityManager = factory.createEntityManager();
		
		//3.獲取事務物件，開啟事務
		EntityTransaction transaction = entityManager.getTransaction();//獲取事務物件
		transaction.begin();//開啟事務
		
		//4.完成增刪改查操作
        Customer customer = new Customer();
        customer.setCustName("測試測試");
        customer.setCustIndustry("拉拉aa");
        //保存
        entityManager.persist(customer);
        
        //5.提交事務（回滾事務）
        transaction.commit();
        
        //6.釋放資源
        entityManager.close();
        factory.close();
	}
	
	
	/**
     * 根據id查詢客戶
     * 使用find方法查詢：
     * 1.查詢的物件就是當前客戶物件本身
     * 2.在調用find方法的時候，就會發送sql語句查詢數據庫
     *
     * 立即加載
     *
     *
     */
    @Test
    public void testFind() {
        //1.通過工具類獲取entityManager
        EntityManager entityManager = JpaUtils.getEntityManager();
        //2.開啟事務
        EntityTransaction tx = entityManager.getTransaction();
        tx.begin();
        //3.增刪改查 -- 根據id查詢客戶
        /**
         * find : 根據id查詢數據
         * class：查詢數據的結果需要包裝的實體類類型的字節碼
         * id：查詢的主鍵的取值
         */
        Customer customer = entityManager.find(Customer.class, 1l);
       // System.out.print(customer);
        //4.提交事務
        tx.commit();
        //5.釋放資源
        entityManager.close();
    }

    /**
     * 根據id查詢客戶
     * getReference方法
     * 1.獲取的對像是一個動態代理物件
     * 2.調用getReference方法不會立即發送sql語句查詢數據庫
     * * 當調用查詢結果物件的時候，才會發送查詢的sql語句：什麼時候用，什麼時候發送sql語句查詢數據庫
     *
     * 延遲加載（懶加載）
     * * 得到的是一個動態代理物件
     * * 什麼時候用，什麼使用才會查詢
     */
    @Test
    public void testReference() {
        //1.通過工具類獲取entityManager
        EntityManager entityManager = JpaUtils.getEntityManager();
        //2.開啟事務
        EntityTransaction tx = entityManager.getTransaction();
        tx.begin();
        //3.增刪改查 -- 根據id查詢客戶
        /**
         * getReference : 根據id查詢數據
         * class：查詢數據的結果需要包裝的實體類類型的class
         * id：查詢的主鍵的取值
         */
        Customer customer = entityManager.getReference(Customer.class, 1l);
        System.out.print(customer);
        //4.提交事務
        tx.commit();
        //5.釋放資源
        entityManager.close();
    }


    /**
     * 刪除客戶的案例
     *
     */
    @Test
    public void testRemove() {
        //1.通過工具類獲取entityManager
        EntityManager entityManager = JpaUtils.getEntityManager();
        //2.開啟事務
        EntityTransaction tx = entityManager.getTransaction();
        tx.begin();
        //3.增刪改查 -- 刪除客戶

        //i 根據id查詢客戶
        Customer customer = entityManager.find(Customer.class,1l);
        //ii 調用remove方法完成刪除操作
        entityManager.remove(customer);

        //4.提交事務
        tx.commit();
        //5.釋放資源
        entityManager.close();
    }


    /**
     * 更新客戶的操作
     * merge(Object)
     */
    @Test
    public void testUpdate() {
        //1.通過工具類獲取entityManager
        EntityManager entityManager = JpaUtils.getEntityManager();
        //2.開啟事務
        EntityTransaction tx = entityManager.getTransaction();
        tx.begin();
        //3.增刪改查 -- 更新操作

        //i 查詢客戶
        Customer customer = entityManager.find(Customer.class,1L);
        //ii 更新客戶
        customer.setCustIndustry("it");
        entityManager.merge(customer);

        //4.提交事務
        tx.commit();
        //5.釋放資源
        entityManager.close();
    }
}
