package com.tnfs.infoApplication.mapper;

import com.tnfs.infoApplication.dto.AccountDetailDto;
import com.tnfs.infoApplication.dto.RoleDto;
import com.tnfs.infoApplication.model.AccountDetailObj;
import com.tnfs.infoApplication.model.RoleObj;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.annotation.Generated;
import org.mapstruct.factory.Mappers;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor"
)
public class IAccountDetailObjMapperImpl implements IAccountDetailObjMapper {

    private final IRoleObjMapper iRoleObjMapper = Mappers.getMapper( IRoleObjMapper.class );

    @Override
    public AccountDetailDto toDto(AccountDetailObj obj) {
        if ( obj == null ) {
            return null;
        }

        AccountDetailDto accountDetailDto = new AccountDetailDto();

        accountDetailDto.setId( obj.getId() );
        accountDetailDto.setName( obj.getName() );
        accountDetailDto.setEmail( obj.getEmail() );
        accountDetailDto.setStatus( obj.getStatus() );
        accountDetailDto.setCreatedAt( obj.getCreatedAt() );
        accountDetailDto.setUpdatedAt( obj.getUpdatedAt() );
        accountDetailDto.setPersonId( obj.getPersonId() );
        accountDetailDto.setPersonName( obj.getPersonName() );
        accountDetailDto.setPersonIsActive( obj.getPersonIsActive() );
        accountDetailDto.setPersonBirthdate( obj.getPersonBirthdate() );
        accountDetailDto.setPersonIdNumber( obj.getPersonIdNumber() );
        accountDetailDto.setPassword( obj.getPassword() );
        accountDetailDto.setRoles( iRoleObjMapper.toDto( obj.getRoles() ) );

        return accountDetailDto;
    }

    @Override
    public List<AccountDetailDto> toDto(List<AccountDetailObj> objList) {
        if ( objList == null ) {
            return null;
        }

        List<AccountDetailDto> list = new ArrayList<AccountDetailDto>( objList.size() );
        for ( AccountDetailObj accountDetailObj : objList ) {
            list.add( toDto( accountDetailObj ) );
        }

        return list;
    }

    @Override
    public AccountDetailObj toObj(AccountDetailDto dto) {
        if ( dto == null ) {
            return null;
        }

        AccountDetailObj accountDetailObj = new AccountDetailObj();

        accountDetailObj.setId( dto.getId() );
        accountDetailObj.setName( dto.getName() );
        accountDetailObj.setEmail( dto.getEmail() );
        accountDetailObj.setStatus( dto.getStatus() );
        accountDetailObj.setCreatedAt( dto.getCreatedAt() );
        accountDetailObj.setUpdatedAt( dto.getUpdatedAt() );
        accountDetailObj.setPersonId( dto.getPersonId() );
        accountDetailObj.setPersonName( dto.getPersonName() );
        accountDetailObj.setPersonIsActive( dto.getPersonIsActive() );
        accountDetailObj.setPersonBirthdate( dto.getPersonBirthdate() );
        accountDetailObj.setPersonIdNumber( dto.getPersonIdNumber() );
        accountDetailObj.setPassword( dto.getPassword() );
        accountDetailObj.setRoles( roleDtoCollectionToRoleObjList( dto.getRoles() ) );

        return accountDetailObj;
    }

    @Override
    public List<AccountDetailObj> toObj(List<AccountDetailDto> dto) {
        if ( dto == null ) {
            return null;
        }

        List<AccountDetailObj> list = new ArrayList<AccountDetailObj>( dto.size() );
        for ( AccountDetailDto accountDetailDto : dto ) {
            list.add( toObj( accountDetailDto ) );
        }

        return list;
    }

    protected List<RoleObj> roleDtoCollectionToRoleObjList(Collection<RoleDto> collection) {
        if ( collection == null ) {
            return null;
        }

        List<RoleObj> list = new ArrayList<RoleObj>( collection.size() );
        for ( RoleDto roleDto : collection ) {
            list.add( iRoleObjMapper.toObj( roleDto ) );
        }

        return list;
    }
}
