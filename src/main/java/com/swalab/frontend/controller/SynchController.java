package com.swalab.frontend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.swalab.frontend.model.Technician;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * This controller manages synchronisation to the server and saving data for offline mode.
 */
public class SynchController {

    private Technician currentTechnician;
    private String baseUrl;
    private String username;
    private RestTemplate restTemplate;

    /**
     * Instantiates a new Synch controller.
     *
     * @param baseUrl  the base url of the server to use
     * @param username the username of the user that is using this Synch controller
     */
    public SynchController(String baseUrl, String username) {
        this.baseUrl = baseUrl;
        this.username = username;
        restTemplate = new RestTemplate();
        restTemplate.setUriTemplateHandler(new DefaultUriBuilderFactory(baseUrl));
    }

    /**
     * Checks whether the application can currently reach the server.
     *
     * @return true if the server is currently unavailable (offline mode), false if the server is available
     */
    public boolean isOffline() {
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(baseUrl + "/hello").openConnection();
            int responseCode = connection.getResponseCode();
            return !(200 <= responseCode && responseCode <= 399);
        } catch (IOException exception) {
            return true; //Server is offline
        }
    }

    /**
     * Gets the technician object for the current user from the server and sets it as the current technician.
     *
     * @return true if the operation was successful and the received technician object is not null, false otherwise
     */
    public boolean getDataFromServer() {
        if (isOffline()) {
            return false;
        }

        String url = baseUrl + "/technician";
        UriComponentsBuilder builder = UriComponentsBuilder
                .fromHttpUrl(url)
                .queryParam("technician", username);

        currentTechnician = restTemplate.getForObject(builder.toUriString(), Technician.class); //TODO something is going very wrong here
        return (currentTechnician != null);
    }

    /**
     * Sends the current technician to the server.
     * Does NOT refresh the current technician with the technician that was updated on the server.
     *
     * @return true if the server responded with 200 OK or 201 CREATED, false otherwise
     */
    public boolean sendDataToServer() {
        if (isOffline()) {
            return false;
        }

        String url = baseUrl + "/technician";
        UriComponentsBuilder builder = UriComponentsBuilder
                .fromHttpUrl(url)
                .queryParam("newTechnician", currentTechnician);
        HttpEntity<Technician> requestEntity = new HttpEntity<>(currentTechnician, new HttpHeaders());
        ResponseEntity responseEntity = restTemplate.exchange(builder.toUriString(), HttpMethod.PUT, requestEntity, Void.class);
        int responseStatusCode = responseEntity.getStatusCodeValue();

        return (responseStatusCode == 200 || responseStatusCode == 201);
    }

    /**
     * Saves the current technician to a file.
     *
     * @return true if successful, false otherwise
     */
    public boolean saveDataToFile() {
        boolean result = false;
        ObjectMapper mapper = new ObjectMapper();
        File file = new File(username + ".json");
        try {
            mapper.writeValue(file, currentTechnician);
            result = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Loads the saved technician for the current user from a file and sets it as the current technician.
     *
     * @return true if successful, false otherwise
     */
    public boolean loadDateFromFile() {
        boolean result = false;
        ObjectMapper mapper = new ObjectMapper();
        File file = new File(username + ".json");
        if (file.isFile()) {
            try {
                currentTechnician = mapper.readValue(file, Technician.class);
                result = true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * Gets the current technician.
     *
     * @return the current technician, can be null
     */
    public Technician getCurrentTechnician() {
        return currentTechnician;
    }
}
