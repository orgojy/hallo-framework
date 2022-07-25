package com.halloframework.fw.web;

import com.halloframework.fw.data.InitYaml;
import java.net.Socket;
import java.util.Properties;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.startup.Tomcat;

public class HalloApplication {

    private static final int WAS_PORT_NUMBER = 18080;

    public static void main(String[] args) throws Exception {
        /**
         * WAS 에 필요한 값들을 정의한 InitYaml 초기화
         */
        InitYaml init = InitYaml.get();
        if (!init.isRead()) {
            return;
        }
        printInitYaml(init);
        /**
         * 이미 사용된 port 인지 확인
         */
        if (!isWebPort(init.getWebPort())) {
            System.out.printf("Port# %d is already used.%n", init.getWebPort());
            return;
        }
        /**
         *
         */
        Properties properties = System.getProperties();
        properties.setProperty("com.code5.app.name", "CODE5");
        /**
         * Tomcat 생성
         */
        Tomcat tomcat = tomcat(init.getTempDir(), init.getWebAppDir(), WAS_PORT_NUMBER);
        /**
         * Setting : Tomcat Encoding
         */
        String characterSet = init.getCharacterSet();
        System.out.println("uri characterSet [" + characterSet + "]");
        Connector conn = tomcat.getConnector();
        conn.setURIEncoding(characterSet);
        /**
         * Tomcat 실행
         */
        tomcat.start();
        /**
         * ASCII Art
         */
        System.out.println(new StringBuilder()
                .append("\n     _    _  _   _         _    _           _    _     \n")
                .append("   /' ) /' )( ) ( )       (_ ) (_ )        ( `\\ ( `\\   \n")
                .append(" /' /'/' /' | |_| |   _ _  | |  | |    _    `\\ `\\`\\ `\\ \n")
                .append("<  < <  <   |  _  | /'_` ) | |  | |  /'_`\\    >  > >  >\n")
                .append(" \\  `\\\\  `\\ | | | |( (_| | | |  | | ( (_) ) /' /'/' /' \n")
                .append("  `\\__)`\\__)(_) (_)`\\__,_)(___)(___)`\\___/'(_/' (_/'   \n")
                .append("                                                       \n")
        );
        System.out.println("\"Hallo Framework\" with Tomcat is started!");
        /**
         * Tomcat 대기
         */
        tomcat.getServer().await();
    }

    private static void printInitYaml(InitYaml init) {
        System.out.println("hostName [" + init.getHostName() + "]");
        System.out.println("webappDir [" + init.getWebAppDir() + "]");
        System.out.println("tempDir [" + init.getTempDir() + "]");
        System.out.println("webPort [" + init.getWebPort() + "]");
    }

    private static boolean isWebPort(int webPort) throws Exception {
        Socket socket = null;
        try {
            socket = new Socket("localhost", webPort);
            socket.setSoTimeout(1);
            if (socket.isConnected()) {
                return false;
            }
            return true;
        } catch (Exception ex) {
            return true;
        } finally {
            if (socket != null) {
                socket.close();
            }
        }

    }

    private static Tomcat tomcat(String tempDir, String webappDir, int port) {
        Tomcat tomcat = new Tomcat();
        tomcat.setBaseDir(tempDir);
        tomcat.setPort(port);
        tomcat.addWebapp("", webappDir);
        return tomcat;
    }
}