package cn.kgc.service;

import cn.kgc.domain.Orders;

public interface OrdersServise extends BaseService<Orders> {
    String afterOrdersPay(String out_trade_no) throws Exception;
}
