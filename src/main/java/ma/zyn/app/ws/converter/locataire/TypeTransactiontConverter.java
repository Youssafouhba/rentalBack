package ma.zyn.app.ws.converter.locataire;

import ma.zyn.app.bean.core.locataire.TypeTransactiont;
import ma.zyn.app.ws.dto.locataire.TypeTransactiontDto;
import ma.zyn.app.zynerator.converter.AbstractConverterHelper;
import ma.zyn.app.zynerator.util.StringUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TypeTransactiontConverter {



    public TypeTransactiont toItem(TypeTransactiontDto dto) {
        if (dto == null) {
            return null;
        } else {
        TypeTransactiont item = new TypeTransactiont();
            if(StringUtil.isNotEmpty(dto.getId()))
                item.setId(dto.getId());
            if(StringUtil.isNotEmpty(dto.getCode()))
                item.setCode(dto.getCode());
            if(StringUtil.isNotEmpty(dto.getLabel()))
                item.setLabel(dto.getLabel());
            if(StringUtil.isNotEmpty(dto.getStyle()))
                item.setStyle(dto.getStyle());



        return item;
        }
    }


    public TypeTransactiontDto toDto(TypeTransactiont item) {
        if (item == null) {
            return null;
        } else {
            TypeTransactiontDto dto = new TypeTransactiontDto();
            if(StringUtil.isNotEmpty(item.getId()))
                dto.setId(item.getId());
            if(StringUtil.isNotEmpty(item.getCode()))
                dto.setCode(item.getCode());
            if(StringUtil.isNotEmpty(item.getLabel()))
                dto.setLabel(item.getLabel());
            if(StringUtil.isNotEmpty(item.getStyle()))
                dto.setStyle(item.getStyle());


        return dto;
        }
    }


	
    public List<TypeTransactiont> toItem(List<TypeTransactiontDto> dtos) {
        List<TypeTransactiont> items = new ArrayList<>();
        if (dtos != null && !dtos.isEmpty()) {
            for (TypeTransactiontDto dto : dtos) {
                items.add(toItem(dto));
            }
        }
        return items;
    }


    public List<TypeTransactiontDto> toDto(List<TypeTransactiont> items) {
        List<TypeTransactiontDto> dtos = new ArrayList<>();
        if (items != null && !items.isEmpty()) {
            for (TypeTransactiont item : items) {
                dtos.add(toDto(item));
            }
        }
        return dtos;
    }


    public void copy(TypeTransactiontDto dto, TypeTransactiont t) {
		BeanUtils.copyProperties(dto, t, AbstractConverterHelper.getNullPropertyNames(dto));
    }

    public List<TypeTransactiont> copy(List<TypeTransactiontDto> dtos) {
        List<TypeTransactiont> result = new ArrayList<>();
        if (dtos != null) {
            for (TypeTransactiontDto dto : dtos) {
                TypeTransactiont instance = new TypeTransactiont();
                copy(dto, instance);
                result.add(instance);
            }
        }
        return result.isEmpty() ? null : result;
    }


}
