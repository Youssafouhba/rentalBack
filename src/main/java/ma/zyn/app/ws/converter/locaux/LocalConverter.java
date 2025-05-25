package ma.zyn.app.ws.converter.locaux;

import ma.zyn.app.bean.core.locaux.Local;
import ma.zyn.app.bean.core.locaux.TypeLocal;
import ma.zyn.app.ws.dto.locaux.LocalDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.beans.BeanUtils;
import ma.zyn.app.zynerator.converter.AbstractConverterHelper;

import java.util.ArrayList;
import java.util.List;
import ma.zyn.app.zynerator.util.ListUtil;

import ma.zyn.app.ws.converter.locataire.StatutLocalConverter;
import ma.zyn.app.bean.core.locataire.StatutLocal;
import ma.zyn.app.ws.converter.locataire.LocationConverter;
import ma.zyn.app.ws.converter.locataire.LocataireConverter;



import ma.zyn.app.zynerator.util.StringUtil;
import ma.zyn.app.zynerator.util.DateUtil;

import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;

@Component
public class LocalConverter {

    @Autowired
    private StatutLocalConverter statutLocalConverter ;
    @Autowired
    private TypeLocalConverter typeLocalConverter ;
    @Autowired
    private LocationConverter locationConverter ;
    @Autowired
    private LocataireConverter locataireConverter ;
    private boolean statutLocal;
    private boolean typeLocal;
    private boolean locations;

    public  LocalConverter() {
        init(true);
    }

    public Local toItem(LocalDto dto) {
        if (dto == null) {
            return null;
        } else {
            Local item = new Local();
            if(StringUtil.isNotEmpty(dto.getId()))
                item.setId(dto.getId());
            if(StringUtil.isNotEmpty(dto.getCode()))
                item.setCode(dto.getCode());
            if(StringUtil.isNotEmpty(dto.getAdresse()))
                item.setAdresse(dto.getAdresse());
            if(StringUtil.isNotEmpty(dto.getDateCreation()))
                item.setDateCreation(DateUtil.stringEnToDate(dto.getDateCreation()));
            if(StringUtil.isNotEmpty(dto.getLabel()))
                item.setLabel(dto.getLabel());
            if(isNotEmpty(dto.getSuperficie()))
                item.setSuperficie(dto.getSuperficie());
            if(StringUtil.isNotEmpty(dto.getPrix()))
                item.setPrix(dto.getPrix());
            if(StringUtil.isNotEmpty(dto.getDescription()))
                item.setDescription(dto.getDescription());
            if(StringUtil.isNotEmpty(dto.getMontantMensuel()))
                item.setMontantMensuel(dto.getMontantMensuel());
            if(this.statutLocal && dto.getStatutLocal()!=null)
                item.setStatutLocal(statutLocalConverter.toItem(dto.getStatutLocal())) ;

            if(this.typeLocal && dto.getTypeLocal()!=null)
                item.setTypeLocal(typeLocalConverter.toItem(dto.getTypeLocal())) ;


            if(this.locations && ListUtil.isNotEmpty(dto.getLocations()))
                item.setLocations(locationConverter.toItem(dto.getLocations()));


            return item;
        }
    }


    public LocalDto toDto(Local item) {
        if (item == null) {
            return null;
        } else {
            LocalDto dto = new LocalDto();
            if(StringUtil.isNotEmpty(item.getId()))
                dto.setId(item.getId());
            if(StringUtil.isNotEmpty(item.getCode()))
                dto.setCode(item.getCode());
            if(StringUtil.isNotEmpty(item.getAdresse()))
                dto.setAdresse(item.getAdresse());
            if(item.getDateCreation()!=null)
                dto.setDateCreation(DateUtil.dateTimeToString(item.getDateCreation()));
            if(StringUtil.isNotEmpty(item.getLabel()))
                dto.setLabel(item.getLabel());
            if(isNotEmpty(item.getSuperficie()))
                dto.setSuperficie(item.getSuperficie());
            if(StringUtil.isNotEmpty(item.getPrix()))
                dto.setPrix(item.getPrix());
            if(StringUtil.isNotEmpty(item.getDescription()))
                dto.setDescription(item.getDescription());
            if(StringUtil.isNotEmpty(item.getMontantMensuel()))
                dto.setMontantMensuel(item.getMontantMensuel());
            if(this.statutLocal && item.getStatutLocal()!=null) {
                dto.setStatutLocal(statutLocalConverter.toDto(item.getStatutLocal())) ;

            }
            if(this.typeLocal && item.getTypeLocal()!=null) {
                dto.setTypeLocal(typeLocalConverter.toDto(item.getTypeLocal())) ;

            }
            if(this.locations && ListUtil.isNotEmpty(item.getLocations())){
                locationConverter.init(true);
                locationConverter.setLocal(false);
                dto.setLocations(locationConverter.toDto(item.getLocations()));
                locationConverter.setLocal(true);

            }


            return dto;
        }
    }

