package com.tnfs.infoApplication.mapper;

import com.tnfs.infoApplication.dto.MemberDto;
import com.tnfs.infoApplication.model.MemberObj;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Set;

@Mapper
public interface IMemberObjMapper {

    IMemberObjMapper INSTANCE = Mappers.getMapper(IMemberObjMapper.class);

    @Mappings({
            @Mapping(source = "id", target = "id")
    })
    public MemberDto toDto(MemberObj obj);

    public List<MemberDto> toDto(List<MemberObj> list);

    public Set<MemberDto> toDto(Set<MemberObj> set);

    @InheritInverseConfiguration(name = "toDto")
    public MemberObj toObj(MemberDto dto);

    @InheritInverseConfiguration(name = "toDto")
    public List<MemberObj> toObj(List<MemberDto> dtoList);

    @InheritInverseConfiguration(name = "toDto")
    public Set<MemberObj> toObj(Set<MemberDto> dtoSet);


}
