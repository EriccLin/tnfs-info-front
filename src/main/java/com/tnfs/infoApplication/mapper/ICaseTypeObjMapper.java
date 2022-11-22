package com.tnfs.infoApplication.mapper;

import com.tnfs.infoApplication.dto.*;
import com.tnfs.infoApplication.model.*;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Set;

@Mapper
public interface ICaseTypeObjMapper {

    ICaseTypeObjMapper INSTANCE = Mappers.getMapper( ICaseTypeObjMapper.class );

    @Mappings({
            @Mapping(source = "id", target = "id"),
            @Mapping(source = "name", target = "name"),
            @Mapping(source = "code", target = "code")
    })
    public CaseTypeDto toDto(CaseTypeObj obj);

    public List<CaseTypeDto> toDto(List<CaseTypeObj> objList);

    public Set<CaseTypeDto> toDto(Set<CaseTypeObj> objSet);

    @InheritInverseConfiguration(name = "toDto")
    public CaseTypeObj toObj(CaseTypeDto dto);

    @InheritInverseConfiguration(name = "toDto")
    public List<CaseTypeObj> toObj(List<CaseTypeDto> list);

    @InheritInverseConfiguration(name = "toDto")
    public Set<CaseTypeObj> toObj(Set<CaseTypeDto> set);
}
