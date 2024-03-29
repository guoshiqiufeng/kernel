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
package com.gitee.fubluesky.kernel.file.ali;

import cn.hutool.core.util.StrUtil;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.OSSObject;
import com.gitee.fubluesky.kernel.file.ali.pojo.AliOssProperties;
import com.gitee.fubluesky.kernel.file.api.FileOperatorApi;
import com.gitee.fubluesky.kernel.file.api.constants.FileConstants;
import com.gitee.fubluesky.kernel.file.api.exception.FileException;
import com.gitee.fubluesky.kernel.file.api.exception.enums.FileExceptionEnum;
import com.gitee.fubluesky.kernel.file.api.utils.IoUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.InputStream;

/**
 * @author yanghq
 * @version 1.0
 * @since 2021-07-31 10:43
 */
@Slf4j
public class AliFileOperator implements FileOperatorApi {

    private final AliOssProperties aliOssProperties;

    public AliFileOperator(AliOssProperties aliOssProperties) {
        this.aliOssProperties = aliOssProperties;
    }

    private OSS ossClient;

    private void init() {
        if (!aliOssProperties.getEnabled()) {
            throw new FileException(FileExceptionEnum.ALI_OSS_NOT_ENABLE);
        }
        ossClient = new OSSClientBuilder().build(aliOssProperties.getEndPoint(), aliOssProperties.getAccessKey(),
                aliOssProperties.getSecretKey());
    }

    /**
     * 获取http路径前缀
     *
     * @return http路径前缀
     */
    @Override
    public String getHttpPrefix() {
        return aliOssProperties.getDomain();
    }

    @Override
    public String upload(InputStream inputStream, String savePrefixPath, String path, Boolean datePathEnabled) {
        try {
            String prefix = savePrefixPath;
            if (StrUtil.isNotEmpty(aliOssProperties.getPrefix()) && datePathEnabled) {
                if (prefix.indexOf(FileConstants.BACKSLASHES) == 0) {
                    prefix = aliOssProperties.getPrefix() + prefix;
                } else {
                    prefix = aliOssProperties.getPrefix() + FileConstants.BACKSLASHES + prefix;
                }
            }
            if (aliOssProperties.getDatePathEnabled() && datePathEnabled) {
                path = getPath(prefix, path.substring(path.lastIndexOf(".")));
            } else if (StrUtil.isNotBlank(prefix)) {
                if (path.indexOf(FileConstants.BACKSLASHES) == 0) {
                    path = prefix + path;
                } else {
                    path = prefix + FileConstants.BACKSLASHES + path;
                }
            }
            // 初始化
            init();
            ossClient.putObject(aliOssProperties.getBucketName(), path, inputStream);
        } catch (Exception e) {
            log.error("ali oss file upload error:", e);
            throw new FileException(FileExceptionEnum.FILE_UPLOAD_ERROR);
        } finally {
            ossClient.shutdown();
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
        InputStream objectContent;
        try {
            // 初始化
            init();
            OSSObject ossObject = ossClient.getObject(aliOssProperties.getBucketName(), path);
            objectContent = ossObject.getObjectContent();
            return IoUtils.readStreamAsByteArray(objectContent);
        } catch (Exception e) {
            log.error("ali oss file get error:", e);
            throw new FileException(FileExceptionEnum.FILE_GET_ERROR);
        } finally {
            ossClient.shutdown();
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
            // 初始化
            init();
            ossClient.deleteObject(aliOssProperties.getBucketName(), path);
        } catch (Exception e) {
            log.error("ali oss file delete error:", e);
            throw new FileException(FileExceptionEnum.FILE_GET_ERROR);
        } finally {
            ossClient.shutdown();
        }
    }

}
