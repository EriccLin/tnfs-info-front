package com.tnfs.infoApplication.mapper;

import com.tnfs.infoApplication.dto.PersonDto;
import com.tnfs.infoApplication.model.PersonObj;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor"
)
public class IPersonObjMapperImpl implements IPersonObjMapper {

    @Override
    public PersonDto toDto(PersonObj obj) {
        if ( obj == null ) {
            return null;
        }

        PersonDto personDto = new PersonDto();

        personDto.setName( obj.getName() );
        personDto.setId( obj.getId() );
        personDto.setIdNumber( obj.getIdNumber() );
        personDto.setBirthdate( obj.getBirthdate() );
        personDto.setIsActive( obj.getIsActive() );
        personDto.setGender( obj.getGender() );

        return personDto;
    }

    @Override
    public List<PersonDto> toDto(List<PersonObj> list) {
        if ( list == null ) {
            return null;
        }

        List<PersonDto> list1 = new ArrayList<PersonDto>( list.size() );
        for ( PersonObj personObj : list ) {
            list1.add( toDto( personObj ) );
        }

        return list1;
    }

    @Override
    public Set<PersonDto> toDto(Set<PersonObj> set) {
        if ( set == null ) {
            return null;
        }

        Set<PersonDto> set1 = new HashSet<PersonDto>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( PersonObj personObj : set ) {
            set1.add( toDto( personObj ) );
        }

        return set1;
    }

    @Override
    public PersonObj toObj(PersonDto dto) {
        if ( dto == null ) {
            return null;
        }

        PersonObj personObj = new PersonObj();

        personObj.setName( dto.getName() );
        personObj.setId( dto.getId() );
        personObj.setBirthdate( dto.getBirthdate() );
        personObj.setIdNumber( dto.getIdNumber() );
        personObj.setIsActive( dto.getIsActive() );
        personObj.setGender( dto.getGender() );

        return personObj;
    }

    @Override
    public List<PersonObj> toObj(List<PersonDto> dtos) {
        if ( dtos == null ) {
            return null;
        }

        List<PersonObj> list = new ArrayList<PersonObj>( dtos.size() );
        for ( PersonDto personDto : dtos ) {
            list.add( toObj( personDto ) );
        }

        return list;
    }

    @Override
    public Set<PersonObj> toObj(Set<PersonDto> dtos) {
        if ( dtos == null ) {
            return null;
        }

        Set<PersonObj> set = new HashSet<PersonObj>( Math.max( (int) ( dtos.size() / .75f ) + 1, 16 ) );
        for ( PersonDto personDto : dtos ) {
            set.add( toObj( personDto ) );
        }

        return set;
    }
}
