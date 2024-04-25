package com.safetynet.alerts.service;

import com.safetynet.alerts.dto.ChildInfo;
import com.safetynet.alerts.dto.PersonInfo;
import com.safetynet.alerts.repository.MedicalRecordRepository;
import com.safetynet.alerts.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static com.safetynet.alerts.util.AgeCalculator.calculateAge;


/**
 * Returns a list of Child info as dto, containing first name, last name, age and a list of family members
 */
@Service
@Primary
public class ChildAlertService implements IChildAlertService<ChildInfo> {

    @Autowired
    private MedicalRecordRepository recordRepository;
    @Autowired
    private PersonRepository personRepository;

    @Override
    public List<ChildInfo> findAllChildren(String address) {

        return personRepository.findByAddress(address).stream()
                .flatMap(Collection::stream)
                .flatMap(person -> recordRepository.findByName(person.getFirstName(), person.getLastName()).stream())
                .filter(record -> calculateAge(record.getBirthdate(), "MM/dd/yyyy") <= 18)
                .map(record -> new ChildInfo(
                                record.getFirstName(),
                                record.getLastName(),
                                calculateAge(record.getBirthdate(), "MM/dd/yyyy"),
                                findFamilyMembers(record.getLastName(), address, record.getFirstName())
                        )
                )
                .collect(Collectors.toList());
    }

    /**
     * Finds family members
     *
     * @param lastName  of the child
     * @param address   of the child
     * @param childName
     * @return list of person info containing firstname last name address and phone of the family member
     */
    public List<PersonInfo> findFamilyMembers(String lastName, String address, String childName) {

        return personRepository.findByAddress(address).stream()
                .flatMap(Collection::stream)
                .filter(person -> person.getAddress().equals(address)
                        && person.getLastName().equals(lastName)
                        && !person.getFirstName().equals(childName))
                .map(person -> new PersonInfo(
                                person.getFirstName(),
                                person.getLastName(),
                                person.getAddress(),
                                person.getPhone()
                        )
                )
                .collect(Collectors.toList());
    }
}
