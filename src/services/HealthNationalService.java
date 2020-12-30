package services; // Package for involved services

import data.HealthCardID;
import medicalconsultation.MedicalPrescription;
import medicalconsultation.ProductSpecification;

import java.util.List;

/**
 * External service for managing and storing ePrescriptions from population
 */

public interface HealthNationalService {

    MedicalPrescription getePrescription(HealthCardID hcID)
            throws HealthCardException, NotValidePrescriptionException, ConnectException;

    List<ProductSpecification> getProductsByKW(String keyWord)
            throws AnyKeyWordMedicineException, ConnectException;

    ProductSpecification getProductSpecific(int opt)
            throws AnyMedicineSearchException, ConnectException;

    MedicalPrescription sendePrescription(MedicalPrescription ePresc)
            throws ConnectException, NotValidePrescription, eSignatureException, NotCompletedMedicalPrescription;

    //TODO ModeloCasosUsoeReceta-ParteContratos.pdf
    //TODO Este servicio se inyectará a la clase pertinente mediante un setter??
}
