import data.HealthCardID;
import data.ProductID;
import exceptions.*;
import medicalconsultation.MedicalPrescription;
import medicalconsultation.ProductSpecification;
import services.HealthNationalService;

import java.math.BigDecimal;
import java.net.ConnectException;
import java.util.*;

public class HealthNationalServiceDobleTest implements HealthNationalService {

    List<ProductSpecification> catalogueDatabase;
    List<ProductSpecification> listOfSelectedProducts;
    Map<HealthCardID, List<MedicalPrescription>> database;

    public HealthNationalServiceDobleTest() throws InvalidUPCFormat, InvalidPriceFormat, StringTooLongException, InvalidCIPFormat {
        catalogueDatabase = new LinkedList<>();
        listOfSelectedProducts = new LinkedList<>();
        database = new HashMap<>();
        HealthCardID id = new HealthCardID("BBBBBBBBAR444851805874780037");
        database.put(id, new LinkedList<>());
        database.get(id).add(new MedicalPrescription(id));
        //Eyes
        catalogueDatabase.add(new ProductSpecification(new ProductID("123456789126"), "natural clinical technologies that treat all problems related to eyes...", new BigDecimal("24.5")));
        //lungs
        catalogueDatabase.add(new ProductSpecification(new ProductID("123456789123"), "natural clinical technologies that treat all problems related to lungs...", new BigDecimal("44.5")));
        catalogueDatabase.add(new ProductSpecification(new ProductID("123456789124"), "Device which helps user to breath and helps with lung related problems...", new BigDecimal("39.99")));
        catalogueDatabase.add(new ProductSpecification(new ProductID("123456789125"), "Pills to treat lung problems...", new BigDecimal("34.99")));
        //Hearth
        catalogueDatabase.add(new ProductSpecification(new ProductID("123456789121"), "natural clinical technologies that treat all problems related to hearth diseases...", new BigDecimal("59.99")));
        catalogueDatabase.add(new ProductSpecification(new ProductID("123456789122"), "Device to monitor your hearth...", new BigDecimal("59.99")));
    }

    @Override
    public MedicalPrescription getePrescription(HealthCardID hcID) throws HealthCardException, NotValidePrescriptionException, ConnectException {
        List<MedicalPrescription> list;
        if ((list = database.get(hcID)) == null)
            throw new HealthCardException();

        if (list.isEmpty())
            throw new NotValidePrescriptionException();

        return list.get(list.size() - 1);
    }

    @Override
    public List<ProductSpecification> getProductsByKW(String keyWord) throws AnyKeyWordMedicineException, ConnectException {

        List<ProductSpecification> catalogueRtn = new LinkedList<>();

        for (ProductSpecification product : catalogueDatabase) {
            if (product.getDescription().toLowerCase().contains(keyWord.toLowerCase()) || product.getDescription().toLowerCase().contains(keyWord.toLowerCase().substring(0, keyWord.length() - 2)))
                catalogueRtn.add(product);
        }

        if (catalogueRtn.isEmpty())
            throw new AnyKeyWordMedicineException();

        this.listOfSelectedProducts = catalogueRtn;
        return catalogueRtn;
    }

    @Override
    public ProductSpecification getProductSpecific(int opt) throws AnyMedicineSearchException, ConnectException {
        if (!listOfSelectedProducts.isEmpty() && listOfSelectedProducts.size() > opt) {
            return listOfSelectedProducts.get(opt);
        } else {
            throw new AnyMedicineSearchException();
        }
    }

    @Override
    public MedicalPrescription sendePrescription(MedicalPrescription ePresc) throws ConnectException, NotValidePrescription, eSignatureException, NotCompletedMedicalPrescription {
        ePresc.setPrescCode(new Random().nextInt(999999));
        return ePresc;
    }
}
