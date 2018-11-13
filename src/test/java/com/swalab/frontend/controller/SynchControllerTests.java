package com.swalab.frontend.controller;

import com.swalab.frontend.model.Technician;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Java6Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SynchControllerTests {

    private String serverUrl = "localhost:8080";
    private String userName = "noJs";
    private SynchController synchController = new SynchController(serverUrl, userName);

    //Todo Warum wird "Server is online" wird nie ausgegeben?
    @Test
    public void testGetDataFromServer() {
        if (!synchController.isOffline()) {
            System.out.println("Server is online.");
            assertThat(synchController.getDataFromServer()).isTrue();
        }
    }


}
