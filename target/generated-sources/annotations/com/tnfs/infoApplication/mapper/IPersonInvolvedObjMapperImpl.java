package com.tnfs.infoApplication.mapper;

import com.tnfs.infoApplication.dto.PersonInvolvedDto;
import com.tnfs.infoApplication.model.PersonInvolvedObj;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor"
)
public class IPersonInvolvedObjMapperImpl implements IPersonInvolvedObjMapper {

    @Override
    public PersonInvolvedDto toDto(PersonInvolvedObj obj) {
        if ( obj == null ) {
            return null;
        }

        PersonInvolvedDto personInvolvedDto = new PersonInvolvedDto();

        personInvolvedDto.setName( obj.getName() );
        personInvolvedDto.setCriminalCaseId( obj.getCriminalCaseId() );
        personInvolvedDto.setPersonId( obj.getPersonId() );
        personInvolvedDto.setBirthdate( obj.getBirthdate() );
        personInvolvedDto.setIdNumber( obj.getIdNumber() );
        personInvolvedDto.setIsActive( obj.getIsActive() );
        personInvolvedDto.setIsUnknown( obj.getIsUnknown() );
        personInvolvedDto.setCountry( obj.getCountry() );
        personInvolvedDto.setType( obj.getType() );
        personInvolvedDto.setReserve( obj.getReserve() );

        return personInvolvedDto;
    }

    @Override
    public List<PersonInvolvedDto> toDto(List<PersonInvolvedObj> list) {
        if ( list == null ) {
            return null;
        }

        List<PersonInvolvedDto> list1 = new ArrayList<PersonInvolvedDto>( list.size() );
        for ( PersonInvolvedObj personInvolvedObj : list ) {
            list1.add( toDto( personInvolvedObj ) );
        }

        return list1;
    }

    @Override
    public Set<PersonInvolvedDto> toDto(Set<PersonInvolvedObj> set) {
        if ( set == null ) {
            return null;
        }

        Set<PersonInvolvedDto> set1 = new HashSet<PersonInvolvedDto>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( PersonInvolvedObj personInvolvedObj : set ) {
            set1.add( toDto( personInvolvedObj ) );
        }

        return set1;
    }

    @Override
    public PersonInvolvedObj toObj(PersonInvolvedDto dto) {
        if ( dto == null ) {
            return null;
        }

        PersonInvolvedObj personInvolvedObj = new PersonInvolvedObj();

        personInvolvedObj.setName( dto.getName() );
        personInvolvedObj.setCriminalCaseId( dto.getCriminalCaseId() );
        personInvolvedObj.setPersonId( dto.getPersonId() );
        personInvolvedObj.setBirthdate( dto.getBirthdate() );
        personInvolvedObj.setIdNumber( dto.getIdNumber() );
        personInvolvedObj.setIsActive( dto.getIsActive() );
        personInvolvedObj.setIsUnknown( dto.getIsUnknown() );
        personInvolvedObj.setCountry( dto.getCountry() );
        personInvolvedObj.setType( dto.getType() );
        personInvolvedObj.setReserve( dto.getReserve() );

        return personInvolvedObj;
    }

    @Override
    public List<PersonInvolvedObj> toObj(List<PersonInvolvedDto> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<PersonInvolvedObj> list = new ArrayList<PersonInvolvedObj>( dtoList.size() );
        for ( PersonInvolvedDto personInvolvedDto : dtoList ) {
            list.add( toObj( personInvolvedDto ) );
        }

        return list;
    }

    @Override
    public Set<PersonInvolvedObj> toObj(Set<PersonInvolvedDto> dtoSet) {
        if ( dtoSet == null ) {
            return null;
        }

        Set<PersonInvolvedObj> set = new HashSet<PersonInvolvedObj>( Math.max( (int) ( dtoSet.size() / .75f ) + 1, 16 ) );
        for ( PersonInvolvedDto personInvolvedDto : dtoSet ) {
            set.add( toObj( personInvolvedDto ) );
        }

        return set;
    }
}
