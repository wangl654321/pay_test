package com.yoyi.pay.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.yoyi.pay.dao.YyPayDao;
import com.yoyi.pay.dao.YyPayMapper;
import com.yoyi.pay.entity.YyPay;
import com.yoyi.pay.utils.JsonMapperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Collection;
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
public class YyPayServicePlus implements IService<YyPay> {

    private static JsonMapperUtil json = new JsonMapperUtil();

    @Autowired
    private YyPayMapper yyPayMapper;

    @Autowired
    private YyPayDao yyPayDao;


    @Override
    public boolean insert(YyPay entity) {
        return false;
    }

    @Override
    public boolean insertAllColumn(YyPay entity) {
        return false;
    }

    @Override
    public boolean insertBatch(List<YyPay> entityList) {
        return false;
    }

    @Override
    public boolean insertBatch(List<YyPay> entityList, int batchSize) {
        return false;
    }

    @Override
    public boolean insertOrUpdateBatch(List<YyPay> entityList) {
        return false;
    }

    @Override
    public boolean insertOrUpdateBatch(List<YyPay> entityList, int batchSize) {
        return false;
    }

    @Override
    public boolean insertOrUpdateAllColumnBatch(List<YyPay> entityList) {
        return false;
    }

    @Override
    public boolean insertOrUpdateAllColumnBatch(List<YyPay> entityList, int batchSize) {
        return false;
    }

    @Override
    public boolean deleteById(Serializable id) {
        return false;
    }

    @Override
    public boolean deleteByMap(Map<String, Object> columnMap) {
        return false;
    }

    @Override
    public boolean delete(Wrapper<YyPay> wrapper) {
        return false;
    }

    @Override
    public boolean deleteBatchIds(Collection<? extends Serializable> idList) {
        return false;
    }

    @Override
    public boolean updateById(YyPay entity) {
        return false;
    }

    @Override
    public boolean updateAllColumnById(YyPay entity) {
        return false;
    }

    @Override
    public boolean update(YyPay entity, Wrapper<YyPay> wrapper) {
        return false;
    }

    @Override
    public boolean updateForSet(String setStr, Wrapper<YyPay> wrapper) {
        return false;
    }

    @Override
    public boolean updateBatchById(List<YyPay> entityList) {
        return false;
    }

    @Override
    public boolean updateBatchById(List<YyPay> entityList, int batchSize) {
        return false;
    }

    @Override
    public boolean updateAllColumnBatchById(List<YyPay> entityList) {
        return false;
    }

    @Override
    public boolean updateAllColumnBatchById(List<YyPay> entityList, int batchSize) {
        return false;
    }

    @Override
    public boolean insertOrUpdate(YyPay entity) {
        return false;
    }

    @Override
    public boolean insertOrUpdateAllColumn(YyPay entity) {
        return false;
    }

    @Override
    public YyPay selectById(Serializable id) {
        return null;
    }

    @Override
    public List<YyPay> selectBatchIds(Collection<? extends Serializable> idList) {
        return null;
    }

    @Override
    public List<YyPay> selectByMap(Map<String, Object> columnMap) {
        return null;
    }

    @Override
    public YyPay selectOne(Wrapper<YyPay> wrapper) {
        return null;
    }

    @Override
    public Map<String, Object> selectMap(Wrapper<YyPay> wrapper) {
        return null;
    }

    @Override
    public Object selectObj(Wrapper<YyPay> wrapper) {
        return null;
    }

    @Override
    public int selectCount(Wrapper<YyPay> wrapper) {
        return 0;
    }

    @Override
    public List<YyPay> selectList(Wrapper<YyPay> wrapper) {
        return null;
    }

    @Override
    public Page<YyPay> selectPage(Page<YyPay> page) {
        return null;
    }

    @Override
    public List<Map<String, Object>> selectMaps(Wrapper<YyPay> wrapper) {
        return null;
    }

    @Override
    public List<Object> selectObjs(Wrapper<YyPay> wrapper) {
        return null;
    }

    @Override
    public Page<Map<String, Object>> selectMapsPage(Page page, Wrapper<YyPay> wrapper) {
        return null;
    }

    @Override
    public Page<YyPay> selectPage(Page<YyPay> page, Wrapper<YyPay> wrapper) {
        return null;
    }
}
