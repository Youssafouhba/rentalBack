package ma.zyn.app.ws.converter.locataire;

import ma.zyn.app.bean.core.finance.Banque;
import ma.zyn.app.bean.core.locataire.Location;
import ma.zyn.app.bean.core.locataire.Reglement;
import ma.zyn.app.ws.converter.finance.BanqueConverter;
import ma.zyn.app.ws.converter.finance.CaisseConverter;
import ma.zyn.app.ws.converter.finance.CompteConverter;
import ma.zyn.app.ws.converter.finance.ModePaiementConverter;
import ma.zyn.app.ws.dto.locataire.ReglementDto;
import ma.zyn.app.zynerator.converter.AbstractConverterHelper;
import ma.zyn.app.zynerator.util.DateUtil;
import ma.zyn.app.zynerator.util.StringUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ReglementConverter {

    @Autowired
    private LocationConverter locationConverter ;
    private boolean location;
    @Autowired
    private BanqueConverter banqueConverter;
    @Autowired
    private CaisseConverter caisseConverter;
    @Autowired
    private ModePaiementConverter modePaiementConverter;
    @Autowired
    private CompteConverter compteConverter;

    public ReglementConverter() {
        initObject(true);
    }

    public Reglement toItem(ReglementDto dto) {
        if (dto == null) {
            return null;
        } else {
        Reglement item = new Reglement();
            if(StringUtil.isNotEmpty(dto.getId()))
                item.setId(dto.getId());
            if(StringUtil.isNotEmpty(dto.getMontant()))
                item.setMontant(dto.getMontant());
            if(StringUtil.isNotEmpty(dto.getDate()))
                item.setDate(DateUtil.stringEnToDate(dto.getDate()));
            if(StringUtil.isNotEmpty(dto.getMotif()))
                item.setMotif(dto.getMotif());
            if(dto.getCompteSource()!=null)
                item.setCompteSource(compteConverter.toItem(dto.getCompteSource())) ;
            if(dto.getCompteDestination()!=null)
                item.setCompteDestination(compteConverter.toItem(dto.getCompteDestination())) ;
            if(dto.getBanque()!=null)
                item.setBanque(banqueConverter.toItem(dto.getBanque())) ;
            if(StringUtil.isNotEmpty(dto.getCode()))
                item.setCode(dto.getCode());
            if(dto.getModePaiement()!=null)
                item.setModePaiement(modePaiementConverter.toItem(dto.getModePaiement())) ;
            if(dto.getCaisse()!=null)
                item.setCaisse(caisseConverter.toItem(dto.getCaisse())) ;
            if(this.location && dto.getLocation()!=null)
                item.setLocation(locationConverter.toItem(dto.getLocation())) ;


        return item;
        }
    }


    public ReglementDto toDto(Reglement item) {
        if (item == null) {
            return null;
        } else {
            ReglementDto dto = new ReglementDto();
            if(StringUtil.isNotEmpty(item.getId()))
                dto.setId(item.getId());
            if(StringUtil.isNotEmpty(item.getCode()))
                dto.setCode(item.getCode());
            if(StringUtil.isNotEmpty(item.getMontant()))
                dto.setMontant(item.getMontant());
            if(item.getDate()!=null)
                dto.setDate(DateUtil.dateTimeToString(item.getDate()));
            if(StringUtil.isNotEmpty(item.getMotif()))
                dto.setMotif(item.getMotif());
            if(item.getCompteSource()!=null) {
                dto.setCompteSource(compteConverter.toDto(item.getCompteSource())) ;
            }
            if(item.getCompteDestination()!=null) {
                dto.setCompteDestination(compteConverter.toDto(item.getCompteDestination())) ;
            }
            if( item.getModePaiement()!=null) {
                dto.setModePaiement(modePaiementConverter.toDto(item.getModePaiement())) ;
            }
            if(item.getBanque()!=null) {
                dto.setBanque(banqueConverter.toDto(item.getBanque())) ;
            }
            if(item.getCaisse()!=null) {
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
    }
	
    public List<Reglement> toItem(List<ReglementDto> dtos) {
        List<Reglement> items = new ArrayList<>();
        if (dtos != null && !dtos.isEmpty()) {
            for (ReglementDto dto : dtos) {
                items.add(toItem(dto));
            }
        }
        return items;
    }


    public List<ReglementDto> toDto(List<Reglement> items) {
        List<ReglementDto> dtos = new ArrayList<>();
        if (items != null && !items.isEmpty()) {
            for (Reglement item : items) {
                dtos.add(toDto(item));
            }
        }
        return dtos;
    }


    public void copy(ReglementDto dto, Reglement t) {
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

    public List<Reglement> copy(List<ReglementDto> dtos) {
        List<Reglement> result = new ArrayList<>();
        if (dtos != null) {
            for (ReglementDto dto : dtos) {
                Reglement instance = new Reglement();
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
