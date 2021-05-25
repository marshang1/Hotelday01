package cn.kgc.dao;

import cn.kgc.domain.Roomsale;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface RoomsaleMapper extends BaseMapper<Roomsale> {
    @Select("select room_num roomNum,SUM(sale_price) salePriceAll from roomsale group by room_num")
    List<Map<String,Object>> selMapRoomSale();
}