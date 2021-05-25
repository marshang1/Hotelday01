package cn.kgc.service.impl;

import cn.kgc.dao.InRoomInfoMapper;
import cn.kgc.dao.RoomsMapper;
import cn.kgc.domain.InRoomInfo;
import cn.kgc.domain.Rooms;
import cn.kgc.service.InRoomInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = false)
public class  InRoomInfoServiceImpl extends BaseServiceImpl<InRoomInfo> implements InRoomInfoService {
    @Autowired
    private InRoomInfoMapper inRoomInfoMapper;

    @Autowired
    private RoomsMapper roomsMapper;

    //重写添加入住信息的方法
    @Override
    public String saveT(InRoomInfo inRoomInfo) {
        //1.添加入住信息
        int inRoomInfoCount = inRoomInfoMapper.insert(inRoomInfo);
        //2.修改客房的状态 ： 未入住 -> 已入住   0 -> 1
        Rooms rooms = new Rooms();
        rooms.setId(inRoomInfo.getRoomId());
        rooms.setRoomStatus("1");
        int updRoomsCount = roomsMapper.updateByPrimaryKeySelective(rooms);
        if(inRoomInfoCount>0 && updRoomsCount>0){
            return "success";
        }
        return "fail";
    }
}
