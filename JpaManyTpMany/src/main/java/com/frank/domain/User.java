package com.frank.domain;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "sys_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_id")
    private Long userId;
    @Column(name="user_name")
    private String userName;
    @Column(name="age")
    private Integer age;

    /**
     * 配置用戶到角色的多對多關係
     * 配置多對多的映射關係
     * 1.聲明表關係的配置
     * @ManyToMany(targetEntity = Role.class) //多對多
     * targetEntity：代表對方的實體類Class
     * 2.配置中間表（包含兩個外鍵）
     * @JoinTable
     * name : 中間表的名稱
     * joinColumns：配置當前物件在中間表的外鍵
     * @JoinColumn的數組
     * name：外鍵名
     * referencedColumnName：參照的主表的主鍵名
     * inverseJoinColumns：配置對方物件在中間表的外鍵
     */
    @ManyToMany(targetEntity = Role.class,cascade = CascadeType.ALL)
    @JoinTable(name = "sys_user_role",
            //joinColumns,當前物件在中間表中的外鍵
            joinColumns = {@JoinColumn(name = "sys_user_id",referencedColumnName = "user_id")},
            //inverseJoinColumns，對方物件在中間表的外鍵
            inverseJoinColumns = {@JoinColumn(name = "sys_role_id",referencedColumnName = "role_id")}
    )
    private Set<Role> roles = new HashSet<Role>();

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}