package com.rafaeldeda.cursomc.resources.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

public class URL {
	public static List<Integer> decodeIntList(String s){
		String[] ver = s.split(",");
		List<Integer> list = new ArrayList<Integer>();
		for (int i = 0; i < ver.length; i++) {
			list.add(Integer.parseInt(ver[i]));
		}
		return list;
	}
	
	public static String decodeParam(String s) {
		try {
			return URLDecoder.decode(s, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			return "";
		}
	}
}
