package com.frank.test;

import java.util.List;
import java.util.Optional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.frank.dao.CustomerDAO;
import com.frank.domain.Customer;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class JpaSpecificationExecutorTest {
    @Autowired
    private CustomerDAO customerDao;
    
    
    
    /**
     * 根據條件，查詢單個物件
     *
     */
    @Test
    public void testSpec() {
        //匿名內部類
        /**
                            * 自定義查詢條件
         * 1.實現Specification接口（提供泛型：查詢的對像類型）
         * 2.實現toPredicate方法（構造查詢條件）
         * 3.需要藉助方法參數中的兩個參數（
         * root：獲取需要查詢的物件屬性
         * CriteriaBuilder：構造查詢條件的，內部封裝了很多的查詢條件（模糊匹配，精準匹配）
                            * ）
                            * 案例：根據客戶名稱查詢，查詢客戶名為測試的客戶
                            * 查詢條件
         * 1.查詢方式
         * criteriaBuilder物件
         * 2.比較的屬性名稱
         * root物件
         *
         */
        Specification<Customer> spec = new Specification<Customer>() {

			public Predicate toPredicate(Root<Customer> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				//1.獲取比較的屬性
				Path<Object> custName = root.get("custName");
				//2.構造查詢條件:select * from cst_customer where cust_name= '測試'
				/**
				 * 第一個參數:需要比較的屬性(path物件)
				 * 第二個參數:需要比較的屬性值
				 * */
				Predicate predicate = criteriaBuilder.equal(custName, "apple");//進行精準的匹配 (比較的捨姓，比較的屬性取值)
				
				return predicate;
			}
		};
         Optional<Customer> findOne = customerDao.findOne(spec);
        System.out.println(findOne.get());
    }
    
    /**
     	* 多條件查詢
     * 		案例:
     * */
    @Test
    public void testSpec1() {
    	Specification<Customer> spec = new Specification<Customer>() {
			
			public Predicate toPredicate(Root<Customer> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				Path<Object> custName = root.get("custName");//客戶名
                Path<Object> custIndustry = root.get("custIndustry");//所屬行業

                //構造查詢
                //1.構造客戶名的精準匹配查詢
                Predicate p1 = criteriaBuilder.equal(custName, "apple");//第一個參數，path（屬性），第二個參數，屬性的取值
                //2..構造所屬行業的精準匹配查詢
                Predicate p2 = criteriaBuilder.equal(custIndustry, "IT");
                //3.將多個查詢條件組合到一起：組合（滿足條件一併且滿足條件二：與關係，滿足條件一或滿足條件二即可：或關係）
                Predicate and = criteriaBuilder.and(p1, p2);//以與的形式拼接多個查詢條件
                // Predicate or = criteriaBuilder.or(p1, p2);//以或的形式拼接多個查詢條件
                return and;
			}
		};
    	
    	List<Customer> findAll = customerDao.findAll(spec);
    	System.out.println(findAll);
    }
    
    /**
                * 案例：完成根據客戶名稱的模糊匹配，返回客戶列表
                * 客戶名稱以 ’app‘ 開頭
     *
     * equal ：直接的到path物件（屬性），然後進行比較即可
     * gt，lt,ge,le,like : 得到path物件，根據path指定比較的參數類型，再去進行比較
               * 指定參數類型：path.as(類型的字節碼物件)
     */
    @Test
    public void testSpec3() {
        //構造查詢條件
        Specification<Customer> spec = new Specification<Customer>() {
            public Predicate toPredicate(Root<Customer> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                //查詢屬性：客戶名
                Path<Object> custName = root.get("custName");
                //查詢方式：模糊匹配
                Predicate like = criteriaBuilder.like(custName.as(String.class), "app%");
                return like;
            }
        };
		// List<Customer> list = customerDao.findAll(spec);
		// for (Customer customer : list) {
		// System.out.println(customer);
		// }
        //添加排序
        //創建排序物件,需要調用構造方法實例化sort物件
        //第一個參數：排序的順序（倒序，正序）
        // Sort.Direction.DESC:倒序
        // Sort.Direction.ASC ： 升序
        //第二個參數：排序的屬性名稱
        Sort sort = Sort.by(Sort.Direction.ASC,"custId");
        List<Customer> list = customerDao.findAll(spec, sort);
        for (Customer customer : list) {
            System.out.println(customer);
        }
    }

    /**
               * 分頁查詢
     * Specification: 查詢條件
     * Pageable：分頁參數
                * 分頁參數：查詢的頁碼，每頁查詢的條數
     * findAll(Specification,Pageable)：帶有條件的分頁
     * findAll(Pageable)：沒有條件的分頁
                * 返回：Page（springDataJpa為我們封裝好的pageBean物件，數據列表，共條數）
     */
    @SuppressWarnings("unchecked")
	@Test
    public void testSpec4() {

        Specification spec = null;
        //PageRequest物件是Pageable接口的實現類
        /**
		          * 創建PageRequest的過程中，需要調用他的構造方法傳入兩個參數
		         * 第一個參數：當前查詢的頁數（從0開始）
		         * 第二個參數：每頁查詢的數量
         */
        Pageable pageable = PageRequest.of(0,2);
        //分頁查詢
        Page<Customer> page = customerDao.findAll(spec, pageable);
        System.out.println(page.getContent()); //得到數據集合列表
        System.out.println(page.getTotalElements());//得到總條數
        System.out.println(page.getTotalPages());//得到總頁數
    }
}
