package one.code.lean.EmailMyip.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.TimerTask;

@Service
public class CheckIpService extends TimerTask {

    @Autowired
    private JavaMailSender mailSender;
    private String actual_ip;
    private final String destination;
    private final URL whatismyip;
    private final String subject;
    private BufferedReader in;

    @Autowired
    public CheckIpService() throws URISyntaxException, IOException {
        this.whatismyip = new URI("http://checkip.amazonaws.com").toURL();
        this.destination = "l.aballone@gmail.com";
        this.subject = "La dirección ip ha cambiado.";
        this.in = new BufferedReader(new InputStreamReader(whatismyip.openStream()));
    }

    public String sendEmail() throws IOException {
        if(actual_ip == null)actual_ip = in.readLine();
        sendEmail(destination,subject,"http://" + actual_ip + ":8081");
        return "Se ha enviado la direccion ip ("+actual_ip+") a " + destination + ".";
    }
    public String sendEmail(String to){
        sendEmail(to,subject,"http://" + actual_ip + ":8081");
        return "Se ha enviado la direccion ip ("+actual_ip+") a " + destination + ".";
    }



    private void sendEmail(String to,String subject, String body){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        mailSender.send(message);
    }

    @Override
    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(whatismyip.openStream()));
            String newestIp = in.readLine();
            if(actual_ip == null){
                this.actual_ip = newestIp;
                sendEmail(destination,subject,"http://" + actual_ip + ":8081");
            }
            if (!newestIp.equals(actual_ip)){
                this.actual_ip = newestIp;
                sendEmail(destination,subject,"http://" + actual_ip + ":8081");
            }
            System.out.println(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy (HH:mm:ss)")) + " - Comprobación realizada con exito. La direccion ip es: " + newestIp);
        } catch (IOException e) {
            System.out.println("CheckIpService > run > catch(IOException)");
            throw new RuntimeException(e);
        }
    }
}
