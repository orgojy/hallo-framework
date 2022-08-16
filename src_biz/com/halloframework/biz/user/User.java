package com.halloframework.biz.user;

import javax.servlet.http.HttpServletRequest;

public class User {

    private static final String USER_WRITE_JSP = "/WEB-INF/classes/com/halloframework/biz/user/jsp/write.jsp";
    private static final String USER_LIST_JSP = "/WEB-INF/classes/com/halloframework/biz/user/jsp/list.jsp";

    public String getInfo(HttpServletRequest request) {
        System.out.println(request.getParameter("nickname"));

        return USER_LIST_JSP;
    }

    public String getEtc(HttpServletRequest request) {
        System.out.println(request.getPathInfo());
        return USER_WRITE_JSP;
    }
}
