
import data.HealthCardID;
import exceptions.HealthCardException;
import exceptions.InvalidCIPFormat;
import services.ScheduledVisitAgenda;

import java.util.LinkedList;
import java.util.List;

public class ScheduledVisitAgendaDobleTest implements ScheduledVisitAgenda {
    //TODO InvalidCIPFormat es fa al introduir codi, getHealthCcardId es un getter que retornr un cardID ja creat

    List<HealthCardID> database;

    public ScheduledVisitAgendaDobleTest() throws InvalidCIPFormat {
        database = new LinkedList<>();
        database.add(new HealthCardID("BBBBBBBBAR444851805874780037")); //TODO mateix problema, gets no poden crear instancies
    }

    @Override
    public HealthCardID getHealthCardID() throws HealthCardException {
        return database.get(0);
    }
}
