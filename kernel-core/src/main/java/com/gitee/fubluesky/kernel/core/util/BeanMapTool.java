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

package com.gitee.fubluesky.kernel.core.util;

import com.google.common.collect.Maps;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author yanghq
 * @version 1.0
 * @since 2021-07-23 17:50
 */
@Slf4j
@UtilityClass
public class BeanMapTool {

	public Map<String, Object> beanToMap(Object bean) {
		try {
			Map<String, Object> map = Maps.newHashMap();
			// 获取JavaBean的描述器
			BeanInfo b = Introspector.getBeanInfo(bean.getClass(), Object.class);
			// 获取属性描述器
			PropertyDescriptor[] pds = b.getPropertyDescriptors();
			// 对属性迭代
			for (PropertyDescriptor pd : pds) {
				// 属性名称
				String propertyName = pd.getName();
				// 属性值,用getter方法获取
				Method m = pd.getReadMethod();
				// 用对象执行getter方法获得属性值
				Object properValue = m.invoke(bean);

				// 把属性名-属性值 存到Map中
				map.put(propertyName, properValue);
			}
			return map;
		}
		catch (Exception e) {
			log.error("bean to map fail.", e);
			return null;
		}
	}

	public <T> T mapToBean(Map<String, Object> map, Class<T> clz) {
		T obj;
		PropertyDescriptor[] pds;
		try {
			// 创建一个需要转换为的类型的对象
			obj = clz.newInstance();
			// 从Map中获取和属性名称一样的值，把值设置给对象(setter方法)
			// 得到属性的描述器
			BeanInfo b = Introspector.getBeanInfo(clz, Object.class);
			pds = b.getPropertyDescriptors();
		}
		catch (Exception e) {
			log.error("map to bean fail.", e);
			return null;
		}
		for (PropertyDescriptor pd : pds) {
			try {
				// 得到属性的setter方法
				Method setter = pd.getWriteMethod();
				// Long类型需手动转换，否则报类型转换出错
				if ("java.lang.Long".equals(setter.getParameterTypes()[0].getName())) {
					Object value = map.get(pd.getName());
					if (value != null) {
						if (!"java.lang.Long".equals(map.get(pd.getName()).getClass().getName())) {
							setter.invoke(obj, ((Integer) value).longValue());
						}
						else {
							setter.invoke(obj, map.get(pd.getName()));
						}
					}
				}
				else {
					setter.invoke(obj, map.get(pd.getName()));
				}
			}
			catch (Exception e) {
				log.error("map to bean fail.", e);
			}
		}
		return obj;
	}

	public List<Map<String, Object>> objectsToMaps(List<Object> objList) {
		List<Map<String, Object>> list = new ArrayList<>();
		if (objList != null && objList.size() > 0) {
			Map<String, Object> map;
			Object bean;
			for (Object t : objList) {
				bean = t;
				map = beanToMap(bean);
				list.add(map);
			}
		}
		return list;
	}

	public List<Object> mapsToObjects(List<Map<String, Object>> maps, Class<Object> clazz) {
		List<Object> list = new ArrayList<>();
		if (maps != null && maps.size() > 0) {
			Map<String, Object> map;
			for (Map<String, Object> stringMap : maps) {
				map = stringMap;
				Object bean = mapToBean(map, clazz);
				list.add(bean);
			}
		}
		return list;
	}

}
