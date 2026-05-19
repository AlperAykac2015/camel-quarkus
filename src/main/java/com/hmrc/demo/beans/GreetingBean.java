package com.hmrc.demo.beans;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;
import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
@ApplicationScoped
@Named("greetingBean")
public class GreetingBean {

    public String enrich(String body) {
        return "Enriched by Java bean: " + body;
    }
}
