package com.halloframework.biz.user;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UserServlet extends HttpServlet {

    private static final String QUERY_PARAMETER_NICKNAME = "nickname";
    private static final String NICKNAME_RYAN = "Ryan";
    private static final String NICKNAME_MICHAEL = "Michael";

    @Override
    protected void service(
            HttpServletRequest request,// Model
            HttpServletResponse response// Model
    ) throws IOException {
        String nickname = request.getParameter(QUERY_PARAMETER_NICKNAME);
        PrintWriter out = response.getWriter();

        if (NICKNAME_RYAN.equalsIgnoreCase(nickname)) {
            ryan(out);
            return;
        }

        if (NICKNAME_MICHAEL.equalsIgnoreCase(nickname)) {
            michael(out);
            return;
        }

        out.println(String.format("Invalid nickname=%s", nickname));
    }

    private void ryan(PrintWriter out) {
        out.print(String.format("I'm %s.", NICKNAME_RYAN));
    }

    private void michael(PrintWriter out) {
        out.print(String.format("I'm %s.", NICKNAME_MICHAEL));
    }
}
