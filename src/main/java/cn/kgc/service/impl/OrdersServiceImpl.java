package cn.kgc.service.impl;

import cn.kgc.dao.InRoomInfoMapper;
import cn.kgc.dao.OrdersMapper;
import cn.kgc.dao.RoomsMapper;
import cn.kgc.dao.RoomsaleMapper;
import cn.kgc.domain.InRoomInfo;
import cn.kgc.domain.Orders;
import cn.kgc.domain.Rooms;
import cn.kgc.domain.Roomsale;
import cn.kgc.service.OrdersServise;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = false)
public class OrdersServiceImpl extends BaseServiceImpl<Orders> implements OrdersServise {
    @Autowired
    private OrdersMapper ordersMapper;

    @Autowired
    private InRoomInfoMapper inRoomInfoMapper;

    @Autowired
    private RoomsMapper roomsMapper;
    @Autowired
    private RoomsaleMapper roomsaleMapper;

    //订单业务层方法重写父类的保存数据方法
    @Override
    public String saveT(Orders orders){
        //1.生成订单数据(以订单的添加为主)
        int insOrdersCount = ordersMapper.insert(orders);
        //2.入住信息是否退房的状态的修改（未退房-->已退房）  0 -> 1
        InRoomInfo inRoomInfo = new InRoomInfo();
        inRoomInfo.setId(orders.getIriId());
        inRoomInfo.setOutRoomStatus("1");
        int updInroomInfoCount = inRoomInfoMapper.updateByPrimaryKeySelective(inRoomInfo);
        //3.客房的状态修改（已入住-->打扫）  1 -> 2
        InRoomInfo selInRoomInfo = inRoomInfoMapper.selectByPrimaryKey(orders.getIriId());
        //3.2新建客房对象
        Rooms rooms = new Rooms();
        //3.3向被修改的客房信息中设置值
        rooms.setId(selInRoomInfo.getRoomId());
        rooms.setRoomStatus("2");
        int updRoomscount = roomsMapper.updateByPrimaryKeySelective(rooms);
        if (insOrdersCount > 0 && updInroomInfoCount > 0 && updRoomscount > 0) {
            return "success";
        } else {
            return "fail";
        }
    }

    @Override
    public String afterOrdersPay(String out_trade_no) throws Exception {
        Orders parOrders= new Orders();
         parOrders.setOrderNum(out_trade_no);
        parOrders = ordersMapper.selTByParams(parOrders);
        Orders orders=new Orders();
        orders.setId(parOrders.getId());
        orders.setOrderStatus("1");
        int updOrdersCount = ordersMapper.updateByPrimaryKeySelective(orders);
        Roomsale roomSale=new Roomsale();
        String[] orderOther=parOrders.getOrderOther().split(",");
        //设置房间号
        roomSale.setRoomNum(orderOther[0]);
        //设置客户名称
        roomSale.setCustomerName(orderOther[1]);
        //设置入住时间
        roomSale.setStartDate(DateUtils.parseDate(orderOther[2],new String[]{"yyyy/MM/dd HH:mm:ss"}));
        //设置退房时间
        roomSale.setEndDate(DateUtils.parseDate(orderOther[3],new String[]{"yyyy/MM/dd HH:mm:ss"}));
        //设置入住天数
        roomSale.setDays(Integer.valueOf(orderOther[4]));
        //2-2 : 获取order_price字段，通过,号分割字符串，得到数据
        String[] orderPrice = parOrders.getOrderPrice().split(",");
        //设置房间单价
        roomSale.setRoomPrice(Double.valueOf(orderPrice[0]));
        /** 住宿费（实际的住房费用） */
        roomSale.setRentPrice(Double.valueOf(orderPrice[2]));
        //其他消费金额
        roomSale.setOtherPrice(Double.valueOf(orderPrice[1]));
        /** 订单的实际支付金额 */
        roomSale.setSalePrice(parOrders.getOrderMoney());
        //优惠金额
        roomSale.setDiscountPrice(roomSale.getRoomPrice()*roomSale.getDays() - roomSale.getRentPrice());
        int roomSaleCount = roomsaleMapper.insert(roomSale);
        if(updOrdersCount>0 && roomSaleCount>0){
            //去到项目的首页
            return "redirect: /model/tohome";
        }else{
            //去到错误提示页面
            return "redirect: /model/toerrorPay";
        }
    }
}
