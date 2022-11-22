package com.tnfs.infoApplication.mapper;

import com.tnfs.infoApplication.dto.MemberDto;
import com.tnfs.infoApplication.model.MemberObj;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor"
)
public class IInvestigationMemberObjMapperImpl implements IInvestigationMemberObjMapper {

    @Override
    public MemberDto toDto(MemberObj obj) {
        if ( obj == null ) {
            return null;
        }

        MemberDto memberDto = new MemberDto();

        memberDto.setId( obj.getId() );
        memberDto.setSdate( obj.getSdate() );
        memberDto.setEdate( obj.getEdate() );
        memberDto.setMrank( obj.getMrank() );
        memberDto.setPersonId( obj.getPersonId() );
        memberDto.setPersonName( obj.getPersonName() );
        memberDto.setUnitId( obj.getUnitId() );
        memberDto.setUnitName( obj.getUnitName() );
        memberDto.setUnitSuperviserId( obj.getUnitSuperviserId() );
        memberDto.setUnitSuperviserName( obj.getUnitSuperviserName() );

        return memberDto;
    }

    @Override
    public List<MemberDto> toDto(List<MemberObj> list) {
        if ( list == null ) {
            return null;
        }

        List<MemberDto> list1 = new ArrayList<MemberDto>( list.size() );
        for ( MemberObj memberObj : list ) {
            list1.add( toDto( memberObj ) );
        }

        return list1;
    }

    @Override
    public MemberObj toObj(MemberDto dto) {
        if ( dto == null ) {
            return null;
        }

        MemberObj memberObj = new MemberObj();

        memberObj.setId( dto.getId() );
        memberObj.setSdate( dto.getSdate() );
        memberObj.setEdate( dto.getEdate() );
        memberObj.setMrank( dto.getMrank() );
        memberObj.setPersonId( dto.getPersonId() );
        memberObj.setPersonName( dto.getPersonName() );
        memberObj.setUnitId( dto.getUnitId() );
        memberObj.setUnitName( dto.getUnitName() );
        memberObj.setUnitSuperviserId( dto.getUnitSuperviserId() );
        memberObj.setUnitSuperviserName( dto.getUnitSuperviserName() );

        return memberObj;
    }

    @Override
    public List<MemberObj> toObj(List<MemberDto> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<MemberObj> list = new ArrayList<MemberObj>( dtoList.size() );
        for ( MemberDto memberDto : dtoList ) {
            list.add( toObj( memberDto ) );
        }

        return list;
    }
}
