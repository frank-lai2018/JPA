package com.frank.test;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.frank.dao.CustomerDAO;
import com.frank.domain.Customer;

@RunWith(SpringJUnit4ClassRunner.class) //羘spring矗ㄑ虫じ代刚吏挂
@ContextConfiguration(locations = "classpath:applicationContext.xml")//﹚spring甧竟皌竚獺
public class CustomerDAOTest {
	
	@Autowired
	private CustomerDAO customerDAO;
	
	@Test
	public void test_01_findOne() {
		Optional<Customer> optional = customerDAO.findById(1L);
		System.out.println(optional.get());
	}
	
	
	/**
	  * save : 玂┪穝
	     * 沮肚患癸钩琌龄id
	     * 狦⊿Τid龄妮┦玂
	     * id龄妮┦沮id琩高计沮穝计沮
     */
	@Test
	public void test_02_saveForAdd() {
		Customer customer = new Customer();
		customer.setCustName("frank");
		customer.setCustPhone("0000145");
		Customer save = customerDAO.save(customer);
		System.out.println(save);
		
	}
	
	@Test
	public void test_03_saveForUpdate() {
		Customer customer = new Customer();
		customer.setCustId(5L);
		customer.setCustName("sssss");
		customer.setCustPhone("0000145");
		Customer save = customerDAO.save(customer);
		System.out.println(save);
		
	}
	
	@Test
	public void test_03_delete() {
		customerDAO.deleteById(5L);
		
	}
	
	/**
     	* 代刚参璸琩高琩高め羆计秖
     * count:参璸羆兵计
     */
    @Test
    public void testCount() {
        long count = customerDAO.count();//琩高场め计秖
        System.out.println(count);
    }

    /**
     	* 代刚耞id4め琌
     * 1. 琩高id4め
     	* 狦ぃ狦ぃ
     * 2. 耞计沮畐いid4め计秖
     * 狦计秖0ぃ狦0
     */
    @Test
    public void testExists() {
        boolean exists = customerDAO.existsById(1l);
        System.out.println("id1め 琌"+exists);
    }


    /**
     * 沮id眖计沮畐琩高
     * @Transactional : 玂靡getOneタ盽笲︽
     *
     * findOne
     * em.find() :ミ更
     * getOne
     * em.getReference :┑筐更
     * * 琌め笆篈瞶癸禜
     * * ぐ或ノぐ或琩高
     */
    @Test
    @Transactional
    public void testGetOne() {
        Customer customer = customerDAO.getOne(4l);
        System.out.println(customer);
    }

}
