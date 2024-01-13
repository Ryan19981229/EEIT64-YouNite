import React, { useState } from 'react';
import axios from 'axios';
import { useForm } from 'react-hook-form';
import { Input } from './FormElements';
import { LoginSocialFacebook, LoginSocialGoogle } from 'reactjs-social-login';
import { FacebookLoginButton, GoogleLoginButton } from 'react-social-login-buttons';
import { Link, useNavigate } from 'react-router-dom';
import AlertModal from './Modal/AlertModal';

function RegisterForm() {
  const [submitting, setSubmitting] = useState(false);
  const [formSubmitted, setFormSubmitted] = useState(false);
  const [showAlertModal, setShowAlertModal] = useState(false);
  const [alertMessage, setAlertMessage] = useState('');
  const [AlertStateIcon, setAlertStateIcon] = useState('');

  const navigate = useNavigate();
  const {
    register,
    handleSubmit,
    watch,
    formState: { errors },
  } = useForm({
    mode: 'onTouched',
  });

  const submitForm = () => {
    return new Promise((resolve) => {
      setTimeout(() => {
        resolve();
      }, 1000);
    });
  };

  const handleAlertRes = (response) => {
    setAlertMessage(response.data.message);
    setAlertStateIcon(response.data.state);
    setFormSubmitted(false);
    setShowAlertModal(true);

    setTimeout(() => {
      setShowAlertModal(false);
    }, 2500);
  };

  const onSubmit = async (data) => {
    try {
      setSubmitting(true);
      const requestBody = {
        username: data.username,
        email: data.email,
        password: data.password,
      };

      const request = {
        username: data.username,
        password: data.password,
      };

      setFormSubmitted(true);
      await submitForm();

      axios.defaults.withCredentials = true;
      const response = await axios.post('/users/register', requestBody);
      console.log(response.data);

      if (response.data.state === 201) {
        handleAlertRes(response);
        console.log('成功了吧');
        const token_response = await axios.post('/users/login', request);
        const token = token_response.data.data.token;
        document.cookie = `token=${token}; path=/;`;
        axios.defaults.headers.common['Authorization'] = token;
        setTimeout(() => {
          navigate('/personal');
        }, 2500);
      } else {
        handleAlertRes(response);
        console.log('API 請求失敗');
        setTimeout(() => {
          navigate('/register');
        }, 2500);
      }
    } catch (error) {
      console.error(error);
    } finally {
      setSubmitting(false);
    }
  };

  const password = watch('password');

  return (
    <div className='container p-0'>
      <div className='bg-light vh-100 d-flex justify-content-center align-items-center'>
        <div className='row'>
          <form
            onSubmit={handleSubmit(onSubmit)}
            className='shadow rounded-3 bg-light bg-opacity-25 p-3'
            style={{ zIndex: '1' }}
          >
            <h4 className='fw-bold text-center'>註冊</h4>
            <div
              className='bg-secondary px-4 py-3 rounded-3 w-35'
              // style={{ width: '495px', height: '610px' }}
            >
              <div className='mb-1'>
                <Input
                  id='username'
                  type='text'
                  errors={errors}
                  labelText='帳號名稱'
                  placeholder='輸入帳號名稱'
                  register={register}
                  rules={{
                    required: '帳號名稱為必填',
                  }}
                ></Input>
              </div>
              <div className='mb-1'>
                <Input
                  id='email'
                  labelText='信箱'
                  type='emailpassword'
                  placeholder='輸入信箱'
                  errors={errors}
                  register={register}
                  rules={{
                    required: '信箱為必填',
                    pattern: {
                      value: /^\S+@\S+$/i,
                      message: '信箱格式不正確',
                    },
                  }}
                ></Input>
              </div>
              <div className='mb-1'>
                <Input
                  id='password'
                  labelText='密碼'
                  type='password'
                  placeholder='輸入密碼'
                  errors={errors}
                  register={register}
                  rules={{
                    required: '密碼為必填',
                    minLength: {
                      value: 6,
                      message: '密碼長度不少於 6',
                    },
                  }}
                ></Input>
              </div>
              <div className='mb-1'>
                <Input
                  id='confirmPassword'
                  labelText='確認密碼'
                  type='password'
                  placeholder='確認密碼'
                  errors={errors}
                  register={register}
                  rules={{
                    required: '確認密碼為必填',
                    minLength: {
                      value: 6,
                      message: '確認密碼長度不少於 6',
                    },
                    validate: (value) => value === password || '密碼不符合',
                  }}
                ></Input>
              </div>
              <div className='d-flex justify-content-center mt-4'>
                <Link to='/personal'>
                  {formSubmitted && (
                    <div className={`fullscreen-overlay ${submitting ? 'show' : ''}`}>
                      <svg
                        version='1.1'
                        id='L9'
                        xmlns='http://www.w3.org/2000/svg'
                        xmlnsXlink='http://www.w3.org/1999/xlink'
                        x='0px'
                        y='0px'
                        viewBox='0 0 100 100'
                        enableBackground='new 0 0 0 0'
                        xmlSpace='preserve'
                      >
                        <path
                          fill='#fff'
                          d='M73,50c0-12.7-10.3-23-23-23S27,37.3,27,50 M30.9,50c0-10.5,8.5-19.1,19.1-19.1S69.1,39.5,69.1,50'
                        >
                          <animateTransform
                            attributeName='transform'
                            attributeType='XML'
                            type='rotate'
                            dur='0.5s'
                            from='0 50 50'
                            to='360 50 50'
                            repeatCount='indefinite'
                          />
                        </path>
                      </svg>
                    </div>
                  )}
                  <button
                    type='submit'
                    className='btn btn-primary py-3 px-7 rounded-2 d-flex align-items-center justify-content-center'
                    disabled={submitting}
                    style={{ color: '#fff', width: '113px', height: '38px' }}
                    onClick={handleSubmit(onSubmit)}
                  >
                    {submitting ? '正在註冊...' : '註冊'}
                  </button>
                </Link>
              </div>
              <div className='pt-3 pb-1 d-flex flex-column align-items-center justify-content-center'>
                <LoginSocialGoogle
                  client_id={process.env.REACT_APP_GOOLGE_LOGIN}
                  scope='openid profile email'
                  discoveryDocs='claims_supported'
                  access_type='offline'
                  onResolve={({ provider, data }) => {
                    console.log(provider, data);
                  }}
                  onReject={(err) => {
                    console.log(err);
                  }}
                >
                  <GoogleLoginButton
                    className='mt-2 d-flex justify-content-center'
                    style={{ width: '396px', height: '40px' }}
                  >
                    <span className='fs-6 fw-light'>使用 Google 帳戶註冊</span>
                  </GoogleLoginButton>
                </LoginSocialGoogle>
                <LoginSocialFacebook
                  appId='672335331385748'
                  onResolve={(response) => {
                    console.log(response);
                  }}
                  onReject={(error) => {
                    console.log(error);
                  }}
                >
                  <FacebookLoginButton
                    className='mt-2 d-flex justify-content-center'
                    style={{ width: '396px', height: '40px' }}
                  >
                    <span className='fs-6 fw-light'>使用facebook帳戶註冊</span>
                  </FacebookLoginButton>
                </LoginSocialFacebook>
              </div>
              <div className='text-dark text-center fw-normal'>
                <span>已經有帳號了嗎? </span>
                <Link to='/login'>
                  <span>登入</span>
                </Link>
              </div>
            </div>
          </form>
        </div>
      </div>
      <AlertModal message={alertMessage} showModal={showAlertModal} state={AlertStateIcon} />
    </div>
  );
}

export default RegisterForm;
