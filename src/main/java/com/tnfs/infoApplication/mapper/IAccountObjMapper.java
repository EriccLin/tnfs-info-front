package com.tnfs.infoApplication.mapper;

import com.tnfs.infoApplication.dto.AccountDto;
import com.tnfs.infoApplication.model.AccountObj;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(uses = {IRoleObjMapper.class})
public interface IAccountObjMapper {
    IAccountObjMapper INSTANCE = Mappers.getMapper(IAccountObjMapper.class);

    @Mappings({
            @Mapping(source = "roles", target = "roles"),
    })
    public AccountDto toDto(AccountObj obj);
    public List<AccountDto> toDto(List<AccountObj> objList);

    @InheritInverseConfiguration(name = "toDto")
    public AccountObj toObj(AccountDto dto);

    @InheritInverseConfiguration(name = "toDto")
    public List<AccountObj> toObj(List<AccountDto> dto);
}
