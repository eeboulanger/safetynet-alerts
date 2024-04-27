package com.safetynet.alerts.service;

import com.safetynet.alerts.dto.ChildDTO;
import com.safetynet.alerts.dto.PersonContactInfo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ChildDTOAlertServiceIT {

    @Autowired
    ChildAlertService service;

    @Test
    @DisplayName("Given there are children when entering address, then return list")
    public void findAllChildrenTest() {

        List<ChildDTO> childDTOList = service.findAllChildren("1509 Culver St");

        assertNotNull(childDTOList);
        assertEquals(2, childDTOList.size());
        assertEquals(4, childDTOList.get(0).getFamilyMemberList().size());
    }

    @Test
    @DisplayName("Given there's no child when entering address, then return empty list")
    public void givenNoChild_whenEnteringAddress_thenReturnEmptyJson() {
        List<ChildDTO> childDTOList = service.findAllChildren("29 15th St");

        assertNotNull(childDTOList);
        assertEquals(0, childDTOList.size());
    }

    @Test
    @DisplayName("Given there are family members to a child then return a list of family members")
    public void findFamilyMembersTest(){
        List<PersonContactInfo> result = service.findFamilyMembers("Boyd", "1509 Culver St", "Tenley");

        assertEquals(4, result.size());
    }
}
