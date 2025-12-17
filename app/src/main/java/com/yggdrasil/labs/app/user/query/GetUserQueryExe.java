package com.yggdrasil.labs.app.user.query;

import jakarta.annotation.Resource;

import org.springframework.stereotype.Component;

import com.alibaba.cola.dto.SingleResponse;
import com.yggdrasil.labs.app.user.assembler.UserAssembler;
import com.yggdrasil.labs.client.dto.enums.ErrorCode;
import com.yggdrasil.labs.client.dto.user.co.UserCO;
import com.yggdrasil.labs.client.dto.user.query.GetUserQuery;
import com.yggdrasil.labs.domain.user.model.User;
import com.yggdrasil.labs.domain.user.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * 获取用户查询执行器
 *
 * @author YoungerYang-Y
 */
@Slf4j
@Component
public class GetUserQueryExe {

    @Resource private UserRepository userRepository;

    @Resource private UserAssembler userAssembler;

    public SingleResponse<UserCO> execute(GetUserQuery query) {
        log.info("获取用户详情: userId={}", query.getId());

        // 查询用户
        User user = userRepository.findById(query.getId());
        if (user == null) {
            return SingleResponse.buildFailure(
                    ErrorCode.B_USER_NOT_FOUND.getErrCode(),
                    ErrorCode.B_USER_NOT_FOUND.getErrDesc());
        }

        // 转换为CO
        UserCO userCO = userAssembler.toCO(user);
        // 设置角色ID列表
        userCO.setRoleIds(user.getRoleIds());

        return SingleResponse.of(userCO);
    }
}
