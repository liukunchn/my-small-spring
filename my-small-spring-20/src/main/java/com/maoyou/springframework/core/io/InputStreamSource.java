package com.maoyou.springframework.core.io;

import java.io.IOException;
import java.io.InputStream;

/**
 * @ClassName InputStreamSource
 * @Description
 * @Author 刘坤 kunliu@yinhai.com
 * @Date 2021/12/23 16:20
 * @Version 1.0
 */
public interface InputStreamSource {
    InputStream getInputStrem() throws IOException;
}
