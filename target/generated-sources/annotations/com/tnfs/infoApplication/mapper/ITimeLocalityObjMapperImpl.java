package com.tnfs.infoApplication.mapper;

import com.tnfs.infoApplication.dto.TimeLocalityDto;
import com.tnfs.infoApplication.model.TimeLocalityObj;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor"
)
public class ITimeLocalityObjMapperImpl implements ITimeLocalityObjMapper {

    @Override
    public TimeLocalityDto toDto(TimeLocalityObj obj) {
        if ( obj == null ) {
            return null;
        }

        TimeLocalityDto timeLocalityDto = new TimeLocalityDto();

        timeLocalityDto.setId( obj.getId() );
        timeLocalityDto.setCriminalCaseId( obj.getCriminalCaseId() );
        timeLocalityDto.setStime( obj.getStime() );
        timeLocalityDto.setEtime( obj.getEtime() );
        timeLocalityDto.setLocation( obj.getLocation() );
        timeLocalityDto.setType( obj.getType() );
        timeLocalityDto.setReserve( obj.getReserve() );

        return timeLocalityDto;
    }

    @Override
    public List<TimeLocalityDto> toDto(List<TimeLocalityObj> list) {
        if ( list == null ) {
            return null;
        }

        List<TimeLocalityDto> list1 = new ArrayList<TimeLocalityDto>( list.size() );
        for ( TimeLocalityObj timeLocalityObj : list ) {
            list1.add( toDto( timeLocalityObj ) );
        }

        return list1;
    }

    @Override
    public Set<TimeLocalityDto> toDto(Set<TimeLocalityObj> set) {
        if ( set == null ) {
            return null;
        }

        Set<TimeLocalityDto> set1 = new HashSet<TimeLocalityDto>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( TimeLocalityObj timeLocalityObj : set ) {
            set1.add( toDto( timeLocalityObj ) );
        }

        return set1;
    }

    @Override
    public TimeLocalityObj toObj(TimeLocalityDto dto) {
        if ( dto == null ) {
            return null;
        }

        TimeLocalityObj timeLocalityObj = new TimeLocalityObj();

        timeLocalityObj.setId( dto.getId() );
        timeLocalityObj.setCriminalCaseId( dto.getCriminalCaseId() );
        timeLocalityObj.setStime( dto.getStime() );
        timeLocalityObj.setEtime( dto.getEtime() );
        timeLocalityObj.setLocation( dto.getLocation() );
        timeLocalityObj.setType( dto.getType() );
        timeLocalityObj.setReserve( dto.getReserve() );

        return timeLocalityObj;
    }

    @Override
    public List<TimeLocalityObj> toObj(List<TimeLocalityDto> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<TimeLocalityObj> list = new ArrayList<TimeLocalityObj>( dtoList.size() );
        for ( TimeLocalityDto timeLocalityDto : dtoList ) {
            list.add( toObj( timeLocalityDto ) );
        }

        return list;
    }

    @Override
    public Set<TimeLocalityObj> toObj(Set<TimeLocalityDto> dtoSet) {
        if ( dtoSet == null ) {
            return null;
        }

        Set<TimeLocalityObj> set = new HashSet<TimeLocalityObj>( Math.max( (int) ( dtoSet.size() / .75f ) + 1, 16 ) );
        for ( TimeLocalityDto timeLocalityDto : dtoSet ) {
            set.add( toObj( timeLocalityDto ) );
        }

        return set;
    }
}
