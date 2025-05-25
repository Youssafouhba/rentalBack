package ma.zyn.app.ws.converter.locataire;


import ma.zyn.app.bean.core.locataire.Locataire;
import ma.zyn.app.bean.core.locataire.Location;
import ma.zyn.app.bean.core.locaux.Local;
import ma.zyn.app.ws.converter.locaux.LocalConverter;
import ma.zyn.app.ws.dto.locataire.LocationDto;
import ma.zyn.app.zynerator.converter.AbstractConverterHelper;
import ma.zyn.app.zynerator.util.DateUtil;
import ma.zyn.app.zynerator.util.StringUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class LocationConverter {

    @Autowired
    private LocalConverter localConverter ;
    @Autowired
    private LocataireConverter locataireConverter ;
    private boolean locataire;
    private boolean local;

    public LocationConverter() {
        initObject(true);
    }

    public Location toItem(LocationDto dto) {
        if (dto == null) {
            return null;
        } else {
        Location item = new Location();
            if(StringUtil.isNotEmpty(dto.getId()))
                item.setId(dto.getId());
            if(StringUtil.isNotEmpty(dto.getCode()))
                item.setCode(dto.getCode());
            if(dto.getActif()!=null)
                item.setActif(dto.getActif());
            if(StringUtil.isNotEmpty(dto.getDateCreation()))
                item.setDateCreation(DateUtil.stringEnToDate(dto.getDateCreation()));
            if(StringUtil.isNotEmpty(dto.getDateDebut()))
                item.setDateDebut(DateUtil.stringEnToDate(dto.getDateDebut()));
            if(StringUtil.isNotEmpty(dto.getDateFin()))
                item.setDateFin(DateUtil.stringEnToDate(dto.getDateFin()));
            if(StringUtil.isNotEmpty(dto.getLoyer()))
                item.setLoyer(dto.getLoyer());
            if(StringUtil.isNotEmpty(dto.getCaution()))
                item.setCaution(dto.getCaution());
            if(StringUtil.isNotEmpty(dto.getSolde()))
                item.setSolde(dto.getSolde());
            if(StringUtil.isNotEmpty(dto.getReference()))
                item.setReference(dto.getReference());
            if(dto.getLocataire() != null && dto.getLocataire().getId() != null){
                item.setLocataire(new Locataire());
                item.getLocataire().setId(dto.getLocataire().getId());
                item.getLocataire().setCode(dto.getLocataire().getCode());
            }

            if(dto.getLocal() != null && dto.getLocal().getId() != null){
                item.setLocal(new Local());
                item.getLocal().setId(dto.getLocal().getId());
                item.getLocal().setCode(dto.getLocal().getCode());
            }




        return item;
        }
    }


    public LocationDto toDto(Location item) {
        if (item == null) {
            return null;
        } else {
            LocationDto dto = new LocationDto();
            if(StringUtil.isNotEmpty(item.getId()))
                dto.setId(item.getId());
            if (StringUtil.isNotEmpty(item.getSolde()))
                dto.setSolde(item.getSolde());
            if(StringUtil.isNotEmpty(item.getCode()))
                dto.setCode(item.getCode());
            if(item.getActif()!=null)
                dto.setActif(item.getActif());
            if(item.getDateCreation()!=null)
                dto.setDateCreation(DateUtil.dateTimeToString(item.getDateCreation()));
            if(item.getDateDebut()!=null)
                dto.setDateDebut(DateUtil.dateTimeToString(item.getDateDebut()));
            if(item.getDateFin()!=null)
                dto.setDateFin(DateUtil.dateTimeToString(item.getDateFin()));
            if(StringUtil.isNotEmpty(item.getLoyer()))
                dto.setLoyer(item.getLoyer());
            if(StringUtil.isNotEmpty(item.getCaution()))
                dto.setCaution(item.getCaution());
            if(StringUtil.isNotEmpty(item.getReference()))
                dto.setReference(item.getReference());
            if(this.locataire && item.getLocataire()!=null) {
                dto.setLocataire(locataireConverter.toDto(item.getLocataire())) ;

            }
            if(this.local && item.getLocal()!=null) {
                localConverter.initList(false);
                dto.setLocal(localConverter.toDto(item.getLocal())) ;

            }


        return dto;
        }
    }

    public void init(boolean value) {
        initObject(value);
    }

    public void initObject(boolean value) {
        this.locataire = value;
        this.local = value;
    }
	
    public List<Location> toItem(List<LocationDto> dtos) {
        List<Location> items = new ArrayList<>();
        if (dtos != null && !dtos.isEmpty()) {
            for (LocationDto dto : dtos) {
                items.add(toItem(dto));
            }
        }
        return items;
    }


    public List<LocationDto> toDto(List<Location> items) {
        List<LocationDto> dtos = new ArrayList<>();
        if (items != null && !items.isEmpty()) {
            for (Location item : items) {
                dtos.add(toDto(item));
            }
        }
        return dtos;
    }


    public void copy(LocationDto dto, Location t) {
		BeanUtils.copyProperties(dto, t, AbstractConverterHelper.getNullPropertyNames(dto));
        if(t.getLocataire() == null  && dto.getLocataire() != null){
            t.setLocataire(new Locataire());
        }else if (t.getLocataire() != null  && dto.getLocataire() != null){
            t.setLocataire(null);
            t.setLocataire(new Locataire());
        }
        if(t.getLocal() == null  && dto.getLocal() != null){
            t.setLocal(new Local());
        }else if (t.getLocal() != null  && dto.getLocal() != null){
            t.setLocal(null);
            t.setLocal(new Local());
        }
        if(dto.getDateDebut() != null)
            t.setDateDebut(DateUtil.stringEnToDate(dto.getDateDebut()));
        if(dto.getDateFin() != null)
            t.setDateFin(DateUtil.stringEnToDate(dto.getDateFin()));
        if (dto.getLocataire() != null)
         locataireConverter.copy(dto.getLocataire(), t.getLocataire());
        if (dto.getLocal() != null)
            localConverter.copy(dto.getLocal(), t.getLocal());
    }

    public List<Location> copy(List<LocationDto> dtos) {
        List<Location> result = new ArrayList<>();
        if (dtos != null) {
            for (LocationDto dto : dtos) {
                Location instance = new Location();
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
    public LocataireConverter getLocataireConverter(){
        return this.locataireConverter;
    }
    public void setLocataireConverter(LocataireConverter locataireConverter ){
        this.locataireConverter = locataireConverter;
    }
    public boolean  isLocataire(){
        return this.locataire;
    }
    public void  setLocataire(boolean locataire){
        this.locataire = locataire;
    }
    public boolean  isLocal(){
        return this.local;
    }
    public void  setLocal(boolean local){
        this.local = local;
    }
}
