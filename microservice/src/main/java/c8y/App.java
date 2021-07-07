package c8y;

import com.cumulocity.microservice.autoconfigure.MicroserviceApplication;
import com.cumulocity.model.authentication.CumulocityBasicCredentials;
import com.cumulocity.sdk.client.Platform;
import com.cumulocity.sdk.client.PlatformImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

@MicroserviceApplication
@RestController
public class App {
    public static int j = 0;

    public static void main (String[] args) {
        //SpringApplication.run(App.class, args);
        //Platform platform = new PlatformImpl("https://bdedov.1.stage.c8y.io/", CumulocityBasicCredentials.from("email:pass"));
        //platform.getEventApi().create(new EventRepresentation());
        String[] info = new String[3];
        for(int i=0; i<3; i++){
            info[i] = "";
        }
        //int j = 0;
        String[] clientId = {""};
        try {
            WebSocketClientEndpoint client = new WebSocketClientEndpoint(new URI("wss://bdedov.1.stage.c8y.io/notification/realtime"));
            client.addMessageHandler(new WebSocketClientEndpoint.MessageHandler() {
                @Override
                public void handleMessage(String message) {
                    if(j == 0) {
                        info[j] += message;
                        j++;
                        int i = info[0].indexOf("clientId");
                        if(i != -1){
                            i += 11;
                            while(info[0].charAt(i) != '"'){
                                clientId[0] += Character.toString(info[0].charAt(i));
                                i++;
                            }
                        }
                        System.out.println(clientId[0]);
                    }else{
                        info[j] = message;
                    }
                }
            });
            client.sendMessage("[\n" +
                    "   {\n" +
                    "      \"ext\":{\n" +
                    "         \"com.cumulocity.authn\":{\n" +
                    "            \"token\":\"dDMxOTMxNTEvbWFydGluLnMuZGluZXYuMjAxN0BlbHN5cy1iZy5vcmc6MjIxNzAzWHg=\"\n" +
                    "         }\n" +
                    "      },\n" +
                    "      \"id\":\"1\",\n" +
                    "      \"version\":\"1.0\",\n" +
                    "      \"minimumVersion\":\"1.0\",\n" +
                    "      \"channel\":\"/meta/handshake\",\n" +
                    "      \"supportedConnectionTypes\":[\n" +
                    "         \"websocket\",\n" +
                    "         \"long-polling\"\n" +
                    "      ],\n" +
                    "      \"advice\":{\n" +
                    "         \"timeout\":60000,\n" +
                    "         \"interval\":0\n" +
                    "      }\n" +
                    "   }\n" +
                    "]");
            client.sendMessage("[\n" +
                    "   {\n" +
                    "      \"id\":\"4\",\n" +
                    "      \"channel\":\"/meta/subscribe\",\n" +
                    "      \"subscription\":\"/measurements/*\",\n" +
                    "      \"clientId\":\"" + clientId[0] +"\"\n" +
                    "   }\n" +
                    "]");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        while(true) {
        }
    }

    @RequestMapping("hello")
    public String greeting (@RequestParam(value = "name", defaultValue = "World") String you) {
        return "Hello " + you + "!";
    }

}
