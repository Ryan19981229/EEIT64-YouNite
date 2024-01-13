import React, { useState } from 'react';
import axios from 'axios';
import { useForm } from 'react-hook-form';
import { Input } from './FormElements';
import { Link, useNavigate } from 'react-router-dom';
// import AlertModal from './Modal/AlertModal';

function ForgetPassword() {
  const [submitting, setSubmitting] = useState(false);
  const [submitting1, setSubmitting1] = useState(false);
  // const [showAlertModal, setShowAlertModal] = useState(false);
  // const [alertMessage, setAlertMessage] = useState('');
  // const [alertStateIcon, setAlertStateIcon] = useState('');

  const navigate = useNavigate();
  const {
    register,
    handleSubmit,
    watch,
    formState: { errors },
  } = useForm({
    mode: 'onTouched',
  });

  // const handleAlertRes = (response) => {
  //   setAlertMessage(response.data.message);
  //   setAlertStateIcon(response.data.state);
  //   setShowAlertModal(true);

  //   setTimeout(() => {
  //     setShowAlertModal(false);
  //   }, 2500);
  // };

  const sendVerificationCode = async (data) => {
    try {
      setSubmitting1(true);

      // 發送 /checkEmail API 請求
      const response = await axios.get('/mail/checkEmail', {
        params: { email: data.email },
      });

      if (response.status === 200) {
        // handleAlertRes(response);
        console.log('成功了吧');
      } else {
        // handleAlertRes(response);
        console.log('API 請求失敗');
      }
    } catch (error) {
      console.error(error);
    }
  };

  const onSubmit = async (data) => {
    try {
      setSubmitting(true);

      // 根據你的需求在這裡執行相應的邏輯
      console.log('驗證碼:', data.validation);
      console.log('密碼:', data.password);

      // 執行完成後導航到其他頁面
      setTimeout(() => {
        navigate('/login');
        setSubmitting1(false); // 將 submitting1 設置為 false
      }, 2500);
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
            <h4 className='fw-bold text-center'>密碼修改</h4>
            <div className='bg-secondary px-4 py-3 rounded-3 w-35'>
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
              {!submitting1 ? (
                <button
                  type='button'
                  className='btn btn-primary py-3 px-7 rounded-2 d-flex align-items-center justify-content-center'
                  disabled={submitting1}
                  style={{ color: '#fff', width: '113px', height: '38px' }}
                  onClick={handleSubmit(sendVerificationCode)}
                >
                  {submitting1 ? '正在發送...' : '發送驗證碼'}
                </button>
              ) : (
                <>
                  <div className='mb-1'>
                    <Input
                      id='validation'
                      labelText='驗證碼'
                      type='validation'
                      placeholder='輸入驗證碼'
                      errors={errors}
                      register={register}
                      rules={{
                        required: '驗證碼必填',
                        pattern: {
                          value: 4,
                          message: '驗證碼格式不正確',
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
                    <Link to='/login'>
                      <button
                        type='submit'
                        className='btn btn-primary py-3 px-7 rounded-2 d-flex align-items-center justify-content-center'
                        disabled={submitting}
                        style={{ color: '#fff', width: '113px', height: '38px' }}
                      >
                        {submitting ? '正在確認...' : '確認'}
                      </button>
                    </Link>
                  </div>
                </>
              )}
            </div>
          </form>
        </div>
      </div>
      {/* <AlertModal message={alertMessage} showModal={showAlertModal} state={alertStateIcon} /> */}
    </div>
  );
}

export default ForgetPassword;
