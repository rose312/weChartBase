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
 * 核心服务类
 * 
 * @author ww
 * @date 2014-05-07
 */
public class CoreService {
	/**
	 * 处理微信发来的请求
	 * 
	 * @param request
	 * @return xml
	 */

	private static PreparedStatement pstmt;
	private static Connection conn;

	public static String processRequest(HttpServletRequest request) {
		// xml格式的消息数据
		String respXml = null;
		// 默认返回的文本消息内容
		String respContent = "未知的消息类型！";
		try {
			// 调用parseXml方法解析请求消息
			Map<String, String> requestMap = MessageUtil.parseXml(request);
			// 发送方帐号
			String fromUserName = requestMap.get("FromUserName");
			// 开发者微信号
			String toUserName = requestMap.get("ToUserName");
			// 消息类型
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

			// 文本消息
			if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)) {
				// respContent = "您发送的是文本消息！";
				if ("110".equalsIgnoreCase(getContent)) {
					boolean isok = SAEMailService.sendMail();
					respContent = isok+getContent + "：先生,你赶紧出去躲躲吧，最近风声紧！";
				} else if ("120".equalsIgnoreCase(getContent)) {
					respContent = getContent + "：先生,你早点回家想吃什么多吃点吧，最近阎王心情不太好！";
				} else if (WechatUtil.isQqFace(getContent)) {
					respContent = "嘿嘿，我逗你玩，别生气！\\n" + getContent;
				} else if (getContent.startsWith("歌曲")) {
					// 将歌曲2个字及歌曲后面的+、空格、-等特殊符号去掉
					String keyWord = getContent.replaceAll(
							"^歌曲[\\+ ~!@#%^-_=]?", "");
					// 如果歌曲名称为空
					if ("".equals(keyWord)) {
						// respContent = getUsage();
					} else {
						String[] kwArr = keyWord.split("@");
						// 歌曲名称
						String musicTitle = kwArr[0];
						// 演唱者默认为空
						String musicAuthor = "";
						if (2 == kwArr.length)
							musicAuthor = kwArr[1];

						// 搜索音乐
						Music music = BaiDuService.searchMusic(musicTitle,
								musicAuthor);
						// 未搜索到音乐
						if (null == music) {
							respContent = "对不起，没有找到你想听的歌曲<" + musicTitle + ">。";
						} else {
							// 音乐消息
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

					// 回复文本消息
					TextMessage textMessage = new TextMessage();
					textMessage.setToUserName(fromUserName);
					textMessage.setFromUserName(toUserName);
					textMessage.setCreateTime(new Date().getTime());
					textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
					// 设置文本消息的内容
					if ("".equals(respContent)) {
						respContent = getContent + "：亲,不好意思，我还在上小学，你乐意教我吗?"
								+ "[难过] /难过 /::(";
					}
					textMessage.setContent(respContent);
					// 将文本消息对象转换成xml
					respXml = MessageUtil.messageToXml(textMessage);
				}

			}
			// 图片消息
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_IMAGE)) {
				respContent = "您发送的是图片消息！";
			}
			// 语音消息
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_VOICE)) {
				respContent = "您发送的是语音消息！";
			}
			// 视频消息
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_VIDEO)) {
				respContent = "您发送的是视频消息！";
			}
			// 地理位置消息
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LOCATION)) {
				respContent = "您发送的是地理位置消息！";
			}
			// 链接消息
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LINK)) {
				respContent = "您发送的是链接消息！";
			}
			// 事件推送
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT)) {
				// 事件类型
				String eventType = requestMap.get("Event");
				// 关注
				if (eventType.equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)) {
					// respContent = "谢谢您的关注！";
					TextMessage textMessage = new TextMessage();
					textMessage.setToUserName(fromUserName);
					textMessage.setFromUserName(toUserName);
					textMessage.setCreateTime(new Date().getTime());
					textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);

					StringBuffer buffer = new StringBuffer();

					buffer.append("您好,我是玫瑰的心,请回复数字选择服务:").append("\n\n");

					buffer.append("110 	给你个惊喜！").append("\n");

					buffer.append("120 	给你个惊喜！").append("\n");
					//buffer.append("0 	Groovy").append("\n");
					buffer.append("1 	回复1，查看你的男朋友的信息！").append("\n");
					buffer.append("2 	回复2，查看你的信息！").append("\n");
					buffer.append("3 	回复3，查看他前任女友的信息！").append("\n");
					buffer.append("4 	回复4，查看他的死党的信息！").append("\n");
					buffer.append("5 	输入\"歌曲+歌曲名\",随时方便收听你喜欢的音乐！").append("\n");

					respContent = buffer.toString();
					textMessage.setContent(respContent);

					respXml = MessageUtil.messageToXml(textMessage);

				}
				// 取消关注
				else if (eventType.equals(MessageUtil.EVENT_TYPE_UNSUBSCRIBE)) {
					// TODO 取消订阅后用户不会再收到公众账号发送的消息，因此不需要回复
				}
				// 扫描带参数二维码
				else if (eventType.equals(MessageUtil.EVENT_TYPE_SCAN)) {
					// TODO 处理扫描带参数二维码事件
				}
				// 上报地理位置
				else if (eventType.equals(MessageUtil.EVENT_TYPE_LOCATION)) {
					// TODO 处理上报地理位置事件
				}
				// 自定义菜单
				else if (eventType.equals(MessageUtil.EVENT_TYPE_CLICK)) {
					// TODO 处理菜单点击事件
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
