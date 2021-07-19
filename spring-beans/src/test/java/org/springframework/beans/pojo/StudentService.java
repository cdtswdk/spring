package org.springframework.beans.pojo;

/**
 * @Auther: chendongtao
 * @Date: 2021/7/19 13:55
 * @Description:
 */
public class StudentService {
	private int age;
	private String name;

	public StudentService() {
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
