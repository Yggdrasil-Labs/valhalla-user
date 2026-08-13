package com.yggdrasil.labs.app.service.impl;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.alibaba.cola.dto.PageResponse;
import com.alibaba.cola.dto.Response;
import com.alibaba.cola.dto.SingleResponse;
import com.yggdrasil.labs.app.api.dto.cmd.CreateApiCmd;
import com.yggdrasil.labs.app.api.dto.cmd.DeleteApiCmd;
import com.yggdrasil.labs.app.api.dto.cmd.UpdateApiCmd;
import com.yggdrasil.labs.app.api.dto.co.ApiCO;
import com.yggdrasil.labs.app.api.dto.query.GetApiQuery;
import com.yggdrasil.labs.app.api.dto.query.PageApiQuery;
import com.yggdrasil.labs.app.api.executor.CreateApiCmdExe;
import com.yggdrasil.labs.app.api.executor.DeleteApiCmdExe;
import com.yggdrasil.labs.app.api.executor.UpdateApiCmdExe;
import com.yggdrasil.labs.app.api.query.GetApiQueryExe;
import com.yggdrasil.labs.app.api.query.PageApiQueryExe;

/** {@link ApiApplicationServiceImpl} 单元测试（验证委派） */
@ExtendWith(MockitoExtension.class)
class ApiApplicationServiceImplTest {

    @Mock private CreateApiCmdExe createApiCmdExe;
    @Mock private UpdateApiCmdExe updateApiCmdExe;
    @Mock private DeleteApiCmdExe deleteApiCmdExe;
    @Mock private GetApiQueryExe getApiQueryExe;
    @Mock private PageApiQueryExe pageApiQueryExe;

    @InjectMocks private ApiApplicationServiceImpl service;

    @Test
    void createApi_shouldDelegate() {
        CreateApiCmd cmd = new CreateApiCmd();
        Response resp = Response.buildSuccess();
        when(createApiCmdExe.execute(cmd)).thenReturn(resp);
        assertSame(resp, service.createApi(cmd));
        verify(createApiCmdExe).execute(cmd);
    }

    @Test
    void updateApi_shouldDelegate() {
        UpdateApiCmd cmd = new UpdateApiCmd();
        Response resp = Response.buildSuccess();
        when(updateApiCmdExe.execute(cmd)).thenReturn(resp);
        assertSame(resp, service.updateApi(cmd));
        verify(updateApiCmdExe).execute(cmd);
    }

    @Test
    void deleteApi_shouldDelegate() {
        DeleteApiCmd cmd = new DeleteApiCmd();
        Response resp = Response.buildSuccess();
        when(deleteApiCmdExe.execute(cmd)).thenReturn(resp);
        assertSame(resp, service.deleteApi(cmd));
        verify(deleteApiCmdExe).execute(cmd);
    }

    @Test
    void getApi_shouldDelegate() {
        GetApiQuery query = new GetApiQuery();
        SingleResponse<ApiCO> resp = SingleResponse.of(new ApiCO());
        when(getApiQueryExe.execute(query)).thenReturn(resp);
        assertSame(resp, service.getApi(query));
        verify(getApiQueryExe).execute(query);
    }

    @Test
    void pageApi_shouldDelegate() {
        PageApiQuery query = new PageApiQuery();
        PageResponse<ApiCO> resp = PageResponse.of(List.of(), 0, 10, 0);
        when(pageApiQueryExe.execute(query)).thenReturn(resp);
        assertSame(resp, service.pageApi(query));
        verify(pageApiQueryExe).execute(query);
    }
}
