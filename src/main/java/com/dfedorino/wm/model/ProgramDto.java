package com.dfedorino.wm.model;

import lombok.Value;

@Value
public class ProgramDto {
    private Long id;
    private String name;

    public ProgramDto(Program program) {
        this.id = program.getId();
        this.name = program.getName();
    }
}
