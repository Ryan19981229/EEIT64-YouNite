import React, { useEffect } from 'react';
import Gradient from '../assets/images/bgc.png';
import Form from '../component/LoginForm';

function Login() {
  useEffect(() => {
    document.body.classList.add('loginBody');
    return () => {
      document.body.classList.remove('loginBody');
    };
  }, []);

  return (
    <main className=''>
      <div className='row mw-100'>
        <div className='col-6 p-0'>
          <img
            src='https://images.unsplash.com/photo-1580250864656-cd501faa9c76?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1887&q=80'
            alt='registerImage'
            className='w-100 vh-100'
            style={{ objectFit: 'cover' }}
          />
        </div>
        <div className='col-6 overflow-hidden p-0 position-relative'>
          <Form />
          <img
            src={Gradient}
            alt='bgGradient'
            className=' d-flex position-absolute top-100 start-0 translate-middle'
          />
          <img
            src={Gradient}
            alt='bgGradient'
            className=' d-flex position-absolute top-0 start-100 translate-middle'
          />
        </div>
      </div>
    </main>
  );
}

export default Login;
