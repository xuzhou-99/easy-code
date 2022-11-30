/*
 * Copyright [2020-2030] [https://www.stylefeng.cn]
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Guns采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：
 *
 * 1.请不要删除和修改根目录下的LICENSE文件。
 * 2.请不要删除和修改Guns源码头部的版权声明。
 * 3.请保留源码和相关描述文件的项目出处，作者声明等。
 * 4.分发源码时候，请注明软件出处 https://gitee.com/stylefeng/guns
 * 5.在修改包名，模块名称，项目代码等时，请注明软件出处 https://gitee.com/stylefeng/guns
 * 6.若您的项目无法满足以上几点，可申请商业授权
 */
package com.qingyan.easycode.platform.db.register;

import java.util.List;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import com.qingyan.easycode.platform.db.tenant.MasterTenantHandler;
import com.qingyan.easycode.platform.db.RegisterTenantDatasource;

import lombok.extern.slf4j.Slf4j;

/**
 * 当spring装配好配置后开始初始化
 *
 * @author xuzhou
 * @since 2022-11-10
 */
@Slf4j
@Component
public class DynamicRegister implements ApplicationListener<ContextRefreshedEvent>, Ordered {

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }

    /**
     * Handle an application event.
     *
     * @param event the event to respond to
     */
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

        log.info("===== 初始化平台企业数据源开始 =====");
        List<String> allTenantIds = MasterTenantHandler.getAllTenantIds();
        try {
            for (String tenant : allTenantIds) {
                RegisterTenantDatasource.blockAndInitTenantDatasource(Long.parseLong(tenant));
            }
        } catch (Exception e) {
            log.warn("初始化平台企业数据源失败，原因：{}", e.getMessage());
        }
        log.info("===== 初始化平台企业数据源完成，共计 {} 个数据源=====", allTenantIds.size());

    }
}
