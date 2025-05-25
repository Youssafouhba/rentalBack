package ma.zyn.app.ws.converter.finance;

import ma.zyn.app.bean.core.finance.CompteInstantanee;
import ma.zyn.app.bean.core.finance.CompteInstantanee;
import ma.zyn.app.bean.core.locataire.Locataire;
import ma.zyn.app.bean.core.locataire.Location;
import ma.zyn.app.bean.core.locaux.Local;
import ma.zyn.app.ws.converter.locataire.LocataireConverter;
import ma.zyn.app.ws.converter.locataire.LocationConverter;
import ma.zyn.app.ws.converter.locataire.TransactionConverter;
import ma.zyn.app.ws.converter.locataire.TypeTransactiontConverter;
import ma.zyn.app.ws.converter.locaux.LocalConverter;
import ma.zyn.app.ws.dto.finance.CompteInstantaneeDto;
import ma.zyn.app.ws.dto.finance.CompteInstantaneeDto;
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
public class CompteInstantaneeConverter {

    @Autowired
    private TransactionConverter transactionConverter ;
    @Autowired
    private TypePaiementConverter typePaiementConverter ;
    @Autowired
    private CompteConverter compteConverter ;
    @Autowired
    private ModePaiementConverter modePaiementConverter ;
    @Autowired
    private LocataireConverter locataireConverter ;
    @Autowired
    private LocalConverter localConverter;
    @Autowired
    private TypeTransactiontConverter typeTransactiontConverter ;
    private boolean locataire;
    private boolean transactions;
    private boolean location;
    private boolean locale;

    public LocalConverter getLocalConverter() {
        return localConverter;
    }

    public void setLocalConverter(LocalConverter localConverter) {
        this.localConverter = localConverter;
    }

    public boolean isLocation() {
        return location;
    }

    public void setLocation(boolean location) {
        this.location = location;
    }

    public boolean isLocale() {
        return locale;
    }

    public void setLocale(boolean locale) {
        this.locale = locale;
    }

    public LocationConverter getLocationConverter() {
        return locationConverter;
    }

    public void setLocationConverter(LocationConverter locationConverter) {
        this.locationConverter = locationConverter;
    }

    @Autowired
    private LocationConverter locationConverter;

    public CompteInstantaneeConverter() {
        init(true);
    }

    public CompteInstantanee toItem(CompteInstantaneeDto dto) {
        if (dto == null) {
            return null;
        } else {
        CompteInstantanee item = new CompteInstantanee();
            if(StringUtil.isNotEmpty(dto.getId()))
                item.setId(dto.getId());
            if(StringUtil.isNotEmpty(dto.getSoldeInitial()))
                item.setSoldeInitial(dto.getSoldeInitial());
            if(StringUtil.isNotEmpty(dto.getSolde()))
                item.setSolde(dto.getSolde());
            if(StringUtil.isNotEmpty(dto.getDebit()))
                item.setDebit(dto.getDebit());
            if(StringUtil.isNotEmpty(dto.getNom()))
                item.setNom(dto.getNom());
            if(StringUtil.isNotEmpty(dto.getDescription()))
                item.setDescription(dto.getDescription());
            if(StringUtil.isNotEmpty(dto.getCredit()))
                item.setCredit(dto.getCredit());
            if(StringUtil.isNotEmpty(dto.getCode()))
                item.setCode(dto.getCode());
            if(StringUtil.isNotEmpty(dto.getDateCreation()))
                item.setDateCreation(DateUtil.stringEnToDate(dto.getDateCreation()));
            if(dto.getLocale() != null && dto.getLocale().getId() != null) {
                item.setLocale(localConverter.toItem(dto.getLocale()));
            }
            if(dto.getLocataire() != null && dto.getLocataire().getId() != null){
                item.setLocataire(new Locataire());
                item.getLocataire().setId(dto.getLocataire().getId());
                item.getLocataire().setCode(dto.getLocataire().getCode());
            }
            if(dto.getLocation() != null && dto.getLocation().getId() != null){
                item.setLocation(new Location());
                item.getLocation().setId(dto.getLocation().getId());
                item.getLocation().setCode(dto.getLocation().getCode());
            }
            if(this.transactions && ListUtil.isNotEmpty(dto.getTransactions()))
                item.setTransactions(transactionConverter.toItem(dto.getTransactions()));


        return item;
        }
    }


