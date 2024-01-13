//Chat.js
import React, { useState, useEffect } from 'react';
import PropTypes from 'prop-types';
import '../../styles/messageItem.scss';
import Call from '../Modal/Call';
import axios from 'axios';

const Chat = ({ friendList, chatRoomInfo, selectedFriend, userInfo }) => {
  const [isCallModalVisible, setCallModalVisible] = useState(false);
  const [isCallModalVisible1, setCallModalVisible1] = useState(false);
  const [isClicked, setIsClicked] = useState(false);
  const [webSocket, setWebSocket] = useState(null);
  const [chatRoomMsg, setChatRoomMsg] = useState([]); //更新這個聊天室的訊息
  const [message, setMessage] = useState('');
  // const [imageData, setImageData] = useState(null);

  //彈跳視窗相關
  const handleCallButtonClick = () => {
    setCallModalVisible(true);
  };
  const handleCallButtonClick1 = () => {
    setCallModalVisible1(true);
  };
  const handleClick = () => {
    setIsClicked(!isClicked);
  };

  //初次渲染直接跟webstock連線
  useEffect(() => {
    const fetchData = async () => {
      setChatRoomMsg(chatRoomInfo); //更新這個聊天室的訊息
      const id = userInfo.data.userId;
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
      setWebSocket(ws); // 將 webSocket 賦值給狀態
      return Promise.resolve(ws);
    };
    const wsPromise = fetchData();
    return () => {
      wsPromise.then((ws) => ws.close());
    };
  }, []);

  //傳送訊息相關
  const handleSend = () => {
    console.log('Sending message:', message);
    const newMessage = {
      messageId: chatRoomMsg.length + 1,
      messageContent: message,
      receiverId: userInfo.data.userId, // 或根據你的需求設置接收者ID
    };
    setChatRoomMsg([...chatRoomMsg, newMessage]); //聊天室訊息
    sendMessage(); // 送出 Webstcok 連線資料到後端
    setMessage(''); // 清空輸入框
  };

  function sendMessage() {
    try {
      let data = {
        message: message,
        type: 'text',
        uid: userInfo.data.userId,
      };
      webSocket.send(JSON.stringify(data));

      const msgtodb = {
        senderId: userInfo.data.userId,
        receiverId: 6,
        messageType: 'text',
        roomId: 32,
        messageContent: message,
      };
      axios
        .post('http://localhost:8080/message/add', msgtodb, {
          headers: {
            'Content-Type': 'application/json',
          },
        })
        .then((response) => {
          console.log('Message saved to the database:', response.data);
        })
        .catch((error) => {
          console.error('Error saving message to the database:', error);
        });
    } catch (error) {
      console.error('Error sending message:', error);
    }
  }

  //傳送圖片
  const handleFileInputChange = () => {
    // let webSocket;
    // const file = event.target.files[0];
    // console.log('Selected file:', file);
    // const formData = new FormData();
    // formData.append('file', file);
    // fetch('http://localhost:8080/storage/upload', {
    //   method: 'POST',
    //   body: formData,
    //   headers: new Headers({
    //     'Content-Type': '',
    //   }),
    // })
    //   .then((res) => res.json())
    //   .then((res) => {
    //     const msg = {
    //       type: 'image',
    //       data: res.data,
    //       ud: userInfo.data.userId,
    //     };
    //     webSocket.send(JSON.stringify(msg));
    //     const msgtodb = {
    //       senderId: userInfo.data.userId,
    //       receiverId: 6,
    //       messageType: 'image',
    //       roomId: 36,
    //       messageContent: res.data,
    //     };
    //     fetch('http://localhost:8080/message/add', {
    //       method: 'POST',
    //       body: JSON.stringify(msgtodb),
    //       headers: new Headers({
    //         'Content-Type': 'application/json',
    //       }),
    //     });
    //   })
    //   .catch((error) => {
    //     console.error('Error uploading file:', error);
    //   });
  };

  //渲染之前的聊天紀錄
  const renderMessageItem = (message) => {
    const displayImage =
      message.receiverId == userInfo.data.userId
        ? userInfo.data.profileAvatar
        : selectedFriend.profileAvatar;

    return (
      <div key={message.messageId}>
        <div className={`message ${message.receiverId == userInfo.data.userId ? 'owner' : ''}`}>
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
      </div>
    );
  };

  return (
    <div className='' style={{ flex: '2' }}>
      {/* 聊天室導覽列 */}
      <div
        className='d-flex align-items-center justify-content-between p-3 border-bottom'
        style={{ height: '75px' }}
      >
        <div className='d-flex mt-2'>
          <img
            className='rounded-circle bg-secondary'
            style={{ height: '50px', width: '50px', objectFit: 'cover' }}
            src={selectedFriend.profileAvatar}
            alt=''
          />
          <div className='mx-3'>
            <span className='text-dark' style={{ fontSize: '20px' }}>
              {selectedFriend.fullName}
            </span>
            <p className='text-radio' style={{ fontSize: '12px' }}></p>
          </div>
        </div>
        <div className='d-flex' style={{ gap: '20px' }}>
          <div>
            <i
              className='fa-solid fa-phone text-dark fa-lg'
              style={{ cursor: 'pointer' }}
              onClick={handleCallButtonClick}
            ></i>
            {isCallModalVisible && (
              <Call
                friendList={friendList}
                selectedFriend={selectedFriend}
                closeModal={() => setCallModalVisible(false)}
              />
            )}
          </div>
          <div>
            <i
              className='fa-solid fa-video text-dark fa-lg'
              style={{ cursor: 'pointer' }}
              onClick={handleCallButtonClick1}
            ></i>
            {isCallModalVisible1 && (
              <Call
                friendList={friendList}
                selectedFriend={selectedFriend}
                closeModal={() => setCallModalVisible1(false)}
                id='chat'
              />
            )}
          </div>
        </div>
        {/* 上面是聊天室導覽列 */}
      </div>
      {/* 下面是聊天室窗渲染的地方  */}
      <div
        className='p-2 overflow-auto col'
        id='messages'
        style={{ height: 'calc(100vh - 130px)' }}
      >
        {chatRoomMsg.map((message, index) => renderMessageItem(message, index))}

        <div className='p-2 d-flex align-items-center justify-content-between msgBottom border-top col'>
          <div className='ps-4'>
            <label htmlFor='fileInput'>
              <i
                className='fa-solid fa-paperclip text-radio fa-lg pe-4'
                style={{ cursor: 'pointer' }}
              ></i>
              <input
                type='file'
                id='fileInput'
                style={{ display: 'none' }}
                onChange={handleFileInputChange}
                accept='image/*'
              />
            </label>
            {isClicked ? (
              <i
                className='fa-solid fa-microphone fa-fade fa-lg'
                style={{ color: '#ff0000' }}
                onClick={handleClick}
              />
            ) : (
              <i
                className='fa-solid fa-microphone text-radio fa-lg pe-0'
                style={{ cursor: 'pointer' }}
                onClick={handleClick}
                id='recordButton'
              ></i>
            )}
          </div>
          <div className='d-flex mesInput p-1 mt-2' style={{ height: '8vh' }} id='msgDiv'>
            <input
              type='text'
              id='msg'
              className='bg-secondary border-0 p-1'
              placeholder='輸入文字'
              value={message}
              onChange={(e) => setMessage(e.target.value)}
              onKeyDown={(e) => {
                if (e.key === 'Enter') {
                  handleSend();
                }
              }}
            />
            <button className='bg-primary border-0 rounded-pill' onClick={handleSend} id='send'>
              <i className='fa-solid fa-paper-plane text-white' />
            </button>
          </div>
        </div>
      </div>
    </div>
  );
};

Chat.propTypes = {
  chatRoomInfo: PropTypes.array.isRequired,
  friendList: PropTypes.array.isRequired,
  selectedFriend: PropTypes.object,
  userInfo: PropTypes.object,
};

export default Chat;
