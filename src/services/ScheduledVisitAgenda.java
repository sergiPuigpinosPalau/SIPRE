package services;

import data.HealthCardID;
import exceptions.HealthCardException;
import exceptions.InvalidCIPFormat; //TODO check unused imports

public interface ScheduledVisitAgenda {
    HealthCardID getHealthCardID() throws HealthCardException;
}
