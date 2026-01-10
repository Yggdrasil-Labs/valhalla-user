package com.yggdrasil.labs.app.service;

import com.alibaba.cola.dto.PageResponse;
import com.alibaba.cola.dto.Response;
import com.alibaba.cola.dto.SingleResponse;
import com.yggdrasil.labs.app.api.dto.cmd.CreateApiCmd;
import com.yggdrasil.labs.app.api.dto.cmd.DeleteApiCmd;
import com.yggdrasil.labs.app.api.dto.cmd.UpdateApiCmd;
import com.yggdrasil.labs.app.api.dto.co.ApiCO;
import com.yggdrasil.labs.app.api.dto.query.GetApiQuery;
import com.yggdrasil.labs.app.api.dto.query.PageApiQuery;

/** API应用服务 */
public interface ApiApplicationService {

    Response createApi(CreateApiCmd cmd);

    Response updateApi(UpdateApiCmd cmd);

    Response deleteApi(DeleteApiCmd cmd);

    SingleResponse<ApiCO> getApi(GetApiQuery query);

    PageResponse<ApiCO> pageApi(PageApiQuery query);
}
