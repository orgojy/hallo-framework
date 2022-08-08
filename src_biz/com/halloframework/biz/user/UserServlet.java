package com.halloframework.biz.user;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UserServlet extends HttpServlet {

    private static final String QUERY_PARAMETER_NICKNAME = "nickname";

    @Override
    protected void service(
            HttpServletRequest request,// Model
            HttpServletResponse response// Model
    ) throws IOException {
        String nickname = request.getParameter(QUERY_PARAMETER_NICKNAME);
        PrintWriter out = response.getWriter();

        if ("Ryan".equalsIgnoreCase(nickname)) {
            ryan(out);
            return;
        }

        if ("Michael".equalsIgnoreCase(nickname)) {
            michael(out);
            return;
        }

        out.println("Invalid nickname=" + nickname);
    }

    void ryan(PrintWriter out) {
        out.print("I'm Ryan.");
    }

    void michael(PrintWriter out) {
        out.print("I'm Michael.");
    }
}
