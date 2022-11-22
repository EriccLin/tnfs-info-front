package com.tnfs.infoApplication.mapper;

import com.tnfs.infoApplication.dto.UnitDto;
import com.tnfs.infoApplication.model.UnitObj;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor"
)
public class IUnitObjMapperImpl implements IUnitObjMapper {

    @Override
    public List<UnitDto> toDto(List<UnitObj> list) {
        if ( list == null ) {
            return null;
        }

        List<UnitDto> list1 = new ArrayList<UnitDto>( list.size() );
        for ( UnitObj unitObj : list ) {
            list1.add( toDto( unitObj ) );
        }

        return list1;
    }

    @Override
    public Set<UnitDto> toDto(Set<UnitObj> set) {
        if ( set == null ) {
            return null;
        }

        Set<UnitDto> set1 = new HashSet<UnitDto>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( UnitObj unitObj : set ) {
            set1.add( toDto( unitObj ) );
        }

        return set1;
    }

    @Override
    public List<UnitObj> toObj(List<UnitDto> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<UnitObj> list = new ArrayList<UnitObj>( dtoList.size() );
        for ( UnitDto unitDto : dtoList ) {
            list.add( toObj( unitDto ) );
        }

        return list;
    }

    @Override
    public Set<UnitObj> toObj(Set<UnitDto> dtoSet) {
        if ( dtoSet == null ) {
            return null;
        }

        Set<UnitObj> set = new HashSet<UnitObj>( Math.max( (int) ( dtoSet.size() / .75f ) + 1, 16 ) );
        for ( UnitDto unitDto : dtoSet ) {
            set.add( toObj( unitDto ) );
        }

        return set;
    }
}
