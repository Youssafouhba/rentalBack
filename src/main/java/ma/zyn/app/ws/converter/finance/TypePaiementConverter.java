package ma.zyn.app.ws.converter.finance;

import ma.zyn.app.bean.core.finance.TypePaiement;
import ma.zyn.app.ws.dto.finance.TypePaiementDto;
import ma.zyn.app.zynerator.converter.AbstractConverterHelper;
import ma.zyn.app.zynerator.util.StringUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TypePaiementConverter {



    public TypePaiement toItem(TypePaiementDto dto) {
        if (dto == null) {
            return null;
        } else {
        TypePaiement item = new TypePaiement();
            if(StringUtil.isNotEmpty(dto.getId()))
                item.setId(dto.getId());
            if(StringUtil.isNotEmpty(dto.getIndexation()))
                item.setIndexation(dto.getIndexation());
            if(StringUtil.isNotEmpty(dto.getCode()))
                item.setCode(dto.getCode());
            if(StringUtil.isNotEmpty(dto.getLabel()))
                item.setLabel(dto.getLabel());
            if(StringUtil.isNotEmpty(dto.getStyle()))
                item.setStyle(dto.getStyle());



        return item;
        }
    }


    public TypePaiementDto toDto(TypePaiement item) {
        if (item == null) {
            return null;
        } else {
            TypePaiementDto dto = new TypePaiementDto();
            if(StringUtil.isNotEmpty(item.getId()))
                dto.setId(item.getId());
            if(StringUtil.isNotEmpty(item.getIndexation()))
                dto.setIndexation(item.getIndexation());
            if(StringUtil.isNotEmpty(item.getCode()))
                dto.setCode(item.getCode());
            if(StringUtil.isNotEmpty(item.getLabel()))
                dto.setLabel(item.getLabel());
            if(StringUtil.isNotEmpty(item.getStyle()))
                dto.setStyle(item.getStyle());


        return dto;
        }
    }


	
    public List<TypePaiement> toItem(List<TypePaiementDto> dtos) {
        List<TypePaiement> items = new ArrayList<>();
        if (dtos != null && !dtos.isEmpty()) {
            for (TypePaiementDto dto : dtos) {
                items.add(toItem(dto));
            }
        }
        return items;
    }


    public List<TypePaiementDto> toDto(List<TypePaiement> items) {
        List<TypePaiementDto> dtos = new ArrayList<>();
        if (items != null && !items.isEmpty()) {
            for (TypePaiement item : items) {
                dtos.add(toDto(item));
            }
        }
        return dtos;
    }


    public void copy(TypePaiementDto dto, TypePaiement t) {
		BeanUtils.copyProperties(dto, t, AbstractConverterHelper.getNullPropertyNames(dto));
    }

    public List<TypePaiement> copy(List<TypePaiementDto> dtos) {
        List<TypePaiement> result = new ArrayList<>();
        if (dtos != null) {
            for (TypePaiementDto dto : dtos) {
                TypePaiement instance = new TypePaiement();
                copy(dto, instance);
                result.add(instance);
            }
        }
        return result.isEmpty() ? null : result;
    }


}
