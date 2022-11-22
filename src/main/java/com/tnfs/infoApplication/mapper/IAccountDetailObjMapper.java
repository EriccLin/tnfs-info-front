package com.tnfs.infoApplication.mapper;

import com.tnfs.infoApplication.dto.AccountDetailDto;
import com.tnfs.infoApplication.dto.AccountDto;
import com.tnfs.infoApplication.model.AccountDetailObj;
import com.tnfs.infoApplication.model.AccountObj;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(uses = {IRoleObjMapper.class})
public interface IAccountDetailObjMapper {
    IAccountDetailObjMapper INSTANCE = Mappers.getMapper(IAccountDetailObjMapper.class);

    @Mappings({
            @Mapping(source = "obj.id", target = "id"),
            @Mapping(source = "obj.name", target = "name"),
            @Mapping(source = "obj.email", target = "email"),
            @Mapping(source = "obj.status", target = "status"),
            @Mapping(source = "obj.createdAt", target = "createdAt"),
            @Mapping(source = "obj.updatedAt", target = "updatedAt"),
            @Mapping(source = "obj.personId", target = "personId"),
            @Mapping(source = "obj.personName", target = "personName"),
            @Mapping(source = "obj.personIsActive", target = "personIsActive"),
            @Mapping(source = "obj.personBirthdate", target = "personBirthdate"),
            @Mapping(source = "obj.personIdNumber", target = "personIdNumber"),
            @Mapping(source = "obj.password", target = "password"),
    })
    public AccountDetailDto toDto(AccountDetailObj obj);

    public List<AccountDetailDto> toDto(List<AccountDetailObj> objList);

    @InheritInverseConfiguration(name = "toDto")
    public AccountDetailObj toObj(AccountDetailDto dto);

    @InheritInverseConfiguration(name = "toDto")
    public List<AccountDetailObj> toObj(List<AccountDetailDto> dto);
}
