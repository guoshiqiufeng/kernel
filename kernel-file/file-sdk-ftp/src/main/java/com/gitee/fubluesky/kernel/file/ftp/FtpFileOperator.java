/*
 *
 *   Copyright 2021 fubluesky.
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *        https://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.gitee.fubluesky.kernel.file.ftp;

import com.gitee.fubluesky.kernel.file.api.FileOperatorApi;
import com.gitee.fubluesky.kernel.file.api.constants.FileConstants;
import com.gitee.fubluesky.kernel.file.api.exception.FileException;
import com.gitee.fubluesky.kernel.file.api.exception.enums.FileExceptionEnum;
import com.gitee.fubluesky.kernel.file.api.utils.IoUtils;
import com.gitee.fubluesky.kernel.file.ftp.pojo.FtpFileProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/**
 * @author yanghq
 * @version 1.0
 * @since 2021-07-31 13:53
 */
@Slf4j
public class FtpFileOperator implements FileOperatorApi {

	private final FtpFileProperties ftpFileProperties;

	public FtpFileOperator(FtpFileProperties ftpFileProperties) {
		this.ftpFileProperties = ftpFileProperties;
	}

	private final ThreadLocal<FTPClient> ftpClientThreadLocal = new ThreadLocal<>();

	/**
	 * 链接ftp服务器
	 */
	private void openConnect() throws IOException {
		// 重复请求链次数
		int reply;
		if (ftpClientThreadLocal.get() != null && ftpClientThreadLocal.get().isConnected()) {
			return;
		}
		// 初始化
		FTPClient ftpClient = new FTPClient();
		// ip
		ftpClient.connect(ftpFileProperties.getHost());
		// 端口
		ftpClient.setDefaultPort(ftpFileProperties.getPort());
		// 编码
		ftpClient.setControlEncoding("UTF-8");

		reply = ftpClient.getReplyCode();

		// ftp 服务拒绝链接
		if (!FTPReply.isPositiveCompletion(reply)) {
			ftpClient.disconnect();
		}

		if (ftpFileProperties.getAuth()) {
			// 登陆用户
			if (!ftpClient.login(ftpFileProperties.getUserName(), ftpFileProperties.getPassword())) {
				disConnect();
			}
		}
		// 设置传输超时时间为60秒
		ftpClient.setDataTimeout(60000);
		/*
		 * ftpClient.setConnectTimeout(60000); //连接超时为60秒
		 */
		ftpClient.enterLocalPassiveMode();
		ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
		ftpClient.setBufferSize(1024 * 1024 * 10);
		if (ftpFileProperties.getIsPassive()) {
			ftpClient.enterLocalPassiveMode();
		}
		else {
			ftpClient.enterLocalActiveMode();
		}
		ftpClientThreadLocal.set(ftpClient);
	}

	/**
	 * 关闭链接
	 */
	private void disConnect() {
		FTPClient ftpClient = ftpClientThreadLocal.get();
		if (ftpClient != null) {
			try {
				ftpClient.logout();
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			finally {
				if (ftpClient.isConnected()) {
					try {
						ftpClient.disconnect();
					}
					catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		ftpClientThreadLocal.remove();
	}

	private String getFileNameByPath(String filePath) {
		String fileName = "";
		try {
			fileName = filePath.substring(filePath.lastIndexOf(FileConstants.BACKSLASHES) + 1);
		}
		catch (Exception e) {
			log.error("get file name fail", e);
		}
		return fileName;
	}

	private boolean createDir(String remote, FTPClient ftpClient) {
		String directory = remote.substring(0, remote.lastIndexOf(FileConstants.BACKSLASHES) + 1);
		try {
			if (!FileConstants.BACKSLASHES.equalsIgnoreCase(directory) && !ftpClient
					.changeWorkingDirectory(new String(directory.getBytes("GBK"), StandardCharsets.ISO_8859_1))) {
				int start;
				int end;
				if (directory.startsWith(FileConstants.BACKSLASHES)) {
					start = 1;
				}
				else {
					start = 0;
				}
				end = directory.indexOf(FileConstants.BACKSLASHES, start);
				do {
					String subDirectory = new String(remote.substring(start, end).getBytes("GBK"),
							StandardCharsets.ISO_8859_1);
					if (!ftpClient.changeWorkingDirectory(subDirectory)) {
						if (!ftpClient.makeDirectory(subDirectory)) {
							log.error("create directory " + subDirectory + " fail.");
							return false;
						}
						ftpClient.changeWorkingDirectory(subDirectory);
					}
					start = end + 1;
					end = directory.indexOf(FileConstants.BACKSLASHES, start);
				}
				while (end > start);
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		return true;
	}

	/**
	 * 获取http路径前缀
	 * @return http路径前缀
	 */
	@Override
	public String getHttpPrefix() {
		return ftpFileProperties.getDomain();
	}

	@Override
	public String upload(InputStream inputStream, String savePrefixPath, String path, Boolean datePathEnabled) {
		try {
			String prefix = savePrefixPath;
			if (StringUtils.isNotEmpty(ftpFileProperties.getPrefix()) && datePathEnabled) {
				if (prefix.indexOf(FileConstants.BACKSLASHES) == 0) {
					prefix = ftpFileProperties.getPrefix() + prefix;
				}
				else {
					prefix = ftpFileProperties.getPrefix() + FileConstants.BACKSLASHES + prefix;
				}
			}
			if (ftpFileProperties.getDatePathEnabled() && datePathEnabled) {
				path = getPath(prefix, path.substring(path.lastIndexOf(".")));
			}
			else if (StringUtils.isNotBlank(prefix)) {
				if (path.indexOf(FileConstants.BACKSLASHES) == 0) {
					path = prefix + path;
				}
				else {
					path = prefix + FileConstants.BACKSLASHES + path;
				}
			}
			// 打开链接
			openConnect();
			FTPClient ftpClient = ftpClientThreadLocal.get();
			this.createDir(path, ftpClient);
			String fileName = getFileNameByPath(path);
			ftpClient.storeFile(fileName, inputStream);
			inputStream.close();
		}
		catch (Exception e) {
			log.error("ftp file upload error:", e);
			throw new FileException(FileExceptionEnum.FILE_UPLOAD_ERROR);
		}
		finally {
			disConnect();
		}
		if (StringUtils.isNotBlank(path) && path.indexOf(FileConstants.BACKSLASHES) == 0) {
			return path;
		}
		return FileConstants.BACKSLASHES + path;
	}

	/**
	 * 获取文件字节
	 * @param path 文件路径，包含文件名
	 * @return 文件字节
	 */
	@Override
	public byte[] getFileBytes(String path) {
		InputStream objectContent;
		try {
			// 打开链接
			openConnect();
			FTPClient ftpClient = ftpClientThreadLocal.get();

			objectContent = ftpClient.retrieveFileStream(path);
			return IoUtils.readStreamAsByteArray(objectContent);
		}
		catch (Exception e) {
			log.error("ftp file delete error:", e);
			throw new FileException(FileExceptionEnum.FILE_DELETE_ERROR);
		}
		finally {
			disConnect();
		}
	}

	/**
	 * 文件删除
	 * @param path 文件路径，包含文件名
	 */
	@Override
	public void delete(String path) {
		try {
			// 打开链接
			openConnect();
			FTPClient ftpClient = ftpClientThreadLocal.get();

			ftpClient.deleteFile(path);
		}
		catch (Exception e) {
			log.error("ftp file delete error:", e);
			throw new FileException(FileExceptionEnum.FILE_DELETE_ERROR);
		}
		finally {
			disConnect();
		}
	}

}
