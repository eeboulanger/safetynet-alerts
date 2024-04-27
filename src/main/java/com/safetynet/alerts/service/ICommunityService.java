package com.safetynet.alerts.service;

import com.safetynet.alerts.dto.PersonInfoDTO;

import java.util.List;

/**
 * Handles search for person information in community
 */
public interface ICommunityService {

    /**
     * Finds all persons by name
     *
     * @param firstName
     * @param lastName
     * @return list of person information
     */
    List<PersonInfoDTO> getAllPersonsByName(String firstName, String lastName);
}
