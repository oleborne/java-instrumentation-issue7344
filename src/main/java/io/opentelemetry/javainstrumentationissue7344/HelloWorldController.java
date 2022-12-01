package io.opentelemetry.javainstrumentationissue7344;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello-world")
@Slf4j
public class HelloWorldController {
    @GetMapping
    public String sayHello(@RequestHeader(required = false) String traceparent) {
        // log headers to check if we received a W3C traceparent header
        log.info("Received traceparent: {}", traceparent);
        return "how are you?";
    }
}