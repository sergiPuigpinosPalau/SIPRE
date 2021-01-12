package services; // Package for involved services

import data.HealthCardID;
import exceptions.*;
import medicalconsultation.MedicalPrescription;
import medicalconsultation.ProductSpecification;

import java.net.ConnectException;
import java.util.List;

/**
 * External service for managing and storing ePrescriptions from population
 */

public interface HealthNationalService {

    MedicalPrescription getePrescription(HealthCardID hcID) throws HealthCardException, NotValidePrescriptionException, ConnectException;

    List<ProductSpecification> getProductsByKW(String keyWord) throws AnyKeyWordMedicineException, ConnectException, InvalidUPCFormat, InvalidPriceFormat, StringTooLongException;

    ProductSpecification getProductSpecific(int opt) throws AnyMedicineSearchException, ConnectException, InvalidPriceFormat, StringTooLongException, InvalidUPCFormat;

    MedicalPrescription sendePrescription(MedicalPrescription ePresc) throws ConnectException, NotValidePrescription, eSignatureException, NotCompletedMedicalPrescription, IncorrectTakingGuidelinesException, ProductAlreadyAdded;

}
