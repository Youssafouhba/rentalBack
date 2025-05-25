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
import ma.zyn.app.bean.core.finance.Caisse;
import ma.zyn.app.ws.dto.finance.CaisseDto;

@Component
public class CaisseConverter {



    public Caisse toItem(CaisseDto dto) {
        if (dto == null) {
            return null;
        } else {
        Caisse item = new Caisse();
            if(StringUtil.isNotEmpty(dto.getId()))
                item.setId(dto.getId());
            if(StringUtil.isNotEmpty(dto.getCode()))
                item.setCode(dto.getCode());
            if(StringUtil.isNotEmpty(dto.getLibelle()))
                item.setLibelle(dto.getLibelle());
            if(StringUtil.isNotEmpty(dto.getSolde()))
                item.setSolde(dto.getSolde());
            if (StringUtil.isNotEmpty(dto.getDateCreation()))
                item.setDateCreation(DateUtil.stringEnToDate(dto.getDateCreation()));


        return item;
        }
    }


    public CaisseDto toDto(Caisse item) {
        if (item == null) {
            return null;
        } else {
            CaisseDto dto = new CaisseDto();
            if(StringUtil.isNotEmpty(item.getId()))
                dto.setId(item.getId());
            if(item.getDateCreation()!=null)
                dto.setDateCreation(DateUtil.dateTimeToString(item.getDateCreation()));
            if(StringUtil.isNotEmpty(item.getCode()))
                dto.setCode(item.getCode());
            if(StringUtil.isNotEmpty(item.getLibelle()))
                dto.setLibelle(item.getLibelle());
            if(StringUtil.isNotEmpty(item.getSolde()))
                dto.setSolde(item.getSolde());


        return dto;
        }
    }


	
    public List<Caisse> toItem(List<CaisseDto> dtos) {
        List<Caisse> items = new ArrayList<>();
        if (dtos != null && !dtos.isEmpty()) {
            for (CaisseDto dto : dtos) {
                items.add(toItem(dto));
            }
        }
        return items;
    }


    public List<CaisseDto> toDto(List<Caisse> items) {
        List<CaisseDto> dtos = new ArrayList<>();
        if (items != null && !items.isEmpty()) {
            for (Caisse item : items) {
                dtos.add(toDto(item));
            }
        }
        return dtos;
    }


    public void copy(CaisseDto dto, Caisse t) {
		BeanUtils.copyProperties(dto, t, AbstractConverterHelper.getNullPropertyNames(dto));
    }

    public List<Caisse> copy(List<CaisseDto> dtos) {
        List<Caisse> result = new ArrayList<>();
        if (dtos != null) {
            for (CaisseDto dto : dtos) {
                Caisse instance = new Caisse();
                copy(dto, instance);
                result.add(instance);
            }
        }
        return result.isEmpty() ? null : result;
    }


}
