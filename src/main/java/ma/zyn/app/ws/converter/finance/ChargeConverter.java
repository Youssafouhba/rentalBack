package  ma.zyn.app.ws.converter.finance;

import ma.zyn.app.bean.core.finance.TypeCharge;
import ma.zyn.app.bean.core.locaux.Local;
import ma.zyn.app.ws.converter.locaux.LocalConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.beans.BeanUtils;
import ma.zyn.app.zynerator.converter.AbstractConverterHelper;

import java.util.ArrayList;
import java.util.List;


import ma.zyn.app.zynerator.util.StringUtil;
import ma.zyn.app.zynerator.util.DateUtil;
import ma.zyn.app.bean.core.finance.Charge;
import ma.zyn.app.ws.dto.finance.ChargeDto;

@Component
public class ChargeConverter {

    @Autowired
    private LocalConverter localConverter ;
    @Autowired
    private TypeChargeConverter typeChargeConverter ;
    private boolean typeCharge;
    private boolean compteCharge;
    private boolean local;

    @Autowired
    private CompteChargeConverter compteChargeConverter;
    @Autowired
    private CompteConverter compteConverter;
    @Autowired
    private ModePaiementConverter modePaiementConverter;

    public  ChargeConverter() {
        initObject(true);
    }

    public Charge toItem(ChargeDto dto) {
        if (dto == null) {
            return null;
        } else {
            Charge item = new Charge();
            if(StringUtil.isNotEmpty(dto.getId()))
                item.setId(dto.getId());
            if(StringUtil.isNotEmpty(dto.getCode()))
                item.setCode(dto.getCode());
            if(StringUtil.isNotEmpty(dto.getLabel()))
                item.setLabel(dto.getLabel());
            if(StringUtil.isNotEmpty(dto.getMontant()))
                item.setMontant(dto.getMontant());
            if(StringUtil.isNotEmpty(dto.getDate()))
                item.setDate(DateUtil.stringEnToDate(dto.getDate()));
            if(dto.getIsPaid() != null)
                item.setIsPaid(dto.getIsPaid());
            if(dto.getModePaiement()!=null)
                item.setModePaiement(modePaiementConverter.toItem(dto.getModePaiement())) ;
            if(StringUtil.isNotEmpty(dto.getDescription()))
                item.setDescription(dto.getDescription());
            if(dto.getCompteCharge()!=null){
                item.setCompteCharge(compteChargeConverter.toItem(dto.getCompteCharge()));
            }
            if(dto.getCompteSource()!=null){
                item.setCompteSource(compteConverter.toItem(dto.getCompteSource()));
            }
            if(this.typeCharge && dto.getTypeCharge()!=null)
                item.setTypeCharge(typeChargeConverter.toItem(dto.getTypeCharge())) ;

            if(dto.getLocal() != null && dto.getLocal().getId() != null){
                item.setLocal(new Local());
                item.getLocal().setId(dto.getLocal().getId());
                item.getLocal().setCode(dto.getLocal().getCode());
            }




            return item;
        }
    }


    public ChargeDto toDto(Charge item) {
        if (item == null) {
            return null;
        } else {
            ChargeDto dto = new ChargeDto();
            if(StringUtil.isNotEmpty(item.getId()))
                dto.setId(item.getId());
            if (item.getModePaiement() != null)
                dto.setModePaiement(modePaiementConverter.toDto(item.getModePaiement()));
            if(StringUtil.isNotEmpty(item.getCode()))
                dto.setCode(item.getCode());
            if(StringUtil.isNotEmpty(item.getLabel()))
                dto.setLabel(item.getLabel());
            if(StringUtil.isNotEmpty(item.getMontant()))
                dto.setMontant(item.getMontant());
            if(item.getDate()!=null)
                dto.setDate(DateUtil.dateTimeToString(item.getDate()));
            dto.setIsPaid(item.getIsPaid());
            if(StringUtil.isNotEmpty(item.getDescription()))
                dto.setDescription(item.getDescription());
            if (this.compteCharge && item.getCompteCharge()!=null) {
                compteChargeConverter.init(false);
                compteChargeConverter.setCharges(false);
                dto.setCompteCharge(compteChargeConverter.toDto(item.getCompteCharge()));
            }
            if(item.getCompteSource()!=null)
                dto.setCompteSource(compteConverter.toDto(item.getCompteSource()));
            if(this.typeCharge && item.getTypeCharge()!=null) {
                dto.setTypeCharge(typeChargeConverter.toDto(item.getTypeCharge())) ;

            }
            if(this.local && item.getLocal()!=null) {
                dto.setLocal(localConverter.toDto(item.getLocal())) ;

            }


            return dto;
        }
    }

    public void init(boolean value) {
        initObject(value);
    }

    public void initObject(boolean value) {
        this.compteCharge = value;
        this.typeCharge = value;
        this.local = value;
    }

    public List<Charge> toItem(List<ChargeDto> dtos) {
        List<Charge> items = new ArrayList<>();
        if (dtos != null && !dtos.isEmpty()) {
            for (ChargeDto dto : dtos) {
                items.add(toItem(dto));
            }
        }
        return items;
    }


    public List<ChargeDto> toDto(List<Charge> items) {
        List<ChargeDto> dtos = new ArrayList<>();
        if (items != null && !items.isEmpty()) {
            for (Charge item : items) {
                dtos.add(toDto(item));
            }
        }
        return dtos;
    }


    public void copy(ChargeDto dto, Charge t) {
        BeanUtils.copyProperties(dto, t, AbstractConverterHelper.getNullPropertyNames(dto));
        if(t.getTypeCharge() == null  && dto.getTypeCharge() != null){
            t.setTypeCharge(new TypeCharge());
        }else if (t.getTypeCharge() != null  && dto.getTypeCharge() != null){
            t.setTypeCharge(null);
            t.setTypeCharge(new TypeCharge());
        }
        if(t.getLocal() == null  && dto.getLocal() != null){
            t.setLocal(new Local());
        }else if (t.getLocal() != null  && dto.getLocal() != null){
            t.setLocal(null);
            t.setLocal(new Local());
        }
        if (dto.getTypeCharge() != null)
            typeChargeConverter.copy(dto.getTypeCharge(), t.getTypeCharge());
        if (dto.getLocal() != null)
            localConverter.copy(dto.getLocal(), t.getLocal());
    }

    public List<Charge> copy(List<ChargeDto> dtos) {
        List<Charge> result = new ArrayList<>();
        if (dtos != null) {
            for (ChargeDto dto : dtos) {
                Charge instance = new Charge();
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
    public boolean  isTypeCharge(){
        return this.typeCharge;
    }
    public void  setTypeCharge(boolean typeCharge){
        this.typeCharge = typeCharge;
    }
    public boolean  isLocal(){
        return this.local;
    }
    public void  setLocal(boolean local){
        this.local = local;
    }
}
