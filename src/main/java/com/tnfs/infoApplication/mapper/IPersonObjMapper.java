package com.tnfs.infoApplication.mapper;

import com.tnfs.infoApplication.dto.PersonDto;
import com.tnfs.infoApplication.model.PersonObj;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Set;

@Mapper
public interface IPersonObjMapper {

    IPersonObjMapper INSTANCE = Mappers.getMapper(IPersonObjMapper.class);

    @Mappings({
            @Mapping(source = "name", target = "name")
    })
    public PersonDto toDto(PersonObj obj);

    public List<PersonDto> toDto(List<PersonObj> list);

    public Set<PersonDto> toDto(Set<PersonObj> set);

    @InheritInverseConfiguration(name = "toDto")
    public PersonObj toObj(PersonDto dto);

    @InheritInverseConfiguration(name = "toDto")
    public List<PersonObj> toObj(List<PersonDto> dtos);

    @InheritInverseConfiguration(name = "toDto")
    public Set<PersonObj> toObj(Set<PersonDto> dtos);
}
