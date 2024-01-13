import React, { useState, useEffect, useRef } from 'react';
const { Configuration, OpenAIApi } = require('openai');
import PropTypes from 'prop-types';
import Draggable from 'react-draggable';
import LoadingGif from '../../assets/gif/loading.gif';
import '../../styles/Modal/chatbot.scss';

function ChatBotModal({ closeModal }) {
  // 移動視窗 程式碼 START
  const [position, setPosition] = useState({ x: undefined, y: undefined });
  const [fadeIn, setFadeIn] = useState(false); // 追蹤是否需要淡入
  useEffect(() => {
    const screenWidth = window.innerWidth;
    const screenHeight = window.innerHeight;
    const modalWidth = 500; // 假設 modal 寬度為 500px
    const modalHeight = 300; // 假設 modal 高度為 300px

    const initialX = (screenWidth - modalWidth) / 2;
    const initialY = (screenHeight - modalHeight) / 2;

    setPosition({ x: initialX, y: initialY });
    setFadeIn(true); // 設置淡入為 true，觸發淡入效果
  }, []);

  const handleDrag = (e, ui) => {
    const { x, y } = position;
    const { deltaX, deltaY } = ui;

    // 調整速度限制
    const speedFactor = 0.2; // 調整此數值以改變速度
    const newPosition = {
      x: x + deltaX * speedFactor,
      y: y + deltaY * speedFactor,
    };
    setPosition(newPosition);
  };

  // openAI 程式碼 START
  const draggableRef = useRef(null);
  const configuration = new Configuration({
    apiKey: process.env.REACT_APP_OPENAI_API_KEY,
  });
  const openai = new OpenAIApi(configuration);

  const [message, setMessage] = useState('');
  const [chats, setChats] = useState([]);
  const [isTyping, setIsTyping] = useState(false);

  const chat = async (e, message) => {
    e.preventDefault();

    if (!message) return;
    setIsTyping(true);
    scrollTo(0, 1e10);

    let msgs = chats;
    msgs.push({ role: 'user', content: message });
    setChats(msgs);

    setMessage('');

    await openai
      .createChatCompletion({
        model: 'gpt-3.5-turbo',
        messages: [
          {
            role: 'system',
            content: '你是會用繁體中文回答問題，在交友軟體上幫助大家順利聊天的機器人',
          },
          ...chats,
        ],
      })
      .then((res) => {
        msgs.push(res.data.choices[0].message);
        setChats(msgs);
        setIsTyping(false);
        scrollTo(0, 1e10);
      })
      .catch((error) => {
        console.log(error);
      });
  };

  return (
    <>
      {/* 宣告 Draggable handle 選擇抓取的物件範圍 */}
      <Draggable handle='.modal-bg' nodeRef={draggableRef} onDrag={handleDrag}>
        <section
          ref={draggableRef}
          className={`modal-bg container-fluid p-0 ${fadeIn ? 'fade-in' : ''}`}
          style={{ top: position.y, left: position.x }}
        >
          <div className='row p-3 justify-content-center '>
            <i className='col-6 fa-solid fa-robot mt-1'></i>
            <div className='col-6 d-flex justify-content-end'>
              <button
                type='button'
                className='btn-close'
                onClick={() => closeModal(false)}
              ></button>
            </div>
            {/* ↓↓↓ 下面可以隨意更改，區塊直接用 col 來寫  ↓↓↓*/}
            <main className='chatBotContent d-flex row align-content-center '>
              <section className='d-flex'>
                {chats && chats.length
                  ? chats.map((chat, index) => (
                      <p
                        key={index}
                        className={('col mb-2', chat.role === 'user' ? 'user_msg' : '')}
                      >
                        <span className='col mb-2'>{chat.content}</span>
                      </p>
                    ))
                  : ''}
                <div className={('col', isTyping ? '' : 'hide')}>
                  <img src={LoadingGif} alt='loading...' style={{ height: '20px' }} />
                </div>
              </section>

              <form
                className='row'
                action=''
                onSubmit={(e) => {
                  e.preventDefault();
                  chat(e, message);
                  return false;
                }}
              >
                <hr />
                <input
                  className='mb-3 col'
                  type='text'
                  name='message'
                  value={message}
                  placeholder='輸入文字...'
                  onChange={(e) => setMessage(e.target.value)}
                  onKeyDown={(e) => {
                    if (e.key === 'Enter') {
                      e.preventDefault();
                      chat(e, message);
                    }
                  }}
                />
                <button type='submit' className='col-2 p-1 bg-primary rounded-5'>
                  <i className='fa-solid fa-paper-plane text-white' />
                </button>
              </form>
            </main>
            {/* ↑↑↑ 上面可以隨意更改，區塊直接用 col 來寫 ↑↑↑ */}
          </div>
        </section>
      </Draggable>
    </>
  );
}

ChatBotModal.propTypes = {
  closeModal: PropTypes.func.isRequired,
};

export default ChatBotModal;
