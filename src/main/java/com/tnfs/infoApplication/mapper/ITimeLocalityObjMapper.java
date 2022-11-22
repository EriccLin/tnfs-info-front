package com.tnfs.infoApplication.mapper;

import com.tnfs.infoApplication.dto.TimeLocalityDto;
import com.tnfs.infoApplication.model.TimeLocalityObj;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Set;

@Mapper
public interface ITimeLocalityObjMapper {

    ITimeLocalityObjMapper INSTANCE = Mappers.getMapper(ITimeLocalityObjMapper.class);

    @Mappings({
            @Mapping(source = "id", target = "id"),
            @Mapping(source = "criminalCaseId", target = "criminalCaseId"),
    })
    public TimeLocalityDto toDto(TimeLocalityObj obj);

    public List<TimeLocalityDto> toDto(List<TimeLocalityObj> list);

    public Set<TimeLocalityDto> toDto(Set<TimeLocalityObj> set);

    @InheritInverseConfiguration(name = "toDto")
    public TimeLocalityObj toObj(TimeLocalityDto dto);

    @InheritInverseConfiguration(name = "toDto")
    public List<TimeLocalityObj> toObj(List<TimeLocalityDto> dtoList);

    @InheritInverseConfiguration(name = "toDto")
    public Set<TimeLocalityObj> toObj(Set<TimeLocalityDto> dtoSet);


}
