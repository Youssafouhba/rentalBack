package ma.zyn.app.ws.converter.finance;

import ma.zyn.app.bean.core.finance.TypeCharge;
import ma.zyn.app.ws.dto.finance.TypeChargeDto;
import ma.zyn.app.zynerator.converter.AbstractConverterHelper;
import ma.zyn.app.zynerator.util.StringUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class TypeChargeConverter {


    private final CompteChargeConverter compteChargeConverter;

    public TypeChargeConverter(CompteChargeConverter compteChargeConverter) {
        this.compteChargeConverter = compteChargeConverter;
    }

    public TypeCharge toItem(TypeChargeDto dto) {
        if (dto == null) {
            return null;
        } else {
        TypeCharge item = new TypeCharge();
            if(StringUtil.isNotEmpty(dto.getId()))
                item.setId(dto.getId());
            if(StringUtil.isNotEmpty(dto.getCode()))
                item.setCode(dto.getCode());
            if(StringUtil.isNotEmpty(dto.getLabel()))
                item.setLabel(dto.getLabel());
            if(StringUtil.isNotEmpty(dto.getStyle()))
                item.setStyle(dto.getStyle());
            if(StringUtil.isNotEmpty(dto.getDescription()))
                item.setDescription(dto.getDescription());

        return item;
        }
    }


    public TypeChargeDto toDto(TypeCharge item) {
        if (item == null) {
            return null;
        } else {
            TypeChargeDto dto = new TypeChargeDto();
            if(StringUtil.isNotEmpty(item.getId()))
                dto.setId(item.getId());
            if(StringUtil.isNotEmpty(item.getCode()))
                dto.setCode(item.getCode());
            if(StringUtil.isNotEmpty(item.getLabel()))
                dto.setLabel(item.getLabel());
            if(StringUtil.isNotEmpty(item.getStyle()))
                dto.setStyle(item.getStyle());
            if(StringUtil.isNotEmpty(item.getDescription()))
                dto.setDescription(item.getDescription());

        return dto;
        }
    }


	
    public Set<TypeCharge> toItem(Set<TypeChargeDto> dtos) {
        List<TypeCharge> items = new ArrayList<>();
        if (dtos != null && !dtos.isEmpty()) {
            for (TypeChargeDto dto : dtos) {
                items.add(toItem(dto));
            }
        }
        return new HashSet<>(items);
    }


    public Set<TypeChargeDto> toDto(Set<TypeCharge> items) {
        List<TypeChargeDto> dtos = new ArrayList<>();
        if (items != null && !items.isEmpty()) {
            for (TypeCharge item : items) {
                dtos.add(toDto(item));
            }
        }
        return new HashSet<>(dtos);
    }


    public void copy(TypeChargeDto dto, TypeCharge t) {
		BeanUtils.copyProperties(dto, t, AbstractConverterHelper.getNullPropertyNames(dto));
    }

    public List<TypeCharge> copy(List<TypeChargeDto> dtos) {
        List<TypeCharge> result = new ArrayList<>();
        if (dtos != null) {
            for (TypeChargeDto dto : dtos) {
                TypeCharge instance = new TypeCharge();
                copy(dto, instance);
                result.add(instance);
            }
        }
        return result.isEmpty() ? null : result;
    }


}
