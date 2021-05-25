layui.use(['jquery','layer', 'table','form','laydate'], function() {
    var $ = layui.jquery    //引入jquery模块
        , layer = layui.layer  //引用layer弹出层模块
        , table = layui.table  //引用table数据表格模块
        , form = layui.form  //引用form表单模块
        , laydate = layui.laydate;  //引用日期模块

    var selJsonVip = {};   //查询会员数据的条件
    //初始化会员数据
    loadPageVip();
    var checkPhoneIf = false;  //判断手机号的唯一性全局变量
//根据条件查询消费记录数据,提交监听表单
    form.on('submit(demo1)', function (data) {
        selJsonVip = data.field;  //重新将查询条件赋值
        console.log(selJsonVip);
        loadPageVip();
        return false;  //阻止表单跳转提交
    });
    //工具条事件
    table.on('tool(test)', function(obj){ //注：tool 是工具条事件名，test 是 table 原始容器的属性 lay-filter="对应的值"
        var data = obj.data; //获得当前行数据, data是后端返回的所有的会员数据
        var layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
        var tr = obj.tr; //获得当前行 tr 的 DOM 对象（如果有的话）
        if(layEvent === 'query'){ //查看
            layer.msg("查看操作...")
        } else if (layEvent === 'upd') { //修改
//1.表单赋值回显
            form.val("updVipForm", { //formTest 即 class="layui-form" 所在元素属性 lay-filter="" 对应的值
                "id": data.id
                ,"phone": data.phone
                ,"vipRate": data.vipRate
            });
            //2.弹框界面
            layer.open({
                type:1,  //弹出类型
                title:"会员修改操作界面",  //弹框标题
                area:['500px','280px'],  //弹框款高度
                anim: 1,  //弹出的动画效果
                shade:0.5,  //阴影遮罩
                content:$("#updVipDiv")  //弹出的内容
            });
            //3.自定义验证
            form.verify({  //做表单提交时的验证
                checkPhone: function(value, item){ //value：表单的值、item：表单的DOM对象
                    if(data.phone!=value){  //判断用户是否进行了手机号的修改，修改进行判断，不修改就不判断
                        checkPhone(value);//手机号的唯一性验证，发送ajax请求访问数据库
                        console.log("checkPhoneIf:",checkPhoneIf);
                        if(!checkPhoneIf){
                            return '该手机号已被使用！！';
                        }
                    }
                }
            });
            //4.执行修改操作
            //监听修改按钮，执行修改的表单
            form.on('submit(demo3)', function(data){
                console.log(data.field) //当前容器的全部表单字段，名值对形式：{name: value}
                //向服务器发送请求，执行修改操作
                updVip(data.field,obj);
                layer.closeAll();
                return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
            });
        }
    });
    //监听单元格编辑
    table.on('edit(test)', function(obj){
        var value = obj.value //得到修改后的值
            ,data = obj.data //得到所在行所有键值
            ,field = obj.field; //得到字段
        //layer.msg('[ID: '+ data.id +'] ' + field + ' 字段更改为：'+ value);
        //创建修改条件
        var updJsonVip = {};
        updJsonVip['id'] = data.id; //主键id
        updJsonVip[field] = value; //修改的名称
        //根据id修改客户名称
        updVipCustomerName(updJsonVip);
    });
    //分页加载会员数据
    function loadPageVip() {
        //表格的分页加载，数据表格方法级渲染
        table.render({  //数据表格的数据渲染(此UI框架底层是进行异步加载)
            elem: '#demo'  //绑定容器  根据标签（数据容器）的id属性来
            , height: 412   //容器高度
            , limit: 3   //每一页显示的数据条数，默认值为10
            , limits: [2, 3, 5, 8, 10, 15, 20]   //进行每一页数据条数的选择
            , url: 'vip/loadDataByParams' //访问服务器端的数据接口(异步请求)，返回的json格式的数据
            , where:selJsonVip
            , even: true  //每一行有渐变效果
            , page: true //开启分页,此时会自动的将当前页page和每一页数据条数limit的数值传回服务器端
            , cols: [[ //表头
                //加入复选框列
                {type: 'checkbox'}
                , {field: 'id', title: 'ID', align: 'center', width: 80, sort: true}
                , {field: 'vipNum', title: '会员卡号', align: 'center', width: 240, sort: true}
                , {field: 'customerName', title: '客人姓名', align: 'center', width: 150, sort: true,edit: 'text'}
                , {field: 'vipRate', title: '会员类型', align: 'center', width: 160,templet:'#vipRateTpl'}
                , {field: 'gender', title: '性别', align: 'center', width: 130,templet:'#genderTpl'}
                , {field: 'idcard', title: '身份证号', align: 'center', width: 240, sort: true,style:'color: #c6612e;'}
                , {field: 'phone', title: '手机号', align: 'center', width: 210,style:'color: red;', sort: true}
                , {field: 'createDate', title: '创建时间',align: 'center', width: 240, sort: true,style:'color: #2ec770;'}
                , {title: '操作', align: 'center', toolbar: '#barDemo',fixed:'right', width: 200}
            ]],
            done: function (res, curr, count) {  //执行分页是的函数回调；res为分页时服务器端的整个Map集合数据  curr为当前页  count为总的数据条数

            }
        });
    }
    //手机号的唯一性验证
    function checkPhone(phone){
        //在$.post()前把ajax设置为同步请求：
        $.ajaxSettings.async = false;
        $.post(
            "/vip/getCountByParams", //调用的是base系列的方法，只需要改mapper.xml文件
            {"phone":phone},
            function (data){
                console.log(data);
                if(data == 0){
                    checkPhoneIf = true;
                }else{
                    checkPhoneIf = false;
                }
            },"json"
        ).error(function (){
            layer.msg("服务器异常！！！",{icon: 3,time:2000,anim: 6,shade:0.5});
        })
    }
    //向服务器发送请求，执行修改操作
    //监听单元格编辑（只有当单元格数据发生变化才会触发此监听）
    // table.on('edit(test)', function(obj){
    //     var value = obj.value //得到修改后的值
    //         ,data = obj.data //得到所在行所有键值
    //         ,field = obj.field; //得到字段
    //     //执行修改
    //     var updJsonVip = {};
    //     updJsonVip['id'] = data.id;
    //     updJsonVip[field] = value;  //修改的字段数据
    //     //执行修改
    //     updVipCustomerName(updJsonVip);
    // });
    //向服务器发送请求，执行修改操作
    function updVip(updJsonVip,obj){
        $.post(
            "/vip/updT", //请求的url路径
            updJsonVip,
            function (data){
                if(data === 'success'){
                    layer.msg("会员信息修改成功！",{icon: 1,time:2000,anim: 1,shade:0.5});
                    //同步更新缓存对应的值
                    //注意：一般使用在：修改的字段个数比较少，单表修改情况。
                    obj.update({
                        phone: updJsonVip.phone
                        ,vipRate: updJsonVip.vipRate
                    });
                }else{
                    layer.msg("会员信息修改失败！",{icon: 2,time:2000,anim: 2,shade:0.5});
                }
            },"text" //text : 表示后端响应的是文本
        ).error(function (){
            layer.msg("数据请求异常！",{icon: 7,time:2000,anim: 3,shade:0.5});
        })
    }
    //根据id修改客户名称
    function updVipCustomerName(updJsonVip){
        $.post(
            "/vip/updT", //请求的url路径
            updJsonVip,
            function (data){
                if(data === 'success'){
                    layer.msg("会员名称修改成功！",{icon: 1,time:2000,anim: 1,shade:0.5});
                }else{
                    layer.msg("会员名称修改失败！",{icon: 2,time:2000,anim: 2,shade:0.5});
                }
            },"text" //text : 表示后端响应的是文本
        ).error(function (){
            layer.msg("数据请求异常！",{icon: 7,time:2000,anim: 3,shade:0.5});
        })
    }

});