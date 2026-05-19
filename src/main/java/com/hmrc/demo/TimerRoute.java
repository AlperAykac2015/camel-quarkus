package com.hmrc.demo;

import org.apache.camel.builder.RouteBuilder;

public class TimerRoute extends RouteBuilder {

    @Override
    public void configure() {

        from("timer:localTimer?period=5000")
                .routeId("timer-route")
                .setBody(simple("Timer fired at ${date:now:yyyy-MM-dd HH:mm:ss}"))
                .to("log:local-timer");
    }
}
