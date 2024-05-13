package com.safetynet.alerts.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.alerts.model.DataContainer;
import com.safetynet.alerts.model.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class PersonJsonDataEditor implements IJsonDataEditor<Person> {
    private static final Logger logger = LoggerFactory.getLogger(PersonJsonDataEditor.class);
    private static final String JSON_DATA_PATH = "./data/data.json";
    private static final ObjectMapper mapper = new ObjectMapper();
    private static final File jsonFile = new File(JSON_DATA_PATH);
    private DataContainer data;
    private List<Person> personList;


    public PersonJsonDataEditor() {
        try {
            if (jsonFile.exists()) {
                data = mapper.readValue(jsonFile, DataContainer.class);
                personList = data.getPersons();
            }
        } catch (IOException e) {
            logger.error("Failed to initialize person editor: " + e);
        }
    }

    @Override
    public boolean create(Person person) {
        if (personList != null) {
            try {
                //Check person doesn't exist already
                for (Person value : personList) {
                    if (value.getFirstName().equals(person.getFirstName())
                            && value.getLastName().equals(person.getLastName())) {

                        logger.debug("Failed to create new person. There's already a person with the same name");
                        return false;
                    }
                }
                personList.add(person);
                saveData();
                return true;
            } catch (Exception e) {
                logger.error("Failed to create new person: " + e);
            }
        }
        logger.debug("Failed to create new person. There's no list of persons");
        return false;
    }

    @Override
    public boolean update(Person person) {
        boolean isUpdated = false;
        if (personList != null) {
            try {
                for (int i = 0; i < personList.size(); i++) {
                    if (personList.get(i).getFirstName().equals(person.getFirstName())
                            && personList.get(i).getLastName().equals(person.getLastName())) {
                        personList.set(i, person);
                        isUpdated = true;
                        break;
                    }
                }
                if (isUpdated) {
                    saveData();
                } else {
                    logger.debug("Failed to update person. There's no person with the given name.");
                }
            } catch (Exception e) {
                logger.error("Failed to update person: " + e);
            }
        }
        logger.debug("Failed to update person. There's no list of persons.");
        return isUpdated;
    }

    @Override
    public boolean delete(Map<String, String> map) {
        boolean isDeleted = false;
        if (personList != null) {
            try {
                isDeleted = personList.removeIf(p -> p.getFirstName().equals(map.get("firstName"))
                        && p.getLastName().equals(map.get("lastName")));
                if (isDeleted) {
                    saveData();
                } else {
                    logger.debug("Failed to delete person. There's no person with the given name.");
                }
            } catch (IOException e) {
                logger.error("Failed to delete person: " + e);
            }
        }
        logger.debug("Failed to delete person. There's no list of persons.");
        return isDeleted;
    }

    private void saveData() throws IOException {
        if (personList != null) {
            mapper.writerWithDefaultPrettyPrinter().writeValue(jsonFile, data);
        }
    }
}
