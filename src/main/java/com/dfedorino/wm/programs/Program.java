package com.dfedorino.wm.programs;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
//@NoArgsConstructor
//@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Entity
@Table(name = "programs")
public class Program {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "volume")
    private int waterVolume;
    @Column(name = "temperature")
    private int waterTemperature;
    @Column(name = "washing_time")
    private int washingTime;

    public Program() {}

    public Program(String name, int waterVolume, int waterTemperature, int washingTime) {
        this.name = name;
        this.waterVolume = waterVolume;
        this.waterTemperature = waterTemperature;
        this.washingTime = washingTime;
    }
}
