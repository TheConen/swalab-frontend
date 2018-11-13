package com.swalab.frontend.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.swalab.frontend.model.Technician;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Java6Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SynchControllerTests {

    private String serverUrl = "http://localhost:80";
    private String userName = "noJs";
    private SynchController synchController = new SynchController(serverUrl, userName);

    private Logger logger = LoggerFactory.getLogger(SynchControllerTests.class);
    private ObjectMapper mapper = new ObjectMapper();

    @Test
    public void testGetDataFromServer() throws JsonProcessingException {
        if (!synchController.isOffline()) {
            logger.info("Server is online.");
            assertThat(synchController.getDataFromServer()).isTrue();
            Technician technician = synchController.getCurrentTechnician();
            assertThat(technician.getName()).isEqualTo(userName);
            logger.info(mapper.writeValueAsString(technician));
        }
    }

}
