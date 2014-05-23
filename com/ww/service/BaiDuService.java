package com.ww.service;

import java.io.InputStream;

import com.ww.message.resp.Music;
import com.ww.util.BaiDuUtil;
import com.ww.util.HttpUtil;

/**
 * 百度API操作类
 * 
 * @author ww
 * @date 2014-05-07
 */
public class BaiDuService {
	/**
	 * 根据名称和作者搜索音乐
	 * 
	 * @param musicTitle
	 *            音乐名称
	 * @param musicAuthor
	 *            音乐作者
	 * @return Music
	 */
	public static Music searchMusic(String musicTitle, String musicAuthor) {
		// 百度音乐搜索地址
		String requestUrl = "http://box.zhangmen.baidu.com/x?op=12&count=1&title={TITLE}$${AUTHOR}$$$$";
		// 对音乐名称、作者进URL编码
		requestUrl = requestUrl.replace("{TITLE}",
				HttpUtil.urlEncodeUTF8(musicTitle));
		requestUrl = requestUrl.replace("{AUTHOR}",
				HttpUtil.urlEncodeUTF8(musicAuthor));
		// 处理名称、作者中间的空格
		requestUrl = requestUrl.replaceAll("\\+", "%20");

		// 查询并获取返回结果
		InputStream inputStream = HttpUtil.httpRequest(requestUrl);
		// 从返回结果中解析出Music
		Music music = BaiDuUtil.parseMusic(inputStream);

		// 如果music不为null，设置标题和描述
		if (null != music) {
			music.setTitle(musicTitle);
			// 如果作者不为""，将描述设置为作者
			if (!"".equals(musicAuthor))
				music.setDescription(musicAuthor);
			else
				music.setDescription("来自百度音乐");
		}
		return music;
	}

	public static void getPlace() {

		String requestUrl = "http://api.map.baidu.com/place/v2/search?" +
				"&query=%E9%93%B6%E8%A1%8C" +
				"&region=%E6%B5%8E%E5%8D%97" +
				"&output=xml" +
				"&ak=AAd7756a027a0d40fee17085934afda7";
		String xml = HttpUtil.httpRequestToString(requestUrl);
		BaiDuUtil.pasePlaces(xml);
	}

	// 测试方法
	public static void main(String[] args) {
		Music music = searchMusic("相信自己", "");
		System.out.println("音乐名称：" + music.getTitle());
		System.out.println("音乐描述：" + music.getDescription());
		System.out.println("普通品质链接：" + music.getMusicUrl());
		System.out.println("高品质链接：" + music.getHQMusicUrl());
		
		getPlace();
		
		
		
	}
	
}