package com.frank.domain;

import javax.persistence.*;

/**
 * 客戶的實體類
 * 配置映射關係
 *
 *
 * 1.實體類和表的映射關係
 * @Entity:聲明實體類
 * @Table : 配置實體類和表的映射關係
 * name : 配置數據庫表的名稱
 * 2.實體類中屬性和表中字段的映射關係
 *
 *
 */
@Entity
@Table(name = "cst_customer")
public class Customer {

    /**
     * @Id：聲明主鍵的配置
     * @GeneratedValue:配置主鍵的生成策略
     * strategy
     * GenerationType.IDENTITY ：自增，mysql
     * * 底層數據庫必須支持自動增長（底層數據庫支持的自動增長方式，對id自增）
     * GenerationType.SEQUENCE : 序列，oracle
     * * 底層數據庫必須支持序列
     * GenerationType.TABLE : jpa提供的一種機制，通過一張數據庫表的形式幫助我們完成主鍵自增
     * GenerationType.AUTO ： 由程序自動的幫助我們選擇主鍵生成策略
     * @Column:配置屬性和字段的映射關係
     * name：數據庫表中字段的名稱
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cust_id")
    private Long custId; //客戶的主鍵

    @Column(name = "cust_name")
    private String custName;//客戶名稱

    @Column(name="cust_source")
    private String custSource;//客戶來源

    @Column(name="cust_level")
    private String custLevel;//客戶級別

    @Column(name="cust_industry")
    private String custIndustry;//客戶所屬行業

    @Column(name="cust_phone")
    private String custPhone;//客戶的聯繫方式

    @Column(name="cust_address")
    private String custAddress;//客戶地址

    public Long getCustId() {
        return custId;
    }

    public void setCustId(Long custId) {
        this.custId = custId;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getCustSource() {
        return custSource;
    }

    public void setCustSource(String custSource) {
        this.custSource = custSource;
    }

    public String getCustLevel() {
        return custLevel;
    }

    public void setCustLevel(String custLevel) {
        this.custLevel = custLevel;
    }

    public String getCustIndustry() {
        return custIndustry;
    }

    public void setCustIndustry(String custIndustry) {
        this.custIndustry = custIndustry;
    }

    public String getCustPhone() {
        return custPhone;
    }

    public void setCustPhone(String custPhone) {
        this.custPhone = custPhone;
    }

    public String getCustAddress() {
        return custAddress;
    }

    public void setCustAddress(String custAddress) {
        this.custAddress = custAddress;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "custId=" + custId +
                ", custName='" + custName + '\'' +
                ", custSource='" + custSource + '\'' +
                ", custLevel='" + custLevel + '\'' +
                ", custIndustry='" + custIndustry + '\'' +
                ", custPhone='" + custPhone + '\'' +
                ", custAddress='" + custAddress + '\'' +
                '}';
    }
}