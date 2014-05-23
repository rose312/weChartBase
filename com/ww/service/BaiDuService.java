package com.ww.service;

import java.io.InputStream;

import com.ww.message.resp.Music;
import com.ww.util.BaiDuUtil;
import com.ww.util.HttpUtil;

/**
 * �ٶ�API������
 * 
 * @author ww
 * @date 2014-05-07
 */
public class BaiDuService {
	/**
	 * �������ƺ�������������
	 * 
	 * @param musicTitle
	 *            ��������
	 * @param musicAuthor
	 *            ��������
	 * @return Music
	 */
	public static Music searchMusic(String musicTitle, String musicAuthor) {
		// �ٶ�����������ַ
		String requestUrl = "http://box.zhangmen.baidu.com/x?op=12&count=1&title={TITLE}$${AUTHOR}$$$$";
		// ���������ơ����߽�URL����
		requestUrl = requestUrl.replace("{TITLE}",
				HttpUtil.urlEncodeUTF8(musicTitle));
		requestUrl = requestUrl.replace("{AUTHOR}",
				HttpUtil.urlEncodeUTF8(musicAuthor));
		// �������ơ������м�Ŀո�
		requestUrl = requestUrl.replaceAll("\\+", "%20");

		// ��ѯ����ȡ���ؽ��
		InputStream inputStream = HttpUtil.httpRequest(requestUrl);
		// �ӷ��ؽ���н�����Music
		Music music = BaiDuUtil.parseMusic(inputStream);

		// ���music��Ϊnull�����ñ��������
		if (null != music) {
			music.setTitle(musicTitle);
			// ������߲�Ϊ""������������Ϊ����
			if (!"".equals(musicAuthor))
				music.setDescription(musicAuthor);
			else
				music.setDescription("���԰ٶ�����");
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

	// ���Է���
	public static void main(String[] args) {
		Music music = searchMusic("�����Լ�", "");
		System.out.println("�������ƣ�" + music.getTitle());
		System.out.println("����������" + music.getDescription());
		System.out.println("��ͨƷ�����ӣ�" + music.getMusicUrl());
		System.out.println("��Ʒ�����ӣ�" + music.getHQMusicUrl());
		
		getPlace();
		
		
		
	}
	
}