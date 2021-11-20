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

package com.gitee.fubluesky.kernel.excel.easy;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.gitee.fubluesky.kernel.core.exception.ServiceException;
import com.gitee.fubluesky.kernel.excel.api.ExcelApi;
import com.gitee.fubluesky.kernel.excel.api.constants.ExcelConstants;
import com.gitee.fubluesky.kernel.excel.api.exception.ExcelException;
import com.gitee.fubluesky.kernel.excel.api.exception.enums.ExcelExceptionEnum;
import com.gitee.fubluesky.kernel.excel.api.pojo.ExportParam;
import com.gitee.fubluesky.kernel.excel.api.pojo.ExportSheetParam;
import com.gitee.fubluesky.kernel.excel.easy.listener.DefaultDataListener;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Collection;
import java.util.List;

/**
 * @author yanghq
 * @version 1.0
 * @since 2021-11-18 16:14
 */
@Slf4j
public class EasyExcelService implements ExcelApi {

	@Override
	public void download(ExportParam exportParam) {
		if (exportParam == null) {
			log.error("download fail: exportParam is null");
			return;
		}
		try {
			HttpServletResponse response = exportParam.getResponse();
			response.setContentType("application/vnd.ms-excel");
			response.setCharacterEncoding("utf-8");

			String fileName = URLEncoder.encode(exportParam.getFileName(), "UTF-8").replaceAll("\\+", "%20");

			response.setHeader("Content-disposition", ExcelConstants.ATTACHMENT + fileName + ".xlsx");

			EasyExcel.write(response.getOutputStream(), exportParam.getClazz()).excelType(ExcelTypeEnum.XLSX)
					.sheet(exportParam.getSheetName()).doWrite(exportParam.getData());
		}
		catch (UnsupportedEncodingException e) {
			log.error("filename error:", e);
		}
		catch (IOException e) {
			throw new ExcelException(ExcelExceptionEnum.EXCEL_EXPORT_ERROR);
		}
	}

	@Override
	public void download(ExportSheetParam exportParam) {
		if (exportParam == null) {
			log.error("download fail: exportParam is null");
			return;
		}
		try {
			HttpServletResponse response = exportParam.getResponse();
			response.setContentType("application/vnd.ms-excel");
			response.setCharacterEncoding("utf-8");

			String fileName = URLEncoder.encode(exportParam.getFileName(), "UTF-8").replaceAll("\\+", "%20");

			response.setHeader("Content-disposition", ExcelConstants.ATTACHMENT + fileName + ".xlsx");

			ExcelWriter excelWriter = EasyExcel.write(response.getOutputStream(), exportParam.getClazz())
					.excelType(ExcelTypeEnum.XLSX).build();

			if (exportParam.getSheets() != null && !exportParam.getSheets().isEmpty()) {
				for (int i = 0; i < exportParam.getSheets().size(); i++) {
					String sheetName = exportParam.getSheets().get(i).getSheetName();
					if (StringUtils.isBlank(sheetName)) {
						sheetName = "sheet" + i;
					}
					Collection<?> exportData = exportParam.getSheets().get(i).getData();
					if (exportData == null) {
						exportData = Lists.newArrayList();
					}
					WriteSheet writeSheet = EasyExcel.writerSheet(i, sheetName).build();
					excelWriter.write(exportData, writeSheet);
				}
			}
			excelWriter.finish();
		}
		catch (UnsupportedEncodingException e) {
			log.error("filename error:", e);
		}
		catch (IOException e) {
			throw new ExcelException(ExcelExceptionEnum.EXCEL_EXPORT_ERROR);
		}
	}

	@Override
	public <T> List<T> getList(InputStream inputStream, Class<T> clazz) {
		if (null == inputStream) {
			return Lists.newArrayList();
		}
		// 创建监听器
		DefaultDataListener<T> readListener = new DefaultDataListener<>();
		// 读取文件
		try {
			EasyExcel.read(inputStream, clazz, readListener).sheet().doRead();
		}
		catch (ServiceException e) {
			throw e;
		}
		catch (Exception e) {
			log.error("easy excl getList error:", e);
			throw new ExcelException(ExcelExceptionEnum.EXCEL_ERROR);
		}
		return readListener.getDataList();
	}

}
