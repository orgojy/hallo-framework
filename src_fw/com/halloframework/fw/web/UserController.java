package com.halloframework.fw.web;

import com.halloframework.biz.user.User;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UserController extends HttpServlet {

    private static final String URI_USERS = "/users";
    private static final String URI_ETC = "/etc";

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pathInfo = request.getPathInfo();
        String jspUrl = "";

        if (URI_USERS.equals(pathInfo)) {
            User user = new User();
            jspUrl = user.getInfo(request);
        } else if (URI_ETC.contains(pathInfo)) {
            User user = new User();
            jspUrl = user.getEtc(request);
        }

        if (jspUrl.isEmpty()) {
            throw new ServletException();
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher(jspUrl);
        dispatcher.forward(request, response);
    }
}
