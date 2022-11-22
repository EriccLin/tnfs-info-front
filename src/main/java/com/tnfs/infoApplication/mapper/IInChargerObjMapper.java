package com.tnfs.infoApplication.mapper;

import com.tnfs.infoApplication.dto.InChargerDto;
import com.tnfs.infoApplication.model.InChargerObj;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Set;

@Mapper
public interface IInChargerObjMapper {

    IInChargerObjMapper INSTANCE = Mappers.getMapper( IInChargerObjMapper.class );

    @Mappings({
            @Mapping(source = "id", target = "id"),
    })
    public InChargerDto toDto(InChargerObj obj);

    public List<InChargerDto> toDto(List<InChargerObj> objList);

    public Set<InChargerDto> toDto(Set<InChargerObj> objSet);

    @InheritInverseConfiguration(name = "toDto")
    public InChargerObj toObj(InChargerDto dto);

    @InheritInverseConfiguration(name = "toDto")
    public List<InChargerObj> toObj(List<InChargerDto> dto);

    @InheritInverseConfiguration(name = "toDto")
    public Set<InChargerObj> toObj(Set<InChargerDto> dto);
}
