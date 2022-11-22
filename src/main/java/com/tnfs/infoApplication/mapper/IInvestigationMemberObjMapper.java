package com.tnfs.infoApplication.mapper;

import com.tnfs.infoApplication.dto.MemberDto;
import com.tnfs.infoApplication.model.MemberObj;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface IInvestigationMemberObjMapper {

    IInvestigationMemberObjMapper INSTANCE = Mappers.getMapper(IInvestigationMemberObjMapper.class);

    @Mappings({
            @Mapping(source = "id", target = "id")
    })
    public MemberDto toDto(MemberObj obj);

    public List<MemberDto> toDto(List<MemberObj> list);

    @InheritInverseConfiguration(name = "toDto")
    public MemberObj toObj(MemberDto dto);

    @InheritInverseConfiguration(name = "toDto")
    public List<MemberObj> toObj(List<MemberDto> dtoList);


}
