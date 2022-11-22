package com.tnfs.infoApplication.mapper;

import com.tnfs.infoApplication.dto.RoleDto;
import com.tnfs.infoApplication.model.RoleObj;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Set;

@Mapper
public interface IRoleObjMapper {

    IRoleObjMapper INSTANCE = Mappers.getMapper(IRoleObjMapper.class);

    @Mappings({
            @Mapping(source = "id", target = "id"),
    })
    public RoleDto toDto(RoleObj obj);

    public List<RoleDto> toDto(List<RoleObj> list);

    public Set<RoleDto> toDto(Set<RoleObj> set);

    @InheritInverseConfiguration(name = "toDto")
    public RoleObj toObj(RoleDto dto);

    @InheritInverseConfiguration(name = "toDto")
    public List<RoleObj> toObj(List<RoleDto> dtoList);

    @InheritInverseConfiguration(name = "toDto")
    public Set<RoleObj> toObj(Set<RoleDto> dtoSet);


}
