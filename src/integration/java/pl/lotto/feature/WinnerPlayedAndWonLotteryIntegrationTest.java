package pl.lotto.feature;

import com.fasterxml.jackson.databind.ObjectMapper;
import config.IntegrationTestConfiguration;
import org.awaitility.Awaitility;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
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
import pl.lotto.numbergenerator.NumberGeneratorFacade;
import pl.lotto.numbergenerator.WinningNumbersNotFoundException;
import pl.lotto.numberreceiver.dto.InputNumbersResponseDto;
import pl.lotto.resultchecker.ResultCheckerFacade;

import java.time.Duration;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = {LottoApp.class, IntegrationTestConfiguration.class})
@AutoConfigureMockMvc
@Testcontainers
@ActiveProfiles("integration")
public class WinnerPlayedAndWonLotteryIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    public ObjectMapper objectMapper;

    @Autowired
    NumberGeneratorFacade numberGeneratorFacade;

    @Autowired
    ResultCheckerFacade resultCheckerFacade;

    @Autowired
    AdjustableClock clock;

    @Container
    public static final MongoDBContainer mongoDBContainer = new MongoDBContainer(DockerImageName.parse("mongo:4.0.10"));

    @DynamicPropertySource
    public static void propertyOverride(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
    }

    @Test
    public void should_user_play_and_check_winning_result() throws Exception {
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


        // step 2: system generated winning numbers for draw date
        // given
        // when
        Awaitility.await()
                .pollInterval(Duration.ofSeconds(1))
                .atMost(Duration.ofSeconds(10)).until(() -> {
                    try {
                        return !numberGeneratorFacade.retrieveNumbersByDate(drawDate).numbers().isEmpty();
                    } catch (WinningNumbersNotFoundException e) {
                        return false;
                    }
                });
        // then


        // step 3: user made GET /winner/id and system returned 404 with body too early
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


        // step 4: draw dateTime arrived
        // given
        // when
        clock.plusDays(2);
        // then


        // step 5: system player result
        // given
        // when
        Awaitility.await()
                .pollInterval(Duration.ofSeconds(1))
                .atMost(Duration.ofSeconds(10)).until(() -> !resultCheckerFacade.getWinnersByDate(drawDate).isEmpty());
        // then

        // step 6: some days passed
        // given
        // when
        clock.plusDays(2);
        // then

        // step 7: user made GET /winner/id and system returned 200 OK
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
