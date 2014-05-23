package com.ww.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.ww.message.resp.Music;
import com.ww.message.resp.MusicMessage;
import com.ww.message.resp.TextMessage;
import com.ww.sql.imp.UserHelp;
import com.ww.sql.imp.UserUtil;
import com.ww.util.MessageUtil;
import com.ww.util.WechatUtil;

/**
 * ���ķ�����
 * 
 * @author ww
 * @date 2014-05-07
 */
public class CoreService {
	/**
	 * ����΢�ŷ���������
	 * 
	 * @param request
	 * @return xml
	 */

	private static PreparedStatement pstmt;
	private static Connection conn;

	public static String processRequest(HttpServletRequest request) {
		// xml��ʽ����Ϣ����
		String respXml = null;
		// Ĭ�Ϸ��ص��ı���Ϣ����
		String respContent = "δ֪����Ϣ���ͣ�";
		try {
			// ����parseXml��������������Ϣ
			Map<String, String> requestMap = MessageUtil.parseXml(request);
			// ���ͷ��ʺ�
			String fromUserName = requestMap.get("FromUserName");
			// ������΢�ź�
			String toUserName = requestMap.get("ToUserName");
			// ��Ϣ����
			String msgType = requestMap.get("MsgType");
			String msgId = requestMap.get("MsgId");
			String getContent = requestMap.get("Content");
			// java.sql.Date date = getCurrentJavaSqlDate();
			// String sql =
			// "insert into messagetext(FromUserName,ToUserName,MsgType,CreateTime,TContent)values(?,?,?,?,?)";
			// conn = DBConnection.getDBConnection();
			// pstmt = conn.prepareStatement(sql);
			// pstmt.setString(1, fromUserName);
			// pstmt.setString(2, toUserName);
			// pstmt.setString(3, msgType);
			// pstmt.setDate(4, date);
			// pstmt.setString(5, getContent);
			// boolean or= pstmt.execute();
			UserUtil user = new UserHelp();

			// �ı���Ϣ
			if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)) {
				// respContent = "�����͵����ı���Ϣ��";
				if ("110".equalsIgnoreCase(getContent)) {
					boolean isok = SAEMailService.sendMail();
					respContent = isok+getContent + "������,��Ͻ���ȥ���ɣ������������";
				} else if ("120".equalsIgnoreCase(getContent)) {
					respContent = getContent + "������,�����ؼ����ʲô��Ե�ɣ�����������鲻̫�ã�";
				} else if (WechatUtil.isQqFace(getContent)) {
					respContent = "�ٺ٣��Ҷ����棬��������\\n" + getContent;
				} else if (getContent.startsWith("����")) {
					// ������2���ּ����������+���ո�-���������ȥ��
					String keyWord = getContent.replaceAll(
							"^����[\\+ ~!@#%^-_=]?", "");
					// �����������Ϊ��
					if ("".equals(keyWord)) {
						// respContent = getUsage();
					} else {
						String[] kwArr = keyWord.split("@");
						// ��������
						String musicTitle = kwArr[0];
						// �ݳ���Ĭ��Ϊ��
						String musicAuthor = "";
						if (2 == kwArr.length)
							musicAuthor = kwArr[1];

						// ��������
						Music music = BaiDuService.searchMusic(musicTitle,
								musicAuthor);
						// δ����������
						if (null == music) {
							respContent = "�Բ���û���ҵ��������ĸ���<" + musicTitle + ">��";
						} else {
							// ������Ϣ
							MusicMessage musicMessage = new MusicMessage();
							musicMessage.setToUserName(fromUserName);
							musicMessage.setFromUserName(toUserName);
							musicMessage.setCreateTime(new Date().getTime());
							musicMessage
									.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_MUSIC);
							musicMessage.setMusic(music);
							respXml = MessageUtil.messageToXml(musicMessage);
						}
					}
				} else {
					respContent = user.getUserbyId(request, getContent)
							.toString();

					// �ظ��ı���Ϣ
					TextMessage textMessage = new TextMessage();
					textMessage.setToUserName(fromUserName);
					textMessage.setFromUserName(toUserName);
					textMessage.setCreateTime(new Date().getTime());
					textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
					// �����ı���Ϣ������
					if ("".equals(respContent)) {
						respContent = getContent + "����,������˼���һ�����Сѧ�������������?"
								+ "[�ѹ�] /�ѹ� /::(";
					}
					textMessage.setContent(respContent);
					// ���ı���Ϣ����ת����xml
					respXml = MessageUtil.messageToXml(textMessage);
				}

			}
			// ͼƬ��Ϣ
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_IMAGE)) {
				respContent = "�����͵���ͼƬ��Ϣ��";
			}
			// ������Ϣ
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_VOICE)) {
				respContent = "�����͵���������Ϣ��";
			}
			// ��Ƶ��Ϣ
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_VIDEO)) {
				respContent = "�����͵�����Ƶ��Ϣ��";
			}
			// ����λ����Ϣ
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LOCATION)) {
				respContent = "�����͵��ǵ���λ����Ϣ��";
			}
			// ������Ϣ
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LINK)) {
				respContent = "�����͵���������Ϣ��";
			}
			// �¼�����
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT)) {
				// �¼�����
				String eventType = requestMap.get("Event");
				// ��ע
				if (eventType.equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)) {
					// respContent = "лл���Ĺ�ע��";
					TextMessage textMessage = new TextMessage();
					textMessage.setToUserName(fromUserName);
					textMessage.setFromUserName(toUserName);
					textMessage.setCreateTime(new Date().getTime());
					textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);

					StringBuffer buffer = new StringBuffer();

					buffer.append("����,����õ�����,��ظ�����ѡ�����:").append("\n\n");

					buffer.append("110 	�������ϲ��").append("\n");

					buffer.append("120 	�������ϲ��").append("\n");
					//buffer.append("0 	Groovy").append("\n");
					buffer.append("1 	�ظ�1���鿴��������ѵ���Ϣ��").append("\n");
					buffer.append("2 	�ظ�2���鿴�����Ϣ��").append("\n");
					buffer.append("3 	�ظ�3���鿴��ǰ��Ů�ѵ���Ϣ��").append("\n");
					buffer.append("4 	�ظ�4���鿴������������Ϣ��").append("\n");
					buffer.append("5 	����\"����+������\",��ʱ����������ϲ�������֣�").append("\n");

					respContent = buffer.toString();
					textMessage.setContent(respContent);

					respXml = MessageUtil.messageToXml(textMessage);

				}
				// ȡ����ע
				else if (eventType.equals(MessageUtil.EVENT_TYPE_UNSUBSCRIBE)) {
					// TODO ȡ�����ĺ��û��������յ������˺ŷ��͵���Ϣ����˲���Ҫ�ظ�
				}
				// ɨ���������ά��
				else if (eventType.equals(MessageUtil.EVENT_TYPE_SCAN)) {
					// TODO ����ɨ���������ά���¼�
				}
				// �ϱ�����λ��
				else if (eventType.equals(MessageUtil.EVENT_TYPE_LOCATION)) {
					// TODO �����ϱ�����λ���¼�
				}
				// �Զ���˵�
				else if (eventType.equals(MessageUtil.EVENT_TYPE_CLICK)) {
					// TODO ����˵�����¼�
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return respXml;
	}

	public static java.sql.Date getCurrentJavaSqlDate() {
		java.util.Date today = new java.util.Date();
		return new java.sql.Date(today.getTime());
	}

}
