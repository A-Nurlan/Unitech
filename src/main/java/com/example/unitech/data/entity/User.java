package com.example.unitech.data.entity;

import lombok.Data;
import lombok.experimental.FieldNameConstants;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@FieldNameConstants
@Table(name = "\"user\"")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String pin;

    private String password;

    private Date registrationDate;

}
