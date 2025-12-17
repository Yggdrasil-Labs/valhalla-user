package com.yggdrasil.labs.adapter.web.convert;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.yggdrasil.labs.adapter.web.request.CreateApiRequest;
import com.yggdrasil.labs.adapter.web.request.PageApiRequest;
import com.yggdrasil.labs.adapter.web.request.UpdateApiRequest;
import com.yggdrasil.labs.client.dto.api.cmd.CreateApiCmd;
import com.yggdrasil.labs.client.dto.api.cmd.DeleteApiCmd;
import com.yggdrasil.labs.client.dto.api.cmd.UpdateApiCmd;
import com.yggdrasil.labs.client.dto.api.query.GetApiQuery;
import com.yggdrasil.labs.client.dto.api.query.PageApiQuery;

/**
 * API Web转换器
 *
 * @author YoungerYang-Y
 */
@Mapper(componentModel = "spring")
public interface ApiWebConverter {

    ApiWebConverter INSTANCE = Mappers.getMapper(ApiWebConverter.class);

    /**
     * Request转CreateApiCmd
     *
     * @param request 创建API请求
     * @return 创建API命令
     */
    CreateApiCmd toCreateApiCmd(CreateApiRequest request);

    /**
     * Request转UpdateApiCmd
     *
     * @param request 更新API请求
     * @return 更新API命令
     */
    UpdateApiCmd toUpdateApiCmd(UpdateApiRequest request);

    /**
     * Request转PageApiQuery
     *
     * @param request 分页查询API请求
     * @return 分页查询API查询
     */
    PageApiQuery toPageApiQuery(PageApiRequest request);

    /**
     * ID转GetApiQuery
     *
     * @param id API ID
     * @return 获取API查询
     */
    default GetApiQuery toGetApiQuery(Long id) {
        GetApiQuery query = new GetApiQuery();
        query.setId(id);
        return query;
    }

    /**
     * ID转DeleteApiCmd
     *
     * @param id API ID
     * @return 删除API命令
     */
    default DeleteApiCmd toDeleteApiCmd(Long id) {
        DeleteApiCmd cmd = new DeleteApiCmd();
        cmd.setId(id);
        return cmd;
    }
}
