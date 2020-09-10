package com.itheima.utils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
  
public class HTML_td{
	private File[] files;

	public HTML_td(File[] files) {
		this.files = files;
		
	}

	public HashMap getNumDir() {
		HashMap hashedMap = new HashMap();
		for (int i = 0; i < files.length; i++) {
			if (files[i].isDirectory()) {
				hashedMap.put(files[i].getAbsolutePath().replaceAll("\\\\","/"), new File(files[i].getAbsolutePath().replaceAll("\\\\","/")).listFiles().length);
				
			}
	   	}
		return hashedMap;
	}

		public Map<String, Integer> getMarkNum() {
		Map<String, Integer> hashedMap = new HashMap<String, Integer>();
		Map<String, Integer> resMap = new HashMap<String, Integer>();
		for (int i = 0; i < files.length; i++) {
			if (files[i].isDirectory()) {
				hashedMap.put(files[i].getAbsolutePath().replaceAll("\\\\","/"), new File(files[i].getAbsolutePath().replaceAll("\\\\","/")).listFiles().length);
			
				File[] nextChild=files[i].listFiles();
				int total=0;
				for (File nextfile : nextChild) {
					if(nextfile.getName().contains("_mask")) {
						total++;
					}
				}
				if(total!=0) {
					resMap.put(files[i].getAbsolutePath().replaceAll("\\\\","/"), total);
					
				}
			}	
		}
		if(!resMap.isEmpty()) {
			return resMap;
		}
		
		for (String key : hashedMap.keySet()) {
			if (hashedMap.keySet().contains(key + "_mask")) {
				resMap.put(key, hashedMap.get(key + "_mask"));
				System.out.println(key + "-----" + hashedMap.get(key + "_mask") + "---resMap");
			}
		}
		return resMap;
	}
	public Map<String, Integer> getYesNum() {
		Map<String, Integer> hashedMap = new HashMap<String, Integer>();
		Map<String, Integer> resMap = new HashMap<String, Integer>();
		for (int i = 0; i < files.length; i++) {
			if (files[i].isDirectory()) {
				hashedMap.put(files[i].getAbsolutePath().replaceAll("\\\\","/"), new File(files[i].getAbsolutePath().replaceAll("\\\\","/")).listFiles().length);
				File[] nextChild=files[i].listFiles();
				int total=0;
				for (File nextfile : nextChild) {
					if(nextfile.getName().contains("_yes")) {
						total++;
					}
				}
				if(total!=0) {
					resMap.put(files[i].getAbsolutePath().replaceAll("\\\\","/"), total);
					
				}
			}	
		}
		if(!resMap.isEmpty()) {
			return resMap;
		}
		
		for (String key : hashedMap.keySet()) {
			if (hashedMap.keySet().contains(key + "_yes")) {
				resMap.put(key, hashedMap.get(key + "_yes"));
				System.out.println(key + "-----" + hashedMap.get(key + "_yes") + "---resMap");
			}
		}
		return resMap;
	}

	public Map<String, String> getTime() {
		Map<String, String> hashedMap = new HashMap<String, String>();
		for (int i = 0; i < files.length; i++) {
			if (files[i].isDirectory()) {
				File f = files[i];
				Calendar cal = Calendar.getInstance();
				long time = f.lastModified();
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				cal.setTimeInMillis(time);
				hashedMap.put(files[i].getAbsolutePath().replaceAll("\\\\","/"), formatter.format(cal.getTime()));
			}
		}
		return hashedMap;
	}
	
	public  String getState(int fileTtotal, int maskTotal) {
		String state;
		if(maskTotal == 0) {
			state = "未标记";
		}else if(maskTotal < fileTtotal) {
			state = "标记中";
		}else {
			state = "已标记";
		}
		return state;
	}

	
}
