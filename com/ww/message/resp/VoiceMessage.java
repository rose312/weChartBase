package com.ww.message.resp;

/**
 * ������Ϣ
 * 
 * @author ww
 * @date 2014-05-12
 */
public class VoiceMessage extends BaseMessage {
	// ����
	private Voice Voice;

	public Voice getVoice() {
		return Voice;
	}

	public void setVoice(Voice voice) {
		Voice = voice;
	}
}
