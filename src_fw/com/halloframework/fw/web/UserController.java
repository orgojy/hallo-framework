package com.halloframework.fw.web;

import com.halloframework.biz.user.User;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UserController extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String pathInfo = request.getPathInfo();

        String jspUrl = null;

        if ("/users".equals(pathInfo)) {
            User user = new User();
            jspUrl = user.getInfo(request);
        } else if ("/etc".contains(pathInfo)) {
            User user = new User();
            jspUrl = user.getEtc(request);
        }

        if (jspUrl == null) {
            throw new ServletException();
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher(jspUrl);
        dispatcher.forward(request, response);
    }
}
