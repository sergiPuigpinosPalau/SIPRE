import data.DigitalSignature;
import data.ProductID;
import exceptions.*;
import medicalconsultation.DayMoment;
import medicalconsultation.FqUnit;
import medicalconsultation.MedicalPrescription;
import medicalconsultation.ProductSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.net.ConnectException;
import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class ConsultationTerminalTest {

    ConsultationTerminal terminal;
    byte[] signatura;

    @BeforeEach
    void setUp() throws InvalidPriceFormat, StringTooLongException, InvalidUPCFormat, InvalidCIPFormat {
        signatura = "Doctor Carlos".getBytes();
        terminal = new ConsultationTerminal(new DigitalSignature(signatura),new HealthNationalServiceDobleTest(),new ScheduledVisitAgendaDobleTest());
    }

    @Test
    void initRevision() throws NotValidePrescriptionException, HealthCardException, ConnectException {
        terminal.initRevision();
        assertEquals("BBBBBBBBAR444851805874780037",terminal.getCurrentePrescription().getHcID().getPersonalID());
    }

    @Test
    void initPrescriptionEdition() {
        //Case where initRevision hasn't set any prescription
        assertThrows(AnyCurrentPrescriptionException.class, () -> {
            terminal.initPrescriptionEdition();
        });
        //Case where end date hasn't arrived yet
        Date currentDate = new java.util.Date();
        Date futureDate = Date.from(currentDate.toInstant().plusSeconds(1576800000));
        assertThrows(NotFinishedTreatmentException.class, () -> {
            terminal.initRevision();
            MedicalPrescription presc = terminal.getCurrentePrescription();
            presc.setEndDate(futureDate);
            terminal.setCurrentePrescription(presc);
            terminal.initPrescriptionEdition();
        });
    }

    @Test
    @DisplayName("HOLA")
    void searchForProductsAndselectProduct() throws AnyMedicineSearchException, ConnectException, AnyKeyWordMedicineException {
        //Case where there hasn't been any previous search done in the catalogue of products
        assertThrows(AnyMedicineSearchException.class, () -> {
            terminal.selectProduct(1);
        });
        //Case where it can't find any product matching the provided keyword
        assertThrows(AnyKeyWordMedicineException.class, () -> {
            terminal.searchForProducts("patata");
        });
        //Check it gets the correct values
        String keyWord= "lungs";
        assertEquals(3,terminal.getHNS().getProductsByKW(keyWord).size());
        assertEquals("ProductID{UPC='123456789124'}",terminal.getHNS().getProductSpecific(1).getUPCcode().toString());
        assertEquals("Device which helps user to breath and helps with lung related problems...", terminal.getHNS().getProductSpecific(1).getDescription());
        assertEquals(new BigDecimal("39.99"),terminal.getHNS().getProductSpecific(1).getPrice());
        assertEquals("ProductID{UPC='123456789125'}",terminal.getHNS().getProductSpecific(2).getUPCcode().toString());
        //Check if we do another search, it doesn't contain values from any previous search
        String keyWord2= "eyes";
        assertNotEquals(3,terminal.getHNS().getProductsByKW(keyWord2).size());
        assertEquals(1,terminal.getHNS().getProductsByKW(keyWord2).size());
        assertEquals("ProductID{UPC='123456789126'}",terminal.getHNS().getProductSpecific(0).getUPCcode().toString());
    }

    @Test
    @DisplayName("")
    void selectProductAndEnterMedicineGuidelines() throws AnyKeyWordMedicineException, ConnectException, AnyMedicineSearchException, AnySelectedMedicineException, IncorrectTakingGuidelinesException, ProductAlreadyAdded, NotValidePrescriptionException, HealthCardException {
        terminal.initRevision();
        terminal.searchForProducts("hearth");
        //Case where no product was selected before entering the medicine guidelines
        assertThrows(AnySelectedMedicineException.class, () -> {
            String[] instrucio = new String[]{"AFTERDINNER", "12", "Tomar con agua.", "13", "23", "WEEK"};
            terminal.enterMedicineGuidelines(instrucio);
        });
        //Check whether guidelines match the ones entered or not
        terminal.selectProduct(0);
        assertEquals("123456789121",terminal.getSelectedProduct().getUPCcode().getUPC());
        String[] instrucio = new String[]{"AFTERDINNER", "12.5f", "Tomar con agua.", "13", "23", "WEEK"};
        terminal.enterMedicineGuidelines(instrucio);
        assertEquals(DayMoment.AFTERDINNER,terminal.getCurrentePrescription().getPrescriptionLines().get(0).getGuideline().getDayMoment());
        assertEquals(12.5f,terminal.getCurrentePrescription().getPrescriptionLines().get(0).getGuideline().getDuration());
        assertEquals("Tomar con agua.",terminal.getCurrentePrescription().getPrescriptionLines().get(0).getGuideline().getInstructions());
        assertEquals(13,terminal.getCurrentePrescription().getPrescriptionLines().get(0).getGuideline().getPosology().getDose());
        assertEquals(23,terminal.getCurrentePrescription().getPrescriptionLines().get(0).getGuideline().getPosology().getFreq());
        assertEquals(FqUnit.WEEK,terminal.getCurrentePrescription().getPrescriptionLines().get(0).getGuideline().getPosology().getFreqUnit());
        //Check it has the correct amount of prescriptions lines as you added
        terminal.selectProduct(1);
        String[] instrucio2 = new String[]{"AFTERDINNER", "12", "Tomar antes de comer. ", "13", "23", "WEEK"};
        terminal.enterMedicineGuidelines(instrucio2);
        assertEquals(2,terminal.getCurrentePrescription().getPrescriptionLines().size());
        //Case where user tries to select product which is not in the list
        assertThrows(AnyMedicineSearchException.class, () -> terminal.selectProduct(10));
    }

    @Test
    @DisplayName("")
    void enterTreatmentEndingDate() throws IncorrectEndingDateException, HealthCardException, ConnectException, NotValidePrescriptionException, AnyMedicineSearchException, AnyKeyWordMedicineException, AnySelectedMedicineException, IncorrectTakingGuidelinesException, ProductAlreadyAdded {
        terminal.initRevision();
        terminal.searchForProducts("hearth");
        terminal.selectProduct(0);
        String[] instrucio = new String[]{"AFTERDINNER", "12", "Tomar con agua.", "13", "23", "WEEK"};
        terminal.enterMedicineGuidelines(instrucio);
        //Case where user enters invalid end date
        Date currentDate = new java.util.Date();
        Date futureDate = Date.from(currentDate.toInstant().plusSeconds(1576800000));
        Date pastDate = Date.from(currentDate.toInstant().plusSeconds(-1576800000));
        assertThrows(IncorrectEndingDateException.class, () -> {
            terminal.enterTreatmentEndingDate(futureDate);
        });
        assertThrows(IncorrectEndingDateException.class, () -> {
            terminal.enterTreatmentEndingDate(pastDate);
        });
        //Check date has been set correctly
        terminal.enterTreatmentEndingDate(new Date(System.currentTimeMillis() + 86400 * 1000 * 2));
        assertEquals(new Date(System.currentTimeMillis()).toString(),terminal.getCurrentePrescription().getPrescDate().toString());
        assertEquals(new Date(System.currentTimeMillis()+ 86400 * 1000 * 2).toString(),terminal.getCurrentePrescription().getEndDate().toString());
    }

    @Test
    @DisplayName("")
   void sendePrescription() throws HealthCardException, eSignatureException, ConnectException, NotValidePrescription, NotCompletedMedicalPrescription, IncorrectTakingGuidelinesException, ProductAlreadyAdded, NotValidePrescriptionException, AnyKeyWordMedicineException, AnyMedicineSearchException, AnySelectedMedicineException, IncorrectEndingDateException {
        terminal.initRevision();
        terminal.searchForProducts("hearth");
        terminal.selectProduct(0);
        String[] instrucio = new String[]{"AFTERDINNER", "12", "Tomar con agua.", "13", "23", "WEEK"};
        terminal.enterMedicineGuidelines(instrucio);
        terminal.enterTreatmentEndingDate(new Date(System.currentTimeMillis()));

        MedicalPrescription pres = new MedicalPrescription(terminal.getSVA().getHealthCardID());
        terminal.sendePrescription();

        assertEquals(new Date(System.currentTimeMillis()).toString(),terminal.getCurrentePrescription().getEndDate().toString());
        assertEquals(new Date(System.currentTimeMillis()).toString(),terminal.getCurrentePrescription().getPrescDate().toString());
        assertEquals(new DigitalSignature(signatura).getSignature(),terminal.getCurrentePrescription().geteSign().getSignature());

        assertEquals("BBBBBBBBAR444851805874780037",terminal.getCurrentePrescription().getHcID().getPersonalID());
        assertEquals("123456789121",terminal.getCurrentePrescription().getPrescriptionLines().get(0).getProductID().getUPC());

        assertEquals(DayMoment.AFTERDINNER,terminal.getCurrentePrescription().getPrescriptionLines().get(0).getGuideline().getDayMoment());
        assertEquals(12,terminal.getCurrentePrescription().getPrescriptionLines().get(0).getGuideline().getDuration());
        assertEquals("Tomar con agua.",terminal.getCurrentePrescription().getPrescriptionLines().get(0).getGuideline().getInstructions());
        assertEquals(13,terminal.getCurrentePrescription().getPrescriptionLines().get(0).getGuideline().getPosology().getDose());
        assertEquals(23,terminal.getCurrentePrescription().getPrescriptionLines().get(0).getGuideline().getPosology().getFreq());
        assertEquals(FqUnit.WEEK,terminal.getCurrentePrescription().getPrescriptionLines().get(0).getGuideline().getPosology().getFreqUnit());
    }
}