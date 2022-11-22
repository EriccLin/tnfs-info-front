package com.tnfs.infoApplication.mapper;

import com.tnfs.infoApplication.dto.InChargerDto;
import com.tnfs.infoApplication.model.InChargerObj;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor"
)
public class IInChargerObjMapperImpl implements IInChargerObjMapper {

    @Override
    public InChargerDto toDto(InChargerObj obj) {
        if ( obj == null ) {
            return null;
        }

        InChargerDto inChargerDto = new InChargerDto();

        inChargerDto.setId( obj.getId() );
        inChargerDto.setCriminalCaseId( obj.getCriminalCaseId() );
        inChargerDto.setUnitId( obj.getUnitId() );
        inChargerDto.setName( obj.getName() );
        inChargerDto.setMrank( obj.getMrank() );
        inChargerDto.setType( obj.getType() );
        inChargerDto.setDispatch( obj.getDispatch() );

        return inChargerDto;
    }

    @Override
    public List<InChargerDto> toDto(List<InChargerObj> objList) {
        if ( objList == null ) {
            return null;
        }

        List<InChargerDto> list = new ArrayList<InChargerDto>( objList.size() );
        for ( InChargerObj inChargerObj : objList ) {
            list.add( toDto( inChargerObj ) );
        }

        return list;
    }

    @Override
    public Set<InChargerDto> toDto(Set<InChargerObj> objSet) {
        if ( objSet == null ) {
            return null;
        }

        Set<InChargerDto> set = new HashSet<InChargerDto>( Math.max( (int) ( objSet.size() / .75f ) + 1, 16 ) );
        for ( InChargerObj inChargerObj : objSet ) {
            set.add( toDto( inChargerObj ) );
        }

        return set;
    }

    @Override
    public InChargerObj toObj(InChargerDto dto) {
        if ( dto == null ) {
            return null;
        }

        InChargerObj inChargerObj = new InChargerObj();

        inChargerObj.setId( dto.getId() );
        inChargerObj.setCriminalCaseId( dto.getCriminalCaseId() );
        inChargerObj.setUnitId( dto.getUnitId() );
        inChargerObj.setName( dto.getName() );
        inChargerObj.setMrank( dto.getMrank() );
        inChargerObj.setType( dto.getType() );
        inChargerObj.setDispatch( dto.getDispatch() );

        return inChargerObj;
    }

    @Override
    public List<InChargerObj> toObj(List<InChargerDto> dto) {
        if ( dto == null ) {
            return null;
        }

        List<InChargerObj> list = new ArrayList<InChargerObj>( dto.size() );
        for ( InChargerDto inChargerDto : dto ) {
            list.add( toObj( inChargerDto ) );
        }

        return list;
    }

    @Override
    public Set<InChargerObj> toObj(Set<InChargerDto> dto) {
        if ( dto == null ) {
            return null;
        }

        Set<InChargerObj> set = new HashSet<InChargerObj>( Math.max( (int) ( dto.size() / .75f ) + 1, 16 ) );
        for ( InChargerDto inChargerDto : dto ) {
            set.add( toObj( inChargerDto ) );
        }

        return set;
    }
}
