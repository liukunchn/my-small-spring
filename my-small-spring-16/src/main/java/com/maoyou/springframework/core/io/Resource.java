package com.maoyou.springframework.core.io;

import java.io.IOException;
import java.io.InputStream;

/**
 * @ClassName Resource
 * @Description
 * @Author 刘坤 kunliu@yinhai.com
 * @Date 2021/9/18 17:41
 * @Version 1.0
 */
public interface Resource {
    InputStream getInputStrem() throws IOException;
}
