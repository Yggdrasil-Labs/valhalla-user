package com.yggdrasil.labs.app.api;

import jakarta.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.cola.dto.MultiResponse;
import com.alibaba.cola.dto.Response;
import com.alibaba.cola.dto.SingleResponse;
import com.yggdrasil.labs.app.api.executor.CreateApiCmdExe;
import com.yggdrasil.labs.app.api.executor.DeleteApiCmdExe;
import com.yggdrasil.labs.app.api.executor.UpdateApiCmdExe;
import com.yggdrasil.labs.app.api.query.GetApiQueryExe;
import com.yggdrasil.labs.app.api.query.PageApiQueryExe;
import com.yggdrasil.labs.client.api.ApiClient;
import com.yggdrasil.labs.client.dto.api.cmd.CreateApiCmd;
import com.yggdrasil.labs.client.dto.api.cmd.DeleteApiCmd;
import com.yggdrasil.labs.client.dto.api.cmd.UpdateApiCmd;
import com.yggdrasil.labs.client.dto.api.co.ApiCO;
import com.yggdrasil.labs.client.dto.api.query.GetApiQuery;
import com.yggdrasil.labs.client.dto.api.query.PageApiQuery;

/**
 * API客户端实现
 *
 * @author YoungerYang-Y
 */
@Service
public class ApiClientImpl implements ApiClient {

    @Resource private CreateApiCmdExe createApiCmdExe;

    @Resource private UpdateApiCmdExe updateApiCmdExe;

    @Resource private DeleteApiCmdExe deleteApiCmdExe;

    @Resource private GetApiQueryExe getApiQueryExe;

    @Resource private PageApiQueryExe pageApiQueryExe;

    @Override
    public Response createApi(CreateApiCmd cmd) {
        return createApiCmdExe.execute(cmd);
    }

    @Override
    public Response updateApi(UpdateApiCmd cmd) {
        return updateApiCmdExe.execute(cmd);
    }

    @Override
    public Response deleteApi(DeleteApiCmd cmd) {
        return deleteApiCmdExe.execute(cmd);
    }

    @Override
    public SingleResponse<ApiCO> getApi(GetApiQuery query) {
        return getApiQueryExe.execute(query);
    }

    @Override
    public MultiResponse<ApiCO> pageApi(PageApiQuery query) {
        return pageApiQueryExe.execute(query);
    }
}
