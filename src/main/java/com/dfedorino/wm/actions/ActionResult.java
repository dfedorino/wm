package com.dfedorino.wm.actions;

import lombok.Value;

import java.time.Instant;

@Value
public class ActionResult {
    private boolean isApplied;
    private String actionName;
    private Instant timestamp;
}
