package cn.kgc.controller;

import cn.kgc.domain.Vip;
import org.springframework.stereotype.Controller;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("vip")
public class VipController extends BaseController<Vip> {
}
