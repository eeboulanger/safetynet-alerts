package com.safetynet.alerts.service;

import java.util.Set;

/**
 * =any class that handles search for email addresses
 */
public interface IEmailService {
    /**
     * Returns a set of persons emails by city containing no duplicates
     */
    Set<String> getAllEmails(String city);
}
