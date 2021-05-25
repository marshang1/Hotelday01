package cn.kgc.controller;

import cn.kgc.service.BaseService;
import cn.kgc.utils.QiniuUploadUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName BaseController
 * @Description TODO 基础公共控制器
 * @Author zhaojing
 * @Date 2021/5/6 11:43
 * @Version 1.0
 */
//注意：BaseController不能添加@Controller注解，因为没有指定具体的类型，启动报错。
public class BaseController<T> {

    @Autowired
        private BaseService<T> baseService;

    /**
     * 异步请求加载数据
     * @param page  当前页码
     * @param limit 每页条数
     * @return  layui所需要的封装的数据
     */
    @RequestMapping("loadData")
    @ResponseBody  //只返回数据JSON格式
    public Map<String,Object> loadData(Integer page, Integer limit){
        System.out.println("page = " + page);
        System.out.println("limit = " + limit);
        Map<String,Object> map = null;
        try{
            //分页查询数据
            map = baseService.findTByPage(page, limit);
            map.put("code", 0); //设置状态码：0 表示请求返回成功
        }catch (Exception e){
            e.printStackTrace();
            map.put("code", 200); //设置状态码：200 表示请求返回失败
            map.put("msg","数据访问失败！");
        }
        return map;
    }


    /**
     * 通过查询条件分页查询数据
     * @param page 当前页码
     * @param limit 每页条数
     * @param t 封装了查询条件
     * @return
     * */

    @RequestMapping("loadDataByParams")
    @ResponseBody  //只返回数据JSON格式
    public Map<String,Object> loadDataByParams(Integer page, Integer limit, T t){
        System.out.println("page = " + page);
        System.out.println("limit = " + limit);
        Map<String,Object> map = null;
        try{
            //分页查询数据
            map = baseService.findTByPageAndParams(page, limit, t);
            map.put("code", 0); //设置状态码：0 表示请求返回成功
        }catch (Exception e){
            e.printStackTrace();
            map.put("code", 200); //设置状态码：200 表示请求返回失败
            map.put("msg","数据访问失败！");
        }
        return map;
    }

    //根据主键id删除单个数据
    @RequestMapping("delTById")
    @ResponseBody
    public String delTById(Integer id){
        try{
            return baseService.removeTById(id);
        }catch (Exception e){
            e.printStackTrace();
            return "error";
        }
    }

    //根据多个主键Id删除数据
    @RequestMapping("delBatchTByIds")
    @ResponseBody
    public String delBatchTByIds(Integer[] ids){
        try{
            return baseService.removeBatchTByIds(ids);
        }catch (Exception e){
            e.printStackTrace();
            return "error";
        }
    }

    //添加单个数据
    @RequestMapping("saveT")
    @ResponseBody
    public String saveT(T t){
        try{
            return baseService.saveT(t);
        }catch (Exception e){
            e.printStackTrace();
            return "error";
        }
    }

    //动态修改数据
    @RequestMapping("updT")
    @ResponseBody
    public String updT(T t){
        try{
            return baseService.modifyT(t);
        }catch (Exception e){
            e.printStackTrace();
            return "error";
        }
    }

    //异步请求加载所有数据
    @RequestMapping("loadAllT")
    @ResponseBody
    public List<T> loadAllT(){
        try{
            return baseService.findTAll();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
    @ResponseBody
    @RequestMapping("loadTByParams")
    public T loadTByParams(T t){
        try {
            return baseService.findTByParams(t);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    @ResponseBody
    @RequestMapping("loadManyByParams")
    public List<T> loadManyByParams(T t){
        try {
            return baseService.findManyByParams(t);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    @RequestMapping("updBatchTByIds")
    @ResponseBody
    public String updBatchTByIds(Integer[] ids,T t){
        try{
            return baseService.modifyBatchByIds(ids, t);
        }catch (Exception e){
            e.printStackTrace();
            return "error";
        }
    }
    @RequestMapping("getCountByParams")
    @ResponseBody
    public Long  getCountByParams(T t){
        try {
            return baseService.findCountByPramas(t);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    @ResponseBody
    @RequestMapping("uploadRoomPic")
    public Map<String,Object> uploadRoomPic(String path,MultipartFile myFile ){
//        Map<String,Object> map=new HashMap<>();
//              try {
//            String oldfilename=myFile.getOriginalFilename();
//            String prefixPath=FilenameUtils.getExtension(oldfilename);
//            //得到新文件名
//            String newFileName=System.currentTimeMillis()+RandomUtils.nextInt(10000)+"."+prefixPath;
//            //创建路径，上传文件
//            File file=new File(path,newFileName);
//            //判断该路径是否存在
//            if (!file.exists()){
//                //创建路径
//                file.mkdirs();
//            }
//            //文件上传的方法
//            myFile.transferTo(file);
//            map.put("code",0);
//            map.put("newFileName",newFileName);
//        } catch (IOException e) {
//            e.printStackTrace();
//            map.put("code",200);
//        }
//        return map;
//
        try {
            return QiniuUploadUtils.upload(myFile);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
