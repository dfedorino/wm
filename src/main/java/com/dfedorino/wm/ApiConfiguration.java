package com.dfedorino.wm;

import com.dfedorino.wm.actions.ActionResult;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class ApiConfiguration {
    @Scope("prototype")
    @Bean public ActionResult actionResult() {
        return new ActionResult();
    }
}
