package  ma.zyn.app.ws.converter.locataire;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.beans.BeanUtils;
import ma.zyn.app.zynerator.converter.AbstractConverterHelper;

import java.util.ArrayList;
import java.util.List;




import ma.zyn.app.zynerator.util.StringUtil;
import ma.zyn.app.zynerator.converter.AbstractConverter;
import ma.zyn.app.zynerator.util.DateUtil;
import ma.zyn.app.bean.core.locataire.TypeLocataire;
import ma.zyn.app.ws.dto.locataire.TypeLocataireDto;

@Component
public class TypeLocataireConverter {



    public TypeLocataire toItem(TypeLocataireDto dto) {
        if (dto == null) {
            return null;
        } else {
        TypeLocataire item = new TypeLocataire();
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
            if(StringUtil.isNotEmpty(dto.getColor()))
                item.setColor(dto.getColor());



        return item;
        }
    }


    public TypeLocataireDto toDto(TypeLocataire item) {
        if (item == null) {
            return null;
        } else {
            TypeLocataireDto dto = new TypeLocataireDto();
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
            if(StringUtil.isNotEmpty(item.getColor()))
                dto.setColor(item.getColor());


        return dto;
        }
    }


	
    public List<TypeLocataire> toItem(List<TypeLocataireDto> dtos) {
        List<TypeLocataire> items = new ArrayList<>();
        if (dtos != null && !dtos.isEmpty()) {
            for (TypeLocataireDto dto : dtos) {
                items.add(toItem(dto));
            }
        }
        return items;
    }


    public List<TypeLocataireDto> toDto(List<TypeLocataire> items) {
        List<TypeLocataireDto> dtos = new ArrayList<>();
        if (items != null && !items.isEmpty()) {
            for (TypeLocataire item : items) {
                dtos.add(toDto(item));
            }
        }
        return dtos;
    }


    public void copy(TypeLocataireDto dto, TypeLocataire t) {
		BeanUtils.copyProperties(dto, t, AbstractConverterHelper.getNullPropertyNames(dto));
    }

    public List<TypeLocataire> copy(List<TypeLocataireDto> dtos) {
        List<TypeLocataire> result = new ArrayList<>();
        if (dtos != null) {
            for (TypeLocataireDto dto : dtos) {
                TypeLocataire instance = new TypeLocataire();
                copy(dto, instance);
                result.add(instance);
            }
        }
        return result.isEmpty() ? null : result;
    }


}
