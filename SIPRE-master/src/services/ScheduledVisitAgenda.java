package services;

import data.HealthCardID;
import exceptions.HealthCardException;
import exceptions.InvalidCIPFormat;

public interface ScheduledVisitAgenda {
    HealthCardID getHealthCardID() throws HealthCardException, InvalidCIPFormat;
}
