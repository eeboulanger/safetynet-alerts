package com.safetynet.alerts.service;

import java.util.List;

/**
 * Find information on children and returns data as a list of dto
 * @param <T> is the DTO object
 */
public interface IChildAlertService<T> {

     List<T> findAllChildren(String address);
}
