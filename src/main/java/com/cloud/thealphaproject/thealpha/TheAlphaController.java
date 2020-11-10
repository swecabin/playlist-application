package com.cloud.thealphaproject.thealpha;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/")
public class TheAlphaController {

    @Autowired
    private TestRepository testRepository;

    @GetMapping(value="ping", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PingResponse> Ping() {

        TestEntity entity = testRepository.findById(1);
        return new ResponseEntity<PingResponse>(PingResponse.builder().message(entity.getName()).build(), HttpStatus.OK);
    }

}
