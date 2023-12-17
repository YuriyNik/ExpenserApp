package restapp.controller;
import model.Expence;
import model.ExpenceSummary;
import model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.shadow.com.univocity.parsers.annotations.Headers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ExpenceRestControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private HttpHeaders headers = new HttpHeaders();


    @Test
    public void testPostExpence() {
        Expence expence = new Expence();
        expence.setAmount(150.0);
        expence.setType("food");
        // Заполните объект Expence данными для теста

        headers.setBasicAuth("test", "test");
        HttpEntity<Expence> requestEntity = new HttpEntity<>(expence, headers);
        Expence responseExpence = restTemplate.postForObject("/expence",requestEntity , Expence.class);

        assertNotNull(responseExpence);
        // Добавьте дополнительные проверки полей, если необходимо
    }

    @Test
    public void testListAllExpence() {
        List<Expence> expences = restTemplate.withBasicAuth("test","test").getForObject("/expenceForThisMonth/12/2023", List.class);

        assertNotNull(expences);
        // Добавьте дополнительные проверки, если необходимо
    }

    // Добавьте другие тесты для других методов контроллера

    @Test
    public void testGetReports() {
        int month = 12;
        int year = 2023;
        String type = "food";

        List<ExpenceSummary> expenceSummaries = restTemplate.withBasicAuth("test","test").getForObject(
                String.format("/reports/%d/%d/%s", month, year, type), List.class);

        assertNotNull(expenceSummaries);
    }
}
