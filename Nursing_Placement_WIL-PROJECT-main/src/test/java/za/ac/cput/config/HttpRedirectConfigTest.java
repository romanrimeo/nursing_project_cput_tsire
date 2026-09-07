package za.ac.cput.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class HttpRedirectConfigIT {

    @LocalServerPort private int httpsPort;
    @Autowired private TestRestTemplate rest;   // follows redirects by default

    @Test
    void httpRequestsAreRedirectedToHttps() {
        // hit HTTP port (8080) – must redirect to httpsPort
        ResponseEntity<String> resp = rest.getForEntity(
                "http://localhost:8080/ping", String.class);

        assertThat(resp.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(resp.getBody()).isEqualTo("pong");

    }
}