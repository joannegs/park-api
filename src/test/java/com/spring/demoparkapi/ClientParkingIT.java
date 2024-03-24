package com.spring.demoparkapi;

import com.spring.demoparkapi.web.dto.ClientParkingCreateDto;
import com.spring.demoparkapi.web.dto.PageableDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/sql/client-parking-insert.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/sql/client-parking-delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class ClientParkingIT {

    @Autowired
    WebTestClient testClient;

    @Test
    public void createCheckin_WithValidData_ReturnCreatedAndLocation() {
        ClientParkingCreateDto createDto = ClientParkingCreateDto.builder()
                .vehicleRegistration("WER-1111").vehicleBrand("FIAT").vehicleModel("PALIO 1.0")
                .vehicleColor("AZUL").clientCpf("09191773016")
                .build();

        testClient.post().uri("/api/v1/client-parking/check-in")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "ana@gmail.com", "123456"))
                .bodyValue(createDto)
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().exists(HttpHeaders.LOCATION)
                .expectBody()
                .jsonPath("vehicleRegistration").isEqualTo("WER-1111")
                .jsonPath("vehicleBrand").isEqualTo("FIAT")
                .jsonPath("vehicleModel").isEqualTo("PALIO 1.0")
                .jsonPath("vehicleColor").isEqualTo("AZUL")
                .jsonPath("clientCpf").isEqualTo("09191773016")
                .jsonPath("receipt").exists()
                .jsonPath("checkinDate").exists()
                .jsonPath("parkingSpotCode").exists();
    }

    @Test
    public void createCheckin_ComRoleClient_ReturnErrorStatus403() {
        ClientParkingCreateDto createDto = ClientParkingCreateDto.builder()
                .vehicleRegistration("WER-1111").vehicleBrand("FIAT").vehicleModel("PALIO 1.0")
                .vehicleColor("AZUL").clientCpf("09191773016")
                .build();

        testClient.post().uri("/api/v1/client-parking/check-in")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "bia@gmail.com", "123456"))
                .bodyValue(createDto)
                .exchange()
                .expectStatus().isForbidden();
    }

    @Test
    public void createCheckin_WithInvalidData_ReturnErrorStatus422() {
        ClientParkingCreateDto createDto = ClientParkingCreateDto.builder()
                .vehicleRegistration("").vehicleBrand("").vehicleModel("")
                .vehicleColor("").clientCpf("")
                .build();

        testClient.post().uri("/api/v1/client-parking/check-in")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "bia@gmail.com", "123456"))
                .bodyValue(createDto)
                .exchange()
                .expectStatus().isEqualTo(422);
    }

    @Test
    public void createCheckin_WithInvalidCpf_ReturnErrorStatus404() {
        ClientParkingCreateDto createDto = ClientParkingCreateDto.builder()
                .vehicleRegistration("WER-1111").vehicleBrand("FIAT").vehicleModel("PALIO 1.0")
                .vehicleColor("AZUL").clientCpf("33838667000")
                .build();

        testClient.post().uri("/api/v1/client-parking/check-in")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "ana@gmail.com", "123456"))
                .bodyValue(createDto)
                .exchange()
                .expectStatus().isNotFound();
    }

    @Sql(scripts = "/sql/client-parking-taken-insert.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/sql/client-parking-taken-delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    public void createCheckin_WithTakenParkingSpots_ReturnErrorStatus404() {
        ClientParkingCreateDto createDto = ClientParkingCreateDto.builder()
                .vehicleRegistration("WER-1111").vehicleBrand("FIAT").vehicleModel("PALIO 1.0")
                .vehicleColor("AZUL").clientCpf("33838667000")
                .build();

        testClient.post().uri("/api/v1/client-parking/check-in")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "ana@gmail.com", "123456"))
                .bodyValue(createDto)
                .exchange()
                .expectStatus().isNotFound()
                .expectBody()
                .jsonPath("status").isEqualTo("404")
                .jsonPath("path").isEqualTo("/api/v1/client-parking/check-in")
                .jsonPath("method").isEqualTo("POST");
    }

    @Test
    public void searchCheckin_WithAdmin_ReturnDataStatus200() {
        testClient.get()
                .uri("/api/v1/client-parking/check-in/{receipt}", "20230313")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "ana@gmail.com", "123456"))
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("vehicleRegistration").isEqualTo("FIT-1020")
                .jsonPath("vehicleBrand").isEqualTo("FIAT")
                .jsonPath("vehicleModel").isEqualTo("PALIO")
                .jsonPath("vehicleColor").isEqualTo("VERDE")
                .jsonPath("clientCpf").isEqualTo("98401203015")
                .jsonPath("receipt").isEqualTo("20230313")
                .jsonPath("checkinDate").isEqualTo("2023-03-13 10:15:00")
                .jsonPath("parkingSpotCode").isEqualTo("A-01");
    }

    @Test
    public void searchCheckin_WithClient_ReturnDataStatus200() {
        testClient.get()
                .uri("/api/v1/client-parking/check-in/{receipt}", "20230313")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "bob@gmail.com", "123456"))
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("vehicleRegistration").isEqualTo("FIT-1020")
                .jsonPath("vehicleBrand").isEqualTo("FIAT")
                .jsonPath("vehicleModel").isEqualTo("PALIO")
                .jsonPath("vehicleColor").isEqualTo("VERDE")
                .jsonPath("clientCpf").isEqualTo("98401203015")
                .jsonPath("receipt").isEqualTo("20230313")
                .jsonPath("checkinDate").isEqualTo("2023-03-13 10:15:00")
                .jsonPath("parkingSpotCode").isEqualTo("A-01");
    }

    @Test
    public void searchCheckin_WithInvalidReceipt_ReturnErrorStatus404() {
        testClient.get()
                .uri("/api/v1/client-parking/check-in/{receipt}", "202303135")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "bob@gmail.com", "123456"))
                .exchange()
                .expectStatus().isNotFound()
                .expectBody()
                .jsonPath("status").isEqualTo("404")
                .jsonPath("path").isEqualTo("/api/v1/client-parking/check-in/202303135")
                .jsonPath("method").isEqualTo("GET");
    }

    @Test
    public void searchCheckin_WithValidReceipt_ReturnSuccess() {
        testClient.put()
                .uri("/api/v1/client-parking/check-out/{receipt}", "20230313")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "ana@gmail.com", "123456"))
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("vehicleRegistration").isEqualTo("FIT-1020")
                .jsonPath("vehicleBrand").isEqualTo("FIAT")
                .jsonPath("vehicleModel").isEqualTo("PALIO")
                .jsonPath("vehicleColor").isEqualTo("VERDE")
                .jsonPath("clientCpf").isEqualTo("98401203015")
                .jsonPath("receipt").isEqualTo("20230313")
                .jsonPath("checkinDate").isEqualTo("2023-03-13 10:15:00")
                .jsonPath("parkingSpotCode").isEqualTo("A-01")
                .jsonPath("checkoutDate").exists()
                .jsonPath("price").exists()
                .jsonPath("discount").exists();
    }

    @Test
    public void createCheckOut_WithInvalidReceipt_ReturnErrorStatus404() {
        testClient.put()
                .uri("/api/v1/client-parking/check-out/{receipt}", "20230313-000000")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "ana@gmail.com", "123456"))
                .exchange()
                .expectStatus().isNotFound()
                .expectBody()
                .jsonPath("status").isEqualTo("404")
                .jsonPath("path").isEqualTo("/api/v1/client-parking/check-out/20230313-000000")
                .jsonPath("method").isEqualTo("PUT");
    }

    @Test
    public void createCheckOut_WithClientRole_ReturnErrorStatus403() {
        testClient.put()
                .uri("/api/v1/client-parking/check-out/{receipt}", "20230313-101300")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "bia@gmail.com", "123456"))
                .exchange()
                .expectStatus().isForbidden();
    }

    @Test
    public void searchParkingClient_byClientCpf_ReturnSuccess() {
        PageableDto responseBody = testClient.get()
                .uri("/api/v1/client-parking/cpf/{cpf}?size=1&page=0", "98401203015")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "ana@gmail.com", "123456"))
                .exchange()
                .expectStatus().isOk()
                .expectBody(PageableDto.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getContent().size()).isEqualTo(1);
        org.assertj.core.api.Assertions.assertThat(responseBody.getTotalPages()).isEqualTo(2);
        org.assertj.core.api.Assertions.assertThat(responseBody.getNumber()).isEqualTo(0);
        org.assertj.core.api.Assertions.assertThat(responseBody.getSize()).isEqualTo(1);

        responseBody = testClient.get()
                .uri("/api/v1/client-parking/cpf/{cpf}?size=1&page=1", "98401203015")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "ana@gmail.com", "123456"))
                .exchange()
                .expectStatus().isOk()
                .expectBody(PageableDto.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getContent().size()).isEqualTo(1);
        org.assertj.core.api.Assertions.assertThat(responseBody.getTotalPages()).isEqualTo(2);
        org.assertj.core.api.Assertions.assertThat(responseBody.getNumber()).isEqualTo(1);
        org.assertj.core.api.Assertions.assertThat(responseBody.getSize()).isEqualTo(1);
    }

    @Test
    public void searchParkingClient_byClientCpfWithClientRole_ReturnErrorStatus403() {
        testClient.get()
                .uri("/api/v1/client-parking/cpf/{cpf}", "98401203015")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "bia@gmail.com", "123456"))
                .exchange()
                .expectStatus().isForbidden();
    }

    @Test
    public void searchParkingClient_ofLoggedUserAdminRole_ReturnError403() {

        testClient.get()
                .uri("/api/v1/client-parking")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "ana@gmail.com", "123456"))
                .exchange()
                .expectStatus().isForbidden();
    }
}