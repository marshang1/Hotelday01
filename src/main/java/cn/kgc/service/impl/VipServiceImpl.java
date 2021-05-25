package cn.kgc.service.impl;

import cn.kgc.domain.Vip;
import cn.kgc.service.VipService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = false)
public class VipServiceImpl extends BaseServiceImpl<Vip> implements VipService {
}
