package com.xdx.common.service.impl;


import com.xdx.common.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * 封装service中的增删改查等通用方法
 *
 * @author gaolinlou
 * @time 2016-1-22 12:24:14
 */
public abstract class BaseServiceImpl<M extends Mapper<T>, T> implements BaseService<T> {
    @Autowired
    protected M mapper;

    @Override
    public T selectOne(T entity) {
        return mapper.selectOne(entity);
    }

    @Override
    public T selectById(Object id) {
        return mapper.selectByPrimaryKey(id);
    }

    @Override
    public List<T> selectList(T entity) {
        return mapper.select(entity);
    }

    @Override
    public List<T> selectListAll() {
        return mapper.selectAll();
    }

    @Override
    public Long selectCountAll() {
        return Long.valueOf(mapper.selectCount(null));
    }

    @Override
    public Long selectCount(T entity) {
        return Long.valueOf(mapper.selectCount(entity));
    }

    @Override
    public void insert(T entity) {
        mapper.insert(entity);
    }

    @Override
    public int insertSelective(T entity) {
       return mapper.insertSelective(entity);
    }

    @Override
    public void delete(T entity) {
        mapper.delete(entity);
    }

    @Override
    public void deleteById(Object id) {
        mapper.deleteByPrimaryKey(id);
    }

    @Override
    public void updateById(T entity) {
        mapper.updateByPrimaryKey(entity);
    }

    @Override
    public int updateSelectiveById(T entity) {
        return mapper.updateByPrimaryKeySelective(entity);
    }
}
