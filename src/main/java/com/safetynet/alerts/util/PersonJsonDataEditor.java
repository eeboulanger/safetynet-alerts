package com.safetynet.alerts.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.alerts.model.DataContainer;
import com.safetynet.alerts.model.Person;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class PersonJsonDataEditor implements IJsonDataEditor<Person> {
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
            e.printStackTrace();
        }
    }

    @Override
    public boolean create(Person person) {
        if (personList != null) {
            try {
                //Check person doesn't exist already
                for (int i = 0; i < personList.size(); i++) {
                    if (personList.get(i).getFirstName().equals(person.getFirstName())
                            && personList.get(i).getLastName().equals(person.getLastName())) {
                        personList.set(i, person);

                        System.out.println("There's already a person with the same name");
                        return false;
                    }
                }
                personList.add(person);
                saveData();
                return true;

            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        } else {
            return false;
        }
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
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
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
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return isDeleted;
    }

    private void saveData() throws IOException {
        if (personList != null) {
            mapper.writerWithDefaultPrettyPrinter().writeValue(jsonFile, data);
        }
    }
}
