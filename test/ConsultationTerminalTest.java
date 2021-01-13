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
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class ConsultationTerminalTest {

    ConsultationTerminal terminal;
    byte[] signatura;
    @BeforeEach
    void setUp() {
        signatura = "Doctor Carlos".getBytes();
        terminal = new ConsultationTerminal(new DigitalSignature(signatura),new HealthNationalServiceDobleTest(),new ScheduledVisitAgendaDobleTest());
    }

    @Test
    void initRevision() throws HealthCardException, InvalidCIPFormat, ConnectException, NotValidePrescriptionException {
        terminal.initRevision();
        assertEquals("BBBBBBBBAR444851805874780037",terminal.getCurrentePrescription().getHcID().getPersonalID());

    }

    @Test
    @DisplayName("")
    void searchForProductsAndselectProduct() throws InvalidPriceFormat, InvalidUPCFormat, AnyMedicineSearchException, ConnectException, StringTooLongException, AnyKeyWordMedicineException {


        //  el caso en que no se haya realizado ninguna búsqueda en el catálogo de productos
        assertThrows(AnyMedicineSearchException.class, () -> {
            terminal.selectProduct(1);
        });
        // el  caso  en  que  no  se  encuentre ningún   medicamento   en   el   catálogo   con   la palabra   clave   introducida
        assertThrows(AnyKeyWordMedicineException.class, () -> {
            terminal.searchForProducts("patata");
        });


        // comprobar el exepcio

        String keyWord= "pulmones";

        assertEquals(3,terminal.getHNS().getProductsByKW(keyWord).size());

        assertEquals("ProductID{UPC='123456789124'}",terminal.getHNS().getProductSpecific(1).getUPCcode().toString());
        assertEquals("Tu presión sistólica está por debajo de 90 milímetros de mercurio (mm Hg) o tu presión diastólica es de 60 mm Hg o menor . .", terminal.getHNS().getProductSpecific(1).getDescription());
        assertEquals(new BigDecimal("39.99"),terminal.getHNS().getProductSpecific(1).getPrice());

        assertEquals("ProductID{UPC='123456789125'}",terminal.getHNS().getProductSpecific(2).getUPCcode().toString());

        // coprobamos si buscamos con otra keyWord2 no contengan productos de la enterio busqueda
        String keyWord2= "ojos";
        assertNotEquals(3,terminal.getHNS().getProductsByKW(keyWord2).size());
        assertEquals(1,terminal.getHNS().getProductsByKW(keyWord2).size());

        assertEquals("ProductID{UPC='123456789126'}",terminal.getHNS().getProductSpecific(0).getUPCcode().toString());


    }

    @Test
    @DisplayName("")
    void selectProductAndEnterMedicineGuidelines() throws AnyKeyWordMedicineException, InvalidPriceFormat, InvalidUPCFormat, ConnectException, StringTooLongException, AnyMedicineSearchException, AnySelectedMedicineException, IncorrectTakingGuidelinesException, ProductAlreadyAdded, NotValidePrescriptionException, HealthCardException, InvalidCIPFormat {
        terminal.initRevision();
        terminal.getHNS().getProductsByKW("cor").size();

        assertThrows(AnySelectedMedicineException.class, () -> {
            String[] instrucio = new String[]{"AFTERDINNER", "12", "Tomar con agua.", "13", "23", "WEEK"};
            terminal.enterMedicineGuidelines(instrucio);
        });


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

        /* mira que passe en cas que fiques un product repetit.
        terminal.selectProduct(0);
        assertThrows(AnySelectedMedicineException.class, () -> {
            String[] instrucioRepetida = new String[]{"AFTERDINNER", "12.5f", "Tomar con agua.", "13", "23", "WEEK"};
            terminal.enterMedicineGuidelines(instrucioRepetida);
        });*/

        terminal.selectProduct(1);
        String[] instrucio2 = new String[]{"AFTERDINNER", "12", "Tomar antes de comer. ", "13", "23", "WEEK"};
        terminal.enterMedicineGuidelines(instrucio2);
        assertEquals(2,terminal.getCurrentePrescription().getPrescriptionLines().size());



        assertThrows(AnyMedicineSearchException.class, () -> {
            terminal.selectProduct(10);
        });



    }



    @Test
    @DisplayName("")
    void enterTreatmentEndingDate() throws IncorrectEndingDateException, HealthCardException, InvalidCIPFormat, ConnectException, NotValidePrescriptionException, InvalidPriceFormat, InvalidUPCFormat, AnyMedicineSearchException, StringTooLongException, AnyKeyWordMedicineException, AnySelectedMedicineException, IncorrectTakingGuidelinesException, ProductAlreadyAdded {

        terminal.initRevision();
        terminal.getHNS().getProductsByKW("cor").size();
        terminal.selectProduct(0);
        String[] instrucio = new String[]{"AFTERDINNER", "12", "Tomar con agua.", "13", "23", "WEEK"};
        terminal.enterMedicineGuidelines(instrucio);


        assertThrows(IncorrectEndingDateException.class, () -> {
            terminal.enterTreatmentEndingDate(new Date(-604075999750L));
        });

        terminal.enterTreatmentEndingDate(new Date(System.currentTimeMillis() + 86400 * 1000 * 2));
        //System.out.println(new Date(System.currentTimeMillis() + 86400 * 1000 * 2));
        assertEquals(new Date(System.currentTimeMillis()).toString(),terminal.getCurrentePrescription().getPrescDate().toString());
        //System.out.println(terminal.getCurrentePrescription().getEndDate());
        assertEquals(new Date(System.currentTimeMillis()+ 86400 * 1000 * 2).toString(),terminal.getCurrentePrescription().getEndDate().toString());

    }



    @Test
    @DisplayName("")
   void sendePrescription() throws InvalidCIPFormat, HealthCardException, eSignatureException, ConnectException, NotValidePrescription, NotCompletedMedicalPrescription, IncorrectTakingGuidelinesException, ProductAlreadyAdded, NotValidePrescriptionException, AnyKeyWordMedicineException, InvalidPriceFormat, InvalidUPCFormat, StringTooLongException, AnyMedicineSearchException, AnySelectedMedicineException, IncorrectEndingDateException {


        terminal.initRevision();
        terminal.getHNS().getProductsByKW("cor").size();
        terminal.selectProduct(0);
        String[] instrucio = new String[]{"AFTERDINNER", "12", "Tomar con agua.", "13", "23", "WEEK"};
        terminal.enterMedicineGuidelines(instrucio);
        terminal.enterTreatmentEndingDate(new Date(System.currentTimeMillis()));

        MedicalPrescription pres = new MedicalPrescription(terminal.getSVA().getHealthCardID());
        terminal.sendePrescription(pres);

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