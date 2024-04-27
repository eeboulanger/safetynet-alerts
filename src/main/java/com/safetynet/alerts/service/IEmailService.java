package com.safetynet.alerts.service;

import java.util.Set;

/**
 * Handles search for emails
 */
public interface IEmailService {
    /**
     * Returns a set of persons emails by city
     */

    Set<String> getAllEmails(String city);
}
