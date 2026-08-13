package com.yggdrasil.labs.app.api.query;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.alibaba.cola.dto.PageResponse;
import com.yggdrasil.labs.app.api.assembler.ApiAssembler;
import com.yggdrasil.labs.app.api.dto.co.ApiCO;
import com.yggdrasil.labs.app.api.dto.query.PageApiQuery;
import com.yggdrasil.labs.domain.api.model.Api;
import com.yggdrasil.labs.domain.api.repository.ApiRepository;
import com.yggdrasil.labs.domain.common.PageResult;

/** {@link PageApiQueryExe} 单元测试 */
@ExtendWith(MockitoExtension.class)
class PageApiQueryExeTest {

    @Mock private ApiRepository apiRepository;

    @Mock private ApiAssembler apiAssembler;

    @InjectMocks private PageApiQueryExe pageApiQueryExe;

    @Test
    void execute_shouldReturnPagedApis() {
        Api api = new Api();
        api.setId(1L);
        api.setApiCode("c");
        when(apiRepository.findPage("code", "v1", "/p", "GET", "ENABLED", 1, 10))
                .thenReturn(new PageResult<>(Arrays.asList(api), 1L));
        ApiCO co = new ApiCO();
        when(apiAssembler.toCO(api)).thenReturn(co);

        PageApiQuery query = new PageApiQuery();
        query.setApiCode("code");
        query.setVersion("v1");
        query.setResourcePath("/p");
        query.setResourceMethod("GET");
        query.setStatus("ENABLED");
        query.setPageNum(1);
        query.setPageSize(10);

        PageResponse<ApiCO> response = pageApiQueryExe.execute(query);

        assertTrue(response.isSuccess());
        assertEquals(1, response.getData().size());
        assertEquals(1L, response.getTotalCount());
    }

    @Test
    void execute_whenNoFilters_shouldPassNulls() {
        Api api = new Api();
        api.setId(2L);
        when(apiRepository.findPage(null, null, null, null, null, 1, 10))
                .thenReturn(new PageResult<>(Arrays.asList(api), 1L));
        ApiCO co = new ApiCO();
        when(apiAssembler.toCO(api)).thenReturn(co);

        PageApiQuery query = new PageApiQuery();

        PageResponse<ApiCO> response = pageApiQueryExe.execute(query);

        assertTrue(response.isSuccess());
        assertEquals(1, response.getData().size());
    }
}
