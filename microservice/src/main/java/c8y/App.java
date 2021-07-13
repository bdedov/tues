package c8y;

import com.cumulocity.microservice.autoconfigure.MicroserviceApplication;
import com.cumulocity.microservice.settings.service.MicroserviceSettingsService;
import com.cumulocity.model.authentication.CumulocityBasicCredentials;
import com.cumulocity.model.idtype.GId;
import com.cumulocity.rest.representation.event.EventRepresentation;
import com.cumulocity.rest.representation.inventory.ManagedObjectRepresentation;
import com.cumulocity.sdk.client.Platform;
import com.cumulocity.sdk.client.PlatformImpl;
import org.joda.time.DateTime;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.URISyntaxException;

@MicroserviceApplication
@RestController
public class App {
    public static int j = 0;

    public static void check_collision(Platform platform, double accelerationX, double accelerationY, double accelerationZ){
        boolean collision = false;

        if(accelerationX > 1.5 || accelerationY > 1.5 || accelerationZ > 1.5) {
            collision = true;   
        }
        
        if(collision){
            var event = new EventRepresentation();
            ManagedObjectRepresentation source = new ManagedObjectRepresentation();
            source.setId(GId.asGId(4719658));
            event.setSource(source);
            event.setType("CrashEvent");
            event.setText("There is a crash!");
            event.setDateTime(DateTime.now());
            platform.getEventApi().create(event);
        }
    }

    public static void main (String[] args) {
        //SpringApplication.run(App.class, args);
        Platform platform = new PlatformImpl("https://bdedov.1.stage.c8y.io/", CumulocityBasicCredentials.from("t3193151/denislav.i.ivanov.2017@elsys-bg.org:75t5wwneqeNK6Qp"));
        String[] info = new String[3];
        for(int i=0; i<3; i++){
            info[i] = "";
        }
        String[] clientId = {""};
        try {
            WebSocketClientEndpoint client = new WebSocketClientEndpoint(new URI("wss://bdedov.1.stage.c8y.io/notification/realtime"));
            client.addMessageHandler(new WebSocketClientEndpoint.MessageHandler() {
                @Override
                public void handleMessage(String message) {
                    System.out.println("msg: " + message);

                    if(j == 0) {
                        System.out.println("client: " + message);
                        info[j] = message;
                        j++;
                        int i = info[0].indexOf("clientId");
                        if(i != -1){
                            i += 11;
                            while(info[0].charAt(i) != '"'){
                                clientId[0] += Character.toString(info[0].charAt(i));
                                i++;
                            }
                        }
                    }else if(j == 1){
                        String check = "";
                        info[j] = message;
                        int i = info[j].indexOf("successful");
                        if(i != -1){
                            i += 12;
                            while(info[j].charAt(i) != 'e'){
                                check += Character.toString(info[j].charAt(i));
                                i++;
                            }
                            check += Character.toString(info[j].charAt(i));
                        }
                        if(check.equals("false")){
                            return;
                        }
                        j++;
                    }else{
                        info[j] = message;
                        message = message.substring(1, message.length() - 1);
                        double accelerationX = 0;
                        double accelerationY = 0;
                        double accelerationZ = 0;
                        JSONObject json = new JSONObject(message);
                        //System.out.println(message);
                        if(json.getJSONObject("data").getJSONObject("data").has("c8y_Acceleration")){
                            accelerationX = json.getJSONObject("data").getJSONObject("data").getJSONObject("c8y_Acceleration").getJSONObject("accelerationX").getDouble("value");
                            accelerationY = json.getJSONObject("data").getJSONObject("data").getJSONObject("c8y_Acceleration").getJSONObject("accelerationY").getDouble("value");
                            accelerationZ = json.getJSONObject("data").getJSONObject("data").getJSONObject("c8y_Acceleration").getJSONObject("accelerationZ").getDouble("value");
                            check_collision(platform, accelerationX, accelerationY, accelerationZ);
                        }

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
            Thread.sleep(3000);
            client.sendMessage("[\n" +
                    "   {\n" +
                    "      \"id\":\"4\",\n" +
                    "      \"channel\":\"/meta/subscribe\",\n" +
                    "      \"subscription\":\"/measurements/*\",\n" +
                    "      \"clientId\":\"" + clientId[0] +"\"\n" +
                    "   }\n" +
                    "]");
            while(true);
        } catch (URISyntaxException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("hello")
    public String greeting (@RequestParam(value = "name", defaultValue = "World") String you) {
        return "Hello " + you + "!";
    }

}
