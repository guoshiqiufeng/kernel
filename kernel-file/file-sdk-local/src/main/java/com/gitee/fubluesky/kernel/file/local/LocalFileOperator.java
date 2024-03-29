/*
 * Copyright (c) 2021-2024, fubluesky (fubluesky@foxmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.gitee.fubluesky.kernel.file.local;

import cn.hutool.core.util.StrUtil;
import com.gitee.fubluesky.kernel.file.api.FileOperatorApi;
import com.gitee.fubluesky.kernel.file.api.constants.FileConstants;
import com.gitee.fubluesky.kernel.file.api.exception.FileException;
import com.gitee.fubluesky.kernel.file.api.exception.enums.FileExceptionEnum;
import com.gitee.fubluesky.kernel.file.api.utils.FileUtil;
import com.gitee.fubluesky.kernel.file.local.pojo.LocalFileProperties;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.InputStream;

/**
 * @author yanghq
 * @version 1.0
 * @since 2021-08-02 9:21
 */
@Slf4j
public class LocalFileOperator implements FileOperatorApi {

    private final LocalFileProperties localFileProperties;

    public LocalFileOperator(LocalFileProperties localFileProperties) {
        try {
            this.localFileProperties = localFileProperties;
        } catch (Exception exception) {
            log.error("local file init fail.", exception);
            throw new FileException(FileExceptionEnum.LOCAL_INIT_ERROR);
        }
    }

    /**
     * 获取http路径前缀
     *
     * @return http路径前缀
     */
    @Override
    public String getHttpPrefix() {
        if (StrUtil.isBlank(localFileProperties.getDomain())) {
            return localFileProperties.getMvcPath();
        }
        if (localFileProperties.getDomain()
                .lastIndexOf(FileConstants.BACKSLASHES) != localFileProperties.getDomain().length() - 1) {
            return localFileProperties.getDomain() + FileConstants.BACKSLASHES + localFileProperties.getMvcPath();
        }
        return localFileProperties.getDomain() + localFileProperties.getMvcPath();
    }

    @Override
    public String upload(InputStream inputStream, String savePrefixPath, String path, Boolean datePathEnabled) {
        try {
            String prefix = savePrefixPath;
            if (StrUtil.isNotEmpty(localFileProperties.getPrefix()) && datePathEnabled) {
                if (prefix.indexOf(FileConstants.BACKSLASHES) == 0) {
                    prefix = localFileProperties.getPrefix() + prefix;
                } else {
                    prefix = localFileProperties.getPrefix() + FileConstants.BACKSLASHES + prefix;
                }
            }
            if (localFileProperties.getDatePathEnabled() && datePathEnabled) {
                path = getPath(prefix, path.substring(path.lastIndexOf(".")));
            } else if (StrUtil.isNotBlank(prefix)) {
                if (path.indexOf(FileConstants.BACKSLASHES) == 0) {
                    path = prefix + path;
                } else {
                    path = prefix + FileConstants.BACKSLASHES + path;
                }
            }
            // 判断目录存在不存在
            String savePath = localFileProperties.getSavePath();
            if (!FileUtil.exist(savePath)) {
                FileUtil.mkdir(savePath);
            }

            // 存储文件
            String absoluteFile = savePath + File.separator + path;
            FileUtil.writeInputStream(inputStream, absoluteFile);
        } catch (Exception e) {
            log.error("ftp file upload error:", e);
            throw new FileException(FileExceptionEnum.FILE_UPLOAD_ERROR);
        }
        if (StrUtil.isNotBlank(path) && path.indexOf(FileConstants.BACKSLASHES) == 0) {
            return path;
        }
        return FileConstants.BACKSLASHES + path;
    }

    /**
     * 获取文件字节
     *
     * @param path 文件路径，包含文件名
     * @return 文件字节
     */
    @Override
    public byte[] getFileBytes(String path) {
        String savePath = localFileProperties.getSavePath();
        // 判断文件存在不存在
        String absoluteFile = savePath + File.separator + path;
        if (!FileUtil.exist(absoluteFile)) {
            throw new FileException(FileExceptionEnum.FILE_NOT_FOUND);
        } else {
            return FileUtil.readBytes(absoluteFile);
        }
    }

    /**
     * 文件删除
     *
     * @param path 文件路径，包含文件名
     */
    @Override
    public void delete(String path) {
        try {
            String savePath = localFileProperties.getSavePath();
            // 判断文件存在不存在
            String file = savePath + File.separator + path;
            if (!FileUtil.exist(file)) {
                return;
            }
            // 删除文件
            FileUtil.del(file);
        } catch (Exception e) {
            log.error("local file delete error:", e);
            throw new FileException(FileExceptionEnum.FILE_DELETE_ERROR);
        }
    }

}
