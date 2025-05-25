package ma.zyn.app.ws.converter.finance;

import ma.zyn.app.bean.core.finance.CompteCharge;
import ma.zyn.app.ws.converter.locaux.LocalConverter;
import ma.zyn.app.ws.dto.finance.CompteChargeDto;
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
public class CompteChargeConverter {

    @Autowired
    private LocalConverter localConverter ;
    @Autowired
    private TypeChargeConverter typeChargeConverter ;
    @Autowired
    private ChargeConverter chargeConverter ;
    private boolean charges;
    private boolean typeCharges;
    private boolean local;

    public  CompteChargeConverter() {
        initList(true);
    }

    public CompteCharge toItem(CompteChargeDto dto) {
        if (dto == null) {
            return null;
        } else {
        CompteCharge item = new CompteCharge();
            if(StringUtil.isNotEmpty(dto.getId()))
                item.setId(dto.getId());
            if(StringUtil.isNotEmpty(dto.getCode()))
                item.setCode(dto.getCode());
            if(StringUtil.isNotEmpty(dto.getLabel()))
                item.setLabel(dto.getLabel());
            if(StringUtil.isNotEmpty(dto.getNom()))
                item.setNom(dto.getNom());
            if(StringUtil.isNotEmpty(dto.getSolde()))
                item.setSolde(dto.getSolde());
            if(StringUtil.isNotEmpty(dto.getDescription()))
                item.setDescription(dto.getDescription());
            if (StringUtil.isNotEmpty(dto.getDateCreation()))
                item.setDateCreation(DateUtil.stringEnToDate(dto.getDateCreation()));
            if(dto.getLocal() != null)
                item.setLocal(localConverter.toItem(dto.getLocal()));
            if(this.typeCharges && ListUtil.isNotEmpty(dto.getTypeCharges().stream().toList()))
                item.setTypeCharges(typeChargeConverter.toItem(dto.getTypeCharges())) ;
            if(this.charges && ListUtil.isNotEmpty(dto.getCharges()))
                item.setCharges(chargeConverter.toItem(dto.getCharges()));


        return item;
        }
    }


    public CompteChargeDto toDto(CompteCharge item) {
        if (item == null) {
            return null;
        } else {
            CompteChargeDto dto = new CompteChargeDto();
            if(StringUtil.isNotEmpty(item.getId()))
                dto.setId(item.getId());
            if(StringUtil.isNotEmpty(item.getCode()))
                dto.setCode(item.getCode());
            if(StringUtil.isNotEmpty(item.getLabel()))
                dto.setLabel(item.getLabel());
            if(StringUtil.isNotEmpty(item.getNom()))
                dto.setNom(item.getNom());
            if(StringUtil.isNotEmpty(item.getSolde()))
                dto.setSolde(item.getSolde());
            if(item.getDateCreation()!=null)
                dto.setDateCreation(DateUtil.dateTimeToString(item.getDateCreation()));
            if(item.getLocal()!=null) {
                dto.setLocal(localConverter.toDto(item.getLocal())) ;
            }
            if(this.typeCharges &&ListUtil.isNotEmpty(item.getTypeCharges().stream().toList())) {
                dto.setTypeCharges(typeChargeConverter.toDto(item.getTypeCharges())) ;
            }
            if(StringUtil.isNotEmpty(item.getDescription()))
                dto.setDescription(item.getDescription());
        if(this.charges && ListUtil.isNotEmpty(item.getCharges())){
            chargeConverter.init(true);
            dto.setCharges(chargeConverter.toDto(item.getCharges()));
        }


        return dto;
        }
    }

    public void init(boolean value) {
        this.local = value;
        initList(value);
    }


    public void initList(boolean value) {
        this.charges = value;
        this.typeCharges = value;

    }
	
    public List<CompteCharge> toItem(List<CompteChargeDto> dtos) {
        List<CompteCharge> items = new ArrayList<>();
        if (dtos != null && !dtos.isEmpty()) {
            for (CompteChargeDto dto : dtos) {
                items.add(toItem(dto));
            }
        }
        return items;
    }


    public List<CompteChargeDto> toDto(List<CompteCharge> items) {
        List<CompteChargeDto> dtos = new ArrayList<>();
        if (items != null && !items.isEmpty()) {
            for (CompteCharge item : items) {
                dtos.add(toDto(item));
            }
        }
        return dtos;
    }


    public void copy(CompteChargeDto dto, CompteCharge t) {
		BeanUtils.copyProperties(dto, t, AbstractConverterHelper.getNullPropertyNames(dto));
        if (dto.getCharges() != null)
            t.setCharges(chargeConverter.copy(dto.getCharges()));
    }

    public List<CompteCharge> copy(List<CompteChargeDto> dtos) {
        List<CompteCharge> result = new ArrayList<>();
        if (dtos != null) {
            for (CompteChargeDto dto : dtos) {
                CompteCharge instance = new CompteCharge();
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
    public TypeChargeConverter getTypeChargeConverter(){
        return this.typeChargeConverter;
    }
    public void setTypeChargeConverter(TypeChargeConverter typeChargeConverter ){
        this.typeChargeConverter = typeChargeConverter;
    }
    public ChargeConverter getChargeConverter(){
        return this.chargeConverter;
    }
    public void setChargeConverter(ChargeConverter chargeConverter ){
        this.chargeConverter = chargeConverter;
    }
    public boolean  isCharges(){
        return this.charges ;
    }
    public void  setCharges(boolean charges ){
        this.charges  = charges ;
    }
}
