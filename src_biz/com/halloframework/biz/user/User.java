package com.halloframework.biz.user;

import javax.servlet.http.HttpServletRequest;

public class User {

    public String getInfo(HttpServletRequest request) {
        String nickname = request.getParameter("nickname");

        System.out.println(nickname);

        return "/WEB-INF/classes/com/halloframework/biz/user/jsp/list.jsp";
    }

    public String getEtc(HttpServletRequest request) {
        return "/WEB-INF/classes/com/halloframework/biz/user/jsp/write.jsp";
    }
}
