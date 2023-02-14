import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Client {
    public static void main(String[] args) {
        final String sensorName = "Client sensor1";
        registerSensor(sensorName);

        Random random = new Random();

        double minTemperature = -10.0;
        double maxTemperature = 55.0;

        for (int i = 0; i < 500 ; i++) {
            System.out.println(i);
            sendIndication((random.nextDouble(maxTemperature-minTemperature)+minTemperature),
                    random.nextBoolean(), sensorName);
        }

    }

    public static void sendIndication(double value, boolean raining, String sensorName) {
        final String url = "http://localhost:8080/indications/add";

        Map<String, Object> jsonData = new HashMap<>();
        jsonData.put("value", value);
        jsonData.put("raining", raining);
        jsonData.put("sensor", Map.of("name", sensorName));

        makePostRequestWithJsonData(url, jsonData);
    }

    public static void registerSensor(String name) {
        final String url = "http://localhost:8080/sensors/registartion";

        Map<String, Object> jsonData = new HashMap<>();
        jsonData.put("name", name);

        makePostRequestWithJsonData(url, jsonData);
    }

    private static void makePostRequestWithJsonData(String url, Map<String, Object> jsonData) {
        final RestTemplate restTemplate = new RestTemplate();

        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Object> request =new HttpEntity<>(jsonData, headers);

        try {
            restTemplate.postForObject(url, request, String.class);
            System.out.println("Измерение успешно отправлено!");
        } catch (HttpClientErrorException e) {
            System.out.println("Ошибка!");
            System.out.println(e.getMessage());
        }
    }
}
