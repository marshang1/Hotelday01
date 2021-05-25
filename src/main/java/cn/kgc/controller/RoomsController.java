package cn.kgc.controller;

import cn.kgc.domain.Rooms;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("rooms")
public class RoomsController extends BaseController<Rooms> {
}
