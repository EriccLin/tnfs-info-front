package com.tnfs.infoApplication.mapper;

import com.tnfs.infoApplication.dto.RoleDto;
import com.tnfs.infoApplication.model.RoleObj;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor"
)
public class IRoleObjMapperImpl implements IRoleObjMapper {

    @Override
    public RoleDto toDto(RoleObj obj) {
        if ( obj == null ) {
            return null;
        }

        RoleDto roleDto = new RoleDto();

        roleDto.setId( obj.getId() );
        roleDto.setName( obj.getName() );

        return roleDto;
    }

    @Override
    public List<RoleDto> toDto(List<RoleObj> list) {
        if ( list == null ) {
            return null;
        }

        List<RoleDto> list1 = new ArrayList<RoleDto>( list.size() );
        for ( RoleObj roleObj : list ) {
            list1.add( toDto( roleObj ) );
        }

        return list1;
    }

    @Override
    public Set<RoleDto> toDto(Set<RoleObj> set) {
        if ( set == null ) {
            return null;
        }

        Set<RoleDto> set1 = new HashSet<RoleDto>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( RoleObj roleObj : set ) {
            set1.add( toDto( roleObj ) );
        }

        return set1;
    }

    @Override
    public RoleObj toObj(RoleDto dto) {
        if ( dto == null ) {
            return null;
        }

        RoleObj roleObj = new RoleObj();

        roleObj.setId( dto.getId() );
        roleObj.setName( dto.getName() );

        return roleObj;
    }

    @Override
    public List<RoleObj> toObj(List<RoleDto> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<RoleObj> list = new ArrayList<RoleObj>( dtoList.size() );
        for ( RoleDto roleDto : dtoList ) {
            list.add( toObj( roleDto ) );
        }

        return list;
    }

    @Override
    public Set<RoleObj> toObj(Set<RoleDto> dtoSet) {
        if ( dtoSet == null ) {
            return null;
        }

        Set<RoleObj> set = new HashSet<RoleObj>( Math.max( (int) ( dtoSet.size() / .75f ) + 1, 16 ) );
        for ( RoleDto roleDto : dtoSet ) {
            set.add( toObj( roleDto ) );
        }

        return set;
    }
}
