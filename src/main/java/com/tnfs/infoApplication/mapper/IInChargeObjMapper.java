package com.tnfs.infoApplication.mapper;

import com.tnfs.infoApplication.dto.InChargeDto;
import com.tnfs.infoApplication.model.InChargeObj;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Set;

@Mapper(uses = {IInChargerObjMapper.class})
public interface IInChargeObjMapper {

    IInChargeObjMapper INSTANCE = Mappers.getMapper( IInChargeObjMapper.class );

    @Mappings({
            @Mapping(source = "inChargers", target = "inChargers")
    })
    public InChargeDto toDto(InChargeObj obj);

    public List<InChargeDto> toDto(List<InChargeObj> objList);

    public Set<InChargeDto> toDto(Set<InChargeObj> objSet);

    @InheritInverseConfiguration(name = "toDto")
    public InChargeObj toObj(InChargeDto dto);

    @InheritInverseConfiguration(name = "toDto")
    public List<InChargeObj> toObj(List<InChargeDto> list);

    @InheritInverseConfiguration(name = "toDto")
    public Set<InChargeObj> toObj(Set<InChargeDto> set);
}
