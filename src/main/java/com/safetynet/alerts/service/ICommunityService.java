package com.safetynet.alerts.service;

import java.util.Set;

public interface ICommunityService {

    /**
     * Cette url doit retourner les adresses mail de tous les habitants de la ville.
     */

    Set<String> getAllEmails(String city);
}
