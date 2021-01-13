
import data.HealthCardID;
import data.ProductID;
import exceptions.*;
import medicalconsultation.MedicalPrescription;
import medicalconsultation.ProductSpecification;
import services.HealthNationalService;

import java.math.BigDecimal;
import java.net.ConnectException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class HealthNationalServiceDobleTest implements HealthNationalService {

    List<ProductSpecification> catalogo = new ArrayList<>();

    @Override
    public MedicalPrescription getePrescription(HealthCardID hcID) throws HealthCardException, NotValidePrescriptionException, ConnectException {

        MedicalPrescription prescripco = new MedicalPrescription(hcID);

        return prescripco ;
    }

    @Override
    public List<ProductSpecification> getProductsByKW(String keyWord) throws AnyKeyWordMedicineException, ConnectException, InvalidUPCFormat, InvalidPriceFormat, StringTooLongException {

        if(catalogo.size()>0){
            for(int i=(catalogo.size()-1); i>=0;i--){
                catalogo.remove(i);
            }

        }

            switch (keyWord) {
                case "ojos":
                    catalogo.add(new ProductSpecification(new ProductID("123456789126"),"tecnologías clínicas naturales que tratan todos los signos .", new BigDecimal("24.5")));
                    return catalogo;
                case "pulmones":
                    catalogo.add(new ProductSpecification(new ProductID("123456789123"),"tecnologías clínicas naturales que tratan todos los signos .", new BigDecimal("44.5")));
                    catalogo.add(new ProductSpecification(new ProductID("123456789124"),"Tu presión sistólica está por debajo de 90 milímetros de mercurio (mm Hg) o tu presión diastólica es de 60 mm Hg o menor . .", new BigDecimal("39.99")));
                    catalogo.add(new ProductSpecification(new ProductID("123456789125"),"tecnologías clínicas naturales . .", new BigDecimal("34.99")));

                    return catalogo;
                case "cor":
                    catalogo.add(new ProductSpecification(new ProductID("123456789121"),"tecnologías clínicas naturales . .", new BigDecimal("59.99")));
                    catalogo.add(new ProductSpecification(new ProductID("123456789122"),"tecnologías clínicas naturales . .", new BigDecimal("59.99")));
                    return catalogo;
                default:
                    throw new AnyKeyWordMedicineException();
            }



    }

    @Override
    public ProductSpecification getProductSpecific(int opt) throws AnyMedicineSearchException, ConnectException, InvalidPriceFormat, StringTooLongException, InvalidUPCFormat {

            if(catalogo.size()>0 && catalogo.size()>opt ) {
                return catalogo.get(opt);
            }else{
                throw new AnyMedicineSearchException();
            }
    }

    @Override
    public MedicalPrescription sendePrescription(MedicalPrescription ePresc) throws ConnectException, NotValidePrescription, eSignatureException, NotCompletedMedicalPrescription, IncorrectTakingGuidelinesException, ProductAlreadyAdded {


            MedicalPrescription ePrescCopia = new MedicalPrescription(ePresc.getHcID());
            ePrescCopia.seteSign(ePresc.geteSign());
            ePrescCopia.setEndDate(ePresc.getEndDate());
            ePrescCopia.setPrescDate(ePresc.getPrescDate());
            ePrescCopia.setPrescCode(ePresc.getPrescCode());

            for(int i=0; i< ePresc.getPrescriptionLines().size(); i++ ) {
                String[] instrucio = new String[6];
                instrucio[0]=ePresc.getPrescriptionLines().get(i).getGuideline().getDayMoment().toString();
                instrucio[1]=ePresc.getPrescriptionLines().get(i).getGuideline().getDuration()+"";
                instrucio[2]= ePresc.getPrescriptionLines().get(i).getGuideline().getInstructions();
                instrucio[3]= ePresc.getPrescriptionLines().get(i).getGuideline().getPosology().getDose()+"";
                instrucio[4]=ePresc.getPrescriptionLines().get(i).getGuideline().getPosology().getFreq()+"";
                instrucio[5]=ePresc.getPrescriptionLines().get(i).getGuideline().getPosology().getFreqUnit().toString();
                ePrescCopia.addLine(ePresc.getPrescriptionLines().get(i).getProductID(), instrucio);
            }

            return ePrescCopia;


    }
}
