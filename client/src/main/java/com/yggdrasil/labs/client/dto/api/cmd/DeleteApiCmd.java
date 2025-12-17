package com.yggdrasil.labs.client.dto.api.cmd;

import jakarta.validation.constraints.NotNull;

import com.alibaba.cola.dto.Command;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 删除API命令
 *
 * @author YoungerYang-Y
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class DeleteApiCmd extends Command {

    /** 接口ID */
    @NotNull(message = "接口ID不能为空")
    private Long id;
}
