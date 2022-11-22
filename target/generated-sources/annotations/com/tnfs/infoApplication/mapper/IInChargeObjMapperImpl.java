package com.tnfs.infoApplication.mapper;

import com.tnfs.infoApplication.dto.InChargeDto;
import com.tnfs.infoApplication.model.InChargeObj;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.Generated;
import org.mapstruct.factory.Mappers;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor"
)
public class IInChargeObjMapperImpl implements IInChargeObjMapper {

    private final IInChargerObjMapper iInChargerObjMapper = Mappers.getMapper( IInChargerObjMapper.class );

    @Override
    public InChargeDto toDto(InChargeObj obj) {
        if ( obj == null ) {
            return null;
        }

        InChargeDto inChargeDto = new InChargeDto();

        inChargeDto.setInChargers( iInChargerObjMapper.toDto( obj.getInChargers() ) );
        inChargeDto.setCriminalCaseId( obj.getCriminalCaseId() );
        inChargeDto.setUnitId( obj.getUnitId() );
        inChargeDto.setUnitName( obj.getUnitName() );
        inChargeDto.setUnitEngName( obj.getUnitEngName() );
        inChargeDto.setUnitSuperviserId( obj.getUnitSuperviserId() );
        inChargeDto.setUnitSuperviserName( obj.getUnitSuperviserName() );
        inChargeDto.setType( obj.getType() );
        inChargeDto.setReserve( obj.getReserve() );
        inChargeDto.setNotification( obj.getNotification() );

        return inChargeDto;
    }

    @Override
    public List<InChargeDto> toDto(List<InChargeObj> objList) {
        if ( objList == null ) {
            return null;
        }

        List<InChargeDto> list = new ArrayList<InChargeDto>( objList.size() );
        for ( InChargeObj inChargeObj : objList ) {
            list.add( toDto( inChargeObj ) );
        }

        return list;
    }

    @Override
    public Set<InChargeDto> toDto(Set<InChargeObj> objSet) {
        if ( objSet == null ) {
            return null;
        }

        Set<InChargeDto> set = new HashSet<InChargeDto>( Math.max( (int) ( objSet.size() / .75f ) + 1, 16 ) );
        for ( InChargeObj inChargeObj : objSet ) {
            set.add( toDto( inChargeObj ) );
        }

        return set;
    }

    @Override
    public InChargeObj toObj(InChargeDto dto) {
        if ( dto == null ) {
            return null;
        }

        InChargeObj inChargeObj = new InChargeObj();

        inChargeObj.setInChargers( iInChargerObjMapper.toObj( dto.getInChargers() ) );
        inChargeObj.setCriminalCaseId( dto.getCriminalCaseId() );
        inChargeObj.setUnitId( dto.getUnitId() );
        inChargeObj.setUnitName( dto.getUnitName() );
        inChargeObj.setUnitEngName( dto.getUnitEngName() );
        inChargeObj.setUnitSuperviserId( dto.getUnitSuperviserId() );
        inChargeObj.setUnitSuperviserName( dto.getUnitSuperviserName() );
        inChargeObj.setType( dto.getType() );
        inChargeObj.setReserve( dto.getReserve() );
        inChargeObj.setNotification( dto.getNotification() );

        return inChargeObj;
    }

    @Override
    public List<InChargeObj> toObj(List<InChargeDto> list) {
        if ( list == null ) {
            return null;
        }

        List<InChargeObj> list1 = new ArrayList<InChargeObj>( list.size() );
        for ( InChargeDto inChargeDto : list ) {
            list1.add( toObj( inChargeDto ) );
        }

        return list1;
    }

    @Override
    public Set<InChargeObj> toObj(Set<InChargeDto> set) {
        if ( set == null ) {
            return null;
        }

        Set<InChargeObj> set1 = new HashSet<InChargeObj>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( InChargeDto inChargeDto : set ) {
            set1.add( toObj( inChargeDto ) );
        }

        return set1;
    }
}
