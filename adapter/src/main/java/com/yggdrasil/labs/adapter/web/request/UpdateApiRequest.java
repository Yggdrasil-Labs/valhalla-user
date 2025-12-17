package com.yggdrasil.labs.adapter.web.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.Data;

/**
 * 更新API请求
 *
 * @author YoungerYang-Y
 */
@Data
public class UpdateApiRequest {

    /** 接口ID */
    @NotNull(message = "接口ID不能为空")
    private Long id;

    /** 接口名称 */
    @NotBlank(message = "接口名称不能为空")
    private String apiName;

    /** 接口描述 */
    private String description;

    /** 扩展信息（JSON） */
    private String metadata;
}
