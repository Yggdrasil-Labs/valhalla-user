package com.yggdrasil.labs.app.api.assembler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import com.yggdrasil.labs.app.api.dto.co.ApiCO;
import com.yggdrasil.labs.domain.api.model.Api;

/** {@link ApiAssembler} 单元测试（使用 MapStruct 生成的 INSTANCE） */
class ApiAssemblerTest {

    @Test
    void toCO_shouldMapFieldsAndConvertIdToString() {
        Api api = new Api();
        api.setId(123L);
        api.setApiCode("c");
        api.setVersion("v1");
        api.setApiName("n");
        api.setResourcePath("/p");
        api.setResourceMethod("GET");
        api.setStatus("ENABLED");
        api.setDescription("d");
        api.setMetadata("m");
        api.setCreateTime(LocalDateTime.now());
        api.setUpdateTime(LocalDateTime.now());

        ApiCO co = ApiAssembler.INSTANCE.toCO(api);

        assertEquals("123", co.getId());
        assertEquals("c", co.getApiCode());
        assertEquals("v1", co.getVersion());
        assertEquals("n", co.getApiName());
        assertEquals("/p", co.getResourcePath());
        assertEquals("GET", co.getResourceMethod());
        assertEquals("ENABLED", co.getStatus());
        assertEquals("d", co.getDescription());
        assertEquals("m", co.getMetadata());
    }

    @Test
    void toCO_whenIdNull_shouldMapNull() {
        Api api = new Api();

        ApiCO co = ApiAssembler.INSTANCE.toCO(api);

        assertNull(co.getId());
    }

    @Test
    void toCO_whenNull_shouldReturnNull() {
        assertNull(ApiAssembler.INSTANCE.toCO(null));
    }
}
