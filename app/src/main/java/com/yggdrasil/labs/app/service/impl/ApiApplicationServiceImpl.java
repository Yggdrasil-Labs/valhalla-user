package com.yggdrasil.labs.app.service.impl;

import jakarta.annotation.Resource;

import org.springframework.stereotype.Service;

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
import com.yggdrasil.labs.app.service.ApiApplicationService;

/** API应用服务实现 */
@Service
public class ApiApplicationServiceImpl implements ApiApplicationService {

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
    public PageResponse<ApiCO> pageApi(PageApiQuery query) {
        return pageApiQueryExe.execute(query);
    }
}
