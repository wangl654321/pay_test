package com.yoyi.pay.dao;


import com.yoyi.pay.entity.YyPay;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/***
 *
 *
 * 描    述：甬易对账文件表
 *
 * 创 建 者： @author wl
 * 创建时间： 2019/4/15 15:28
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
@Component
public interface YyPayMapper {

    /**
     * 根据主键删除
     *
     * @param pid
     * @return int
     */
    int deleteByPrimaryKey(Integer pid);

    /**
     * 保存不带标签
     *
     * @param record
     * @return int
     */
    int insert(YyPay record);

    /**
     * 保存带标签
     *
     * @param record
     * @return int
     */
    int insertSelective(YyPay record);

    /**
     * 根据主键查询
     *
     * @param pid
     * @return YyPay
     */
    YyPay selectByPrimaryKey(Integer pid);

    /**
     * 更新带标签
     *
     * @param record
     * @return int
     */
    int updateByPrimaryKeySelective(YyPay record);

    /**
     * 更新不带标签
     *
     * @param record
     * @return int
     */
    int updateByPrimaryKey(YyPay record);

    /**
     * 批量保存
     *
     * @param read
     * @return
     */
    int batchInsert(List<Map<String, String>> read);
}