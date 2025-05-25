package ma.zyn.app.ws.converter.finance;

import ma.zyn.app.bean.core.finance.CompteAdmin;
import ma.zyn.app.ws.converter.locataire.TransactionConverter;
import ma.zyn.app.ws.dto.finance.CompteAdminDto;
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
public class CompteAdminConverter {

    @Autowired
    private TransactionConverter transactionConverter ;
    @Autowired
    private CompteConverter compteConverter ;
    @Autowired
    private BanqueConverter banqueConverter ;
    private boolean comptes;

    public  CompteAdminConverter() {
        initList(true);
    }

    public CompteAdmin toItem(CompteAdminDto dto) {
        if (dto == null) {
            return null;
        } else {
        CompteAdmin item = new CompteAdmin();
            if(StringUtil.isNotEmpty(dto.getId()))
                item.setId(dto.getId());
            if(StringUtil.isNotEmpty(dto.getSolde()))
                item.setSolde(dto.getSolde());
            if(StringUtil.isNotEmpty(dto.getDateCreation()))
                item.setDateCreation(DateUtil.stringEnToDate(dto.getDateCreation()));
            if(StringUtil.isNotEmpty(dto.getCode()))
                item.setCode(dto.getCode());
            if(this.comptes && ListUtil.isNotEmpty(dto.getComptes()))
                item.setComptes(compteConverter.toItem(dto.getComptes()));


        return item;
        }
    }


    public CompteAdminDto toDto(CompteAdmin item) {
        if (item == null) {
            return null;
        } else {
            CompteAdminDto dto = new CompteAdminDto();
            if(StringUtil.isNotEmpty(item.getId()))
                dto.setId(item.getId());
            if(StringUtil.isNotEmpty(item.getSolde()))
                dto.setSolde(item.getSolde());
            if(StringUtil.isNotEmpty(item.getCode()))
                dto.setCode(item.getCode());
            if(item.getDateCreation()!=null)
                dto.setDateCreation(DateUtil.dateTimeToString(item.getDateCreation()));
        if(this.comptes && ListUtil.isNotEmpty(item.getComptes())){
            compteConverter.init(true);
            dto.setComptes(compteConverter.toDto(item.getComptes()));
        }


        return dto;
        }
    }

    public void init(boolean value) {
        initList(value);
    }

    public void initList(boolean value) {
        this.comptes = value;
    }
	
    public List<CompteAdmin> toItem(List<CompteAdminDto> dtos) {
        List<CompteAdmin> items = new ArrayList<>();
        if (dtos != null && !dtos.isEmpty()) {
            for (CompteAdminDto dto : dtos) {
                items.add(toItem(dto));
            }
        }
        return items;
    }


    public List<CompteAdminDto> toDto(List<CompteAdmin> items) {
        List<CompteAdminDto> dtos = new ArrayList<>();
        if (items != null && !items.isEmpty()) {
            for (CompteAdmin item : items) {
                dtos.add(toDto(item));
            }
        }
        return dtos;
    }


    public void copy(CompteAdminDto dto, CompteAdmin t) {
		BeanUtils.copyProperties(dto, t, AbstractConverterHelper.getNullPropertyNames(dto));
        if (dto.getComptes() != null)
            t.setComptes(compteConverter.copy(dto.getComptes()));
    }

    public List<CompteAdmin> copy(List<CompteAdminDto> dtos) {
        List<CompteAdmin> result = new ArrayList<>();
        if (dtos != null) {
            for (CompteAdminDto dto : dtos) {
                CompteAdmin instance = new CompteAdmin();
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
    public CompteConverter getCompteConverter(){
        return this.compteConverter;
    }
    public void setCompteConverter(CompteConverter compteConverter ){
        this.compteConverter = compteConverter;
    }
    public BanqueConverter getBanqueConverter(){
        return this.banqueConverter;
    }
    public void setBanqueConverter(BanqueConverter banqueConverter ){
        this.banqueConverter = banqueConverter;
    }
    public boolean  isComptes(){
        return this.comptes ;
    }
    public void  setComptes(boolean comptes ){
        this.comptes  = comptes ;
    }
}
