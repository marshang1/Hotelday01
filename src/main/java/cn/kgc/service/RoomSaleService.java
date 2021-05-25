package cn.kgc.service;

import cn.kgc.domain.Roomsale;

import java.util.List;
import java.util.Map;

public interface RoomSaleService extends BaseService<Roomsale> {
    Map<String,Object> findMapRoomSale();
}
