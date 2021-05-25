package cn.kgc.controller;

import cn.kgc.domain.User;
import cn.kgc.service.BaseService;
import cn.kgc.service.UserService;
import cn.kgc.utils.MD5;
import cn.kgc.utils.VerifyCodeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
@RequestMapping("user")
public class UserController extends BaseController<User> {
    @Autowired
    private UserService userService;
    @RequestMapping("getVerifyCode")
    public void getVerifyCode(HttpSession session, HttpServletResponse response){
        //response: 通过响应流把生成的验证图片，响应到页面上
        //session: 使用session来存储服务器生成的验证码
        //1.生成5位数的验证码
        String verifyCode=VerifyCodeUtils.generateVerifyCode(5);
        //2.把验证码转换为小写放入到session中
        session.setAttribute("verifyCode",verifyCode.toLowerCase());
        //验证码图片响应到页面上显示
        try {
            VerifyCodeUtils.outputImage(220,35,response.getOutputStream(),verifyCode);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @ResponseBody
    @RequestMapping("checkVerifyCode")
    public String checkVerifyCode(String yzm,HttpSession session){
        //1.获取服务器上session保存的验证码
        String verifyCode=(String) session.getAttribute("verifyCode");
        //2.把服务器上session保存的验证码跟前端传递过来的验证码进行对比
        //yzm.toLowerCase() : 需要转换为小写
        if (verifyCode.equals(yzm.toLowerCase())){
            return "success";//验证成功
        }else {
            return "fail";//验证失败
        }
    }
    //根据用户名和密码验证是否登录成功
    @RequestMapping("checkLogin")
    @ResponseBody
    public String checkLogin(User user, HttpSession session){
        user.setPwd(MD5.md5crypt(user.getPwd()));
        try {
            User loginUser= userService.findTByParams(user);
            if (loginUser!=null){
                session.setAttribute("loginUser",loginUser);
                return "success";
            }else {
                return "fail";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }
    //退出用户
    @ResponseBody
    @RequestMapping("exitUser")
    public String exitUser(HttpSession session){
        //将session中用户数据删除
        try {
            session.removeAttribute("loginUser");
            return "success";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }
}
