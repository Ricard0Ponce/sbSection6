package sb.uam.interceptor.s6.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class AppControlller {
    @GetMapping("/app")
    public Map<String, String> getApp(){

        return Collections.singletonMap("message","Handler app del controlador");
    }

    @GetMapping("/var")
    public Map<String, String> getVar(){

        return Collections.singletonMap("message","Handler var del controlador");
    }

    @GetMapping("/baz")
    public Map<String, String> getBaz(){

        return Collections.singletonMap("message","Handler baz del controlador");
    }
}
