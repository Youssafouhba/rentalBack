package  ma.zyn.app.ws.converter.finance;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.beans.BeanUtils;
import ma.zyn.app.zynerator.converter.AbstractConverterHelper;

import java.util.ArrayList;
import java.util.List;




import ma.zyn.app.zynerator.util.StringUtil;
import ma.zyn.app.zynerator.converter.AbstractConverter;
import ma.zyn.app.zynerator.util.DateUtil;
import ma.zyn.app.bean.core.finance.Banque;
import ma.zyn.app.ws.dto.finance.BanqueDto;

@Component
public class BanqueConverter {



    public Banque toItem(BanqueDto dto) {
        if (dto == null) {
            return null;
        } else {
            Banque item = new Banque();
            if(StringUtil.isNotEmpty(dto.getId()))
                item.setId(dto.getId());
            if (StringUtil.isNotEmpty(dto.getDateCreation()))
                item.setDateCreation(DateUtil.stringEnToDate(dto.getDateCreation()));
            if(StringUtil.isNotEmpty(dto.getCode()))
                item.setCode(dto.getCode());
            if(StringUtil.isNotEmpty(dto.getLabel()))
                item.setLabel(dto.getLabel());
            if(StringUtil.isNotEmpty(dto.getNom()))
                item.setNom(dto.getNom());
            if(StringUtil.isNotEmpty(dto.getNumeroCompte()))
                item.setNumeroCompte(dto.getNumeroCompte());
            if(StringUtil.isNotEmpty(dto.getSolde()))
                item.setSolde(dto.getSolde());



            return item;
        }
    }


    public BanqueDto toDto(Banque item) {
        if (item == null) {
            return null;
        } else {
            BanqueDto dto = new BanqueDto();
            if(StringUtil.isNotEmpty(item.getId()))
                dto.setId(item.getId());
            if(StringUtil.isNotEmpty(item.getCode()))
                dto.setCode(item.getCode());
            if(item.getDateCreation()!=null)
                dto.setDateCreation(DateUtil.dateTimeToString(item.getDateCreation()));
            if(StringUtil.isNotEmpty(item.getLabel()))
                dto.setLabel(item.getLabel());
            if(StringUtil.isNotEmpty(item.getNom()))
                dto.setNom(item.getNom());
            if(StringUtil.isNotEmpty(item.getNumeroCompte()))
                dto.setNumeroCompte(item.getNumeroCompte());
            if(StringUtil.isNotEmpty(item.getSolde()))
                dto.setSolde(item.getSolde());


            return dto;
        }
    }



    public List<Banque> toItem(List<BanqueDto> dtos) {
        List<Banque> items = new ArrayList<>();
        if (dtos != null && !dtos.isEmpty()) {
            for (BanqueDto dto : dtos) {
                items.add(toItem(dto));
            }
        }
        return items;
    }


    public List<BanqueDto> toDto(List<Banque> items) {
        List<BanqueDto> dtos = new ArrayList<>();
        if (items != null && !items.isEmpty()) {
            for (Banque item : items) {
                dtos.add(toDto(item));
            }
        }
        return dtos;
    }


    public void copy(BanqueDto dto, Banque t) {
        BeanUtils.copyProperties(dto, t, AbstractConverterHelper.getNullPropertyNames(dto));
    }

    public List<Banque> copy(List<BanqueDto> dtos) {
        List<Banque> result = new ArrayList<>();
        if (dtos != null) {
            for (BanqueDto dto : dtos) {
                Banque instance = new Banque();
                copy(dto, instance);
                result.add(instance);
            }
        }
        return result.isEmpty() ? null : result;
    }


}
