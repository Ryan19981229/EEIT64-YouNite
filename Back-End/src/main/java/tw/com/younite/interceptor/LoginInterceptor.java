//package tw.com.younite.interceptor;
//
//
//import org.springframework.web.servlet.HandlerInterceptor;
//import org.springframework.web.servlet.ModelAndView;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
///**
// * 攔截器: 防止用戶隨意訪問頁面
// */
//public class LoginInterceptor implements HandlerInterceptor {
//    @Override
//    public boolean preHandle(
//            HttpServletRequest request,
//            HttpServletResponse response,
//            Object handler) throws Exception {
//        Object obj = request.getSession().getAttribute("id");

//        if (obj == null) {
//            response.sendRedirect("/login.html");
//            return false;
//        }

//        return true;
//    }
//
//}
