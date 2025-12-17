package com.yggdrasil.labs.app.user.query;

import java.util.List;

import jakarta.annotation.Resource;

import org.springframework.stereotype.Component;

import com.alibaba.cola.dto.MultiResponse;
import com.yggdrasil.labs.app.user.assembler.UserAssembler;
import com.yggdrasil.labs.client.dto.user.co.UserCO;
import com.yggdrasil.labs.client.dto.user.query.PageUserQuery;
import com.yggdrasil.labs.domain.user.model.User;
import com.yggdrasil.labs.domain.user.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * 分页查询用户执行器
 *
 * @author YoungerYang-Y
 */
@Slf4j
@Component
public class PageUserQueryExe {

    @Resource private UserRepository userRepository;

    @Resource private UserAssembler userAssembler;

    public MultiResponse<UserCO> execute(PageUserQuery query) {
        log.info(
                "分页查询用户: username={}, status={}, pageNum={}, pageSize={}",
                query.getUsername(),
                query.getStatus(),
                query.getPageNum(),
                query.getPageSize());

        // 查询用户列表
        List<User> userList =
                userRepository.findPage(
                        query.getUsername(),
                        query.getStatus(),
                        query.getPageNum(),
                        query.getPageSize());

        // 转换为CO列表
        List<UserCO> userCOList =
                userList.stream()
                        .map(
                                user -> {
                                    UserCO userCO = userAssembler.toCO(user);
                                    // 设置角色ID列表
                                    userCO.setRoleIds(user.getRoleIds());
                                    return userCO;
                                })
                        .toList();

        return MultiResponse.of(userCOList);
    }
}
