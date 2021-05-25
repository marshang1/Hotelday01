layui.use(['jquery','layer', 'element','form','laypage'], function() {
    var $ = layui.jquery    //引入jquery模块
        , layer = layui.layer  //引用layer弹出层模块
        , element = layui.element  //引用element面板模块
        , form = layui.form  //引用form表单模块
        , laypage = layui.laypage;  //引用分页组件


    var page = 1;  //全局变量的当前页初始值为1
    var qnyName ="http://qt6nn5f1q.hn-bkt.clouddn.com/";

    var limit = 3;  //全局变量的每一页的数据条数初始值为3

    var count;  //全局变量总的数据条数
    var checkRoomsOfRoomTypeIf = false;  //验证房型是否可删的判断
    //初始化客房类型的数据
    loadPageRoomType();
    //初始化加载分页查询
    loadPage()
    //完整功能
    function loadPage() {
        laypage.render({  //此分页的数据加载均为异步加载
            elem: 'test1'  //分页标签容器
            , count: count //分页时总的数据条数（重要）变量
            , limit: limit  //每一页的数据条数，默认值为10，用于计算页数
            , limits: [2, 3, 5, 8, 10, 15, 20]
            //分页标签展示的图表按钮  总条数  上一页  第几页  下一页   每一页数据条数  重载  跳转到第               几页
            , layout: ['count', 'prev', 'page', 'next', 'limit', 'refresh', 'skip']
            , jump: function (obj, first) {  //执行分页时的函数回调，当点击上一页下一页等等时只会调用此函                                           数（不会将此js代码整个至上而下执行），加载分页数据
                //首次不执行，不是首次执行
                if (!first) {
                    page = obj.curr;  //将分页的当前页的值赋值给全局变量当前页
                    limit = obj.limit; //将分页得到每页显示的条数赋值给全局变量当前页
                    loadPageRoomType(); //执行房型的分页查询
                }
            }
        });
    }


    /***************************自定义函数*****************************/
    //加载第1页的房型数据，要得到总的数据条数（重要）
    function loadPageRoomType(){
        //在$.post()前把ajax设置为同步请求：
        $.ajaxSettings.async = false;
        $.post(
            "/roomType/loadDataByParams", //调用的是base系列的方法，只需要改mapper.xml文件
            {"page":page,"limit":limit},
            function (data){
                console.log(data);
                //将数据总的条数赋值给全局变量
                count = data.count;
                var roomTypeStr = ``;
                $.each(data.data,function (i,roomType){
                    roomTypeStr += `
                        <div class="layui-colla-item" id="item${roomType.id}" style="margin-top: 10px;">
                            <button type="button" class="layui-btn layui-btn-sm layui-btn-danger" event="del" value="${roomType.id}" style="float: right;">删除</button>
                            <button type="button" class="layui-btn layui-btn-sm layui-btn-warm" event="upd" value="${roomType.id},${roomType.roomTypeName},${roomType.roomPrice}" style=" float: right;">修改</button>
                            <h2 class="layui-colla-title" roomTypeId="${roomType.id}">${roomType.roomTypeName} -- ${roomType.roomPrice}元/天</h2>
                            <div class="layui-colla-content">
                                <p id="p${roomType.id}"></p>
                            </div>
                        </div>
                    `;
                })
                $("#collapseDiv").html(roomTypeStr);
                //将面板渲染
                element.render("collapse")

            },"json"
        ).error(function (){
            layer.msg("服务器异常！！！",{icon: 3,time:2000,anim: 6,shade:0.5});
        })
    }
    //做删除和修改操作
    $("#collapseDiv").on('click','button',function (){
        //拿到event
        var event = $(this).attr("event");
        //判断
        if(event == 'del'){ //点击了删除
            var roomTypeId = $(this).val();
            //1.先验证该客房类型下有没有客房数据
            checkRoomsOfRoomType(roomTypeId);
            if(checkRoomsOfRoomTypeIf){
                layer.confirm('真的要删除客房类型数据么', function(index){
                    //向服务端发送删除指令,删除对应的客房类型数据
                    delRoomTypeById(roomTypeId);
                    layer.close(index);
                });
            }else{
                layer.msg("该客房类型下有客房数据，不能删除！",{icon: 7,time:2000,anim: 3,shade:0.5});
            }
            //2.弹框提示是否删除
        }else{ //点击了修改
            //1.数据回显
            var roomTypeArr = $(this).val().split(",");
            //给表单赋值
            form.val("updRoomTypeFromFilter", { //formTest 即 class="layui-form" 所在元素属性 lay-filter="" 对应的值
                "id": roomTypeArr[0]// "name": "value"
                ,"roomTypeName": roomTypeArr[1]
                ,"roomPrice": roomTypeArr[2]
            });
            //2.弹框
            layer.open({
                type:1,  //弹出类型
                title:"房型修改操作界面",  //弹框标题
                area:['380px','280px'],  //弹框款高度
                anim: 4,  //弹出的动画效果
                shade:0.5,  //阴影遮罩
                content:$("#updRoomTypeDiv")  //弹出的内容
            });

            //3.监听修改表单的提交按钮操作  - 作业
            form.on('submit(demo4)', function (data) {
                var updJsonRoomType = data.field;
                updRoomType(updJsonRoomType);  //执行修改
                return false;  //阻止表单跳转提交
            });
        }
    })
//验证该房型下有没有客房数据，有则不能删除，否则可以删除
    function checkRoomsOfRoomType(id){
        //在$.post()前把ajax设置为同步请求：
        $.ajaxSettings.async = false;
        $.post(
            "/rooms/getCountByParams", //调用的是base系列的方法，只需要改mapper.xml文件
            {"roomTypeId":id},
            function (data){
                console.log(data);
                if(data == 0){
                    checkRoomsOfRoomTypeIf = true;
                }else{
                    checkRoomsOfRoomTypeIf = false;
                }

            },"json"
        ).error(function (){
            layer.msg("服务器异常！！！",{icon: 3,time:2000,anim: 6,shade:0.5});
        })
    }
    //执行房型数据删除
    function delRoomTypeById(id){
        $.post(
            "/roomType/delTById", //调用的是base系列的方法，只需要改mapper.xml文件
            {"id":id},
            function (data){
                if(data === 'success'){
                    loadPageRoomType(); //先执行分页数据加载
                    loadPage(); //重新加载分页（关键就是加载总的数据条数），因为此时的数据总的条数会变化
                    layer.msg("房型数据删除成功！",{icon: 1,time:2000,anim: 4,shade:0.5});
                }else{
                    layer.msg("房型数据删除失败！",{icon: 2,time:2000,anim: 4,shade:0.5});
                }
            },"text"
        ).error(function (){
            layer.msg("服务器异常！！！",{icon: 3,time:2000,anim: 6,shade:0.5});
        })
    }
//执行修改操作
    function updRoomType(updJsonRoomType){
        layer.closeAll();  //关闭所有弹框
        $.post(
            "/roomType/updT", //调用的是base系列的方法，只需要改mapper.xml文件
            updJsonRoomType,
            function (data){
                if(data === 'success'){
                    loadPageRoomType(); //重新加载当前页
                    layer.msg("房型数据修改成功！",{icon: 1,time:2000,anim: 4,shade:0.5});
                }else{
                    layer.msg("房型数据修改失败！",{icon: 2,time:2000,anim: 4,shade:0.5});
                }
            },"text"
        ).error(function (){
            layer.msg("服务器异常！！！",{icon: 3,time:2000,anim: 6,shade:0.5});
        })
    }
    //添加房型数据
    $("#saveRoomTypeBtn").click(function () {
        //清空表单上一次数据
        $("#saveRoomTypeDiv form").find("input").val("");
        //弹框
        layer.open({
                type: 1,  //弹出类型
                title: "房型添加操作界面",  //弹框标题
                area: ['380px', '280px'],  //弹框款高度
                anim: 3,  //弹出的动画效果
                shade: 0.5,  //阴影遮罩
                content: $("#saveRoomTypeDiv")  //弹出的内容
            }
        )
    })
    //验证房型名称唯一性判断
    var checkRoomTypeNameIf=false;
    //表单的自定义验证-验证客房类型名称和价格
    form.verify({
        roomTypeName:function (value,item) {
            checkRoomTypeName(value)
            if (!checkRoomTypeNameIf){
                return "房型名称有重复！"
            }
        },
        roomPrice:function (value,item) {
            if (value<100||value>8888){
                return "客房价格在100到8888之间";
            }

        }
    })
    //验证唯一性，根据房型名称多条查询
    function checkRoomTypeName(roomTypeName) {
        $.ajaxSettings.async=false;//同步请求
        $.post(
            "roomType/getCountByParams",
            {"roomTypeName":roomTypeName},
            function (data) {
                console.log(data)
                if (data==0){
                    checkRoomTypeNameIf=true
                    layer.tips('没有重复的房型名称，验证通过','#roomTypeName', {tips: [2,'green'],time:2000});
                } else {
                    checkRoomTypeNameIf=false
                    layer.tips('有重复的房型名称，验证不通过','#roomTypeName', {tips: [2,'red'],time:2000});
                }
            },"json"
        ).error(function () {
            layer.msg("服务器异常！！！",{icon: 3,time:2000,anim: 6,shade:0.5})
        })
    }
    //监听提交按钮
    form.on('submit(demo3)',function (data) {
        var saveJsonRoomType=data.field;
        saveRoomType(saveJsonRoomType);//执行添加
        return false;//阻止表单跳转提交
    })
    //添加客房类型数据
    function saveRoomType(saveJsonRoomType) {
        layer.closeAll()//关闭所有弹框
        $.post(
            "roomType/saveT",
            saveJsonRoomType,
            function (data) {
                if (data==='success'){
                    page=1;//使当前页为1
                    loadPageRoomType();//重新加载当前页
                    loadPage();//由于数据的条数发生变化，重新加载layui的分页组件
                    layer.msg("房型数据添加成功！",{icon: 1,time:2000,anim: 4,shade:0.5})
                }else {
                    layer.msg("房型数据添加失败！",{icon: 2,time:2000,anim: 4,shade:0.5})
                }
            },"text"
        ).error(function () {
            layer.msg("服务器异常！！！",{icon: 3,time:2000,anim: 6,shade:0.5});
        })
    }
    //监听折叠面板
    element.on('collapse(test)',function (data) {
        if (data.show){
            //面板展开时的操作
            var roomTypeId=$(this).attr("roomTypeId")
            loadRoomsroomTypeId(roomTypeId)//执行根据客房类型id查询多条数据
        }
    })
    //根据客房类型id查询多条数据
    function loadRoomsroomTypeId(roomTypeId) {
        $.post(
            "rooms/loadManyByParams",
            {"roomTypeId":roomTypeId},
            function (data) {
                console.log(data)
                if(!$.isEmptyObject(data)){ //此房型有客房数据
                    var rooms = `<ul class="site-doc-icon site-doc-anim">`;
                    $.each(data,function (i,room){
                        if(room.roomStatus == '0'){
                            rooms += `<li style="background-color: #009688;">`;
                        }else if(room.roomStatus == '1'){
                            rooms += `<li style="background-color: red;">`;
                        }else {
                            rooms += `<li style="background-color: blueviolet;">`;
                        }
                        rooms += `<img class="layui-anim" id="demo1" src="${qnyName}${room.roomPic}" width="135px" height="135px"/>
<div class="code">
<span style="display: block;color: #0C0C0C;">${room.roomNum} - ${room.roomType.roomTypeName} - ${room.roomType.roomPrice}元/天</span>
</div>
</li>    
`;})
                    rooms += `</ul>`;
                    $("#p"+roomTypeId).html(rooms);
                }else{ //此房型没有客房数据
                    layer.msg("此房型没有客房数据！",{icon: 7,time: 2000,anim:6,shade:0.5})
                }
            },"json"
        ).error(function (){
            layer.msg("服务器异常！！！",{icon: 3,time:2000,anim: 6,shade:0.5});
        })
    }
})