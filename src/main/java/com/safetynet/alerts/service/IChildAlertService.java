package com.safetynet.alerts.service;

import java.util.List;

/**
 * Any class retrieving information on children and returns data as a list of dto containing the data requested
 * @param <T> is the DTO object
 */
public interface IChildAlertService<T> {

     List<T> findAllChildren(String address);
}
