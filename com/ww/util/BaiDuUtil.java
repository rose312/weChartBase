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
	 * 解析音乐参数
	 * 
	 * @param inputStream
	 *            百度音乐搜索API返回的输入流
	 * @return Music
	 */
	@SuppressWarnings("unchecked")
	public static Music parseMusic(InputStream inputStream) {
		Music music = null;
		try {
			// 使用dom4j解析xml字符串
			SAXReader reader = new SAXReader();
			Document document = reader.read(inputStream);
			// 得到xml根元素
			Element root = document.getRootElement();
			// count表示搜索到的歌曲数
			String count = root.element("count").getText();
			// 当搜索到的歌曲数大于0时
			if (!"0".equals(count)) {
				// 普通品质
				List<Element> urlList = root.elements("url");
				// 高品质
				List<Element> durlList = root.elements("durl");

				// 普通品质的encode、decode
				String urlEncode = urlList.get(0).element("encode").getText();
				String urlDecode = urlList.get(0).element("decode").getText();
				// 普通品质音乐的URL
				String url = urlEncode.substring(0,
						urlEncode.lastIndexOf("/") + 1) + urlDecode;
				if (-1 != urlDecode.lastIndexOf("&"))
					url = urlEncode
							.substring(0, urlEncode.lastIndexOf("/") + 1)
							+ urlDecode
									.substring(0, urlDecode.lastIndexOf("&"));

				// 默认情况下，高音质音乐的URL 等于 普通品质音乐的URL
				String durl = url;

				// 判断高品质节点是否存在
				Element durlElement = durlList.get(0).element("encode");
				if (null != durlElement) {
					// 高品质的encode、decode
					String durlEncode = durlList.get(0).element("encode")
							.getText();
					String durlDecode = durlList.get(0).element("decode")
							.getText();
					// 高品质音乐的URL
					durl = durlEncode.substring(0,
							durlEncode.lastIndexOf("/") + 1) + durlDecode;
					if (-1 != durlDecode.lastIndexOf("&"))
						durl = durlEncode.substring(0,
								durlEncode.lastIndexOf("/") + 1)
								+ durlDecode.substring(0,
										durlDecode.lastIndexOf("&"));
				}
				music = new Music();
				// 设置普通品质音乐链接
				music.setMusicUrl(url);
				// 设置高品质音乐链接
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
			// 得到xml根元素
			Element root = document.getRootElement();
			// 从根元素获取<results>
			Element resultsElement = root.element("results");
			// 从<results>中获取<result>集合
			List<Element> resultElementList = resultsElement.elements("result");

			// 遍历所有子节点
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
