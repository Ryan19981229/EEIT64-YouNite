import React from 'react';
import '../styles/notificationCollapse.scss';
import UserImage from '../assets/images/sia.png';

// 以下應改寫 map => 去抓資料帶變數讓他跑迴圈

function NotificationCollapse() {
  return (
    <section className='notification-collapse col fade-in-out'>
      <div className='collapse-bg'>
        <h5>通知</h5>
        <hr />
        <h6>這週</h6>
        <div className='d-flex collapse-content mt-3'>
          <img src={UserImage} className='collapse-user-image' alt='' />
          <p className='m-3'>
            <span className='collapse-username'>猛男&nbsp;</span>
            邀請你開啟語音功能!
          </p>
          <i className='fa-solid fa-check m-2'></i>
          <i className='fa-solid fa-xmark m-2'></i>
        </div>
        <div className='d-flex collapse-content mt-2'>
          <img src={UserImage} className='collapse-user-image' alt='' />
          <p className='m-3'>
            <span className='collapse-username'>猛男&nbsp;</span>
            邀請你開啟語音功能!
          </p>
          <i className='fa-solid fa-check m-2'></i>
          <i className='fa-solid fa-xmark m-2'></i>
        </div>
        <div className='d-flex collapse-content mt-2'>
          <img src={UserImage} className='collapse-user-image' alt='' />
          <p className='m-3'>
            <span className='collapse-username'>猛男&nbsp;</span>
            邀請你開啟語音功能!
          </p>
          <i className='fa-solid fa-check m-2'></i>
          <i className='fa-solid fa-xmark m-2'></i>
        </div>
        <hr />
        <h6 className='mt-3'>上週</h6>
        <div className='d-flex collapse-content mt-2'>
          <img src={UserImage} className='collapse-user-image' alt='' />
          <p className='m-3'>
            <span className='collapse-username'>猛男&nbsp;</span>
            邀請你開啟語音功能!
          </p>
          <i className='fa-solid fa-check m-2'></i>
          <i className='fa-solid fa-xmark m-2'></i>
        </div>
        <div className='d-flex collapse-content mt-2'>
          <img src={UserImage} className='collapse-user-image' alt='' />
          <p className='m-3'>
            <span className='collapse-username'>猛男&nbsp;</span>
            邀請你開啟語音功能!
          </p>
          <i className='fa-solid fa-check m-2'></i>
          <i className='fa-solid fa-xmark m-2'></i>
        </div>
        <div className='d-flex collapse-content mt-2'>
          <img src={UserImage} className='collapse-user-image' alt='' />
          <p className='m-3'>
            <span className='collapse-username'>猛男&nbsp;</span>
            邀請你開啟語音功能!
          </p>
          <i className='fa-solid fa-check m-2'></i>
          <i className='fa-solid fa-xmark m-2'></i>
        </div>
      </div>
    </section>
  );
}

export default NotificationCollapse;