    public void init(boolean value) {
        initList(value);
    }

    public void initList(boolean value) {
        this.locations = value;
    }
    public void initObject(boolean value) {
        this.statutLocal = value;
        this.typeLocal = value;
    }

    public List<Local> toItem(List<LocalDto> dtos) {
        List<Local> items = new ArrayList<>();
        if (dtos != null && !dtos.isEmpty()) {
            for (LocalDto dto : dtos) {
                items.add(toItem(dto));
            }
        }
        return items;
    }


    public List<LocalDto> toDto(List<Local> items) {
        List<LocalDto> dtos = new ArrayList<>();
        if (items != null && !items.isEmpty()) {
            for (Local item : items) {
                dtos.add(toDto(item));
            }
        }
        return dtos;
    }


    public void copy(LocalDto dto, Local t) {
        BeanUtils.copyProperties(dto, t, AbstractConverterHelper.getNullPropertyNames(dto));
        if(t.getStatutLocal() == null  && dto.getStatutLocal() != null){
            t.setStatutLocal(new StatutLocal());
        }else if (t.getStatutLocal() != null  && dto.getStatutLocal() != null){
            t.setStatutLocal(null);
            t.setStatutLocal(new StatutLocal());
        }

        if(t.getTypeLocal() == null  && dto.getTypeLocal() != null){
            t.setTypeLocal(new TypeLocal());
        }else if (t.getTypeLocal() != null  && dto.getTypeLocal() != null){
            t.setTypeLocal(null);
            t.setTypeLocal(new TypeLocal());
        }
        if (dto.getStatutLocal() != null)
            statutLocalConverter.copy(dto.getStatutLocal(), t.getStatutLocal());
        if (dto.getTypeLocal() != null)
            typeLocalConverter.copy(dto.getTypeLocal(), t.getTypeLocal());
        if (dto.getLocations() != null)
            t.setLocations(locationConverter.copy(dto.getLocations()));
    }

    public List<Local> copy(List<LocalDto> dtos) {
        List<Local> result = new ArrayList<>();
        if (dtos != null) {
            for (LocalDto dto : dtos) {
                Local instance = new Local();
                copy(dto, instance);
                result.add(instance);
            }
        }
        return result.isEmpty() ? null : result;
    }


    public StatutLocalConverter getStatutLocalConverter(){
        return this.statutLocalConverter;
    }
    public void setStatutLocalConverter(StatutLocalConverter statutLocalConverter ){
        this.statutLocalConverter = statutLocalConverter;
    }
    public TypeLocalConverter getTypeLocalConverter(){
        return this.typeLocalConverter;
    }
    public void setTypeLocalConverter(TypeLocalConverter typeLocalConverter ){
        this.typeLocalConverter = typeLocalConverter;
    }
    public LocationConverter getLocationConverter(){
        return this.locationConverter;
    }
    public void setLocationConverter(LocationConverter locationConverter ){
        this.locationConverter = locationConverter;
    }
    public LocataireConverter getLocataireConverter(){
        return this.locataireConverter;
    }
    public void setLocataireConverter(LocataireConverter locataireConverter ){
        this.locataireConverter = locataireConverter;
    }
    public boolean  isStatutLocal(){
        return this.statutLocal;
    }
    public void  setStatutLocal(boolean statutLocal){
        this.statutLocal = statutLocal;
    }
    public boolean  isTypeLocal(){
        return this.typeLocal;
    }
    public void  setTypeLocal(boolean typeLocal){
        this.typeLocal = typeLocal;
    }
    public boolean  isLocations(){
        return this.locations ;
    }
    public void  setLocations(boolean locations ){
        this.locations  = locations ;
    }
}
