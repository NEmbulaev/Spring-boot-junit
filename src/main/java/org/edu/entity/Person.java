package org.edu.entity;


import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "tbl_person")
@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private int age;
    private String name;

    public Person(int age, String name) {
        this.age = age;
        this.name = name;
    }
}
