package com.example.BlogEngine.demo;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;



@RestController
@RequestMapping("/api/demo-auth")

public class DemoController {

@GetMapping("/authenticate")
public ResponseEntity<String> sayHello(){
    return ResponseEntity.ok("hello from secured endpoint");
}



}
