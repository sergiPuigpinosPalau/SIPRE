package ConsultationTerminal;

import data.HealthCardID;
import exceptions.HealthCardException;
import exceptions.InvalidCIPFormat;
import services.ScheduledVisitAgenda;

public class ScheduledVisitAgendaDobleTest implements ScheduledVisitAgenda {
    @Override
    public HealthCardID getHealthCardID() throws HealthCardException, InvalidCIPFormat {
        return new HealthCardID("BBBBBBBBAR444851805874780037");
    }
}
