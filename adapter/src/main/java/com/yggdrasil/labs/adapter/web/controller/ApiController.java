package com.yggdrasil.labs.adapter.web.controller;

import jakarta.annotation.Resource;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.cola.dto.PageResponse;
import com.alibaba.cola.dto.Response;
import com.alibaba.cola.dto.SingleResponse;
import com.yggdrasil.labs.adapter.web.convert.ApiWebConverter;
import com.yggdrasil.labs.adapter.web.request.CreateApiRequest;
import com.yggdrasil.labs.adapter.web.request.PageApiRequest;
import com.yggdrasil.labs.adapter.web.request.UpdateApiRequest;
import com.yggdrasil.labs.app.api.dto.co.ApiCO;
import com.yggdrasil.labs.app.service.ApiApplicationService;

/**
 * API控制器
 *
 * @author YoungerYang-Y
 */
@RestController
@RequestMapping("/api/v1/apis")
public class ApiController {

    @Resource private ApiApplicationService apiApplicationService;

    @Resource private ApiWebConverter apiWebConverter;

    /**
     * 创建API
     *
     * @param request 创建API请求
     * @return 响应结果
     */
    @PostMapping
    public Response createApi(@Valid @RequestBody CreateApiRequest request) {
        return apiApplicationService.createApi(apiWebConverter.toCreateApiCmd(request));
    }

    /**
     * 更新API
     *
     * @param id 接口ID
     * @param request 更新API请求
     * @return 响应结果
     */
    @PutMapping("/{id}")
    public Response updateApi(@PathVariable Long id, @Valid @RequestBody UpdateApiRequest request) {
        request.setId(id);
        return apiApplicationService.updateApi(apiWebConverter.toUpdateApiCmd(request));
    }

    /**
     * 删除API
     *
     * @param id 接口ID
     * @return 响应结果
     */
    @DeleteMapping("/{id}")
    public Response deleteApi(@PathVariable Long id) {
        return apiApplicationService.deleteApi(apiWebConverter.toDeleteApiCmd(id));
    }

    /**
     * 获取API详情
     *
     * @param id 接口ID
     * @return API信息
     */
    @GetMapping("/{id}")
    public SingleResponse<ApiCO> getApi(@PathVariable Long id) {
        return apiApplicationService.getApi(apiWebConverter.toGetApiQuery(id));
    }

    /**
     * 分页查询API列表
     *
     * @param request 查询请求
     * @return API列表
     */
    @GetMapping
    public PageResponse<ApiCO> pageApi(@Valid PageApiRequest request) {
        return apiApplicationService.pageApi(apiWebConverter.toPageApiQuery(request));
    }
}
