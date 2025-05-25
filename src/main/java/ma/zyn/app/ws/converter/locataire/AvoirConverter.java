package ma.zyn.app.ws.converter.locataire;

import ma.zyn.app.bean.core.locataire.Avoir;
import ma.zyn.app.bean.core.locataire.Location;
import ma.zyn.app.ws.converter.finance.BanqueConverter;
import ma.zyn.app.ws.converter.finance.CaisseConverter;
import ma.zyn.app.ws.converter.finance.ModePaiementConverter;
import ma.zyn.app.ws.dto.locataire.AvoirDto;
import ma.zyn.app.zynerator.converter.AbstractConverterHelper;
import ma.zyn.app.zynerator.util.DateUtil;
import ma.zyn.app.zynerator.util.StringUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AvoirConverter {

    @Autowired
    private LocationConverter locationConverter ;
    private boolean location;
    private boolean locataire;
    private boolean banque;
    private boolean caisse;
    @Autowired
    private ModePaiementConverter modePaiementConverter;

    public boolean isLocataire() {
        return locataire;
    }

    public void setLocataire(boolean locataire) {
        this.locataire = locataire;
    }

    public boolean isBanque() {
        return banque;
    }

    public void setBanque(boolean banque) {
        this.banque = banque;
    }

    public boolean isCaisse() {
        return caisse;
    }

    public void setCaisse(boolean caisse) {
        this.caisse = caisse;
    }

    @Autowired
    private CaisseConverter caisseConverter;
    @Autowired
    private LocataireConverter locataireConverter;
    @Autowired
    private BanqueConverter banqueConverter;

    public  AvoirConverter() {
        initObject(true);
    }

    public Avoir toItem(AvoirDto dto) {
        if (dto == null) {
            return null;
        } else {
            Avoir item = new Avoir();
            if(StringUtil.isNotEmpty(dto.getId()))
                item.setId(dto.getId());
            if(StringUtil.isNotEmpty(dto.getMontant()))
                item.setMontant(dto.getMontant());
            if(StringUtil.isNotEmpty(dto.getDate()))
                item.setDate(DateUtil.stringEnToDate(dto.getDate()));
            if(StringUtil.isNotEmpty(dto.getMotif()))
                item.setMotif(dto.getMotif());
            if(StringUtil.isNotEmpty(dto.getCode()))
                item.setCode(dto.getCode());
            if(StringUtil.isNotEmpty(dto.getCompteSource()))
                item.setCompteSource(dto.getCompteSource());
            if(dto.getModePaiement()!=null)
                item.setModePaiement(modePaiementConverter.toItem(dto.getModePaiement())) ;
            if(this.locataire && dto.getLocataire()!=null)
                item.setLocataire(locataireConverter.toItem(dto.getLocataire())) ;
            if(this.banque && dto.getBanque()!=null)
                item.setBanque(banqueConverter.toItem(dto.getBanque())) ;
            if(this.caisse && dto.getCaisse()!=null)
                item.setCaisse(caisseConverter.toItem(dto.getCaisse())) ;
            if(this.location && dto.getLocation()!=null)
                item.setLocation(locationConverter.toItem(dto.getLocation())) ;
        return item;
        }
    }


    public AvoirDto toDto(Avoir item) {
        if (item == null) {
            return null;
        } else {
            AvoirDto dto = new AvoirDto();
            if(StringUtil.isNotEmpty(item.getId()))
                dto.setId(item.getId());
            if(StringUtil.isNotEmpty(item.getMontant()))
                dto.setMontant(item.getMontant());
            if(item.getDate()!=null)
                dto.setDate(DateUtil.dateTimeToString(item.getDate()));
            if(StringUtil.isNotEmpty(item.getMotif()))
                dto.setMotif(item.getMotif());
            if(StringUtil.isNotEmpty(item.getCompteSource()))
                dto.setCompteSource(item.getCompteSource());
            if(item.getModePaiement()!=null) {
                dto.setModePaiement(modePaiementConverter.toDto(item.getModePaiement())) ;
            }
            if(StringUtil.isNotEmpty(item.getCode()))
                dto.setCode(item.getCode());
            if(this.locataire && item.getLocataire()!=null) {
                locataireConverter.setAvoirs(false);
                dto.setLocataire(locataireConverter.toDto(item.getLocataire())) ;

            }
            if(this.banque && item.getBanque()!=null) {
                dto.setBanque(banqueConverter.toDto(item.getBanque())) ;

            }
            if(this.caisse && item.getCaisse()!=null) {
                dto.setCaisse(caisseConverter.toDto(item.getCaisse())) ;

            }
            if(this.location && item.getLocation()!=null) {
                dto.setLocation(locationConverter.toDto(item.getLocation())) ;

            }


        return dto;
        }
    }

    public void init(boolean value) {
        initObject(value);
    }

    public void initObject(boolean value) {
        this.location = value;
        this.banque = value;
        this.caisse = value;
        this.locataire = value;
    }
	
    public List<Avoir> toItem(List<AvoirDto> dtos) {
        List<Avoir> items = new ArrayList<>();
        if (dtos != null && !dtos.isEmpty()) {
            for (AvoirDto dto : dtos) {
                items.add(toItem(dto));
            }
        }
        return items;
    }


    public List<AvoirDto> toDto(List<Avoir> items) {
        List<AvoirDto> dtos = new ArrayList<>();
        if (items != null && !items.isEmpty()) {
            for (Avoir item : items) {
                dtos.add(toDto(item));
            }
        }
        return dtos;
    }


    public void copy(AvoirDto dto, Avoir t) {
		BeanUtils.copyProperties(dto, t, AbstractConverterHelper.getNullPropertyNames(dto));
        if(t.getLocation() == null  && dto.getLocation() != null){
            t.setLocation(new Location());
        }else if (t.getLocation() != null  && dto.getLocation() != null){
            t.setLocation(null);
            t.setLocation(new Location());
        }
        if (dto.getLocation() != null)
        locationConverter.copy(dto.getLocation(), t.getLocation());
    }

    public List<Avoir> copy(List<AvoirDto> dtos) {
        List<Avoir> result = new ArrayList<>();
        if (dtos != null) {
            for (AvoirDto dto : dtos) {
                Avoir instance = new Avoir();
                copy(dto, instance);
                result.add(instance);
            }
        }
        return result.isEmpty() ? null : result;
    }


    public LocationConverter getLocationConverter(){
        return this.locationConverter;
    }
    public void setLocationConverter(LocationConverter locationConverter ){
        this.locationConverter = locationConverter;
    }
    public boolean  isLocation(){
        return this.location;
    }
    public void  setLocation(boolean location){
        this.location = location;
    }
}
