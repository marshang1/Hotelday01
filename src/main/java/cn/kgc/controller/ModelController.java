package cn.kgc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.net.ServerSocket;

/**
 * @ClassName ModelController
 * @Description TODO  专门用于视图跳转的控制器
 * @Author zhaojing
 * @Date 2021/4/28 16:26
 * @Version 1.0
 */
@Controller
@RequestMapping("model")
public class ModelController {

    //跳转到显示员工和部门页面
    @RequestMapping("showEmpUI")
    public String showEmpUI(){
        return "home";
    }
    @RequestMapping("toshowInfoRoomInfo")
    public String toshowInfoRoomInfo(){
        return "inRoomInfo/showInRoomInfo";
    }
    @RequestMapping("toSaveInRoomInfo")
    public String toSaveInRoonInfo(){
        return "inRoomInfo/saveInRoomInfo";
    }
    @RequestMapping("toShowOrders")
    public String toShowOrders(){
        return "orders/showOrders";
    }
    @RequestMapping("toShowRoomSale")
    public String toShowRoomSale(){
        return "roomSale/showRomSale";
    }
    //跳转到支付页面
    @RequestMapping("toOrdersPay")
    public String toOrdersPay(){
        return "alipay/ordersPay";
    }
    @RequestMapping("tohome")
    public String tohome(){
        return "home";
    }
    @RequestMapping("toerrorPay")
    public String toerrorPay(){
        return "alipay/errorPay";
    }
    @RequestMapping("toshowVip")
    public String toshowVip(){
        return "vip/showVip";
    }
    @RequestMapping("tosaveVip")
    public String tosaveVip(){
        return "vip/saveVip";
    }
    @RequestMapping("toshowRooms")
    public String toshowRooms(){
        return "rooms/showRooms";
    }
    @RequestMapping("toshowRoomType")
    public String toshowRoomType(){
        return "roomType/showRoomType";
    }
    @RequestMapping("loginUI")
    public String loginUI(){
        return "login/login";
    }
    @RequestMapping("toshowdbi")
    public String toshowdbi(){
        return "dbi/showdbi";
    }
}


