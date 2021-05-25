package cn.kgc.service.impl;

import cn.kgc.domain.User;
import cn.kgc.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = false)
public class UserServiceImpl extends BaseServiceImpl<User> implements UserService {
}
