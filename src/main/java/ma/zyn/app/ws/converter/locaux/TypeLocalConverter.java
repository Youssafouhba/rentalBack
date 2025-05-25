package ma.zyn.app.ws.converter.locaux;

import ma.zyn.app.bean.core.locaux.TypeLocal;
import ma.zyn.app.ws.dto.locaux.TypeLocalDto;
import ma.zyn.app.zynerator.converter.AbstractConverterHelper;
import ma.zyn.app.zynerator.util.StringUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TypeLocalConverter {



    public TypeLocal toItem(TypeLocalDto dto) {
        if (dto == null) {
            return null;
        } else {
        TypeLocal item = new TypeLocal();
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


    public TypeLocalDto toDto(TypeLocal item) {
        if (item == null) {
            return null;
        } else {
            TypeLocalDto dto = new TypeLocalDto();
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


	
    public List<TypeLocal> toItem(List<TypeLocalDto> dtos) {
        List<TypeLocal> items = new ArrayList<>();
        if (dtos != null && !dtos.isEmpty()) {
            for (TypeLocalDto dto : dtos) {
                items.add(toItem(dto));
            }
        }
        return items;
    }


    public List<TypeLocalDto> toDto(List<TypeLocal> items) {
        List<TypeLocalDto> dtos = new ArrayList<>();
        if (items != null && !items.isEmpty()) {
            for (TypeLocal item : items) {
                dtos.add(toDto(item));
            }
        }
        return dtos;
    }


    public void copy(TypeLocalDto dto, TypeLocal t) {
		BeanUtils.copyProperties(dto, t, AbstractConverterHelper.getNullPropertyNames(dto));
    }

    public List<TypeLocal> copy(List<TypeLocalDto> dtos) {
        List<TypeLocal> result = new ArrayList<>();
        if (dtos != null) {
            for (TypeLocalDto dto : dtos) {
                TypeLocal instance = new TypeLocal();
                copy(dto, instance);
                result.add(instance);
            }
        }
        return result.isEmpty() ? null : result;
    }


}
