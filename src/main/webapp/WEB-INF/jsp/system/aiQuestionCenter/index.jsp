<%--
  Created by IntelliJ IDEA.
  User: Liushiqian
  Date: 2025/7/1
  Time: 09:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <title>AI问答中心 - 智能教学管理系统</title>
    <%@include file="/WEB-INF/jsp/common.jsp"%>
    <style>
        .ai-container {
            display: flex;
            flex-direction: column;
            height: calc(100vh - 80px);
            padding: 15px;
            background-color: #f8f8f8;
        }

        .ai-chat-box {
            flex: 1;
            overflow-y: auto;
            padding: 15px;
            margin-bottom: 15px;
            background-color: #fff;
            border-radius: 5px;
            box-shadow: 0 1px 2px 0 rgba(0,0,0,.1);
        }

        .ai-input-area {
            display: flex;
            padding: 10px;
            background-color: #fff;
            border-radius: 5px;
            box-shadow: 0 1px 2px 0 rgba(0,0,0,.1);
        }

        .ai-input {
            flex: 1;
            padding: 10px;
            border: 1px solid #e6e6e6;
            border-radius: 3px;
            outline: none;
            resize: none;
            min-height: 60px;
            max-height: 120px;
        }

        .ai-send-btn {
            margin-left: 10px;
            padding: 0 20px;
            height: 60px;
            line-height: 60px;
            background-color: #1E9FFF;
            color: #fff;
            border-radius: 3px;
            cursor: pointer;
            border: none;
        }

        .ai-send-btn:hover {
            background-color: #0c7cd5;
        }

        .ai-control-bar {
            display: flex;
            align-items: center;
            justify-content: flex-start; /* 左对齐 */
        }

        .ai-clear-btn-small {
            padding: 0 15px;
            height: 30px;
            line-height: 30px;
            font-size: 12px;
            background-color: #ff4d4f;
        }

        .ai-history-btn-small {
            padding: 0 15px;
            height: 30px;
            line-height: 30px;
            font-size: 12px;
            background-color: #FFB800;
        }

        .message {
            margin-bottom: 15px;
            padding: 10px 15px;
            border-radius: 5px;
            max-width: 80%;
            word-wrap: break-word;
        }

        .user-message {
            background-color: #e6f7ff;
            margin-left: auto;
            border: 1px solid #bae7ff;
        }

        .ai-message {
            background-color: #f6f6f6;
            margin-right: auto;
            border: 1px solid #e8e8e8;
        }

        .message-time {
            font-size: 12px;
            color: #999;
            margin-top: 5px;
            text-align: right;
        }

        .ai-header {
            padding: 10px 15px;
            background-color: #1E9FFF;
            color: #fff;
            border-radius: 5px 5px 0 0;
            margin-bottom: 10px;
        }

        .typing-indicator {
            display: inline-block;
            padding: 10px 15px;
            background-color: #f6f6f6;
            border-radius: 5px;
            border: 1px solid #e8e8e8;
        }

        .typing-dot {
            display: inline-block;
            width: 8px;
            height: 8px;
            border-radius: 50%;
            background-color: #999;
            margin-right: 3px;
            animation: typingAnimation 1.4s infinite ease-in-out;
        }

        .typing-dot:nth-child(1) {
            animation-delay: 0s;
        }

        .typing-dot:nth-child(2) {
            animation-delay: 0.2s;
        }

        .typing-dot:nth-child(3) {
            animation-delay: 0.4s;
        }

        @keyframes typingAnimation {
            0%, 60%, 100% { transform: translateY(0); }
            30% { transform: translateY(-5px); }
        }
    </style>
</head>
<body>
<div class="ai-container">
    <div class="ai-header">
        <h2>AI问答中心</h2>
        <p>智能教学助手随时为您解答问题</p>
    </div>

    <div class="ai-chat-box" id="chatBox">
        <!-- 初始欢迎消息 -->
        <div class="message ai-message">
            <div>您好！我是智能教学助手，请问有什么可以帮助您的？</div>
            <div class="message-time"><%= new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm").format(new java.util.Date()) %></div>
        </div>
    </div>

    <div class="ai-input-area">
        <textarea class="ai-input" id="userInput" placeholder="请输入您的问题..." rows="3"></textarea>
        <button class="ai-send-btn" id="sendBtn">发送</button>
    </div>

    <!-- 清空对话按钮（放在输入区域下方，左对齐） -->
    <div class="ai-control-bar" style="margin-top: 10px;">
        <button class="ai-send-btn ai-history-btn-small" id="loadHistoryBtn">加载历史对话</button>
        <button class="ai-send-btn ai-clear-btn-small" id="clearChatBtn">清空对话</button>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/marked/marked.min.js"></script>
