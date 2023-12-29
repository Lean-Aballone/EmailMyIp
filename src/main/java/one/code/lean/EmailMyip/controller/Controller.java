package one.code.lean.EmailMyip.controller;

import one.code.lean.EmailMyip.service.CheckIpService;
import one.code.lean.EmailMyip.service.DaemonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import java.io.IOException;

@RestController
public class Controller {

    @Autowired
    private CheckIpService checkIpService;

    @Autowired
    private DaemonService daemonService;

    @PostMapping("/ip")
    public ResponseEntity<?> sendAnEmail() throws IOException {
        return new ResponseEntity<>(checkIpService.sendEmail(), HttpStatus.OK);
    }

    @PostMapping("/startDaemon")
    public ResponseEntity<?> startTimer(){
        return new ResponseEntity<>(daemonService.startIpChecker(),HttpStatus.OK);
    }
}
