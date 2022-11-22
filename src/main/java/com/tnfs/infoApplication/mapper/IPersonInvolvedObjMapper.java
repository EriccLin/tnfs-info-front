package com.tnfs.infoApplication.mapper;

import com.tnfs.infoApplication.dto.PersonInvolvedDto;
import com.tnfs.infoApplication.model.PersonInvolvedObj;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Set;

@Mapper
public interface IPersonInvolvedObjMapper {

    IPersonInvolvedObjMapper INSTANCE = Mappers.getMapper(IPersonInvolvedObjMapper.class);

    @Mappings({
            @Mapping(source = "name", target = "name")
    })
    public PersonInvolvedDto toDto(PersonInvolvedObj obj);

    public List<PersonInvolvedDto> toDto(List<PersonInvolvedObj> list);

    public Set<PersonInvolvedDto> toDto(Set<PersonInvolvedObj> set);

    @InheritInverseConfiguration(name = "toDto")
    public PersonInvolvedObj toObj(PersonInvolvedDto dto);

    @InheritInverseConfiguration(name = "toDto")
    public List<PersonInvolvedObj> toObj(List<PersonInvolvedDto> dtoList);

    @InheritInverseConfiguration(name = "toDto")
    public Set<PersonInvolvedObj> toObj(Set<PersonInvolvedDto> dtoSet);
}
