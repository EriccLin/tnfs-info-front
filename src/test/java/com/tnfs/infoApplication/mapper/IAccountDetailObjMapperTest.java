package com.tnfs.infoApplication.mapper;

import com.tnfs.infoApplication.dto.AccountDetailDto;
import com.tnfs.infoApplication.model.AccountDetailObj;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class IAccountDetailObjMapperTest {
    @Test
    public void toDtoAndBack() {
        IAccountDetailObjMapper accountDetailObjMapper = IAccountDetailObjMapper.INSTANCE;;
        AccountDetailObj obj = new AccountDetailObj(1L, "Alaska Malamutes", "test.01@mail.test.com", "PENDING",
                LocalDateTime.now().minusDays(10), LocalDateTime.now(),
                1L, "linjiayuan", false, LocalDate.of(1991, 1,3), "A123456789", "Aa123456789");
        AccountDetailDto dto = accountDetailObjMapper.toDto(obj);
        AccountDetailObj actual = accountDetailObjMapper.toObj(dto);
        assertEquals(obj.getId(), actual.getPersonId());
        assertEquals(obj.getName(), actual.getName());
        assertEquals(obj.getEmail(), actual.getEmail());
        assertEquals(obj.getStatus(), actual.getStatus());
        assertEquals(obj.getCreatedAt(), actual.getCreatedAt());
        assertEquals(obj.getUpdatedAt(), actual.getUpdatedAt());

        assertEquals(obj.getPersonId(), actual.getPersonId());
        assertEquals(obj.getPersonName(), actual.getPersonName());
        assertEquals(obj.getPersonIsActive(), actual.getPersonIsActive());
        assertEquals(obj.getPersonBirthdate(), actual.getPersonBirthdate());
        assertEquals(obj.getPassword(), actual.getPassword());
        assertEquals(obj.getPersonIdNumber(), actual.getPersonIdNumber());
    }
}