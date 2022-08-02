package com.halloframework.fw.data;

import java.io.InputStream;
import java.util.Map;
import java.util.Objects;
import org.yaml.snakeyaml.Yaml;

public final class YamlReader {

    private YamlReader() {
        throw new AssertionError();
    }

    public static Map<String, Object> getMap(String yamlName) {
        InputStream inputStream = null;
        String yamlFileName = yamlName.replace("\\.", "\\/").concat(".yaml");

        try {
            ClassLoader cl = YamlReader.class.getClassLoader();

            inputStream = cl.getResourceAsStream(yamlFileName);

            Yaml yaml = new Yaml();
            Map<String, Object> map = yaml.load(inputStream);

            String path = Objects.requireNonNull(cl.getResource(yamlFileName)).getPath();
            map.put("THIS_YAML_URL", path);

            return map;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
}
