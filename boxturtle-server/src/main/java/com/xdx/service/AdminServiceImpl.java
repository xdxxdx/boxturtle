package com.xdx.service;

import com.xdx.common.service.impl.BaseServiceImpl;
import com.xdx.dao.AdminMapper;
import com.xdx.model.Admin;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl extends BaseServiceImpl<AdminMapper,Admin> implements AdminService {

}
