package com.yggdrasil.labs.client.api;

import com.alibaba.cola.dto.MultiResponse;
import com.alibaba.cola.dto.Response;
import com.alibaba.cola.dto.SingleResponse;
import com.yggdrasil.labs.client.dto.api.cmd.CreateApiCmd;
import com.yggdrasil.labs.client.dto.api.cmd.DeleteApiCmd;
import com.yggdrasil.labs.client.dto.api.cmd.UpdateApiCmd;
import com.yggdrasil.labs.client.dto.api.co.ApiCO;
import com.yggdrasil.labs.client.dto.api.query.GetApiQuery;
import com.yggdrasil.labs.client.dto.api.query.PageApiQuery;

/**
 * API客户端接口
 *
 * @author YoungerYang-Y
 */
public interface ApiClient {

    /**
     * 创建API
     *
     * @param cmd 创建API命令
     * @return 响应结果
     */
    Response createApi(CreateApiCmd cmd);

    /**
     * 更新API
     *
     * @param cmd 更新API命令
     * @return 响应结果
     */
    Response updateApi(UpdateApiCmd cmd);

    /**
     * 删除API
     *
     * @param cmd 删除API命令
     * @return 响应结果
     */
    Response deleteApi(DeleteApiCmd cmd);

    /**
     * 获取API详情
     *
     * @param query 查询条件
     * @return API信息
     */
    SingleResponse<ApiCO> getApi(GetApiQuery query);

    /**
     * 分页查询API列表
     *
     * @param query 查询条件
     * @return API列表
     */
    MultiResponse<ApiCO> pageApi(PageApiQuery query);
}
