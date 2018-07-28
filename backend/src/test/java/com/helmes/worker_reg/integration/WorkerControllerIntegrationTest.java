package com.helmes.worker_reg.integration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.helmes.worker_reg.entities.Sector;
import com.helmes.worker_reg.entities.Worker;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class WorkerControllerIntegrationTest {


    @Autowired
    private MockMvc mvc;

    @LocalServerPort
    private int port;

    @Test
    public void checkWorkerByIdWithCorrectFirstNameFirstWorkerPreLoaded() throws Exception {
        mvc.perform(get("/workers/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.firstName", is("Hilgert")));
    }
    @Test
    public void checkWorkerByIdWithCorrectLastNameLastWorkerPreLoaded() throws Exception {
        mvc.perform(get("/workers/7")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.lastName", is("Shadows")));
    }
    @Test
    public void checkWhetherInitialWorkersPreLoaded() throws Exception {
        mvc.perform(get("/workers/")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(5))));
    }
    @Test
    public void addWorkerToPreLoadedListCheckName() throws Exception {
        Worker worker = new Worker();
        Sector sector = new Sector();
        worker.setId((long)777);
        worker.setFirstName("Jaina");
        worker.setLastName("Proudmoore");
        worker.setAgreedToTerms(true);
        sector.setSectorName("Manufacturing");
        sector.setId((long)1);
        worker.setSector(sector);

        mvc.perform(post("/workers")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Access-Control-Request-Method", "POST")
                .header("Origin", "http://localhost:8088")
                .content(asJsonString(asJsonString(worker))))
                .andExpect(status().isCreated());
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
