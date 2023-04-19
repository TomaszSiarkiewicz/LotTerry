package pl.lotto.feature;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import config.IntegrationTestConfiguration;
import org.awaitility.Awaitility;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;
import pl.lotto.AdjustableClock;
import pl.lotto.LottoApp;
import pl.lotto.infrastructre.controller.resultannouncer.AnnouncerResponseDto;
import pl.lotto.infrastructre.numbergeneratorclient.NumberGeneratorClientImpl;
import pl.lotto.numberreceiver.dto.InputNumbersResponseDto;
import pl.lotto.resultchecker.ResultCheckerFacade;

import java.time.Duration;
import java.time.LocalDateTime;

import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = {LottoApp.class, IntegrationTestConfiguration.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Testcontainers
@ActiveProfiles("integration")
public class WinnerPlayedAndWonLotteryIntegrationTest {

    @RegisterExtension
    public static WireMockExtension wireMockServer = WireMockExtension.newInstance()
            .options(wireMockConfig().dynamicPort())
            .build();

    @Autowired
    MockMvc mockMvc;

    @Autowired
    public ObjectMapper objectMapper;

    @Autowired
    NumberGeneratorClientImpl numberGeneratorClient;

    @Autowired
    ResultCheckerFacade resultCheckerFacade;

    @Autowired
    AdjustableClock clock;

    @Container
    public static final MongoDBContainer mongoDBContainer = new MongoDBContainer(DockerImageName.parse("mongo:4.0.10"));

    public static final String WIRE_MOCK_HOST = "http://localhost";

    @DynamicPropertySource
    public static void propertyOverride(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
        registry.add("lotto.lotto-number-generator.baseURL", () -> WIRE_MOCK_HOST + ":" + wireMockServer.getPort());
    }

    @Test
    public void should_user_play_and_check_winning_result() throws Exception {
        // step 0: generating number service will response
        //
        wireMockServer.stubFor(WireMock.get(urlEqualTo("/winnum/2023-04-08T12%3A00%3A00"))
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", "application/json")
                        .withBody("""
                                {"numbers":[1,2,3,4,5,6],"date":"2023-04-08T12:00:00"}
                                """
                                .trim())));


        // step 1: user made POST request to /lottery and system returned 201 CREATED
        // given
        // when
        ResultActions inputNumbersResponse = mockMvc.perform(post("/lottery").contentType(MediaType.APPLICATION_JSON).content("""
                {
                "numbers": [1,2,3,4,5,6]
                }
                """));
        // then
        inputNumbersResponse.andExpect(status().isCreated());
        String json = inputNumbersResponse.andReturn().getResponse().getContentAsString();
        InputNumbersResponseDto inputNumbersResponseDto = objectMapper.readValue(json, InputNumbersResponseDto.class);
        LocalDateTime drawDate = inputNumbersResponseDto.ticketDto().drawDate();
        String lotteryId = inputNumbersResponseDto.ticketDto().lotteryId();
        assertAll(() -> assertThat(drawDate).isNotNull(), () -> assertThat(lotteryId).isNotNull(), () -> assertThat(inputNumbersResponseDto.message()).isEqualTo("Success!"));


        // step 2: user made GET /winner/id and system returned 404 with body too early
        // given
        // when
        ResultActions earlyResponse = mockMvc.perform(get("/result/" + lotteryId));

        // then
        earlyResponse.andExpect(status().isNotFound());
        String jsonEarlyResponse = earlyResponse
                .andReturn()
                .getResponse()
                .getContentAsString();
        AnnouncerResponseDto responseDto = objectMapper
                .readValue(jsonEarlyResponse, AnnouncerResponseDto.class);

        assertThat(responseDto.message()).isEqualTo("Checking to soon, no drawing for your ticket");


        // step 3: draw dateTime arrived
        // given
        // when
        clock.plusDays(2);
        // then


        // step 4: system player result
        // given
        // when
        Awaitility.await()
                .pollInterval(Duration.ofSeconds(1))
                .atMost(Duration.ofSeconds(10)).until(() -> !resultCheckerFacade.getWinnersByDate(drawDate).isEmpty());
        // then

        // step 5: some days passed
        // given
        // when
        clock.plusDays(2);
        // then

        // step 6: user made GET /winner/id and system returned 200 OK
        // given
        // when
        ResultActions goodResponse = mockMvc.perform(get("/result/" + lotteryId));

        // then
        goodResponse.andExpect(status().isOk());
        String jsonGoodResponse = goodResponse
                .andReturn()
                .getResponse()
                .getContentAsString();
        AnnouncerResponseDto goodResponseDto = objectMapper
                .readValue(jsonGoodResponse, AnnouncerResponseDto.class);
        assertThat(goodResponseDto.message()).isEqualTo("Your result");

    }
}