    public CompteInstantaneeDto toDto(CompteInstantanee item) {
        if (item == null) {
            return null;
        } else {
            CompteInstantaneeDto dto = new CompteInstantaneeDto();
            if(StringUtil.isNotEmpty(item.getId()))
                dto.setId(item.getId());
            if(StringUtil.isNotEmpty(item.getSolde()))
                dto.setSolde(item.getSolde());
            if(StringUtil.isNotEmpty(item.getNom()))
                dto.setNom(item.getNom());
            if (StringUtil.isNotEmpty(item.getCode()))
                dto.setCode(item.getCode());
            if(item.getDateCreation()!=null)
                dto.setDateCreation(DateUtil.dateTimeToString(item.getDateCreation()));
            if(StringUtil.isNotEmpty(item.getDebit()))
                dto.setDebit(item.getDebit());
            if(StringUtil.isNotEmpty(item.getSoldeInitial()))
                dto.setSoldeInitial(item.getSoldeInitial());
            if (StringUtil.isNotEmpty(item.getDescription()))
                dto.setDescription(item.getDescription());
            if(StringUtil.isNotEmpty(item.getCredit()))
                dto.setCredit(item.getCredit());
            if(this.locale && item.getLocale()!=null) {
                localConverter.init(false);
                dto.setLocale(localConverter.toDto(item.getLocale()));
            }
            if(this.location && item.getLocation()!=null) {
                locationConverter.init(false);
                locationConverter.setLocal(true);
                dto.setLocation(locationConverter.toDto(item.getLocation()));
            }
            if(this.locataire && item.getLocataire()!=null) {
                locataireConverter.initObject(false);
                locataireConverter.init(false);
                dto.setLocataire(locataireConverter.toDto(item.getLocataire())) ;
                locataireConverter.init(true);
            }
        if(this.transactions && ListUtil.isNotEmpty(item.getTransactions())){
            transactionConverter.init(true);
            dto.setTransactions(transactionConverter.toDto(item.getTransactions()));
        }


        return dto;
        }
    }

    public void init(boolean value) {
        initList(value);
    }

    public void initList(boolean value) {
        this.transactions = value;

    }
    public void initObject(boolean value) {
        this.locataire = value;
        this.location = value;
        this.locale = value;
    }
	
    public List<CompteInstantanee> toItem(List<CompteInstantaneeDto> dtos) {
        List<CompteInstantanee> items = new ArrayList<>();
        if (dtos != null && !dtos.isEmpty()) {
            for (CompteInstantaneeDto dto : dtos) {
                items.add(toItem(dto));
            }
        }
        return items;
    }


    public List<CompteInstantaneeDto> toDto(List<CompteInstantanee> items) {
        List<CompteInstantaneeDto> dtos = new ArrayList<>();
        if (items != null && !items.isEmpty()) {
            for (CompteInstantanee item : items) {
                dtos.add(toDto(item));
            }
        }
        return dtos;
    }


    public void copy(CompteInstantaneeDto dto, CompteInstantanee t) {
		BeanUtils.copyProperties(dto, t, AbstractConverterHelper.getNullPropertyNames(dto));
        if(t.getLocataire() == null  && dto.getLocataire() != null){
            t.setLocataire(new Locataire());
        }else if (t.getLocataire() != null  && dto.getLocataire() != null){
            t.setLocataire(null);
            t.setLocataire(new Locataire());
        }
        if (dto.getLocataire() != null)
        locataireConverter.copy(dto.getLocataire(), t.getLocataire());
        if (dto.getTransactions() != null)
            t.setTransactions(transactionConverter.copy(dto.getTransactions()));
    }

    public List<CompteInstantanee> copy(List<CompteInstantaneeDto> dtos) {
        List<CompteInstantanee> result = new ArrayList<>();
        if (dtos != null) {
            for (CompteInstantaneeDto dto : dtos) {
                CompteInstantanee instance = new CompteInstantanee();
                copy(dto, instance);
                result.add(instance);
            }
        }
        return result.isEmpty() ? null : result;
    }


    public TransactionConverter getTransactionConverter(){
        return this.transactionConverter;
    }
    public void setTransactionConverter(TransactionConverter transactionConverter ){
        this.transactionConverter = transactionConverter;
    }
    public TypePaiementConverter getTypePaiementConverter(){
        return this.typePaiementConverter;
    }
    public void setTypePaiementConverter(TypePaiementConverter typePaiementConverter ){
        this.typePaiementConverter = typePaiementConverter;
    }
    public CompteConverter getCompteConverter(){
        return this.compteConverter;
    }
    public void setCompteConverter(CompteConverter compteConverter ){
        this.compteConverter = compteConverter;
    }
    public ModePaiementConverter getModePaiementConverter(){
        return this.modePaiementConverter;
    }
    public void setModePaiementConverter(ModePaiementConverter modePaiementConverter ){
        this.modePaiementConverter = modePaiementConverter;
    }
    public LocataireConverter getLocataireConverter(){
        return this.locataireConverter;
    }
    public void setLocataireConverter(LocataireConverter locataireConverter ){
        this.locataireConverter = locataireConverter;
    }
    public TypeTransactiontConverter getTypeTransactiontConverter(){
        return this.typeTransactiontConverter;
    }
    public void setTypeTransactiontConverter(TypeTransactiontConverter typeTransactiontConverter ){
        this.typeTransactiontConverter = typeTransactiontConverter;
    }
    public boolean  isLocataire(){
        return this.locataire;
    }
    public void  setLocataire(boolean locataire){
        this.locataire = locataire;
    }
    public boolean  isTransactions(){
        return this.transactions ;
    }
    public void  setTransactions(boolean transactions ){
        this.transactions  = transactions ;
    }
}
