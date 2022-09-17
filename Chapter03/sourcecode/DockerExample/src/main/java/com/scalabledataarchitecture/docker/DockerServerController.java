package com.scalabledataarchitecture.docker;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DockerServerController {
    @GetMapping("/hello")
    public String getMessage() {
        return "Hello from the Container!";
    }

}
