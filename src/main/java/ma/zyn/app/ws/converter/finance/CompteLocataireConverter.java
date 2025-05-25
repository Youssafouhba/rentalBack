package ma.zyn.app.ws.converter.finance;

import ma.zyn.app.bean.core.finance.CompteLocataire;
import ma.zyn.app.bean.core.locataire.Locataire;
import ma.zyn.app.bean.core.locataire.Location;
import ma.zyn.app.ws.converter.finance.ModePaiementConverter;
import ma.zyn.app.ws.converter.finance.TypePaiementConverter;
import ma.zyn.app.ws.converter.locataire.LocataireConverter;
import ma.zyn.app.ws.converter.locataire.LocationConverter;
import ma.zyn.app.ws.converter.locataire.TransactionConverter;
import ma.zyn.app.ws.converter.locataire.TypeTransactiontConverter;
import ma.zyn.app.ws.dto.finance.CompteLocataireDto;
import ma.zyn.app.zynerator.converter.AbstractConverterHelper;
import ma.zyn.app.zynerator.util.ListUtil;
import ma.zyn.app.zynerator.util.StringUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CompteLocataireConverter {

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
    private TypeTransactiontConverter typeTransactiontConverter ;
    private boolean locataire;
    private boolean transactions;
    private boolean location;
    @Autowired
    private LocationConverter locationConverter;

    public  CompteLocataireConverter() {
        init(true);
    }

    public CompteLocataire toItem(CompteLocataireDto dto) {
        if (dto == null) {
            return null;
        } else {
            CompteLocataire item = new CompteLocataire();
            if(StringUtil.isNotEmpty(dto.getId()))
                item.setId(dto.getId());
            if(StringUtil.isNotEmpty(dto.getSoldeInitial()))
                item.setSoldeInitial(dto.getSoldeInitial());
            if(StringUtil.isNotEmpty(dto.getSolde()))
                item.setSolde(dto.getSolde());
            if(StringUtil.isNotEmpty(dto.getDebit()))
                item.setDebit(dto.getDebit());
            if(StringUtil.isNotEmpty(dto.getCredit()))
                item.setCredit(dto.getCredit());
            if(dto.getLocataire() != null && dto.getLocataire().getId() != null){
                item.setLocataire(new Locataire());
                item.getLocataire().setId(dto.getLocataire().getId());
                item.getLocataire().setCode(dto.getLocataire().getCode());
            }

            if(this.transactions && ListUtil.isNotEmpty(dto.getTransactions()))
                item.setTransactions(transactionConverter.toItem(dto.getTransactions()));


            return item;
        }
    }


    public CompteLocataireDto toDto(CompteLocataire item) {
        if (item == null) {
            return null;
        } else {
            CompteLocataireDto dto = new CompteLocataireDto();
            if(StringUtil.isNotEmpty(item.getId()))
                dto.setId(item.getId());
            if(StringUtil.isNotEmpty(item.getSolde()))
                dto.setSolde(item.getSolde());
            if(StringUtil.isNotEmpty(item.getDebit()))
                dto.setDebit(item.getDebit());
            if(StringUtil.isNotEmpty(item.getSoldeInitial()))
                dto.setSoldeInitial(item.getSoldeInitial());
            if(StringUtil.isNotEmpty(item.getCredit()))
                dto.setCredit(item.getCredit());

            if(this.locataire && item.getLocataire()!=null) {
                locataireConverter.initObject(false);
                locataireConverter.init(false);
                dto.setLocataire(locataireConverter.toDto(item.getLocataire())) ;
                locataireConverter.setCompteLocataire(true);
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
    }

    public List<CompteLocataire> toItem(List<CompteLocataireDto> dtos) {
        List<CompteLocataire> items = new ArrayList<>();
        if (dtos != null && !dtos.isEmpty()) {
            for (CompteLocataireDto dto : dtos) {
                items.add(toItem(dto));
            }
        }
        return items;
    }


    public List<CompteLocataireDto> toDto(List<CompteLocataire> items) {
        List<CompteLocataireDto> dtos = new ArrayList<>();
        if (items != null && !items.isEmpty()) {
            for (CompteLocataire item : items) {
                dtos.add(toDto(item));
            }
        }
        return dtos;
    }


    public void copy(CompteLocataireDto dto, CompteLocataire t) {
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

    public List<CompteLocataire> copy(List<CompteLocataireDto> dtos) {
        List<CompteLocataire> result = new ArrayList<>();
        if (dtos != null) {
            for (CompteLocataireDto dto : dtos) {
                CompteLocataire instance = new CompteLocataire();
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
