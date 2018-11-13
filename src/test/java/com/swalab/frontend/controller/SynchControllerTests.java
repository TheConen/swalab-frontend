package com.swalab.frontend.controller;

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

    //Todo Warum wird "Server is online" nie ausgegeben?
    @Test
    public void testGetDataFromServer() {
        if (!synchController.isOffline()) {
            logger.debug("Server is online.");
            assertThat(synchController.getDataFromServer()).isTrue();
            Technician technician = synchController.getCurrentTechnician();
            assertThat(technician.getName()).isEqualTo(userName);
        }
    }


}
