package com.safetynet.alerts.service;

import com.safetynet.alerts.dto.PersonInfoDTO;

import java.util.List;

/**
 * Any class that handles search for person information in community
 */
public interface ICommunityService {

    /**
     * Finds all persons by name
     *
     * @param firstName of the person
     * @param lastName of the person
     * @return list of person information
     */
    List<PersonInfoDTO> getAllPersonsByName(String firstName, String lastName);
}
