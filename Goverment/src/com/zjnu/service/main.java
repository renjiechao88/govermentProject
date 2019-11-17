package com.zjnu.service;

public class main {

	public static void main(String[] args) {
		String value= "student.sname";
		String[] name = value.split("\\.");
		System.out.println(name.length);
		for(int i=0;i<name.length;i++) System.out.println(name[i]);
	}
}
