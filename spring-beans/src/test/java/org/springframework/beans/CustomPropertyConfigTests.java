package org.springframework.beans;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.beans.pojo.StudentService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.util.DefaultPropertiesPersister;
import org.springframework.util.PropertiesPersister;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @Auther: chendongtao
 * @Date: 2021/7/19 13:45
 * @Description:
 */
public class CustomPropertyConfigTests extends PropertyPlaceholderConfigurer {
	private Resource[] locations;

	private PropertiesPersister propertiesPersister = new DefaultPropertiesPersister();

	@Override
	public void setLocations(Resource... locations) {
		super.setLocations(locations);
	}

	@Override
	public void setLocalOverride(boolean localOverride) {
		this.localOverride = localOverride;
	}

	/**
	 * 覆盖这个方法，根据启动参数，动态读取配置文件
	 *
	 * @param props
	 * @throws IOException
	 */
	@Override
	protected void loadProperties(Properties props) throws IOException {
		if (locations != null) {
			// locations 里面就已经包含了那三个定义的文件
			for (Resource location : this.locations) {
				InputStream is = null;
				try {
					String filename = location.getFilename();
					String env = "application-" + System.getProperty("spring.profiles.active", "dev") + ".properties";

					// 找到我们需要的文件，加载
					if (filename.contains(env)) {
						logger.info("Loading properties file from " + location);
						is = location.getInputStream();
						this.propertiesPersister.load(props, is);

					}
				} catch (IOException ex) {
					logger.info("读取配置文件失败.....");
					throw ex;
				} finally {
					if (is != null) {
						is.close();
					}
				}
			}
		}
	}

	@Test
	public void test() {
		ApplicationContext context = new ClassPathXmlApplicationContext("org/springframework/beans/spring.xml");

		StudentService studentService = (StudentService) context.getBean("studentService");
		System.out.println("student name abc:" + studentService.getName());
	}
}
