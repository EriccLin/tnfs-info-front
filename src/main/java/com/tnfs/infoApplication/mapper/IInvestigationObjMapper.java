package com.tnfs.infoApplication.mapper;

import com.tnfs.infoApplication.dto.InvestigationDto;
import com.tnfs.infoApplication.model.InvestigationObj;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Set;

@Mapper(uses = {IInvestigationMemberObjMapper.class})
public interface IInvestigationObjMapper {

    IInvestigationObjMapper INSTANCE = Mappers.getMapper(IInvestigationObjMapper.class);

    @Mappings({
            @Mapping(source = "investigationMembers", target = "investigationMembers")
    })
    public InvestigationDto toDto(InvestigationObj obj);

    public List<InvestigationDto> toDto(List<InvestigationObj> list);

    public Set<InvestigationDto> toDto(Set<InvestigationObj> set);

    @InheritInverseConfiguration(name = "toDto")
    public InvestigationObj toObj(InvestigationDto dto);

    @InheritInverseConfiguration(name = "toDto")
    public List<InvestigationObj> toObj(List<InvestigationDto> dtoList);

    @InheritInverseConfiguration(name = "toDto")
    public Set<InvestigationObj> toObj(Set<InvestigationDto> dtoSet);


}
