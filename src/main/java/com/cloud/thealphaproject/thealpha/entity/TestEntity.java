package com.cloud.thealphaproject.thealpha.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name="testtable")
public class TestEntity {

    @Id
    private int id;
    private String name;
}
