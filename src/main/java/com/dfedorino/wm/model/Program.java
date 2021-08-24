package com.dfedorino.wm.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "programs")
public class Program {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "volume")
    private int waterVolume;
    @Column(name = "temp")
    private int waterTemperature;
    @Column(name = "washing_time")
    private int washingTime;
}
