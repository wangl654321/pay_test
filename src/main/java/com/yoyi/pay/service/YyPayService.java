package com.yoyi.pay.service;

import com.yoyi.pay.dao.YyPayMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/***
 *
 *
 * 描    述：对账文件解析批量保存
 *
 * 创 建 者：@author wl
 * 创建时间：2019/4/169:24
 * 创建描述：
 *
 * 修 改 者：
 * 修改时间：
 * 修改描述：
 *
 * 审 核 者：
 * 审核时间：
 * 审核描述：
 *
 */
@Service
@Transactional(rollbackFor = RuntimeException.class)
public class YyPayService {

    @Autowired
    private YyPayMapper yyPayMapper;

    /**
     * 对账文件解析批量保存
     *
     * @param subList
     * @return
     */
    public int batchInsert(List<Map<String, String>> subList) {
        int num = yyPayMapper.batchInsert(subList);
        return num;
    }
}
