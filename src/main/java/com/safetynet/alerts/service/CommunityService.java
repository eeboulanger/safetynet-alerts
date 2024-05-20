package com.safetynet.alerts.service;

import com.safetynet.alerts.dto.PersonInfoDTO;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.safetynet.alerts.util.AgeCalculator.calculateAge;

@Service
public class CommunityService implements ICommunityService {
    @Autowired
    private IPersonService personService;
    @Autowired
    private IMedicalRecordService medicalRecordService;

    /**
     * Finds all persons with the same first and last name.
     *
     * @param firstName used for search
     * @param lastName used for search
     * @return person info : name, address, age, mail, medications and allergies
     */
    public List<PersonInfoDTO> getAllPersonsByName(String firstName, String lastName) {
        List<PersonInfoDTO> personInfoDTO = new ArrayList<>();

        List<Person> personList = personService.findByName(firstName, lastName).orElse(Collections.emptyList());

        for (Person person : personList) {
            Optional<MedicalRecord> optionalMedicalRecord = medicalRecordService.findByName(firstName, lastName);

            if (optionalMedicalRecord.isPresent()) {
                MedicalRecord record = optionalMedicalRecord.get();

                PersonInfoDTO dto = new PersonInfoDTO(
                        firstName,
                        lastName,
                        person.getEmail(),
                        calculateAge(record.getBirthdate(), "DD/mm/yyyy"),
                        record.getMedications(),
                        record.getAllergies()
                );
                personInfoDTO.add(dto);
            }
        }
        return personInfoDTO;
    }
}
