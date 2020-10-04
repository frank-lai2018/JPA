package com.frank.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.frank.domain.Customer;

/**
 * �ŦXSpringDataJpa��dao�h���f�W�d
 * JpaRepository<�ާ@�������������A���������D���ݩʪ�����>
 * * �ʸˤF��CRUD�ާ@
 * JpaSpecificationExecutor<�ާ@������������>
 * * �ʸˤF�����d�ߡ]�����^
 */
public interface CustomerDAO extends JpaRepository<Customer,Long>,JpaSpecificationExecutor<Customer>{
	/**
 	* �רҡG�ھګȤ�W�٬d�߫Ȥ�
 	* �ϥ�jpql���Φ��d��
    * jpql�Gfrom Customer where custName = ?
 	*
 	* �t�mjpql�y�y�A�ϥΪ�@Query����
 	*/
    @Query(value="from Customer where custName = ?1")
    public Customer findJpql(String custName);


    /**
     	* �רҡG�ھګȤ�W�٩M�Ȥ�id�d�߫Ȥ�
     * jpql�G from Customer where custName = ? and custId = ?
     *
               *  ���h�Ӧ���ŰѼ�
              * ��Ȫ��ɭԡA�q�{�����p�U�A����Ū���m�ݭn�M��k�ѼƤ�����m�O���@�P
     *
     	* �i�H���w����ŰѼƪ���m
     * ? ���ު��覡�A���w�����쪺���Ȩӷ�
     */
    @Query(value = "from Customer where custName = ?2 and custId = ?1")
    public Customer findCustNameAndId(Long id,String name);

    /**
	     *   �ϥ�jpql������s�ާ@
	     *   �ר� �G �ھ�id��s�A�Ȥ᪺�W��
	   *     ��s4���Ȥ᪺�W�١A�N�W�٧אּ���°��{�ǭ���
     *
     * sql �Gupdate cst_customer set cust_name = ? where cust_id = ?
     * jpql : update Customer set custName = ? where custId = ?
     *
     * @Query : �N���O�i��d��
     * * �n������k�O�ΨӶi���s�ާ@
     * @Modifying
     * * ��e���檺�O�@�ӧ�s�ާ@
     *
     */
    @Query(value = " update Customer set custName = ?2 where custId = ?1 ")
    @Modifying
    public void updateCustomer(long custId,String custName);


    /**
	     * �ϥ�sql���Φ��d�ߡG
	     * �d�ߥ������Ȥ�
     * sql �G select * from cst_customer;
     * Query : �t�msql�d��
     * value �G sql�y�y
     * nativeQuery �G �d�ߤ覡
     * true �G sql�d��
     * false�Gjpql�d��
     *
     */
    //@Query(value = " select * from cst_customer" ,nativeQuery = true)
    @Query(value="select * from cst_customer where cust_name like ?1",nativeQuery = true)
    public List<Object [] > findSql(String name);


	/**
 	* ��k�W�����w�G
 	* findBy : �d��
 	* �ﹳ�����ݩʦW�]���r���j�g�^ �G �d�ߪ�����
 	* CustName
 	* * �q�{���p �G �ϥ� ���󪺤覡�d��
 	* �S���d�ߤ覡
 	*
 	* findByCustName -- �ھګȤ�W�٬d��
 	*
	 * �AspringdataJpa���B�涥�q
	 * �|�ھڤ�k�W�ٶi��ѪR findBy from xxx(������)
	 * �ݩʦW�� where custName =
	*
    * 1.findBy + �ݩʦW�� �]�ھ��ݩʦW�ٶi�槹���ǰt���d��=�^
    * 2.findBy + �ݩʦW�� + ���d�ߤ覡�]Like | isnull�^��
    * findByCustNameLike
    * 3.�h����d��
    * findBy + �ݩʦW + ���d�ߤ覡�� + ���h���󪺳s���š]and|or�^�� + �ݩʦW + ���d�ߤ覡��
    */
    public Customer findByCustName(String custName);


    public List<Customer> findByCustNameLike(String custName);

    //�ϥΫȤ�W�ټҽk�ǰt�M�Ȥ���ݦ�~��Ǥǰt���d��
    public Customer findByCustNameLikeAndCustIndustry(String custName,String custIndustry);
}
