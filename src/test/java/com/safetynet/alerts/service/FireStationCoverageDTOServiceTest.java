package com.safetynet.alerts.service;

import com.safetynet.alerts.model.FireStation;
import com.safetynet.alerts.dto.FireStationCoverageDTO;
import com.safetynet.alerts.repository.FireStationRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FireStationCoverageDTOServiceTest {

    @Mock
    FireStationService fireStationService;

    @Mock
    Optional<List<FireStation>> mockedStations;

    @InjectMocks
    FireStationCoverageService service;

    @Test
    public void getListOfPersonsCoveredByStation() {
        when(fireStationService.findByStationNumber(1)).thenReturn(mockedStations);

        FireStationCoverageDTO coverage = service.findPersonsCoveredByFireStation(1);

        verify(fireStationService).findByStationNumber(1);
        assertNotNull(coverage);
    }
}
