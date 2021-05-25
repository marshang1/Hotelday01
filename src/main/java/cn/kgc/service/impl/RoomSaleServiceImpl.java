package cn.kgc.service.impl;

import cn.kgc.dao.RoomsaleMapper;
import cn.kgc.domain.Roomsale;
import cn.kgc.service.RoomSaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional(readOnly = false)
public class RoomSaleServiceImpl extends BaseServiceImpl<Roomsale> implements RoomSaleService {
    @Autowired
    private RoomsaleMapper roomsaleMapper;
    @Override
    public Map<String, Object> findMapRoomSale() {
        //进行分组查询，使用客房编号分组将其销售金额进行想加
        List<Map<String,Object>> mapList=roomsaleMapper.selMapRoomSale();
        //新建一个Map集合，装数据
        Map<String,Object> dataMap=new HashMap<>();
        //装图像显示提示
        dataMap.put("legend","客房销售");
        //装横轴数据和图形数据
        //新建横轴数据的List集合
        List<String> xAxis=new ArrayList<String>();
        //新建图形数据的List集合
        List<Double> series=new ArrayList<Double>();
        for (Map<String,Object> map:mapList){
            //装横轴数据
            xAxis.add(map.get("roomNum").toString());
            //装图形数据
            series.add(Double.valueOf(map.get("salePriceAll").toString()));
        }
        //将设置好的数据装入到map中
        dataMap.put("xAxis",xAxis);
        dataMap.put("series",series);
        return dataMap;
    }
}
