package  ma.zyn.app.ws.converter.finance;

import ma.zyn.app.ws.dto.locataire.ModePaiementDto;
import org.springframework.stereotype.Component;
import org.springframework.beans.BeanUtils;
import ma.zyn.app.zynerator.converter.AbstractConverterHelper;

import java.util.ArrayList;
import java.util.List;




import ma.zyn.app.zynerator.util.StringUtil;
import ma.zyn.app.bean.core.finance.ModePaiement;

@Component
public class ModePaiementConverter {



    public ModePaiement toItem(ModePaiementDto dto) {
        if (dto == null) {
            return null;
        } else {
            ModePaiement item = new ModePaiement();
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


    public ModePaiementDto toDto(ModePaiement item) {
        if (item == null) {
            return null;
        } else {
            ModePaiementDto dto = new ModePaiementDto();
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



    public List<ModePaiement> toItem(List<ModePaiementDto> dtos) {
        List<ModePaiement> items = new ArrayList<>();
        if (dtos != null && !dtos.isEmpty()) {
            for (ModePaiementDto dto : dtos) {
                items.add(toItem(dto));
            }
        }
        return items;
    }


    public List<ModePaiementDto> toDto(List<ModePaiement> items) {
        List<ModePaiementDto> dtos = new ArrayList<>();
        if (items != null && !items.isEmpty()) {
            for (ModePaiement item : items) {
                dtos.add(toDto(item));
            }
        }
        return dtos;
    }


    public void copy(ModePaiementDto dto, ModePaiement t) {
        BeanUtils.copyProperties(dto, t, AbstractConverterHelper.getNullPropertyNames(dto));
    }

    public List<ModePaiement> copy(List<ModePaiementDto> dtos) {
        List<ModePaiement> result = new ArrayList<>();
        if (dtos != null) {
            for (ModePaiementDto dto : dtos) {
                ModePaiement instance = new ModePaiement();
                copy(dto, instance);
                result.add(instance);
            }
        }
        return result.isEmpty() ? null : result;
    }


}
