import React from 'react';
import '../styles/landing.scss';
import { Link } from 'react-router-dom';
import Logo from '../assets/logo/whit-logo-type.png';

function landingPage() {
  return (
    <main className='test'>
      <img src={Logo} alt='logo' className='position-absolute top-10 start-10 mt-3 ms-3' />
      <div className='buttonArea d-flex flex-row position-absolute top-50 start-50 translate-middle'>
        <Link to='/register'>
          <button
            type='default'
            className='btn btn-outline-primary btn-lg m-2 border-2'
            style={{ color: '#fff' }}
          >
            註冊
          </button>
        </Link>
        <Link to='/login'>
          <button type='button' className='btn btn-primary btn-lg m-2' style={{ color: '#fff' }}>
            登入
          </button>
        </Link>
      </div>
    </main>
  );
}

export default landingPage;
