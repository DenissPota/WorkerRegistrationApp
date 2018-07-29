package com.helmes.worker_reg.integration;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.helmes.worker_reg.entities.Sector;
import com.helmes.worker_reg.entities.Worker;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@FixMethodOrder()
public class WorkerControllerIntegrationTest {


    @Autowired
    private MockMvc mvc;

    @LocalServerPort
    private int port;

    private Worker worker = new Worker();
    private Sector sector = new Sector();

    @Before
    public void setup() {
        worker.setId((long) 777);
        worker.setFirstName("Jaina");
        worker.setLastName("Proudmoore");
        worker.setAgreedToTerms(true);
        sector.setSectorName("Manufacturing");
        sector.setId((long) 1);
        worker.setSector(sector);
    }

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
    public void checkWhetherAllInitialWorkersPreLoaded() throws Exception {
        mvc.perform(get("/workers/")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(7))));
    }

    @Test
    public void addWorkerToPreLoadedListOkStatus() throws Exception {
        mvc.perform(post("/workers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(worker)))
                .andExpect(status().isOk());

        MvcResult result = mvc.perform(get("/workers")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();

        String content = result.getResponse().getContentAsString();

        Assert.assertEquals(true, content.contains("Jaina"));
    }

    @Test
    public void checkWorkerNotFoundException() throws Exception {
        mvc.perform(get("/workers/99"))
                .andExpect(status().isNotFound())
                .andExpect(status().reason(containsString("no worker with such id")));
    }

    @Test
    public void checkChangeWorkerSuccess() throws Exception {
        mvc.perform(put("/workers/5")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(worker)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", is("Jaina")));
    }

    @Test
    public void checkDeleteWorkerSuccess() throws Exception {
        mvc.perform(delete("/workers/1"))
                .andExpect(status().isOk());

        mvc.perform(get("/workers/1"))
                .andExpect(status().isNotFound())
                .andExpect(status().reason(containsString("no worker with such id")));

    }

    @Test
    public void checkAllInitialSectorsPreLoaded() throws Exception {
        mvc.perform(get("/sector"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(79))));
    }

    @Test
    public void checkSectorsBaseDepth() throws Exception {
        MvcResult result = mvc.perform(get("/sector/1"))
                .andDo(print())
                .andReturn();


        String content = result.getResponse().getContentAsString();
        String expected = "[[\"Manufacturing\",1,1],[\"Other\",3,1],[\"Service\",2,1]]";

        Assert.assertEquals(expected, content);
    }

    @Test
    public void checkSectorsDepth2WithManufacturingParentDoesNotGoDeeper() throws Exception {
        MvcResult result = mvc.perform(get("/sector/2/1"))
                .andDo(print())
                .andReturn();

        String content = result.getResponse().getContentAsString();

        Assert.assertEquals(true, content.contains("Construction materials"));
        Assert.assertEquals(true, content.contains("Food and Beverage"));
        Assert.assertEquals(false, content.contains("Beverages"));
    }

    @Test
    public void checkSectorsDepth3WithServiceTransportAndLogisticsParent() throws Exception {
        MvcResult result = mvc.perform(get("/sector/3/21"))
                .andDo(print())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        String expected = "[[\"Air\",111,21,3],[\"Rail\",114,21,3],[\"Road\",112,21,3],[\"Water\",113,21,3]]";

        Assert.assertEquals(expected, content);
    }

    @Test
    public void checkSectorsDepth4WithManufacturingMetalWorkingMetalWorksParent() throws Exception {
        MvcResult result = mvc.perform(get("/sector/4/542"))
                .andDo(print())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        String expected = "[[\"CNC-machining\",75,542,4],[\"Forgings, Fasteners\",62,542,4],[\"Gas, Plasma, Laser cutting\",69,542,4],[\"MIG, TIG, Aluminum welding\",66,542,4]]";

        Assert.assertEquals(expected, content);
    }


    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
