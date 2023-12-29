package one.code.lean.EmailMyip.service;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Timer;

@Service
@AllArgsConstructor
public class DaemonService {
    @Autowired
    private CheckIpService checkIpService;
    public String startIpChecker(){
        Timer timer = new Timer();
        timer.schedule(checkIpService,0,60000L * 10);
        return "Started.";
    }
}
