package com.ww.message.event;

/**
 * ɨ���������ά���¼�
 * 
 * @author ww
 * @date 2014-05-12
 */
public class QRCodeEvent extends BaseEvent {
	// �¼�KEYֵ
	private String EventKey;
	// ���ڻ�ȡ��ά��ͼƬ
	private String Ticket;

	public String getEventKey() {
		return EventKey;
	}

	public void setEventKey(String eventKey) {
		EventKey = eventKey;
	}

	public String getTicket() {
		return Ticket;
	}

	public void setTicket(String ticket) {
		Ticket = ticket;
	}
}
