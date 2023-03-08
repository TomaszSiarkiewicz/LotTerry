package pl.lotto.feature;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import pl.lotto.LottoApp;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = LottoApp.class)
@AutoConfigureMockMvc
public class WinnerPlayedAndWonLotteryIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    public void should_user_play_and_check_winning_result() throws Exception {
        // step 1: user made POST request to /inputNumbers and system returned 201 CREATED
        // given
        // when
        ResultActions perform = mockMvc.perform(get("/newlottery/1,2,3,4,5,6"));
        // then
        perform.andExpect(status().isOk());

        // step 2: system generated winning numbers for draw date
        // given
        // when
        // then

        // step 3: system player result
        // given
        // when
        // then

        // step 4: some days passed
        // given
        // when
        // then

        // step 5: user made GET /winner/id and system returned 200 OK
        // given
        // when
        // then
    }
}
