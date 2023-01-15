package com.maoyou.springframework.core.io.support;

import com.maoyou.springframework.core.io.Resource;
import com.maoyou.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.Properties;

/**
 * @ClassName PropertiesLoaderSupport
 * @Description
 * @Author 刘坤 kunliu@yinhai.com
 * @Date 2021/12/23 15:36
 * @Version 1.0
 */
public abstract class PropertiesLoaderSupport {
    protected Properties[] localProperties;
    private Resource[] locations;
    protected boolean localOverride = false;
    private boolean ignoreResourceNotFound = false;
    private String fileEncoding;

    public void setProperties(Properties properties) {
        this.localProperties = new Properties[]{properties};
    }

    public void setPropertiesArray(Properties... propertiesArray) {
        this.localProperties = propertiesArray;
    }

    public void setLocation(Resource location) {
        this.locations = new Resource[]{location};
    }

    public void setLocations(Resource... locations) {
        this.locations = locations;
    }

    public void setLocalOverride(boolean localOverride) {
        this.localOverride = localOverride;
    }

    public void setIgnoreResourceNotFound(boolean ignoreResourceNotFound) {
        this.ignoreResourceNotFound = ignoreResourceNotFound;
    }

    public void setFileEncoding(String fileEncoding) {
        this.fileEncoding = fileEncoding;
    }

    protected Properties mergeProperties() throws IOException {
        Properties result = new Properties();
        if (localOverride) {
            loadProperties(result);
        }
        if (localProperties != null) {
            for (Properties properties : localProperties) {
                CollectionUtils.mergePropertiesIntoMap(properties, result);
            }
        }
        if (!localOverride) {
            loadProperties(result);
        }
        return result;
    }

    private void loadProperties(Properties result) throws IOException {
        if (locations != null) {
            for (Resource location : locations) {
                try {
                    EncodedResource encodedResource = new EncodedResource(location, this.fileEncoding);
                    if (encodedResource.requiresReader()) {
                        result.load(encodedResource.getReader());
                    } else {
                        result.load(encodedResource.getInputStrem());
                    }
                } catch (IOException e) {
                    if (this.ignoreResourceNotFound) {
                        // log.debug()
                    }
                    else {
                        throw e;
                    }
                }
            }
        }
    }
}
