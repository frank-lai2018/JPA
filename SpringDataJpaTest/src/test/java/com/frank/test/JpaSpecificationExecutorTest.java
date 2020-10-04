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
     * �ھڱ���A�d�߳�Ӫ���
     *
     */
    @Test
    public void testSpec() {
        //�ΦW������
        /**
                            * �۩w�q�d�߱���
         * 1.��{Specification���f�]���Ѫx���G�d�ߪ��ﹳ�����^
         * 2.��{toPredicate��k�]�c�y�d�߱���^
         * 3.�ݭn�ǧU��k�ѼƤ�����ӰѼơ]
         * root�G����ݭn�d�ߪ������ݩ�
         * CriteriaBuilder�G�c�y�d�߱��󪺡A�����ʸˤF�ܦh���d�߱���]�ҽk�ǰt�A��Ǥǰt�^
                            * �^
                            * �רҡG�ھګȤ�W�٬d�ߡA�d�߫Ȥ�W�����ժ��Ȥ�
                            * �d�߱���
         * 1.�d�ߤ覡
         * criteriaBuilder����
         * 2.������ݩʦW��
         * root����
         *
         */
        Specification<Customer> spec = new Specification<Customer>() {

			public Predicate toPredicate(Root<Customer> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				//1.���������ݩ�
				Path<Object> custName = root.get("custName");
				//2.�c�y�d�߱���:select * from cst_customer where cust_name= '����'
				/**
				 * �Ĥ@�ӰѼ�:�ݭn������ݩ�(path����)
				 * �ĤG�ӰѼ�:�ݭn������ݩʭ�
				 * */
				Predicate predicate = criteriaBuilder.equal(custName, "apple");//�i���Ǫ��ǰt (������˩m�A������ݩʨ���)
				
				return predicate;
			}
		};
         Optional<Customer> findOne = customerDao.findOne(spec);
        System.out.println(findOne.get());
    }
    
    /**
     	* �h����d��
     * 		�ר�:
     * */
    @Test
    public void testSpec1() {
    	Specification<Customer> spec = new Specification<Customer>() {
			
			public Predicate toPredicate(Root<Customer> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				Path<Object> custName = root.get("custName");//�Ȥ�W
                Path<Object> custIndustry = root.get("custIndustry");//���ݦ�~

                //�c�y�d��
                //1.�c�y�Ȥ�W����Ǥǰt�d��
                Predicate p1 = criteriaBuilder.equal(custName, "apple");//�Ĥ@�ӰѼơApath�]�ݩʡ^�A�ĤG�ӰѼơA�ݩʪ�����
                //2..�c�y���ݦ�~����Ǥǰt�d��
                Predicate p2 = criteriaBuilder.equal(custIndustry, "IT");
                //3.�N�h�Ӭd�߱���զX��@�_�G�զX�]��������@�֥B��������G�G�P���Y�A��������@�κ�������G�Y�i�G�����Y�^
                Predicate and = criteriaBuilder.and(p1, p2);//�H�P���Φ������h�Ӭd�߱���
                // Predicate or = criteriaBuilder.or(p1, p2);//�H�Ϊ��Φ������h�Ӭd�߱���
                return and;
			}
		};
    	
    	List<Customer> findAll = customerDao.findAll(spec);
    	System.out.println(findAll);
    }
    
    /**
                * �רҡG�����ھګȤ�W�٪��ҽk�ǰt�A��^�Ȥ�C��
                * �Ȥ�W�٥H ��app�� �}�Y
     *
     * equal �G��������path����]�ݩʡ^�A�M��i�����Y�i
     * gt�Alt,ge,le,like : �o��path����A�ھ�path���w������Ѽ������A�A�h�i����
               * ���w�Ѽ������Gpath.as(�������r�`�X����)
     */
    @Test
    public void testSpec3() {
        //�c�y�d�߱���
        Specification<Customer> spec = new Specification<Customer>() {
            public Predicate toPredicate(Root<Customer> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                //�d���ݩʡG�Ȥ�W
                Path<Object> custName = root.get("custName");
                //�d�ߤ覡�G�ҽk�ǰt
                Predicate like = criteriaBuilder.like(custName.as(String.class), "app%");
                return like;
            }
        };
		// List<Customer> list = customerDao.findAll(spec);
		// for (Customer customer : list) {
		// System.out.println(customer);
		// }
        //�K�[�Ƨ�
        //�ЫرƧǪ���,�ݭn�եκc�y��k��Ҥ�sort����
        //�Ĥ@�ӰѼơG�ƧǪ����ǡ]�˧ǡA���ǡ^
        // Sort.Direction.DESC:�˧�
        // Sort.Direction.ASC �G �ɧ�
        //�ĤG�ӰѼơG�ƧǪ��ݩʦW��
        Sort sort = Sort.by(Sort.Direction.ASC,"custId");
        List<Customer> list = customerDao.findAll(spec, sort);
        for (Customer customer : list) {
            System.out.println(customer);
        }
    }

    /**
               * �����d��
     * Specification: �d�߱���
     * Pageable�G�����Ѽ�
                * �����ѼơG�d�ߪ����X�A�C���d�ߪ�����
     * findAll(Specification,Pageable)�G�a�����󪺤���
     * findAll(Pageable)�G�S�����󪺤���
                * ��^�GPage�]springDataJpa���ڭ̫ʸ˦n��pageBean����A�ƾڦC��A�@���ơ^
     */
    @SuppressWarnings("unchecked")
	@Test
    public void testSpec4() {

        Specification spec = null;
        //PageRequest����OPageable���f����{��
        /**
		          * �Ы�PageRequest���L�{���A�ݭn�եΥL���c�y��k�ǤJ��ӰѼ�
		         * �Ĥ@�ӰѼơG��e�d�ߪ����ơ]�q0�}�l�^
		         * �ĤG�ӰѼơG�C���d�ߪ��ƶq
         */
        Pageable pageable = PageRequest.of(0,2);
        //�����d��
        Page<Customer> page = customerDao.findAll(spec, pageable);
        System.out.println(page.getContent()); //�o��ƾڶ��X�C��
        System.out.println(page.getTotalElements());//�o���`����
        System.out.println(page.getTotalPages());//�o���`����
    }
}
