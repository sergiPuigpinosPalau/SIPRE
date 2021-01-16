import data.HealthCardID;
import exceptions.HealthCardException;
import exceptions.InvalidCIPFormat;
import services.ScheduledVisitAgenda;

import java.util.LinkedList;
import java.util.List;

public class ScheduledVisitAgendaDoubleTestImpl implements ScheduledVisitAgenda {

    List<HealthCardID> database;

    public ScheduledVisitAgendaDoubleTestImpl() throws InvalidCIPFormat {
        database = new LinkedList<>();
        database.add(new HealthCardID("BBBBBBBBAR444851805874780037"));
    }

    @Override
    public HealthCardID getHealthCardID() throws HealthCardException {
        return database.get(0);
    }
}
