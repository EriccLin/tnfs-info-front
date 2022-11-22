package com.tnfs.infoApplication.mapper;

import com.tnfs.infoApplication.dto.EvidenceDto;
import com.tnfs.infoApplication.model.EvidenceObj;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Set;

@Mapper
public interface IEvidenceObjMapper {

    IEvidenceObjMapper INSTANCE = Mappers.getMapper(IEvidenceObjMapper.class);

    @Mappings({
            @Mapping(source = "items", target = "items")
    })
    public EvidenceDto toDto(EvidenceObj obj);

    public List<EvidenceDto> toDto(List<EvidenceObj> list);

    public Set<EvidenceDto> toDto(Set<EvidenceObj> set);

    @InheritInverseConfiguration(name = "toDto")
    public EvidenceObj toObj(EvidenceDto dto);

    @InheritInverseConfiguration(name = "toDto")
    public List<EvidenceObj> toObj(List<EvidenceDto> dtoList);

    @InheritInverseConfiguration(name = "toDto")
    public Set<EvidenceObj> toObj(Set<EvidenceDto> dtoSet);


}
