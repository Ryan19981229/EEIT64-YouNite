import React, { useState, useEffect, useRef } from 'react';
import PropTypes from 'prop-types';
import axios from 'axios';
import '../../styles/messageItem.scss';
import Call from '../Modal/Call';

const Chat = ({ friendList, chatRoomInfo, userInfo, fullName, profileAvatar }) => {
  const [isCallModalVisible, setCallModalVisible] = useState(false);
  const [isCallModalVisible1, setCallModalVisible1] = useState(false);
  const [isClicked, setIsClicked] = useState(false);
  const [messageInput, setMessageInput] = useState('');
  const wsRef = useRef(null);

  const handleCallButtonClick = () => {
    setCallModalVisible(true);
  };

  const handleCallButtonClick1 = () => {
    setCallModalVisible1(true);
  };

  useEffect(() => {
    const fetchData = async () => {
      const resUserInfo = await axios.get('/users/profile');
      const id = resUserInfo.data.data.userId;
      let ws = new WebSocket(`ws://localhost:8080/websocket/${id}/2`);
      ws.onopen = () => {
        console.log('WebSocket connection successful');
      };
      ws.onmessage = (event) => {
        console.log('Received message:', event.data);
      };
      ws.onclose = (event) => {
        console.log('WebSocket closed: ' + event.code + ', reason: ' + event.reason);
      };
      ws.onerror = () => {
        console.log('Error');
      };
      wsRef.current = ws; // 保存 WebSocket 对象到引用变量
    };

    fetchData();

    return () => {
      if (wsRef.current && wsRef.current.close) {
        wsRef.current.close();
      }
    };
  }, []);

  const handleClick = () => {
    setIsClicked(!isClicked);
  };

  const handleChange = (e) => {
    setMessageInput(e.target.value);
  };

  const handleSend = () => {
    console.log('Sending message:', messageInput);
    sendMessage();
    handleSendMessage(messageInput); // 呼叫 handleSendMessage 函式，將訊息傳遞出去
  };

  const handleSendMessage = (message) => {
    // 在這裡處理傳送訊息的邏輯
    console.log('Sending message:', message);
  };

  const sendMessage = () => {
    const ws = wsRef.current;
    console.log(ws);
    if (ws && ws.readyState === WebSocket.OPEN) {
      if (messageInput !== '') {
        const uidInput = friendList[0].userid;
        const message = {
          message: messageInput,
          type: 'text',
          uid: uidInput,
        };
        ws.send(JSON.stringify(message));
        setMessageInput(''); // 清空输入框内容
        console.log('消息已发送:', messageInput); // 调试语句
      }
    }
  };

  // 接之前的聊天紀錄
  const renderMessageItem = (message) => {
    const displayImage = message.isOwner ? userInfo.data.profileAvatar : friendList.profileAvatar;
    const lastMessage = chatRoomInfo[chatRoomInfo.length - 1];

    return (
      <div key={message.messageId}>
        <div className={`message ${message.isOwner ? 'owner' : ''}`}>
          <div className='messageInfo'>
            <img
              className='rounded-circle bg-secondary'
              style={{ height: '50px', width: '50px', objectFit: 'cover' }}
              src={displayImage}
              alt=''
            />
          </div>
          <div className='messageContent'>
            <p>{message.messageContent}</p>
            <img className='' src={message.imageURL} alt='' />
          </div>
        </div>
        <div className={`message ${lastMessage.isOwner ? 'owner' : ''}`}>
          <div className='messageInfo'>
            <img
              className='rounded-circle bg-secondary'
              style={{ height: '50px', width: '50px', objectFit: 'cover' }}
              src={displayImage}
              alt=''
            />
          </div>
          <div className='messageContent'>
            <p>{lastMessage.messageContent}</p>
          </div>
        </div>
      </div>
    );
  };

  const handleFileInputChange = (event) => {
    const uid = 333; // 替换为您的uid
    let webSocket;
    const file = event.target.files[0];
    console.log('Selected file:', file);
    const formData = new FormData();
    formData.append('file', file);
    fetch('http://localhost:8080/storage/upload', {
      method: 'POST',
      body: formData,
      headers: new Headers({
        'Content-Type': '',
      }),
    })
      .then((res) => res.json())
      .then((res) => {
        const msg = {
          type: 'image',
          data: res.data,
          ud: uid,
        };
        webSocket.send(JSON.stringify(msg));
        const msgtodb = {
          senderId: 333,
          receiverId: 6,
          messageType: 'image',
          roomId: 36,
          messageContent: res.data,
        };
        fetch('http://localhost:8080/message/add', {
          method: 'POST',
          body: JSON.stringify(msgtodb),
          headers: new Headers({
            'Content-Type': 'application/json',
          }),
        });
      })
      .catch((error) => {
        console.error('Error uploading file:', error);
      });
  };

  return (
    <div className='' style={{ flex: '2' }}>
      {/* 其他組件內容 */}
    </div>
  );
};

Chat.propTypes = {
  friendList: PropTypes.array.isRequired,
  chatRoomInfo: PropTypes.array.isRequired,
  userInfo: PropTypes.object.isRequired,
  fullName: PropTypes.string.isRequired,
  profileAvatar: PropTypes.string.isRequired,
};

export default Chat;
