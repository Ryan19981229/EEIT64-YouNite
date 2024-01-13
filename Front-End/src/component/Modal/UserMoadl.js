import React, { useState, useEffect } from 'react';
import PropTypes from 'prop-types';
import Draggable from 'react-draggable';
import '../../styles/Modal/userModal.scss';
import axios from 'axios';
import userImge from '../../assets/images/sia.png'

function UserModal({ closeModal, userID, data, likedUsers }) {
  // 移動視窗 程式碼 START
  const [position, setPosition] = useState({ x: undefined, y: undefined });
  const [fadeIn, setFadeIn] = useState(false); // 追蹤是否需要淡入
  useEffect(() => {
    const screenWidth = window.innerWidth;
    const screenHeight = window.innerHeight;
    const modalWidth = 500; // 假設 modal 寬度為 500px
    const modalHeight = 500; // 假設 modal 高度為 300px

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

  const handleModalClose = () => {
    closeModal(); // 觸發父元件傳遞的關閉函數
  };

  const [post, setPost] = useState([]);
  axios.defaults.withCredentials = true;
  useEffect(() => {
    const fetchData = async () => {
      try {
        const response = await axios.get(`/users/profile/${parseInt(userID)}`);
        setPost(response.data);
      } catch (error) {
        console.error(error);
      }
    };
  
    fetchData();
  }, [userID]);

  const [isButtonDisabled, setButtonDisabled] = useState(false);
  const [isButtonClicked, setButtonClicked] = useState(false);
  const [hasSentInvite, setHasSentInvite] = useState(false);

  useEffect(() => {
    const likedUserIDs = likedUsers.data || []; // 預設為空陣列
    setHasSentInvite(likedUserIDs.includes(parseInt(userID)));
    console.log(likedUserIDs);
  }, [likedUsers, userID]);
  

  const handleButtonClick = async () => {
    try {
      const requestBody = {
        likedUserID: parseInt(userID),
      };      
      console.log(requestBody);
      axios.defaults.withCredentials = true;
      const response = await axios.post(`/users/like`, requestBody);
      setButtonDisabled(true); // 禁用按鈕
      setButtonClicked(true); // 按鈕已被點擊
      if (response.data.state === 200) {
        setHasSentInvite(true); // 設定為已傳送成功
        console.log('成功了吧');
      } else {
        console.log('API 請求失敗');
      }
    } catch (error) {
      console.error(error);
    }
  };  

  return (
    <>
      {/* 宣告 Draggable handle 選擇抓取的物件範圍 */}
      <Draggable handle='.userInfo' onDrag={handleDrag}>
        {post.data? (
          <section
          className={`userInfo container-fluid p-0 ${fadeIn ? 'fade-in' : ''}`}
          style={{'--bg-image': `url(${post.data.profileAvatar || userImge})`, top: position.y, left: position.x }}
        >
          <div className='row p-3 h-100 d-flex flex-column justify-content-end'>
            <div className='col-12 d-flex justify-content-end'>
            <button
              type='button'
              className='btn-close'
              onClick={handleModalClose} // 移除 false 參數
            ></button>
            </div>
            {/* ↓↓↓ 下面可以隨意更改，區塊直接用 col 來寫  ↓↓↓*/}
            <div className='mt-auto'>
              <div className='d-flex mt-1'>
                <h4 className='mx-2'>{post.data.fullName}</h4>
                <p className='me-3 ms-2'>{data.age}</p>
                <div className='mx-2'>
                  <i className="fa-solid fa-location-dot text-radio pe-1 fa-xs" />
                  <label>{post.data.city || '臺灣'}</label>
                </div>
                <div className='mx-2'>
                  <i className="fa-solid fa-user-group text-radio pe-1 fa-xs" />
                  <label>{post.data.datingGoal}</label>
                </div>
              </div>
              <div className='mb-1'>
                {data.interests && data.interests.slice(0,6).map((interest, i) => (
                  <button key={i} className='btn btn-outline-primary btn-sm rounded-pill btn-block me-2'>
                    #{interest}
                  </button>
                ))}
              </div>
              <p className='m-2'>{post.data.selfIntro || '我正在尋找一個真誠的伴侶，與我一起分享生活的喜悅和挑戰。'}</p>
              <div className='d-flex justify-content-around '>
                <button
                  className='button col-5 rounded-pill bg-primary text-white'
                  onClick={handleButtonClick}
                  disabled={isButtonDisabled || isButtonClicked || hasSentInvite} // 綁定 disabled 屬性
                >
                  {hasSentInvite || isButtonClicked ? '已傳送成功' : '寄出好友邀請'}
                </button>
              </div>
            </div>
            {/* ↑↑↑ 上面可以隨意更改，區塊直接用 col 來寫 ↑↑↑ */}
          </div>
        </section>
        ) : (
          <p>loading</p>
        )}
        
      </Draggable>
    </>
  );
}

UserModal.propTypes = {
  closeModal: PropTypes.func.isRequired,
  userID: PropTypes.string.isRequired,
  data: PropTypes.object.isRequired,
  likedUsers: PropTypes.object.isRequired,
};

export default UserModal;
