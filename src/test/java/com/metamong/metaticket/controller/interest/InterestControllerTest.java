package com.metamong.metaticket.controller.interest;

import com.metamong.metaticket.service.interest.InterestService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;


class InterestControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private InterestService interestService;

    @Test
    void selectInterests() throws Exception {
        mvc.perform(get("/interests"))
                .andExpect(status().isOk());
    }

    @Test
    void saveOrDeleteInterest() {
    }
}