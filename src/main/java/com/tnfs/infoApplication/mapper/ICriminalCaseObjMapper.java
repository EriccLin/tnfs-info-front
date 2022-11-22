package com.tnfs.infoApplication.mapper;

import com.tnfs.infoApplication.model.CriminalCaseObj;

import com.tnfs.infoApplication.dto.CriminalCaseDto;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(uses = {
        IMemberObjMapper.class,
        IPersonInvolvedObjMapper.class,
        ITimeLocalityObjMapper.class,
        IInvestigationObjMapper.class,
        IInChargeObjMapper.class,
        IEvidenceObjMapper.class,
        ICaseTypeObjMapper.class,
})
public interface ICriminalCaseObjMapper {
    ICriminalCaseObjMapper INSTANCE = Mappers.getMapper(ICriminalCaseObjMapper.class);

    @Mappings({
            @Mapping(source = "id", target = "id")
    })
    public CriminalCaseDto toDto(CriminalCaseObj obj);

    public List<CriminalCaseDto> toDto(List<CriminalCaseObj> objList);

    @InheritInverseConfiguration(name = "toDto")
    public CriminalCaseObj toObj(CriminalCaseDto dto);

    @InheritInverseConfiguration(name = "toDto")
    public List<CriminalCaseObj> toObj(List<CriminalCaseDto> dtoList);
}
