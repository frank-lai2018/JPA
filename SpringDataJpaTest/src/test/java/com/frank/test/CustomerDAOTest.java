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

@RunWith(SpringJUnit4ClassRunner.class) //�n��spring���Ѫ��椸��������
@ContextConfiguration(locations = "classpath:applicationContext.xml")//���wspring�e�����t�m�H��
public class CustomerDAOTest {
	
	@Autowired
	private CustomerDAO customerDAO;
	
	@Test
	public void test_01_findOne() {
		Optional<Customer> optional = customerDAO.findById(1L);
		System.out.println(optional.get());
	}
	
	
	/**
	  * save : �O�s�Ϊ̧�s
	     * �ھڶǻ����ﹳ�O�_�s�b�D��id�A
	     * �p�G�S��id�D���ݩʡG�O�s
	     * �s�bid�D���ݩʡA�ھ�id�d�߼ƾڡA��s�ƾ�
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
     	* ���ղέp�d�ߡG�d�߫Ȥ᪺�`�ƶq
     * count:�έp�`����
     */
    @Test
    public void testCount() {
        long count = customerDAO.count();//�d�ߥ������Ȥ�ƶq
        System.out.println(count);
    }

    /**
     	* ���աG�P�_id��4���Ȥ�O�_�s�b
     * 1. �i�H�d�ߥH�Uid��4���Ȥ�
     	* �p�G�Ȭ��šA�N���s�b�A�p�G�����šA�N��s�b
     * 2. �P�_�ƾڮw��id��4���Ȥ᪺�ƶq
     * �p�G�ƶq��0�A�N���s�b�A�p�G�j��0�A�N��s�b
     */
    @Test
    public void testExists() {
        boolean exists = customerDAO.existsById(1l);
        System.out.println("id��1���Ȥ� �O�_�s�b�G"+exists);
    }


    /**
     * �ھ�id�q�ƾڮw�d��
     * @Transactional : �O��getOne���`�B��
     *
     * findOne�G
     * em.find() :�ߧY�[��
     * getOne�G
     * em.getReference :����[��
     * * ��^���O�@�ӫȤ᪺�ʺA�N�z��H
     * * ����ɭԥΡA����ɭԬd��
     */
    @Test
    @Transactional
    public void testGetOne() {
        Customer customer = customerDAO.getOne(4l);
        System.out.println(customer);
    }

}
