package com.dfedorino.wm.model;

import lombok.Value;

@Value
public class Program {
    private Long id;
    private int waterVolume;
    private int waterTemperature;
    private int washingTime;
}
