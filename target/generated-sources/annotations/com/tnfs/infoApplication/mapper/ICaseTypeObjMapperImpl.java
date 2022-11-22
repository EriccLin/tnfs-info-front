package com.tnfs.infoApplication.mapper;

import com.tnfs.infoApplication.dto.CaseTypeDto;
import com.tnfs.infoApplication.model.CaseTypeObj;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor"
)
public class ICaseTypeObjMapperImpl implements ICaseTypeObjMapper {

    @Override
    public CaseTypeDto toDto(CaseTypeObj obj) {
        if ( obj == null ) {
            return null;
        }

        CaseTypeDto caseTypeDto = new CaseTypeDto();

        caseTypeDto.setId( obj.getId() );
        caseTypeDto.setName( obj.getName() );
        caseTypeDto.setCode( obj.getCode() );
        caseTypeDto.setType( obj.getType() );

        return caseTypeDto;
    }

    @Override
    public List<CaseTypeDto> toDto(List<CaseTypeObj> objList) {
        if ( objList == null ) {
            return null;
        }

        List<CaseTypeDto> list = new ArrayList<CaseTypeDto>( objList.size() );
        for ( CaseTypeObj caseTypeObj : objList ) {
            list.add( toDto( caseTypeObj ) );
        }

        return list;
    }

    @Override
    public Set<CaseTypeDto> toDto(Set<CaseTypeObj> objSet) {
        if ( objSet == null ) {
            return null;
        }

        Set<CaseTypeDto> set = new HashSet<CaseTypeDto>( Math.max( (int) ( objSet.size() / .75f ) + 1, 16 ) );
        for ( CaseTypeObj caseTypeObj : objSet ) {
            set.add( toDto( caseTypeObj ) );
        }

        return set;
    }

    @Override
    public CaseTypeObj toObj(CaseTypeDto dto) {
        if ( dto == null ) {
            return null;
        }

        CaseTypeObj caseTypeObj = new CaseTypeObj();

        caseTypeObj.setId( dto.getId() );
        caseTypeObj.setName( dto.getName() );
        caseTypeObj.setCode( dto.getCode() );
        caseTypeObj.setType( dto.getType() );

        return caseTypeObj;
    }

    @Override
    public List<CaseTypeObj> toObj(List<CaseTypeDto> list) {
        if ( list == null ) {
            return null;
        }

        List<CaseTypeObj> list1 = new ArrayList<CaseTypeObj>( list.size() );
        for ( CaseTypeDto caseTypeDto : list ) {
            list1.add( toObj( caseTypeDto ) );
        }

        return list1;
    }

    @Override
    public Set<CaseTypeObj> toObj(Set<CaseTypeDto> set) {
        if ( set == null ) {
            return null;
        }

        Set<CaseTypeObj> set1 = new HashSet<CaseTypeObj>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( CaseTypeDto caseTypeDto : set ) {
            set1.add( toObj( caseTypeDto ) );
        }

        return set1;
    }
}
