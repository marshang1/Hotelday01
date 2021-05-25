package cn.kgc.domain;

import lombok.Data;

import java.util.Date;
@Data
public class Orders {
    private Integer id;

    private String orderNum;

    private Double orderMoney;

    private String remark;

    private String orderStatus;

    private Integer iriId;

    private Date createDate;

    private String flag;

    private String orderOther;

    private String orderPrice;
    //引入入住信息对象
    private InRoomInfo inRoomInfo;
    private Date minDate;
    private Date maxDate;

}