package org.zerock.b01.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Member {

    @Id // primarykey
    @GeneratedValue(strategy = GenerationType.IDENTITY) //
    private Long id;
    private String name;
    private  String phone;
}
