package com.yggdrasil.labs.client.dto.api.cmd;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import com.alibaba.cola.dto.Command;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 更新API命令
 *
 * @author YoungerYang-Y
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class UpdateApiCmd extends Command {

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
