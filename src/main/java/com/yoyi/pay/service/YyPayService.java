/*
package com.yoyi.pay.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.yoyi.pay.dao.YyPayDao;
import com.yoyi.pay.dao.YyPayMapper;
import com.yoyi.pay.entity.YyPay;
import com.yoyi.pay.utils.JsonMapperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

*/
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
 *//*

@Service
@Transactional(rollbackFor = RuntimeException.class)
public class YyPayService {

    private static JsonMapperUtil json = new JsonMapperUtil();

    @Autowired
    private YyPayMapper yyPayMapper;

    @Autowired
    private YyPayDao yyPayDao;

    */
/**
     * 对账文件解析批量保存
     *
     * @param subList
     * @return
     *//*

    public int batchInsert(List<Map<String, String>> subList) {
        subList.forEach(stringStringMap -> {
            YyPay yyPay = json.fromJson(json.toJson(stringStringMap), YyPay.class);
            Integer insert = yyPayDao.insert(yyPay);
        });
        return subList.size();
    }

    */
/**
     * 分页
     *
     * @param pageNumber
     * @param pageSize
     * @return
     *//*

    public List<YyPay> selectAllInPage(int pageNumber, int pageSize) {

        Page<YyPay> page = new Page<>(pageNumber, pageSize);
        YyPay yyPay = new YyPay();
        yyPay.setStatus("1");
        EntityWrapper entityWrapper = new EntityWrapper(yyPay);
        return yyPayDao.selectPage(page, entityWrapper);
    }


    */
/**
     * delete功能
     *//*

    public void delete() {
        YyPay yyPay = new YyPay();
        yyPay.setPid(1);
        EntityWrapper entityWrapper = new EntityWrapper(yyPay);
        yyPayDao.delete(entityWrapper);
    }


    */
/**
     * id批量in 查询
     *
     * @return
     *//*

    public List<YyPay> selectInIdArr() {
        List<Integer> idList = new ArrayList<>();
        idList.add(1);
        idList.add(2);
        idList.add(3);
        return yyPayDao.selectBatchIds(idList);
    }

    */
/**
     * 根据 entity 条件，查询全部记录
     *
     * @return
     *//*

    public List<YyPay> selectList() {
        YyPay yyPay = new YyPay();
        yyPay.setStatus("1");
        EntityWrapper entityWrapper = new EntityWrapper(yyPay);
        List list = yyPayDao.selectList(entityWrapper);
        return list;
    }

    */
/**
     * 复杂的多条件查询
     *
     * @return
     *//*

    public List<YyPay> selectAllByWrapper4() {
        EntityWrapper entity = new EntityWrapper();
        //大于
        entity.gt("pid", "0");
        //小于等于
        entity.le("pid", 11);
        //不等于
        entity.ne("pid", "null");
        entity.like("pid", "tt");
        entity.notLike("pid", "sadas");
        entity.orderBy("pid");
        return yyPayDao.selectList(entity);
    }
}
*/
