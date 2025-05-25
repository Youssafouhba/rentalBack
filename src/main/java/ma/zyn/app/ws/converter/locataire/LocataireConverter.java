package ma.zyn.app.ws.converter.locataire;

import ma.zyn.app.bean.core.finance.CompteLocataire;
import ma.zyn.app.bean.core.locataire.Locataire;
import ma.zyn.app.bean.core.locataire.TypeLocataire;
import ma.zyn.app.ws.converter.finance.CompteLocataireConverter;
import ma.zyn.app.ws.converter.locaux.LocalConverter;
import ma.zyn.app.ws.dto.locataire.LocataireDto;
import ma.zyn.app.zynerator.converter.AbstractConverterHelper;
import ma.zyn.app.zynerator.util.DateUtil;
import ma.zyn.app.zynerator.util.ListUtil;
import ma.zyn.app.zynerator.util.StringUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class LocataireConverter {

    @Autowired
    private LocalConverter localConverter ;
    @Autowired
    private AvoirConverter avoirConverter ;
    @Autowired
    private CompteLocataireConverter compteLocataireConverter ;
    @Autowired
    private TypeLocataireConverter typeLocataireConverter ;
    @Autowired
    private LocationConverter locationConverter ;
    private boolean typeLocataire;
    private boolean compteLocataire;
    private boolean locations;
    private boolean avoirs;

    public  LocataireConverter() {
        init(true);
    }

    public Locataire toItem(LocataireDto dto) {
        if (dto == null) {
            return null;
        } else {
            Locataire item = new Locataire();
            if(StringUtil.isNotEmpty(dto.getId()))
                item.setId(dto.getId());
            if(StringUtil.isNotEmpty(dto.getCode()))
                item.setCode(dto.getCode());
            if(StringUtil.isNotEmpty(dto.getNom()))
                item.setNom(dto.getNom());
            if(StringUtil.isNotEmpty(dto.getPrenom()))
                item.setPrenom(dto.getPrenom());
            if(StringUtil.isNotEmpty(dto.getFullName()))
                item.setFullName(dto.getFullName());
            if(StringUtil.isNotEmpty(dto.getTelephone()))
                item.setTelephone(dto.getTelephone());
            if(StringUtil.isNotEmpty(dto.getDateCreation()))
                item.setDateCreation(DateUtil.stringEnToDate(dto.getDateCreation()));
            if(this.typeLocataire && dto.getTypeLocataire()!=null)
                item.setTypeLocataire(typeLocataireConverter.toItem(dto.getTypeLocataire())) ;

            if(dto.getCompteLocataire() != null && dto.getCompteLocataire().getId() != null){
                item.setCompteLocataire(new CompteLocataire());
                item.getCompteLocataire().setId(dto.getCompteLocataire().getId());
                item.getCompteLocataire().setId(dto.getCompteLocataire().getId());
            }


            if(this.locations && ListUtil.isNotEmpty(dto.getLocations()))
                item.setLocations(locationConverter.toItem(dto.getLocations()));
            if(this.avoirs && ListUtil.isNotEmpty(dto.getAvoirs()))
                item.setAvoirs(avoirConverter.toItem(dto.getAvoirs()));


            return item;
        }
    }


    public LocataireDto toDto(Locataire item) {
        if (item == null) {
            return null;
        } else {
            LocataireDto dto = new LocataireDto();
            if(StringUtil.isNotEmpty(item.getId()))
                dto.setId(item.getId());
            if(StringUtil.isNotEmpty(item.getFullName()))
                dto.setFullName(item.getFullName());
            if(StringUtil.isNotEmpty(item.getCode()))
                dto.setCode(item.getCode());
            if(StringUtil.isNotEmpty(item.getNom()))
                dto.setNom(item.getNom());
            if(StringUtil.isNotEmpty(item.getPrenom()))
                dto.setPrenom(item.getPrenom());
            if(StringUtil.isNotEmpty(item.getTelephone()))
                dto.setTelephone(item.getTelephone());
            if(item.getDateCreation()!=null)
                dto.setDateCreation(DateUtil.dateTimeToString(item.getDateCreation()));
            if(this.typeLocataire && item.getTypeLocataire()!=null) {
                dto.setTypeLocataire(typeLocataireConverter.toDto(item.getTypeLocataire())) ;

            }
            if(this.compteLocataire && item.getCompteLocataire()!=null) {
                compteLocataireConverter.setLocataire(false);
                dto.setCompteLocataire(compteLocataireConverter.toDto(item.getCompteLocataire())) ;
                compteLocataireConverter.setLocataire(true);

            }
            if(this.locations && ListUtil.isNotEmpty(item.getLocations())){
                locationConverter.init(true);
                locationConverter.setLocataire(false);
                dto.setLocations(locationConverter.toDto(item.getLocations()));
                locationConverter.setLocal(true);

            }
            if(this.avoirs && ListUtil.isNotEmpty(item.getAvoirs())){
                avoirConverter.init(true);
                dto.setAvoirs(avoirConverter.toDto(item.getAvoirs()));

            }


            return dto;
        }
    }

    public void init(boolean value) {
        initList(value);
    }

    public void initList(boolean value) {
        this.locations = value;
        this.avoirs = value;
    }
    public void initObject(boolean value) {
        this.typeLocataire = value;
        this.compteLocataire = value;
    }

    public List<Locataire> toItem(List<LocataireDto> dtos) {
        List<Locataire> items = new ArrayList<>();
        if (dtos != null && !dtos.isEmpty()) {
            for (LocataireDto dto : dtos) {
                items.add(toItem(dto));
            }
        }
        return items;
    }


    public List<LocataireDto> toDto(List<Locataire> items) {
        List<LocataireDto> dtos = new ArrayList<>();
        if (items != null && !items.isEmpty()) {
            for (Locataire item : items) {
                dtos.add(toDto(item));
            }
        }
        return dtos;
    }


    public void copy(LocataireDto dto, Locataire t) {
        BeanUtils.copyProperties(dto, t, AbstractConverterHelper.getNullPropertyNames(dto));
        if(t.getTypeLocataire() == null  && dto.getTypeLocataire() != null){
            t.setTypeLocataire(new TypeLocataire());
        }else if (t.getTypeLocataire() != null  && dto.getTypeLocataire() != null){
            t.setTypeLocataire(null);
            t.setTypeLocataire(new TypeLocataire());
        }
        if(t.getCompteLocataire() == null  && dto.getCompteLocataire() != null){
            t.setCompteLocataire(new CompteLocataire());
        }else if (t.getCompteLocataire() != null  && dto.getCompteLocataire() != null){
            t.setCompteLocataire(null);
            t.setCompteLocataire(new CompteLocataire());
        }
        if (dto.getTypeLocataire() != null)
            typeLocataireConverter.copy(dto.getTypeLocataire(), t.getTypeLocataire());
        if (dto.getCompteLocataire() != null)
            compteLocataireConverter.copy(dto.getCompteLocataire(), t.getCompteLocataire());
        if (dto.getLocations() != null)
            t.setLocations(locationConverter.copy(dto.getLocations()));
        if (dto.getAvoirs() != null)
            t.setAvoirs(avoirConverter.copy(dto.getAvoirs()));
    }

    public List<Locataire> copy(List<LocataireDto> dtos) {
        List<Locataire> result = new ArrayList<>();
        if (dtos != null) {
            for (LocataireDto dto : dtos) {
                Locataire instance = new Locataire();
                copy(dto, instance);
                result.add(instance);
            }
        }
        return result.isEmpty() ? null : result;
    }


    public LocalConverter getLocalConverter(){
        return this.localConverter;
    }
    public void setLocalConverter(LocalConverter localConverter ){
        this.localConverter = localConverter;
    }
    public AvoirConverter getAvoirConverter(){
        return this.avoirConverter;
    }
    public void setAvoirConverter(AvoirConverter avoirConverter ){
        this.avoirConverter = avoirConverter;
    }
    public CompteLocataireConverter getCompteLocataireConverter(){
        return this.compteLocataireConverter;
    }
    public void setCompteLocataireConverter(CompteLocataireConverter compteLocataireConverter ){
        this.compteLocataireConverter = compteLocataireConverter;
    }
    public TypeLocataireConverter getTypeLocataireConverter(){
        return this.typeLocataireConverter;
    }
    public void setTypeLocataireConverter(TypeLocataireConverter typeLocataireConverter ){
        this.typeLocataireConverter = typeLocataireConverter;
    }
    public LocationConverter getLocationConverter(){
        return this.locationConverter;
    }
    public void setLocationConverter(LocationConverter locationConverter ){
        this.locationConverter = locationConverter;
    }
    public boolean  isTypeLocataire(){
        return this.typeLocataire;
    }
    public void  setTypeLocataire(boolean typeLocataire){
        this.typeLocataire = typeLocataire;
    }
    public boolean  isCompteLocataire(){
        return this.compteLocataire;
    }
    public void  setCompteLocataire(boolean compteLocataire){
        this.compteLocataire = compteLocataire;
    }
    public boolean  isLocations(){
        return this.locations ;
    }
    public void  setLocations(boolean locations ){
        this.locations  = locations ;
    }
    public boolean  isAvoirs(){
        return this.avoirs ;
    }
    public void  setAvoirs(boolean avoirs ){
        this.avoirs  = avoirs ;
    }
}
