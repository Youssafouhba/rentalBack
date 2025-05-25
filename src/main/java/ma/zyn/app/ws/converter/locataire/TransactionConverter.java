package ma.zyn.app.ws.converter.locataire;

import ma.zyn.app.bean.core.finance.Compte;
import ma.zyn.app.bean.core.finance.ModePaiement;
import ma.zyn.app.bean.core.finance.TypePaiement;
import ma.zyn.app.bean.core.locataire.Transaction;
import ma.zyn.app.bean.core.locataire.TypeTransactiont;
import ma.zyn.app.ws.converter.finance.CompteConverter;
import ma.zyn.app.ws.converter.finance.CompteLocataireConverter;
import ma.zyn.app.ws.converter.finance.ModePaiementConverter;
import ma.zyn.app.ws.converter.finance.TypePaiementConverter;
import ma.zyn.app.ws.dto.locataire.TransactionDto;
import ma.zyn.app.zynerator.converter.AbstractConverterHelper;
import ma.zyn.app.zynerator.util.DateUtil;
import ma.zyn.app.zynerator.util.StringUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TransactionConverter {

    @Autowired
    private TypePaiementConverter typePaiementConverter ;
    @Autowired
    private CompteConverter compteConverter ;
    @Autowired
    private ModePaiementConverter modePaiementConverter ;
    @Autowired
    private TypeTransactiontConverter typeTransactiontConverter ;
    private boolean compteLocataire;
    private boolean typeTransaction;
    private boolean modePaiement;
    private boolean typePaiement;
    private boolean compteSource;
    private boolean compteDestination;
    @Autowired
    private CompteLocataireConverter compteLocataireConverter;

    public  TransactionConverter() {
        initObject(true);
    }

    public Transaction toItem(TransactionDto dto) {
        if (dto == null) {
            return null;
        } else {
            Transaction item = new Transaction();
            if(StringUtil.isNotEmpty(dto.getId()))
                item.setId(dto.getId());
            if(StringUtil.isNotEmpty(dto.getDate()))
                item.setDate(DateUtil.stringEnToDate(dto.getDate()));
            if(StringUtil.isNotEmpty(dto.getMontant()))
                item.setMontant(dto.getMontant());
            if(StringUtil.isNotEmpty(dto.getDescription()))
                item.setDescription(dto.getDescription());
            if(this.typeTransaction && dto.getTypeTransaction()!=null)
                item.setTypeTransaction(typeTransactiontConverter.toItem(dto.getTypeTransaction())) ;
            if(this.compteSource && dto.getCompteSource()!=null)
                item.setCompteSource(compteConverter.toItem(dto.getCompteSource())) ;
            if(this.modePaiement && dto.getModePaiement()!=null)
                item.setModePaiement(modePaiementConverter.toItem(dto.getModePaiement())) ;
            if(this.compteLocataire && dto.getCompteLocataire()!=null) {
                compteLocataireConverter.initList(false);
                item.setCompteLocataire(compteLocataireConverter.toItem(dto.getCompteLocataire()));
            }
            if(this.typePaiement && dto.getTypePaiement()!=null)
                item.setTypePaiement(typePaiementConverter.toItem(dto.getTypePaiement())) ;

            if(dto.getCompteSource() != null && dto.getCompteSource().getId() != null){
                item.setCompteSource(new Compte());
                item.getCompteSource().setId(dto.getCompteSource().getId());
                item.getCompteSource().setId(dto.getCompteSource().getId());
            }

            if(dto.getCompteDestination() != null && dto.getCompteDestination().getId() != null){
                item.setCompteDestination(new Compte());
                item.getCompteDestination().setId(dto.getCompteDestination().getId());
                item.getCompteDestination().setId(dto.getCompteDestination().getId());
            }




            return item;
        }
    }


    public TransactionDto toDto(Transaction item) {
        if (item == null) {
            return null;
        } else {
            TransactionDto dto = new TransactionDto();
            if(StringUtil.isNotEmpty(item.getId()))
                dto.setId(item.getId());
            if(item.getDate()!=null)
                dto.setDate(DateUtil.dateTimeToString(item.getDate()));
            if(StringUtil.isNotEmpty(item.getMontant()))
                dto.setMontant(item.getMontant());
            if(StringUtil.isNotEmpty(item.getDescription()))
                dto.setDescription(item.getDescription());
            if(this.compteLocataire && item.getCompteLocataire()!=null) {
                compteLocataireConverter.init(false);
                compteLocataireConverter.initObject(true);
                dto.setCompteLocataire(compteLocataireConverter.toDto(item.getCompteLocataire())) ;
            }
            if(this.typeTransaction && item.getTypeTransaction()!=null) {
                dto.setTypeTransaction(typeTransactiontConverter.toDto(item.getTypeTransaction())) ;

            }
            if(this.modePaiement && item.getModePaiement()!=null) {
                dto.setModePaiement(modePaiementConverter.toDto(item.getModePaiement())) ;

            }
            if(this.typePaiement && item.getTypePaiement()!=null) {
                dto.setTypePaiement(typePaiementConverter.toDto(item.getTypePaiement())) ;

            }
            if(this.compteSource && item.getCompteSource()!=null) {
                compteConverter.init(false);
                compteConverter.initObject(false);
                dto.setCompteSource(compteConverter.toDto(item.getCompteSource())) ;
            }

            if(this.compteDestination && item.getCompteDestination()!=null) {
                dto.setCompteDestination(compteConverter.toDto(item.getCompteDestination())) ;
            }


            return dto;
        }
    }

    public void init(boolean value) {
        initObject(value);
    }

    public void initObject(boolean value) {
        this.typeTransaction = value;
        this.modePaiement = value;
        this.typePaiement = value;
        this.compteSource = value;
        this.compteDestination = value;
        this.compteLocataire = value;
    }

    public List<Transaction> toItem(List<TransactionDto> dtos) {
        List<Transaction> items = new ArrayList<>();
        if (dtos != null && !dtos.isEmpty()) {
            for (TransactionDto dto : dtos) {
                items.add(toItem(dto));
            }
        }
        return items;
    }


    public List<TransactionDto> toDto(List<Transaction> items) {
        List<TransactionDto> dtos = new ArrayList<>();
        if (items != null && !items.isEmpty()) {
            for (Transaction item : items) {
                dtos.add(toDto(item));
            }
        }
        return dtos;
    }


    public void copy(TransactionDto dto, Transaction t) {
        BeanUtils.copyProperties(dto, t, AbstractConverterHelper.getNullPropertyNames(dto));
        if(t.getTypeTransaction() == null  && dto.getTypeTransaction() != null){
            t.setTypeTransaction(new TypeTransactiont());
        }else if (t.getTypeTransaction() != null  && dto.getTypeTransaction() != null){
            t.setTypeTransaction(null);
            t.setTypeTransaction(new TypeTransactiont());
        }
        if(t.getModePaiement() == null  && dto.getModePaiement() != null){
            t.setModePaiement(new ModePaiement());
        }else if (t.getModePaiement() != null  && dto.getModePaiement() != null){
            t.setModePaiement(null);
            t.setModePaiement(new ModePaiement());
        }
        if(t.getTypePaiement() == null  && dto.getTypePaiement() != null){
            t.setTypePaiement(new TypePaiement());
        }else if (t.getTypePaiement() != null  && dto.getTypePaiement() != null){
            t.setTypePaiement(null);
            t.setTypePaiement(new TypePaiement());
        }
        if(t.getCompteSource() == null  && dto.getCompteSource() != null){
            t.setCompteSource(new Compte());
        }else if (t.getCompteSource() != null  && dto.getCompteSource() != null){
            t.setCompteSource(null);
            t.setCompteSource(new Compte());
        }
        if(t.getCompteDestination() == null  && dto.getCompteDestination() != null){
            t.setCompteDestination(new Compte());
        }else if (t.getCompteDestination() != null  && dto.getCompteDestination() != null){
            t.setCompteDestination(null);
            t.setCompteDestination(new Compte());
        }
        if (dto.getTypeTransaction() != null)
            typeTransactiontConverter.copy(dto.getTypeTransaction(), t.getTypeTransaction());
        if (dto.getModePaiement() != null)
            modePaiementConverter.copy(dto.getModePaiement(), t.getModePaiement());
        if (dto.getTypePaiement() != null)
            typePaiementConverter.copy(dto.getTypePaiement(), t.getTypePaiement());
        if (dto.getCompteSource() != null)
            compteConverter.copy(dto.getCompteSource(), t.getCompteSource());
        if (dto.getCompteDestination() != null)
            compteConverter.copy(dto.getCompteDestination(), t.getCompteDestination());
    }

    public List<Transaction> copy(List<TransactionDto> dtos) {
        List<Transaction> result = new ArrayList<>();
        if (dtos != null) {
            for (TransactionDto dto : dtos) {
                Transaction instance = new Transaction();
                copy(dto, instance);
                result.add(instance);
            }
        }
        return result.isEmpty() ? null : result;
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
    public TypeTransactiontConverter getTypeTransactiontConverter(){
        return this.typeTransactiontConverter;
    }
    public void setTypeTransactiontConverter(TypeTransactiontConverter typeTransactiontConverter ){
        this.typeTransactiontConverter = typeTransactiontConverter;
    }
    public boolean  isTypeTransaction(){
        return this.typeTransaction;
    }
    public void  setTypeTransaction(boolean typeTransaction){
        this.typeTransaction = typeTransaction;
    }
    public boolean  isModePaiement(){
        return this.modePaiement;
    }
    public void  setModePaiement(boolean modePaiement){
        this.modePaiement = modePaiement;
    }
    public boolean  isTypePaiement(){
        return this.typePaiement;
    }
    public void  setTypePaiement(boolean typePaiement){
        this.typePaiement = typePaiement;
    }
    public boolean  isCompteSource(){
        return this.compteSource;
    }
    public void  setCompteSource(boolean compteSource){
        this.compteSource = compteSource;
    }
    public boolean  isCompteDestination(){
        return this.compteDestination;
    }
    public void  setCompteDestination(boolean compteDestination){
        this.compteDestination = compteDestination;
    }
}
