package com.yggdrasil.labs.adapter.web.request;

import jakarta.validation.constraints.NotBlank;

import lombok.Data;

/**
 * 创建API请求
 *
 * @author YoungerYang-Y
 */
@Data
public class CreateApiRequest {

    /** 接口代码 */
    @NotBlank(message = "接口代码不能为空")
    private String apiCode;

    /** 接口名称 */
    @NotBlank(message = "接口名称不能为空")
    private String apiName;

    /** 资源路径（API路径） */
    @NotBlank(message = "资源路径不能为空")
    private String resourcePath;

    /** HTTP方法（GET、POST、PUT、DELETE等） */
    @NotBlank(message = "HTTP方法不能为空")
    private String resourceMethod;

    /** 接口描述 */
    private String description;

    /** 扩展信息（JSON） */
    private String metadata;
}
