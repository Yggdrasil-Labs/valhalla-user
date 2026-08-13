package com.yggdrasil.labs.app.api.query;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.alibaba.cola.dto.SingleResponse;
import com.yggdrasil.labs.app.api.assembler.ApiAssembler;
import com.yggdrasil.labs.app.api.dto.co.ApiCO;
import com.yggdrasil.labs.app.api.dto.query.GetApiQuery;
import com.yggdrasil.labs.app.common.dto.enums.ErrorCode;
import com.yggdrasil.labs.domain.api.model.Api;
import com.yggdrasil.labs.domain.api.repository.ApiRepository;

/** {@link GetApiQueryExe} 单元测试 */
@ExtendWith(MockitoExtension.class)
class GetApiQueryExeTest {

    @Mock private ApiRepository apiRepository;

    @Mock private ApiAssembler apiAssembler;

    @InjectMocks private GetApiQueryExe getApiQueryExe;

    @Test
    void execute_whenNotFound_shouldReturnFailure() {
        GetApiQuery query = new GetApiQuery();
        query.setId(1L);
        when(apiRepository.findById(1L)).thenReturn(null);

        SingleResponse<ApiCO> response = getApiQueryExe.execute(query);

        assertFalse(response.isSuccess());
        assertEquals(ErrorCode.B_API_NOT_FOUND.getErrCode(), response.getErrCode());
    }

    @Test
    void execute_whenFound_shouldReturnCo() {
        Api api = new Api();
        api.setId(5L);
        when(apiRepository.findById(5L)).thenReturn(api);
        ApiCO co = new ApiCO();
        when(apiAssembler.toCO(api)).thenReturn(co);

        GetApiQuery query = new GetApiQuery();
        query.setId(5L);

        SingleResponse<ApiCO> response = getApiQueryExe.execute(query);

        assertTrue(response.isSuccess());
    }
}
