package com.hmrc.demo;

import org.apache.camel.builder.RouteBuilder;

public class HelloRoute extends RouteBuilder {

    @Override
    public void configure() {

        from("platform-http:/hello")
                .routeId("hello-route")
                .log("Received request on /hello")
                .setBody(simple("Hello from Camel Quarkus on local MacBook"));
    }
}
