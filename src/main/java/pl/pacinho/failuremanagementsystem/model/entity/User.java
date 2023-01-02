/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.pacinho.failuremanagementsystem.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import pl.pacinho.failuremanagementsystem.model.enums.Department;
import pl.pacinho.failuremanagementsystem.model.enums.Role;

import javax.persistence.*;

/**
 * @author pojdana
 */
@Entity
@Table(name = "Users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GenericGenerator(name = "userIdGen", strategy = "increment")
    @GeneratedValue(generator = "userIdGen")
    private Long id;

    private String firstName;

    private String lastName;

    @Column(unique = true)
    private String username;

    private String password;

    private int active;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "DEPARTMENT")
    @Enumerated(EnumType.STRING)
    private Department department;

    public User(String firstName, String lastName, String username, String password, int active, Role role, Department department ) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.active = active;
        this.role = role;
        this.department = department;
    }

    public String getName(){
        return this.getFirstName() + ' ' + this.getLastName();
    }
}