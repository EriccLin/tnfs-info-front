package com.tnfs.infoApplication.mapper;

import com.tnfs.infoApplication.dto.InvestigationDto;
import com.tnfs.infoApplication.dto.InvestigationMemberDto;
import com.tnfs.infoApplication.model.InvestigationMemberObj;
import com.tnfs.infoApplication.model.InvestigationObj;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor"
)
public class IInvestigationObjMapperImpl implements IInvestigationObjMapper {

    @Override
    public InvestigationDto toDto(InvestigationObj obj) {
        if ( obj == null ) {
            return null;
        }

        InvestigationDto investigationDto = new InvestigationDto();

        investigationDto.setInvestigationMembers( investigationMemberObjListToInvestigationMemberDtoList( obj.getInvestigationMembers() ) );
        investigationDto.setId( obj.getId() );
        investigationDto.setCriminalCaseId( obj.getCriminalCaseId() );
        investigationDto.setStime( obj.getStime() );
        investigationDto.setEtime( obj.getEtime() );
        investigationDto.setLocation( obj.getLocation() );
        investigationDto.setType( obj.getType() );

        return investigationDto;
    }

    @Override
    public List<InvestigationDto> toDto(List<InvestigationObj> list) {
        if ( list == null ) {
            return null;
        }

        List<InvestigationDto> list1 = new ArrayList<InvestigationDto>( list.size() );
        for ( InvestigationObj investigationObj : list ) {
            list1.add( toDto( investigationObj ) );
        }

        return list1;
    }

    @Override
    public Set<InvestigationDto> toDto(Set<InvestigationObj> set) {
        if ( set == null ) {
            return null;
        }

        Set<InvestigationDto> set1 = new HashSet<InvestigationDto>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( InvestigationObj investigationObj : set ) {
            set1.add( toDto( investigationObj ) );
        }

        return set1;
    }

    @Override
    public InvestigationObj toObj(InvestigationDto dto) {
        if ( dto == null ) {
            return null;
        }

        InvestigationObj investigationObj = new InvestigationObj();

        investigationObj.setInvestigationMembers( investigationMemberDtoListToInvestigationMemberObjList( dto.getInvestigationMembers() ) );
        investigationObj.setId( dto.getId() );
        investigationObj.setCriminalCaseId( dto.getCriminalCaseId() );
        investigationObj.setStime( dto.getStime() );
        investigationObj.setEtime( dto.getEtime() );
        investigationObj.setLocation( dto.getLocation() );
        investigationObj.setType( dto.getType() );

        return investigationObj;
    }

    @Override
    public List<InvestigationObj> toObj(List<InvestigationDto> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<InvestigationObj> list = new ArrayList<InvestigationObj>( dtoList.size() );
        for ( InvestigationDto investigationDto : dtoList ) {
            list.add( toObj( investigationDto ) );
        }

        return list;
    }

    @Override
    public Set<InvestigationObj> toObj(Set<InvestigationDto> dtoSet) {
        if ( dtoSet == null ) {
            return null;
        }

        Set<InvestigationObj> set = new HashSet<InvestigationObj>( Math.max( (int) ( dtoSet.size() / .75f ) + 1, 16 ) );
        for ( InvestigationDto investigationDto : dtoSet ) {
            set.add( toObj( investigationDto ) );
        }

        return set;
    }

    protected InvestigationMemberDto investigationMemberObjToInvestigationMemberDto(InvestigationMemberObj investigationMemberObj) {
        if ( investigationMemberObj == null ) {
            return null;
        }

        InvestigationMemberDto investigationMemberDto = new InvestigationMemberDto();

        investigationMemberDto.setId( investigationMemberObj.getId() );
        investigationMemberDto.setInvestigationId( investigationMemberObj.getInvestigationId() );
        investigationMemberDto.setMemberId( investigationMemberObj.getMemberId() );
        investigationMemberDto.setMrank( investigationMemberObj.getMrank() );
        investigationMemberDto.setPersonId( investigationMemberObj.getPersonId() );
        investigationMemberDto.setPersonName( investigationMemberObj.getPersonName() );
        investigationMemberDto.setUnitId( investigationMemberObj.getUnitId() );
        investigationMemberDto.setUnitName( investigationMemberObj.getUnitName() );
        investigationMemberDto.setUnitSuperviserId( investigationMemberObj.getUnitSuperviserId() );
        investigationMemberDto.setUnitSuperviserName( investigationMemberObj.getUnitSuperviserName() );
        investigationMemberDto.setUnitLevel( investigationMemberObj.getUnitLevel() );
        investigationMemberDto.setTask( investigationMemberObj.getTask() );

        return investigationMemberDto;
    }

    protected List<InvestigationMemberDto> investigationMemberObjListToInvestigationMemberDtoList(List<InvestigationMemberObj> list) {
        if ( list == null ) {
            return null;
        }

        List<InvestigationMemberDto> list1 = new ArrayList<InvestigationMemberDto>( list.size() );
        for ( InvestigationMemberObj investigationMemberObj : list ) {
            list1.add( investigationMemberObjToInvestigationMemberDto( investigationMemberObj ) );
        }

        return list1;
    }

    protected InvestigationMemberObj investigationMemberDtoToInvestigationMemberObj(InvestigationMemberDto investigationMemberDto) {
        if ( investigationMemberDto == null ) {
            return null;
        }

        InvestigationMemberObj investigationMemberObj = new InvestigationMemberObj();

        investigationMemberObj.setId( investigationMemberDto.getId() );
        investigationMemberObj.setInvestigationId( investigationMemberDto.getInvestigationId() );
        investigationMemberObj.setMemberId( investigationMemberDto.getMemberId() );
        investigationMemberObj.setMrank( investigationMemberDto.getMrank() );
        investigationMemberObj.setPersonId( investigationMemberDto.getPersonId() );
        investigationMemberObj.setPersonName( investigationMemberDto.getPersonName() );
        investigationMemberObj.setUnitId( investigationMemberDto.getUnitId() );
        investigationMemberObj.setUnitName( investigationMemberDto.getUnitName() );
        investigationMemberObj.setUnitSuperviserId( investigationMemberDto.getUnitSuperviserId() );
        investigationMemberObj.setUnitSuperviserName( investigationMemberDto.getUnitSuperviserName() );
        investigationMemberObj.setUnitLevel( investigationMemberDto.getUnitLevel() );
        investigationMemberObj.setTask( investigationMemberDto.getTask() );

        return investigationMemberObj;
    }

    protected List<InvestigationMemberObj> investigationMemberDtoListToInvestigationMemberObjList(List<InvestigationMemberDto> list) {
        if ( list == null ) {
            return null;
        }

        List<InvestigationMemberObj> list1 = new ArrayList<InvestigationMemberObj>( list.size() );
        for ( InvestigationMemberDto investigationMemberDto : list ) {
            list1.add( investigationMemberDtoToInvestigationMemberObj( investigationMemberDto ) );
        }

        return list1;
    }
}
