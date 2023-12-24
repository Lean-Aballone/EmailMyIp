package one.code.lean.EmailMyip.service;

import org.springframework.beans.factory.annotation.Autowired;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class CheckIpService implements Runnable {
    @Autowired
    EmailService emailService;

    private String actual_ip;
    private final String destination;
    private final URL whatismyip;
    private final String subject;

    public CheckIpService(String to) throws URISyntaxException, MalformedURLException{
        this.whatismyip = new URI("http://checkip.amazonaws.com").toURL();
        this.destination = to;
        this.subject = "La dirección ip ha cambiado.";
    }

    public String sendEmail(){
        emailService.sendEmail(destination,subject,"http://" + actual_ip + ":8081");
        return "Se ha enviado la direccion ip ("+actual_ip+") a " + destination + ".";
    }
    public String sendEmail(String to){
        emailService.sendEmail(to,subject,"http://" + actual_ip + ":8081");
        return "Se ha enviado la direccion ip ("+actual_ip+") a " + destination + ".";
    }

    @Override
    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(whatismyip.openStream()));
            String newestIp = in.readLine();
            if(actual_ip.isEmpty()){
                this.actual_ip = newestIp;
                emailService.sendEmail(destination,subject,"http://" + actual_ip + ":8081");
            }
            if (!newestIp.equals(actual_ip)){
                this.actual_ip = newestIp;
                emailService.sendEmail(destination,subject,"http://" + actual_ip + ":8081");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
