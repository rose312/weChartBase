package com.ww.message.req;

/**
 * ��Ƶ��Ϣ
 * 
 * @author ww
 * @date 2014-05-12
 */
public class VideoMessage extends BaseMessage {
	// ��Ƶ��Ϣý��id
	private String MediaId;
	// ��Ƶ��Ϣ����ͼ��ý��id
	private String ThumbMediaId;

	public String getMediaId() {
		return MediaId;
	}

	public void setMediaId(String mediaId) {
		MediaId = mediaId;
	}

	public String getThumbMediaId() {
		return ThumbMediaId;
	}

	public void setThumbMediaId(String thumbMediaId) {
		ThumbMediaId = thumbMediaId;
	}
}
