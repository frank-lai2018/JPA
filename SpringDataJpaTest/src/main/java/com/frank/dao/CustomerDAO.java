package com.frank.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.frank.domain.Customer;

/**
 * 符合SpringDataJpa的dao層接口規範
 * JpaRepository<操作的實體類類型，實體類中主鍵屬性的類型>
 * * 封裝了基本CRUD操作
 * JpaSpecificationExecutor<操作的實體類類型>
 * * 封裝了複雜查詢（分頁）
 */
public interface CustomerDAO extends JpaRepository<Customer,Long>,JpaSpecificationExecutor<Customer>{
	/**
 	* 案例：根據客戶名稱查詢客戶
 	* 使用jpql的形式查詢
    * jpql：from Customer where custName = ?
 	*
 	* 配置jpql語句，使用的@Query註解
 	*/
    @Query(value="from Customer where custName = ?1")
    public Customer findJpql(String custName);


    /**
     	* 案例：根據客戶名稱和客戶id查詢客戶
     * jpql： from Customer where custName = ? and custId = ?
     *
               *  對於多個佔位符參數
              * 賦值的時候，默認的情況下，佔位符的位置需要和方法參數中的位置保持一致
     *
     	* 可以指定佔位符參數的位置
     * ? 索引的方式，指定此佔位的取值來源
     */
    @Query(value = "from Customer where custName = ?2 and custId = ?1")
    public Customer findCustNameAndId(Long id,String name);

    /**
	     *   使用jpql完成更新操作
	     *   案例 ： 根據id更新，客戶的名稱
	   *     更新4號客戶的名稱，將名稱改為“黑馬程序員”
     *
     * sql ：update cst_customer set cust_name = ? where cust_id = ?
     * jpql : update Customer set custName = ? where custId = ?
     *
     * @Query : 代表的是進行查詢
     * * 聲明此方法是用來進行更新操作
     * @Modifying
     * * 當前執行的是一個更新操作
     *
     */
    @Query(value = " update Customer set custName = ?2 where custId = ?1 ")
    @Modifying
    public void updateCustomer(long custId,String custName);


    /**
	     * 使用sql的形式查詢：
	     * 查詢全部的客戶
     * sql ： select * from cst_customer;
     * Query : 配置sql查詢
     * value ： sql語句
     * nativeQuery ： 查詢方式
     * true ： sql查詢
     * false：jpql查詢
     *
     */
    //@Query(value = " select * from cst_customer" ,nativeQuery = true)
    @Query(value="select * from cst_customer where cust_name like ?1",nativeQuery = true)
    public List<Object [] > findSql(String name);


	/**
 	* 方法名的約定：
 	* findBy : 查詢
 	* 對像中的屬性名（首字母大寫） ： 查詢的條件
 	* CustName
 	* * 默認情況 ： 使用 等於的方式查詢
 	* 特殊的查詢方式
 	*
 	* findByCustName -- 根據客戶名稱查詢
 	*
	 * 再springdataJpa的運行階段
	 * 會根據方法名稱進行解析 findBy from xxx(實體類)
	 * 屬性名稱 where custName =
	*
    * 1.findBy + 屬性名稱 （根據屬性名稱進行完成匹配的查詢=）
    * 2.findBy + 屬性名稱 + “查詢方式（Like | isnull）”
    * findByCustNameLike
    * 3.多條件查詢
    * findBy + 屬性名 + “查詢方式” + “多條件的連接符（and|or）” + 屬性名 + “查詢方式”
    */
    public Customer findByCustName(String custName);


    public List<Customer> findByCustNameLike(String custName);

    //使用客戶名稱模糊匹配和客戶所屬行業精準匹配的查詢
    public Customer findByCustNameLikeAndCustIndustry(String custName,String custIndustry);
}
