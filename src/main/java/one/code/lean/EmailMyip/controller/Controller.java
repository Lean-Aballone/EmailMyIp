package one.code.lean.EmailMyip.controller;

import one.code.lean.EmailMyip.service.CheckIpService;
import one.code.lean.EmailMyip.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    @Autowired
    CheckIpService checkIpService;

    @GetMapping("/ip/{mail}")
    public ResponseEntity<?> sendAnEmail(@PathVariable String mail){
        return new ResponseEntity<>(checkIpService.sendEmail(mail), HttpStatus.OK);
    }
    @GetMapping("/ip")
    public ResponseEntity<?> sendAnEmail(){
        return new ResponseEntity<>(checkIpService.sendEmail(), HttpStatus.OK);
    }
}
