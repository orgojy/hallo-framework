package com.halloframework.fw.web;

import com.halloframework.fw.data.InitYaml;
import java.net.Socket;
import java.util.Properties;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.startup.Tomcat;

/***
 * Starting "Hallo Application"
 * @author Yimin An
 */
public class HalloApplication {

    private static final InitYaml INIT = InitYaml.get();
    private static final int WAS_PORT_NUMBER = 18080;
    private static final String APP_NAME = "Hallo";
    private static final int SOCKET_TIMEOUT_MILLISECOND = 1;

    public static void main(String[] args) throws Exception {
        /**
         * WAS 에 필요한 값들을 정의한 InitYaml 초기화
         */
        if (!INIT.isRead()) {
            return;
        }
        printInitYaml(INIT);
        /**
         * 이미 사용된 port 인지 확인
         */
        if (!isWebPort(INIT.getWebPort())) {
            System.out.printf("Port# %d is already used.%n", INIT.getWebPort());
            return;
        }
        /**
         * Web Application 의 이름 설정
         */
        Properties properties = System.getProperties();
        properties.setProperty(InitYaml.APP_NAME_PROPERTY, APP_NAME);
        /**
         * Tomcat 생성
         */
        Tomcat tomcat = tomcat(INIT.getTempDir(), INIT.getWebAppDir(), WAS_PORT_NUMBER);
        /**
         * Setting : Tomcat Encoding
         */
        String characterSet = INIT.getCharacterSet();
        System.out.printf("uri characterSet [%s]%n", characterSet);
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
                .append("by Yimin An.                                           \n")
        );
        System.out.printf("\"%s Framework\" with Tomcat is started!%n", APP_NAME);
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

    private static boolean isWebPort(int webPort) {
        try (Socket socket = new Socket("localhost", webPort)) {
            socket.setSoTimeout(SOCKET_TIMEOUT_MILLISECOND);
            return !socket.isConnected();
        } catch (Exception ex) {
            return true;
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