<script>
    layui.use(['jquery', 'layer'], function(){
        var $ = layui.jquery;
        var layer = layui.layer;

        // 基础路径
        var basePath = '${path}';

        // 发送消息函数
        function sendMessage() {
            var userInput = $('#userInput').val().trim();
            if (userInput === '') {
                layer.msg('请输入问题内容', {icon: 5});
                return;
            }

            // 添加用户消息到聊天框
            addMessage(userInput, 'user');

            // 清空输入框
            $('#userInput').val('');

            // 显示AI正在输入指示器
            showTypingIndicator();

            // 调用后端API获取AI回答
            $.ajax({
                url: basePath + '/aiQuestionCenter/ask',
                type: 'POST',
                dataType: 'json',
                data: {
                    question: userInput
                },
                success: function(response) {
                    removeTypingIndicator();
                    if(response.code === 0) {
                        // 成功获取AI回答
                        addMessage(response.data, 'ai');
                    } else {
                        // 显示错误信息
                        addMessage('获取回答失败: ' + response.msg, 'ai-error');
                        layer.msg('获取回答失败', {icon: 5});
                    }
                },
                error: function(xhr, status, error) {
                    removeTypingIndicator();
                    var errorMsg = '请求失败: ' + (xhr.responseJSON && xhr.responseJSON.msg || error);
                    addMessage(errorMsg, 'ai-error');
                    layer.msg('请求失败', {icon: 5});
                }
            });
        }

        // 添加消息到聊天框
        // function addMessage(content, sender) {
        //     var now = new Date();
        //     var timeString = now.getFullYear() + '-' +
        //         (now.getMonth() + 1).toString().padStart(2, '0') + '-' +
        //         now.getDate().toString().padStart(2, '0') + ' ' +
        //         now.getHours().toString().padStart(2, '0') + ':' +
        //         now.getMinutes().toString().padStart(2, '0');
        //
        //     var messageClass = 'message ' + sender + '-message';
        //     var messageHtml = '<div class="' + messageClass + '">' +
        //         '<div>' + content + '</div>' +
        //         '<div class="message-time">' + timeString + '</div>' +
        //         '</div>';
        //
        //     $('#chatBox').append(messageHtml);
        //     scrollToBottom();
        // }

        // 将 AI 返回的 Markdown 文本渲染为格式化内容
        function addMessage(content, sender) {
            var now = new Date();
            var timeString = now.getFullYear() + '-' +
                (now.getMonth() + 1).toString().padStart(2, '0') + '-' +
                now.getDate().toString().padStart(2, '0') + ' ' +
                now.getHours().toString().padStart(2, '0') + ':' +
                now.getMinutes().toString().padStart(2, '0');

            var messageClass = 'message ' + sender + '-message';

            // 使用 marked 将 content 转换为 HTML
            var htmlContent = marked.parse(content);

            var messageHtml = '<div class="' + messageClass + '">' +
                '<div>' + htmlContent + '</div>' +
                '<div class="message-time">' + timeString + '</div>' +
                '</div>';

            $('#chatBox').append(messageHtml);
            scrollToBottom();
        }


        // 显示"正在输入"指示器
        function showTypingIndicator() {
            var typingHtml = '<div class="message ai-message" id="typingIndicator">' +
                '<div class="typing-indicator">' +
                '<span class="typing-dot"></span>' +
                '<span class="typing-dot"></span>' +
                '<span class="typing-dot"></span>' +
                '</div>' +
                '</div>';

            $('#chatBox').append(typingHtml);
            scrollToBottom();
        }

        // 移除"正在输入"指示器
        function removeTypingIndicator() {
            $('#typingIndicator').remove();
        }

        // 滚动到底部
        function scrollToBottom() {
            var chatBox = document.getElementById('chatBox');
            chatBox.scrollTop = chatBox.scrollHeight;
        }

        // 加载历史对话记录 (10 条)
        function loadHistory() {
            $.ajax({
                url: basePath + '/aiQuestionCenter/history',
                type: 'GET',
                dataType: 'json',
                data: {
                    page: 1,
                    limit: 10
                },
                success: function(response) {
                    if(response.code === 0 && response.data && response.data.length > 0) {
                        // 按时间顺序显示历史记录
                        response.data.reverse().forEach(function(item) {
                            addMessage(item.question, 'user');
                            addMessage(item.answer, 'ai');
                        });
                    }
                },
                error: function() {
                    layer.msg('加载历史记录失败', {icon: 5});
                }
            });
        }

        // 清空聊天记录
        function clearChat() {
            $('#chatBox').empty(); // 清空聊天框内容

            // 插入初始欢迎消息
            var welcomeHtml = `
                <div class="message ai-message">
                    <div>您好！我是智能教学助手，请问有什么可以帮助您的？</div>
                    <div class="message-time">` + getCurrentTime() + `</div>
                </div>
            `;

            $('#chatBox').append(welcomeHtml);
            layer.msg('对话记录已清空');
        }

        // 获取当前时间，格式：yyyy-MM-dd HH:mm
        function getCurrentTime() {
            var now = new Date();
            var year = now.getFullYear();
            var month = (now.getMonth() + 1).toString().padStart(2, '0');
            var date = now.getDate().toString().padStart(2, '0');
            var hours = now.getHours().toString().padStart(2, '0');
            var minutes = now.getMinutes().toString().padStart(2, '0');
            return year + '-' + month + '-' + date + ' ' + hours + ':' + minutes;
        }

        // 页面加载完成后初始化
        $(function() {
            // 绑定发送按钮点击事件
            $('#sendBtn').click(sendMessage);

            // 绑定回车键发送
            $('#userInput').keypress(function(e) {
                if (e.which == 13 && !e.shiftKey) {
                    e.preventDefault();
                    sendMessage();
                }
            });

            // 绑定点击事件 - 清除对话
            $('#clearChatBtn').click(clearChat);

            // 加载历史对话记录 - 加载历史会话 (10 条)
            // loadHistory();
            $('#loadHistoryBtn').click(loadHistory);
        });
    });
</script>
</body>
</html>


















