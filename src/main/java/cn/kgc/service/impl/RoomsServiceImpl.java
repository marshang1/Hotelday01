package cn.kgc.service.impl;

import cn.kgc.domain.Rooms;
import cn.kgc.service.RoomsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = false)
public class RoomsServiceImpl extends BaseServiceImpl<Rooms> implements RoomsService {
}
