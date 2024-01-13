package tw.com.younite.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@Api(tags ="付完款跳轉")
@Controller
public class EndPayController {
        @PostMapping("/redirectPost")
        @ResponseBody
        public String index(@ApiParam(value = "回應顯示頁面跳轉", required = true)
                                    HttpServletResponse response) throws IOException {
            response.sendRedirect("http://localhost:3000/home");
            return null;
        }

//    @RequestMapping("/redirectPost")
//    @CrossOrigin(origins = "*")
//    public void redirect(HttpServletResponse response) throws Exception{
//        response.sendRedirect("http://localhost:3000/home");
//    }


}
