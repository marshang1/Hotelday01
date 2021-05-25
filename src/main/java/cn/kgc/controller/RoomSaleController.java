package cn.kgc.controller;

import cn.kgc.domain.Roomsale;
import cn.kgc.service.RoomSaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
@RequestMapping("roomSale")
public class RoomSaleController extends BaseController<Roomsale> {
    @Autowired
    private RoomSaleService roomSaleService;
    @RequestMapping("loadRoomSale")
    @ResponseBody
    public Map<String,Object> loadRoomSale(){
        try {
            return roomSaleService.findMapRoomSale();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }
}
