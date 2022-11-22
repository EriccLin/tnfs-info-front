package com.tnfs.infoApplication.mapper;

import com.tnfs.infoApplication.dto.AccountDto;
import com.tnfs.infoApplication.model.AccountObj;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class IAccountObjMapperTest {
    @Test
    public void toDtoAndBack() {
        IAccountObjMapper accountObjMapper = IAccountObjMapper.INSTANCE;
        AccountObj obj = new AccountObj(1L, "Alaska Malamutes", "test.01@mail.test.com", "PENDING",
                LocalDateTime.now().minusDays(10), LocalDateTime.now(),
                1L, "linjiayuan", false, LocalDate.of(1991, 1,3));
        AccountDto dto = accountObjMapper.toDto(obj);
        AccountObj actual = accountObjMapper.toObj(dto);
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
    }
}