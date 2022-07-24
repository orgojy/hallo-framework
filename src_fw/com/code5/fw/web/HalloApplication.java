package com.code5.fw.web;

import com.biz.board.UserServlet;
import java.io.File;
import org.apache.catalina.Context;
import org.apache.catalina.startup.Tomcat;

public class HalloApplication {

    private static final int WAS_PORT_NUMBER = 18080;

    public static void main(String[] args) throws Exception {
        /**
         * Tomcat 의 Base Directory Path
         */
        String baseDir = new File(".").getAbsolutePath() + File.separatorChar + "temp";
        /**
         * Tomcat 생성
         */
        Tomcat tomcat = tomcat(baseDir, WAS_PORT_NUMBER);
        /**
         * Tomcat 에 Context 추가
         */
        Context context = tomcat.addContext("/", baseDir);

        String servletName = "user";
        /**
         * Tomcat 에 Servlet 추가
         */
        tomcat.addServlet("/", servletName, new UserServlet());
        /**
         * Tomcat Context 에 Servlet 매핑
         */
        context.addServletMappingDecoded("/user", servletName);
        /**
         * Tomcat 실행
         */
        tomcat.start();
        /**
         * Tomcat 대기
         */
        tomcat.getServer().await();
    }

    private static Tomcat tomcat(String baseDir, int port) {
        Tomcat tomcat = new Tomcat();
        tomcat.setBaseDir(baseDir);
        tomcat.setPort(port);
        return tomcat;
    }
}
