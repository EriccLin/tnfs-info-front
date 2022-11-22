package com.tnfs.infoApplication.mapper;

import com.tnfs.infoApplication.dto.AccountDto;
import com.tnfs.infoApplication.dto.RoleDto;
import com.tnfs.infoApplication.model.AccountObj;
import com.tnfs.infoApplication.model.RoleObj;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.annotation.Generated;
import org.mapstruct.factory.Mappers;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor"
)
public class IAccountObjMapperImpl implements IAccountObjMapper {

    private final IRoleObjMapper iRoleObjMapper = Mappers.getMapper( IRoleObjMapper.class );

    @Override
    public AccountDto toDto(AccountObj obj) {
        if ( obj == null ) {
            return null;
        }

        AccountDto accountDto = new AccountDto();

        accountDto.setRoles( iRoleObjMapper.toDto( obj.getRoles() ) );
        accountDto.setId( obj.getId() );
        accountDto.setName( obj.getName() );
        accountDto.setEmail( obj.getEmail() );
        accountDto.setStatus( obj.getStatus() );
        accountDto.setCreatedAt( obj.getCreatedAt() );
        accountDto.setUpdatedAt( obj.getUpdatedAt() );
        accountDto.setPersonId( obj.getPersonId() );
        accountDto.setPersonName( obj.getPersonName() );
        accountDto.setPersonIsActive( obj.getPersonIsActive() );
        accountDto.setPersonBirthdate( obj.getPersonBirthdate() );

        return accountDto;
    }

    @Override
    public List<AccountDto> toDto(List<AccountObj> objList) {
        if ( objList == null ) {
            return null;
        }

        List<AccountDto> list = new ArrayList<AccountDto>( objList.size() );
        for ( AccountObj accountObj : objList ) {
            list.add( toDto( accountObj ) );
        }

        return list;
    }

    @Override
    public AccountObj toObj(AccountDto dto) {
        if ( dto == null ) {
            return null;
        }

        AccountObj accountObj = new AccountObj();

        accountObj.setRoles( roleDtoCollectionToRoleObjList( dto.getRoles() ) );
        accountObj.setId( dto.getId() );
        accountObj.setName( dto.getName() );
        accountObj.setEmail( dto.getEmail() );
        accountObj.setStatus( dto.getStatus() );
        accountObj.setCreatedAt( dto.getCreatedAt() );
        accountObj.setUpdatedAt( dto.getUpdatedAt() );
        accountObj.setPersonId( dto.getPersonId() );
        accountObj.setPersonName( dto.getPersonName() );
        accountObj.setPersonIsActive( dto.getPersonIsActive() );
        accountObj.setPersonBirthdate( dto.getPersonBirthdate() );

        return accountObj;
    }

    @Override
    public List<AccountObj> toObj(List<AccountDto> dto) {
        if ( dto == null ) {
            return null;
        }

        List<AccountObj> list = new ArrayList<AccountObj>( dto.size() );
        for ( AccountDto accountDto : dto ) {
            list.add( toObj( accountDto ) );
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
