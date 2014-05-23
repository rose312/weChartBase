package com.ww.util;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.ww.entity.Location;
import com.ww.entity.Place;
import com.ww.message.resp.Music;

public class BaiDuUtil {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

	}

	/**
	 * �������ֲ���
	 * 
	 * @param inputStream
	 *            �ٶ���������API���ص�������
	 * @return Music
	 */
	@SuppressWarnings("unchecked")
	public static Music parseMusic(InputStream inputStream) {
		Music music = null;
		try {
			// ʹ��dom4j����xml�ַ���
			SAXReader reader = new SAXReader();
			Document document = reader.read(inputStream);
			// �õ�xml��Ԫ��
			Element root = document.getRootElement();
			// count��ʾ�������ĸ�����
			String count = root.element("count").getText();
			// ���������ĸ���������0ʱ
			if (!"0".equals(count)) {
				// ��ͨƷ��
				List<Element> urlList = root.elements("url");
				// ��Ʒ��
				List<Element> durlList = root.elements("durl");

				// ��ͨƷ�ʵ�encode��decode
				String urlEncode = urlList.get(0).element("encode").getText();
				String urlDecode = urlList.get(0).element("decode").getText();
				// ��ͨƷ�����ֵ�URL
				String url = urlEncode.substring(0,
						urlEncode.lastIndexOf("/") + 1) + urlDecode;
				if (-1 != urlDecode.lastIndexOf("&"))
					url = urlEncode
							.substring(0, urlEncode.lastIndexOf("/") + 1)
							+ urlDecode
									.substring(0, urlDecode.lastIndexOf("&"));

				// Ĭ������£����������ֵ�URL ���� ��ͨƷ�����ֵ�URL
				String durl = url;

				// �жϸ�Ʒ�ʽڵ��Ƿ����
				Element durlElement = durlList.get(0).element("encode");
				if (null != durlElement) {
					// ��Ʒ�ʵ�encode��decode
					String durlEncode = durlList.get(0).element("encode")
							.getText();
					String durlDecode = durlList.get(0).element("decode")
							.getText();
					// ��Ʒ�����ֵ�URL
					durl = durlEncode.substring(0,
							durlEncode.lastIndexOf("/") + 1) + durlDecode;
					if (-1 != durlDecode.lastIndexOf("&"))
						durl = durlEncode.substring(0,
								durlEncode.lastIndexOf("/") + 1)
								+ durlDecode.substring(0,
										durlDecode.lastIndexOf("&"));
				}
				music = new Music();
				// ������ͨƷ����������
				music.setMusicUrl(url);
				// ���ø�Ʒ����������
				music.setHQMusicUrl(durl);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return music;
	}

	public static List<Place> pasePlaces(String xml) {

		List<Place> lPlaces = new ArrayList<Place>();

		try {
			Document document = DocumentHelper.parseText(xml);
			// �õ�xml��Ԫ��
			Element root = document.getRootElement();
			// �Ӹ�Ԫ�ػ�ȡ<results>
			Element resultsElement = root.element("results");
			// ��<results>�л�ȡ<result>����
			List<Element> resultElementList = resultsElement.elements("result");

			// ���������ӽڵ�
			for (Element e : resultElementList) {
				Place place=new Place();
				place.setName(e.element("name").getTextTrim()!=null ?e.element("name").getTextTrim():null);
				Location location=new Location();
				Element locEl=e.element("location");
				location.setLat(locEl.element("lat").getTextTrim()!=null ?locEl.element("lat").getTextTrim():null);
				location.setLng(locEl.element("lng").getTextTrim()!=null ?locEl.element("lng").getTextTrim():null);
				place.setLocation(location);
				place.setAddress( e.element("address").getTextTrim()!=null ?e.element("address").getTextTrim():null);
				place.setUid( e.element("uid").getTextTrim()!=null ?e.element("uid").getTextTrim():null);
				place.setTelephone(e.element("telephone").getTextTrim()!=null ?e.element("telephone").getTextTrim():null);
				lPlaces.add(place);
			}
		} catch (DocumentException e) {
			e.printStackTrace();
		}

		//

		return lPlaces;

	}

}
