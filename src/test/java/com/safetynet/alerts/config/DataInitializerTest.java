package com.safetynet.alerts.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.alerts.model.DataContainer;
import com.safetynet.alerts.model.FireStation;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.File;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DataInitializerTest {

    @Mock
    private IPersonRepository personRepository;
    @Mock
    private IFireStationRepository fireStationRepository;
    @Mock
    private IMedicalRecordRepository medicalRecordRepository;
    @Mock
    private ObjectMapper mapper;
    @InjectMocks
    private DataInitializer dataInitializer;

    private DataContainer dataContainer;
    private List<Person> personList;
    private List<FireStation> fireStationList;
    private List<MedicalRecord> medicalRecordList;

    @BeforeEach
    public void setUp() {
        dataContainer = new DataContainer();
        personList = List.of(
                new Person("Paul", "Smith", "Elm street", "Washington", 123, "00-11-22", "paul@gmail.com")
        );
        fireStationList = List.of(
                new FireStation("Elm street", 1)
        );
        medicalRecordList = List.of(
                new MedicalRecord(
                        "Paul",
                        "Smith",
                        "01/01/1950",
                        List.of("meds"),
                        List.of("allergies")
                )
        );
        dataContainer.setPersons(personList);
        dataContainer.setMedicalrecords(medicalRecordList);
        dataContainer.setFirestations(fireStationList);
    }

    @Test
    public void initializeDataTest() throws Exception {
        when(mapper.readValue(any(File.class), eq(DataContainer.class))).thenReturn(dataContainer);
        when(personRepository.saveAll(personList)).thenReturn(true);
        when(fireStationRepository.saveAll(fireStationList)).thenReturn(true);
        when(medicalRecordRepository.saveAll(medicalRecordList)).thenReturn(true);

        dataInitializer.run();

        verify(mapper, times(1)).readValue(any(File.class), eq(DataContainer.class));
        verify(personRepository).saveAll(personList);
        verify(fireStationRepository).saveAll(fireStationList);
        verify(medicalRecordRepository).saveAll(medicalRecordList);
    }

    @Test
    public void initializeDataFailsTest() throws Exception {
        when(mapper.readValue(any(File.class), eq(DataContainer.class))).thenReturn(null);

        dataInitializer.run();

        verify(mapper, times(1)).readValue(any(File.class), eq(DataContainer.class));
        verify(personRepository, never()).saveAll(anyList());
        verify(fireStationRepository, never()).saveAll(anyList());
        verify(medicalRecordRepository, never()).saveAll(anyList());
    }
}
