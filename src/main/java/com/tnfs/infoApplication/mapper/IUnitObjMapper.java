package com.tnfs.infoApplication.mapper;

import com.tnfs.infoApplication.cache.UnitCache;
import com.tnfs.infoApplication.dto.UnitDto;
import com.tnfs.infoApplication.model.UnitObj;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Set;

@Mapper
public interface IUnitObjMapper {

    IUnitObjMapper INSTANCE = Mappers.getMapper(IUnitObjMapper.class);

    default public UnitDto toDto(UnitObj obj) {
        if ( obj == null ) {
            return null;
        }

        UnitDto unitDto = new UnitDto();
        Long superviserId = obj.getSuperviser() == null ? null : obj.getSuperviser().getId();

        unitDto.setId( obj.getId() );
        unitDto.setName( obj.getName() );
        unitDto.setEngName( obj.getEngName() );
        unitDto.setSuperviserId(superviserId);
        unitDto.setAddress( obj.getAddress() );
        unitDto.setLevel( obj.getLevel() );

        return unitDto;
    }
    public List<UnitDto> toDto(List<UnitObj> list);

    public Set<UnitDto> toDto(Set<UnitObj> set);

    default public UnitObj toObj(UnitDto dto) {
        if ( dto == null ) {
            return null;
        }

        UnitObj unit = new UnitObj();
        Long superviserId = dto.getSuperviserId();

        unit.setId( dto.getId() );
        unit.setName( dto.getName() );
        unit.setEngName( dto.getEngName() );
        unit.setLevel( dto.getLevel() );
        unit.setAddress( dto.getAddress() );
//        UnitObj superviser = UnitCache.getInstance().getUnitObj(superviserId);
//        if (superviser == null && superviserId != null) {
//            superviser = new UnitObj();
//            superviser.setId(superviserId);
//        }
        UnitObj superviser = null;
        if (superviserId != null) {
            superviser = new UnitObj();
            superviser.setId(superviserId);
        }
        unit.setSuperviser(superviser);

        return unit;
    }

    @InheritInverseConfiguration(name = "toDto")
    public List<UnitObj> toObj(List<UnitDto> dtoList);

    @InheritInverseConfiguration(name = "toDto")
    public Set<UnitObj> toObj(Set<UnitDto> dtoSet);


}
