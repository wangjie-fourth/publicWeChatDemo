package com.wangjie.github.publicwechatdemo.service.impl;

import com.wangjie.github.publicwechatdemo.messagedomain.resp.TextMessage;
import com.wangjie.github.publicwechatdemo.utils.MessageUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;

/**
 * @ClassName CoreMessageHandlerService
 * @Description
 * @Author 25127
 * @Date 2019/11/2 14:15
 * @Email jie.wang13@hand-china.com
 **/
public class CoreMessageHandlerService {

    /**
     * 处理微信发来的请求
     *
     * @param request http请求
     * @return xml
     */
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

            // 回复文本消息
            TextMessage textMessage = new TextMessage();
            textMessage.setToUserName(fromUserName);
            textMessage.setFromUserName(toUserName);
            textMessage.setCreateTime(new Date().getTime());
            textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);

            // 文本消息
            if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)) {
                respContent = "您发送的是文本消息！";
            } else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_IMAGE)) { // 图片消息
                respContent = "您发送的是图片消息！";
            } else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_VOICE)) { // 语音消息
                respContent = "您发送的是语音消息！";
            } else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_VIDEO)) { // 视频消息
                respContent = "您发送的是视频消息！";
            } else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_SHORTVIDEO)) {// 视频消息
                respContent = "您发送的是小视频消息！";
            } else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LOCATION)) {// 地理位置消息
                respContent = "您发送的是地理位置消息！";
            } else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LINK)) {// 链接消息
                respContent = "您发送的是链接消息！";
            } else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT)) {// 事件推送
                // 事件类型
                String eventType = requestMap.get("Event");
                // 关注
                if (eventType.equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)) {
                    respContent = "谢谢您的关注！";
                } else if (eventType.equals(MessageUtil.EVENT_TYPE_UNSUBSCRIBE)) {// 取消关注
                    // TODO 取消订阅后用户不会再收到公众账号发送的消息，因此不需要回复
                } else if (eventType.equals(MessageUtil.EVENT_TYPE_SCAN)) {// 扫描带参数二维码
                    // TODO 处理扫描带参数二维码事件
                } else if (eventType.equals(MessageUtil.EVENT_TYPE_LOCATION)) {// 上报地理位置
                    // TODO 处理上报地理位置事件
                } else if (eventType.equals(MessageUtil.EVENT_TYPE_CLICK)) {// 自定义菜单
                    // TODO 处理菜单点击事件
                }
            }
            // 设置文本消息的内容
            textMessage.setContent(respContent);
            // 将文本消息对象转换成xml
            respXml = MessageUtil.messageToXml(textMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return respXml;
    }

}
