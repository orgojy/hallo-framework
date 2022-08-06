package com.halloframework.fw.data;

import java.io.File;
import java.net.InetAddress;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class InitYaml {

    public static final String APP_NAME_PROPERTY = "com.halloframework.app.name";
    private static final String HOST_NAME_DEFAULT = "DEFAULT";

    private static final InitYaml thisInc = new InitYaml();

    public static InitYaml get() {
        return thisInc;
    }

    private Map<String, Object> map = null;

    private boolean isRead = false;

    public boolean isRead() {
        return isRead;
    }

    private String hostName = null;

    public String getHostName() {
        return hostName;
    }

    private String appRoot = null;

    public String getAppRoot() {
        InitYaml.get().getAppName();
        return appRoot;
    }

    private String appName = null;

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {

        if (this.appName != null) {
            return;
        }

        this.appName = appName;
    }

    private String characterSet = null;

    public String getCharacterSet() {
        return characterSet;
    }

    private boolean isProduct = false;

    public boolean isProduct() {
        return isProduct;
    }

    private boolean isCache = false;

    public boolean isCache() {
        return isCache;
    }

    private String tempDir = null;
    private int webPort = 0;
    private String webAppDir = null;

    private InitYaml() {

        Properties properties = System.getProperties();
        this.appName = properties.getProperty(APP_NAME_PROPERTY);

        this.map = YamlReader.getMap("init");

        if (this.map.isEmpty()) {
            return;
        }

        this.hostName = HOST_NAME_DEFAULT;

        try {
            this.hostName = InetAddress.getLocalHost().getHostName();
            if (this.hostName.contains(".")) {
                this.hostName = HOST_NAME_DEFAULT;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        this.isCache = is("CACHE");

        this.isProduct = is("PRODUCT");

        this.characterSet = s("CHARACTER_SET");

        String yamlUrl = s("THIS_YAML_URL");

        // webAppRoot/web/WEB-INF/classes/init.yaml
        File file = new File(yamlUrl);

        // webAppRoot/web/WEB-INF/classes
        file = file.getParentFile();

        // webAppRoot/web/WEB-INF
        file = file.getParentFile();

        // webAppRoot/WEB-INF
        file = file.getParentFile();

        // webAppRoot
        file = file.getParentFile();

        this.appRoot = file.getAbsolutePath();

        this.webPort = Integer.parseInt(s("WEB_PORT"));

        this.webAppDir = new File(s("WEB_APP_DIR")).getAbsolutePath();
        this.tempDir = new File(s("TEMP_DIR")).getAbsolutePath();

        isRead = true;
    }

    public String getTempDir() {
        return tempDir;
    }

    public int getWebPort() {
        return webPort;
    }

    public String getWebAppDir() {
        return webAppDir;
    }

    private Object get(String key) {

        Object obj = _get(key + "." + this.hostName);
        if (obj != null) {
            return obj;
        }

        obj = _get(key + "." + HOST_NAME_DEFAULT);
        if (obj != null) {
            return obj;
        }

        return _get(key);

    }

    @SuppressWarnings("unchecked")
    private Object _get(String key) {

        if (this.map == null) {
            return "";
        }

        String[] keys = key.split("\\.");

        Object obj = this.map;

        for (int i = 0; i < keys.length; i++) {

            String thisKey = keys[i];

            if (!(obj instanceof Map)) {
                return null;
            }

            obj = ((Map<String, Object>) obj).get(thisKey);
        }

        return obj;
    }

    private String convert$(String s) {
        if (this.appRoot != null) {
            s = s.replace("[APP_ROOT]", this.appRoot);
        }
        return s;
    }

    public String s(String key) {
        Object obj = get(key);
        if (!(obj instanceof String)) {
            return "";

        }

        String s = (String) obj;
        s = convert$(s);
        return s;
    }

    public String[] ss(String key) {
        Object obj = get(key);
        if (!(obj instanceof List)) {
            return new String[]{""};
        }

        @SuppressWarnings("unchecked")
        List<String> list = (List<String>) obj;

        String[] ret = new String[list.size()];
        for (int i = 0; i < ret.length; i++) {

            String s = list.get(i);
            s = convert$(s);
            ret[i] = s;
        }

        return ret;

    }

    public boolean is(String key) {
        Object obj = get(key);
        if (obj instanceof String) {
            return "true".equals(obj);
        }
        return false;
    }
}
