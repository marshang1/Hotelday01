package cn.kgc.controller;

import cn.kgc.domain.Orders;
import cn.kgc.service.OrdersServise;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("orders")
public class OrdersController extends BaseController<Orders> {
    @Autowired
    private OrdersServise ordersServise;
    @RequestMapping("afterordersPay")
    public String afterordersPay(String out_trade_no){
        try {
            return ordersServise.afterOrdersPay(out_trade_no);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }
}
