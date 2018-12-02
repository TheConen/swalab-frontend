package com.swalab.frontend.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.swalab.frontend.model.*;
import org.springframework.core.ParameterizedTypeReference;
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
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * This controller manages synchronisation to the server and saving data for offline mode.
 */
public class SynchController {

    private Technician currentTechnician;
    private String baseUrl;
    private String username;
    private RestTemplate restTemplate;
    private List<Consumer<Technician>> _models;

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
        _models = new ArrayList<>(4);
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

        currentTechnician = restTemplate.getForObject(builder.toUriString(), Technician.class);
        notifyObservers();
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

        notifyObservers();
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

    /**
     * This method will update the current technician with either a technician loaded from a file (if offline) or from the server.
     * If none of those is successful, a empty technician will be created.
     *
     * @return The updated technician. Cannot be null.
     */
    public Technician updateTechnician() {
        boolean wasSuccessful = false;
        if (isOffline()) {
            wasSuccessful = loadDateFromFile();
        } else {
            wasSuccessful = getDataFromServer();
        }

        if(!wasSuccessful || currentTechnician == null) {
            String email = "";
            String name = "";
            String password = "";
            String phone = "";
            List<Appointment> appointments = new ArrayList<Appointment>();
            List<AbstractTaskAndNote> taskAndNotes = new ArrayList<AbstractTaskAndNote>();
            List<Customer> customers = new ArrayList<Customer>();
            List<WarehousePartAndOrder> parts = new ArrayList<WarehousePartAndOrder>();
            currentTechnician = new Technician(email, name, password, phone, appointments, taskAndNotes, customers, parts);
            notifyObservers();
        }
        return currentTechnician;
    }

    /**
     * offers the possibility for consumers to register them the the controller
     *
     * @param model which should be updated iff model changes by the server were triggered
     * @return true if the model could be added - otherwise false
     */
    public boolean registerModelForUpdate(Consumer<Technician> model) {
        return _models.add(model);
    }

    /**
     * offers the possibility for consumers to unregister them the the controller
     *
     * @param model which should be removed as an observer from the model updates
     * @return true if the model could be removed - otherwise false
     */
    public boolean removeModelForUpdate(Consumer<Technician> model) {
        return _models.remove(model);
    }

    private void notifyObservers() {
        _models.stream().forEach(c -> c.accept(getCurrentTechnician()));
    }

    /**
     * saves the data depending on the application state from a local file or the server
     * @node if the application works online, the data is online stored
     */
    public void persistData() {
        if(isOffline()){
            saveDataToFile();
        }else{
            sendDataToServer();
        }
    }

    /**
     * loads the data depending on the application state from a local file or the server
     * @node if the application works online, the data is online stored
     */
    public void loadData() {
        if(isOffline()){
            loadDateFromFile();
        }else{
            getDataFromServer();
        }
    }


    /**
     * Gets all available parts.
     *
     * @return All available parts.
     */
    public List<AvailablePart> getAvailableParts() {
        List<AvailablePart> availableParts = new ArrayList<AvailablePart>();
        if(isOffline()) {
            ObjectMapper mapper = new ObjectMapper();
            File file = new File("availableParts.json");
            if (file.isFile()) {
                try {
                    availableParts = mapper.readValue(file, new TypeReference<ArrayList<AvailablePart>>() {});
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            String url = baseUrl + "/availablepart/all";
            ResponseEntity<List<AvailablePart>> responseEntity = restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<AvailablePart>>() {});
            int responseCode = responseEntity.getStatusCodeValue();
            if (200 <= responseCode && responseCode <= 399) {
                availableParts = responseEntity.getBody();
                ObjectMapper mapper = new ObjectMapper();
                File file = new File("availableParts.json");
                try {
                    mapper.writeValue(file, availableParts);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return availableParts;
    }
}
