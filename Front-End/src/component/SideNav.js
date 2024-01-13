import React, { useState, useEffect } from 'react';
import { NavLink, Link, useNavigate } from 'react-router-dom';
import Logo from '../assets/logo/logo-type.png';
import '../styles/component/nav.scss';
import ChatBotModal from './Modal/ChatBotModal';
import NotificationCollapse from '../pages/NotificationCollapse';
import axios from 'axios';

function SideNav() {
  const [openModal, setOpenModal] = useState(false); // Model 開關
  const [isActive, setIsActive] = useState(false); // Modal 開關連動 nav icon 顏色切換
  const [isCollapseOpen, setIsCollapseOpen] = useState(false); // 通知欄位開關
  const [fadeInModal, setFadeInModal] = useState(false); // 追蹤是否需要淡入

  const handleModalClick = () => {
    setOpenModal(true);
    setIsActive(true);
    setFadeInModal(true); //設置淡入為 true，觸發淡入效果
  };

  const handleCollapseClick = () => {
    setIsCollapseOpen(!isCollapseOpen);
  };

  const handleCloseModal = () => {
    setOpenModal(false);
    setIsActive(false);
  };

  axios.defaults.withCredentials = true;

  // 初始化 post 狀態為一個空物件
  const [post, setPost] = useState('');

  useEffect(() => {
    axios.defaults.withCredentials = true;
    axios.get('/users/profile').then((response) => {
      setPost(response.data);
      console.log(response.data);
    });
  }, []);

  const navigate = useNavigate();

  const handleLogout = () => {
    axios.defaults.withCredentials = true;

    axios
      .post('/users/logout')
      .then((response) => {
        console.log(response);
        // 登出成功後的處理
        navigate('/'); // 導航到登入頁面
      })
      .catch((error) => {
        console.error(error);
        // 處理登出失敗的情況
      });
  };

  return (
    <>
      <nav className='bg-secondary d-flex p-0 justify-content-between flex-column align-items-center'>
        <figure className='text-decoration-none mt-5 d-flex flex-column align-items-center'>
          <img src={Logo} alt='YouNite-Logo' className='mb-5' style={{ height: '20px' }} />
          <img
            src={post && post.data.profileAvatar}
            alt='Picture'
            className='mb-1 nav-user-image'
          />
          <h6 className='text-black nav-text mt-2'>{post && post.data.fullName}</h6>
        </figure>

        <ul className='nav flex-column fs-5 align-items-center'>
          <li className='nav-item'>
            <NavLink to='/home' className='nav-link' aria-current='page'>
              <i className='fa-solid fa-house'></i>
            </NavLink>
          </li>
          <li className='nav-item'>
            <Link to='#' className='nav-link' onClick={handleCollapseClick}>
              <span className='fa-layers fa-fw'>
                <i className='fa-solid fa-bell'></i>
                <span className='bg-primary text-white rounded-5 p-1 badge'>1</span>
              </span>
            </Link>
          </li>
          <li className='nav-item'>
            <NavLink to='/chatroom' className='nav-link'>
              <i className='fa-solid fa-comment-dots'></i>
            </NavLink>
          </li>
          <li className='nav-item'>
            <Link
              to='#'
              className={`nav-link ${isActive ? 'active' : ''}`}
              onClick={handleModalClick}
            >
              <i className='fa-solid fa-robot'></i>
            </Link>
          </li>
          <li className='nav-item'>
            <NavLink to='/store' className='nav-link'>
              <i className='fa-solid fa-crown'></i>
            </NavLink>
          </li>
        </ul>
        <ul className='nav flex-column mb-5 fs-5 nav-bottom align-items-center'>
          <li className='nav-item'>
            <NavLink to='/setting' className='nav-link'>
              <i className='fa-solid fa-gear '></i>
            </NavLink>
          </li>
          {post && (
            <li className='nav-item'>
              <button onClick={handleLogout} className='nav-link'>
                <i className='fa-solid fa-right-from-bracket'></i>
              </button>
            </li>
          )}
        </ul>
      </nav>
      {isCollapseOpen && <NotificationCollapse />}
      {openModal && <ChatBotModal closeModal={handleCloseModal} fadeIn={fadeInModal} />}
    </>
  );
}

export default SideNav;
