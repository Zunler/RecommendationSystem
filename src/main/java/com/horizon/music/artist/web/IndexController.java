package com.horizon.music.artist.web;

import com.horizon.sm.user.service.ISysUserManagerService;
import com.horizon.sm.user.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Controller
@RequestMapping("/")
public class IndexController {

    @Autowired
    private ISysUserManagerService sysUserService;
    @RequestMapping({"/index","/"})
    public String getIndexPage(HttpServletRequest req) {
        String userID=(String)req.getSession().getAttribute("userID");
        if(userID==null){
            return "login";
        }
        else{
            return  "index";
        }
    }

//    @RequestMapping(value = "/", method = RequestMethod.POST )
//    public String login(User user, HttpServletRequest req, HttpServletResponse response) throws Exception {
//        System.out.println("````");
//        Map<String, String> result = sysUserService.checkUser(user);
//        if(result.get("code").equals("0")) {
//            req.setAttribute("userID",result.get("desc"));
//            return "music/index";
//        }
//        req.setAttribute("code",result.get("code"));
//        req.setAttribute("desc",result.get("desc"));
//        return "errorPage";
//    }

    @RequestMapping(value = "/login")
    public String login() {
        return "login";
    }

    @RequestMapping(value = "/signup")
    public String signup() {
        return "signup";
    }
}
