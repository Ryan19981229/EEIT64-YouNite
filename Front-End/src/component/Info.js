import React, { useState, useRef } from 'react';
import Select from 'react-select';
import { useForm, Controller } from 'react-hook-form';
import { Input, RadioButtonGroup } from './FormElements';
import { Button, Divider } from 'antd';
import Avatar from './InfoElements/Avatar';
import PhotoWall from './InfoElements/PhotoWall';
import LocationModal from './InfoElements/Location';
import InfoModal from './Modal/InfoModal';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';
import AlertModal from './Modal/AlertModal';

function Info() {
  const [submitting, setSubmitting] = useState(false);
  const [formSubmitted, setFormSubmitted] = useState(false);
  const [showAlertModal, setShowAlertModal] = useState(false); //Alert 視窗
  const [alertMessage, setAlertMessage] = useState(''); //Alert 訊息
  const [AlertStateIcon, setAlertStateIcon] = useState(''); //Alert icon
  const navigate = useNavigate();
  const {
    register,
    handleSubmit,
    control,
    setValue,
    formState: { errors },
  } = useForm({
    mode: 'onTouched',
  });

  const handleAlertRes = (response) => {
    //Alert用
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
      let str = [];
      for (let i = 0; i < data.hobbies.length; i++) {
        str.push(data.hobbies[i].label);
      }
      
      data.photoWall = photoWallValue.map((file) => file.originFileObj);
      setSubmitting(true);
      setFormSubmitted(true);
      await submitForm(data);
      const formData = new FormData();
      console.log(data.photoWall);
      console.log(data.profileAvatar[0].originFileObj);
      console.log(data);
      formData.append('fullName', data.name);
      formData.append('gender', data.radioGender);
      formData.append('sexualOrientation', data.radioSO);
      formData.append('location', JSON.stringify(data.address));
      formData.append('selfIntro', data.textareaFieldName);
      formData.append('preferredGender', data.radioShow);
      formData.append('datingGoal', data.goal);
      formData.append('avatar', data.profileAvatar[0].originFileObj);
      formData.append('birthday', data.birthday);
      formData.append('professions', data.profession.label);
      formData.append('phone', data.tel);
      data.photoWall.forEach((file) => {
        formData.append('photos', file, file.name);
      });
      formData.append('hobbies', str);
      axios.defaults.withCredentials = true;
      const response = await axios.post('/users/profile', formData, {
        headers: {
          'Content-Type': 'multipart/form-data; boundary=<calculated when request is sent>',
        },
      });
      console.log(response.data);
      if (response.data.state == 201) {
        handleAlertRes(response);
        setTimeout(() => {
          navigate('/home');
        }, 2500);
      } else {
        handleAlertRes(response);
        setTimeout(() => {
          navigate('/personal');
        }, 2500);
      }
    } catch (error) {
      console.error(error);
    } finally {
      setSubmitting(false);
    }
  };

  const submitForm = () => {
    return new Promise((resolve) => {
      setTimeout(() => {
        resolve();
      }, 1500);
    });
  };

  const radioGender = [
    { label: '男生', value: 'Male' },
    { label: '女生', value: 'Female' },
    { label: '其他', value: 'Other' },
  ];

  const radioSO = [
    { label: '生理男', value: 'Man' },
    { label: '生理女', value: 'Woman' },
    { label: '雙性戀', value: 'Bisexual' },
  ];

  const radioShow = [
    { label: '男生', value: 'Male' },
    { label: '女生', value: 'Female' },
    { label: '所有人', value: 'Other' },
  ];

  const hobbies = [
    { value: 'music', label: '音樂' },
    { value: 'reading', label: '閱讀' },
    { value: 'sports', label: '運動' },
    { value: 'photography', label: '攝影' },
    { value: 'travel', label: '旅遊' },
    { value: 'cooking', label: '烹飪' },
    { value: 'painting', label: '繪畫' },
    { value: 'gardening', label: '園藝' },
    { value: 'hiking', label: '遠足' },
    { value: 'biking', label: '騎單車' },
    { value: 'dancing', label: '舞蹈' },
    { value: 'yoga', label: '瑜伽' },
    { value: 'gaming', label: '遊戲' },
    { value: 'movies', label: '電影' },
    { value: 'stamps', label: '集郵' },
    { value: 'coins', label: '集幣' },
    { value: 'instruments', label: '樂器' },
    { value: 'writing', label: '寫作' },
    { value: 'birdwatching', label: '觀鳥' },
    { value: 'volunteering', label: '志願服務' },
    { value: 'fishing', label: '釣魚' },
    { value: 'knitting', label: '編織' },
    { value: 'shopping', label: '購物' },
    { value: 'singing', label: '唱歌' },
    { value: 'boardgames', label: '桌遊' },
    { value: 'sports-watching', label: '看體育比賽' },
    { value: 'crafting', label: '手工藝' },
    { value: 'puzzles', label: '解謎' },
    { value: 'languages', label: '學習語言' },
  ];

  const profession = [
    { value: 'doctor', label: '醫生' },
    { value: 'teacher', label: '教師/教育工作者' },
    { value: 'lawyer', label: '律師/法務人員' },
    { value: 'engineer', label: '工程師' },
    { value: 'finance', label: '金融/銀行從業人員' },
    { value: 'sales', label: '銷售/市場行銷人員' },
    { value: 'media', label: '媒體/新聞從業人員' },
    { value: 'designer', label: '設計師' },
    { value: 'management', label: '企業管理/行政人員' },
    { value: 'it', label: '電腦/資訊科技專業人員' },
    { value: 'hr', label: '人力資源專員' },
    { value: 'architect', label: '建築師/室內設計師' },
    { value: 'hospitality', label: '餐飲/酒店從業人員' },
    { value: 'artist', label: '藝術家/藝術從業人員' },
    { value: 'fitness', label: '健身教練/體育運動員' },
  ];

  const [isInfoModalVisible, setInfoModalVisible] = useState(false);
  const [isLocationModalVisible, setLocationModalVisible] = useState(false);
  const [isLocationSelected, setLocationSelected] = useState(false);
  const [selectedButtonLabel, setSelectedButtonLabel] = useState(null); // 新增选中的按钮标签状态
  const [selectedLocation, setSelectedLocation] = useState(null);

  const handleInfoModalButtonClick = () => {
    setInfoModalVisible(true);
  };

  const handleLocationModalButtonClick = () => {
    setLocationModalVisible(true);
  };

  const handleDialogCancel = () => {
    setInfoModalVisible(false);
    setLocationModalVisible(false);
  };

  const handleDialogOk = (selectedLocation) => {
    // 传入选中按钮的标签
    setLocationModalVisible(false);
    setLocationSelected(true);
    setSelectedLocation(selectedLocation); // 更新选中的按钮标签
    setValue('address', selectedLocation); // 使用setValue更新Controller的值
  };

  const handleDialogOk1 = (selectedButtonLabel) => {
    // 传入选中按钮的标签
    setInfoModalVisible(false);
    setSelectedButtonLabel(selectedButtonLabel); // 更新选中按钮的标签
    setValue('goal', selectedButtonLabel); // 使用setValue更新goal字段的值
  };

  // const handleFileChange = (file) => {
  //   console.log('File changed:', file);
  // };

  const fileInputRef = useRef(null);

  const handleFileUpload = (event) => {
    const file = event.target.files[0];
    const fileName = file.name;
    const fileExtension = fileName.substring(fileName.lastIndexOf('.') + 1).toLowerCase();

    // 检查文件扩展名是否为音频格式
    if (fileExtension === 'mp3' || fileExtension === 'wav') {
      // 在这里执行您的音频文件上传逻辑
      console.log('Uploaded audio file:', file);
    } else {
      console.log('Invalid file format. Please select an MP3 or WAV file.');
    }
  };

  const handleButtonClick = () => {
    fileInputRef.current.click();
  };

  const [photoWallValue, setPhotoWallValue] = useState([]);

  const handleChange = (value) => {
    setPhotoWallValue(value);
  };

  return (
    <div className='container'>
      <form className='row justify-content-center flex-md-row flex-column-reverse'>
        <div className='col-md-5'>
          <div className='pb-4 w-50'>
            <i className='fa-solid fa-user text-black' />
            <span className='text-danger p-1'>*</span>
            <Input
              id='name'
              labelText='姓名'
              type='text'
              placeholder='輸入姓名'
              errors={errors}
              register={register}
              rules={{
                required: '姓名為必填',
              }}
            ></Input>
          </div>
          <div className='pb-4 w-50'>
            <i className='fa-solid fa-phone text-black' />
            <span className='text-danger p-1'>*</span>
            <Input
              id='tel'
              labelText='電話'
              type='tel'
              placeholder='輸入手機號碼'
              errors={errors}
              register={register}
              rules={{
                required: '電話為必填',
                pattern: {
                  value: /^[0-9]{10}$/,
                  message: '電話必須是 10 位數字',
                },
              }}
            ></Input>
          </div>
          <div className='pb-4 w-50'>
            <i className='fa-solid fa-cake-candles text-black'></i>
            <span className='text-danger p-1'>*</span>
            {/* <label htmlFor="">生日</label>
          <br /> */}
            <Input
              id='birthday'
              labelText='生日'
              type='text'
              placeholder='輸入生日 (yyyy-mm-dd)'
              errors={errors}
              register={register}
              rules={{
                required: '生日為必填',
                pattern: {
                  value: /^\d{4}-\d{2}-\d{2}$/,
                  message: '請輸入有效的生日格式 (yyyy-mm-dd)',
                },
              }}
            />
          </div>
          <div className='pb-4'>
            <i className='fa-solid fa-briefcase text-black'></i>
            <span className='text-danger p-1'>*</span>
            <label className='mb-2'>職業</label>
            <br />
            <Controller
              name='profession'
              control={control}
              rules={{ required: '請選擇職業' }}
              defaultValue={[]}
              render={({ field }) => (
                <Select
                  {...field}
                  options={profession}
                  className='basic-multi-select w-50 rounded shadow-sm'
                  classNamePrefix='select'
                  styles={{
                    placeholder: (provided) => ({
                      ...provided,
                      color: 'rgba(0, 0, 0, 0.2)',
                    }),
                  }}
                  placeholder='選擇職業'
                />
              )}
            />
            {errors.profession && (
              <div className='error-message text-danger mt-2' style={{ fontSize: '0.9rem' }}>
                {errors.profession.message}
              </div>
            )}
          </div>
          <div className='pb-4'>
            <i className='fa-solid fa-venus-mars text-black'></i>
            <span className='text-danger p-1'>*</span>
            <label>性別</label>
            <br />
            <RadioButtonGroup
              name='radioGender'
              options={radioGender}
              register={register}
              errors={errors}
              rules={{
                required: '請選擇性別',
              }}
            />
          </div>
          <div className='pb-4'>
            <i className='fa-solid fa-transgender text-black'></i>
            <span className='text-danger p-1'>*</span>
            <label>性向</label>
            <br />
            <RadioButtonGroup
              name='radioSO'
              options={radioSO}
              register={register}
              errors={errors}
              rules={{
                required: '請選擇性向',
              }}
            />
          </div>
          <div className='pb-4'>
            <i className='fa-solid fa-users text-black'></i>
            <span className='text-danger p-1'>*</span>
            <label>顯示給我看</label>
            <br />
            <RadioButtonGroup
              name='radioShow'
              options={radioShow}
              register={register}
              errors={errors}
              rules={{
                required: '顯示給我看為必填',
              }}
            />
          </div>
          <div className='pb-4'>
            <i className='fa-solid fa-heart-circle-plus text-black'></i>
            <span className='text-danger p-1'>*</span>
            <label className='mb-2'>興趣</label>
            <br />
            <Controller
              name='hobbies'
              control={control}
              rules={{ required: '請選擇興趣' }}
              defaultValue={[]}
              render={({ field }) => (
                <Select
                  {...field}
                  isMulti
                  options={hobbies}
                  className='basic-multi-select w-75 rounded shadow-sm'
                  classNamePrefix='select'
                  styles={{
                    placeholder: (provided) => ({
                      ...provided,
                      color: 'rgba(0, 0, 0, 0.2)',
                    }),
                  }}
                  placeholder='新增興趣'
                />
              )}
            />
            {errors.hobbies && (
              <div className='error-message text-danger mt-2' style={{ fontSize: '0.9rem' }}>
                {errors.hobbies.message}
              </div>
            )}
          </div>
          <div className='d-flex pb-4'>
            <div className='mb-2 pe-5'>
              <i className='fa-solid fa-magnifying-glass text-black'></i>
              <span className='text-danger p-1'>*</span>
              <label className='mb-2'>我想找尋</label>
              <br />
              <Controller
                control={control}
                name='goal'
                rules={{ required: true }}
                render={({ field }) => (
                  <Button
                    type='dashed'
                    {...field}
                    className={` ${errors.goal && 'is-invalid'}`}
                    onClick={handleInfoModalButtonClick}
                  >
                    +新增交友目標
                  </Button>
                )}
              />
              {errors.goal && !selectedButtonLabel && (
                <div className='invalid-feedback'>請選擇交友目標</div>
              )}
              {isInfoModalVisible && (
                <InfoModal
                  visible={isInfoModalVisible}
                  closeModal={handleDialogCancel}
                  onOk={handleDialogOk1}
                  setSelectedButtonLabel={setSelectedButtonLabel} // 传递 setSelectedButtonLabel 函数
                  selectedButtonLabel={selectedButtonLabel}
                />
              )}
              {selectedButtonLabel && <div className='completed-tag'>{selectedButtonLabel}</div>}
            </div>
            <div className='mb-2 ps-5'>
              <i className='fa-solid fa-location-dot text-black'></i>
              <span className='text-danger p-1'>*</span>
              <label className='mb-2'>地點</label>
              <br />
              <Controller
                control={control}
                name='address'
                rules={{ required: true }}
                render={({ field }) => (
                  <Button
                    type='dashed'
                    {...field}
                    className={` ${errors.address && 'is-invalid'}`}
                    onClick={handleLocationModalButtonClick}
                  >
                    +地點
                  </Button>
                )}
              />
              {isLocationSelected && selectedLocation && (
                <div className='completed-tag'>
                  <i className='fa-solid fa-check text-danger' />
                  已選擇地點
                </div>
              )}
              {errors.address && !isLocationSelected && (
                <div className='invalid-feedback'>請選擇地點</div>
              )}
              <LocationModal
                visible={isLocationModalVisible}
                onCancel={handleDialogCancel}
                onOk={handleDialogOk}
              />
            </div>
          </div>
          <Divider plain style={{ color: '#BBBBBB' }}>
            選填
          </Divider>

          <div className='pb-4'>
            <i className='fa-solid fa-volume-low text-black pe-2'></i>
            <label className='mb-2'>語音自我介紹</label>
            <br />
            <input
              type='file'
              style={{ display: 'none' }}
              ref={fileInputRef}
              onChange={handleFileUpload}
              accept='.mp3,.wav' // 限制可选的文件类型为 MP3 和 WAV
            />
            <Button type='dashed d-flex col align-items-center' onClick={handleButtonClick}>
              <i className='fa-solid fa-volume-high text-black pe-1'></i>
              <div
                className='circle rounded-circle me-1'
                style={{ backgroundColor: '#D3D3D3', width: '4px', height: '4px' }}
              ></div>
              <div
                className='circle rounded-circle me-1'
                style={{ backgroundColor: '#D3D3D3', width: '4px', height: '4px' }}
              ></div>
              <div
                className='circle rounded-circle me-1'
                style={{ backgroundColor: '#D3D3D3', width: '4px', height: '4px' }}
              ></div>
            </Button>
          </div>
          <div className='pb-4'>
            <i className='fa-solid fa-pen-to-square text-black pe-2' />
            <label htmlFor='exampleFormControlTextarea1' className='form-label mb-2'>
              文字自我介紹
            </label>
            <textarea
              className='form-control'
              id='exampleFormControlTextarea1'
              rows='3'
              maxLength={40}
              {...register('textareaFieldName')}
            ></textarea>
          </div>
        </div>
        <div className='col-md-4'>
          <div className='p-4 mb-4'>
            <span className='text-danger p-1'>*</span>
            <label className='mb-4'>大頭貼照片</label>
            <Controller
              control={control}
              name='profileAvatar'
              rules={{ required: true }}
              render={({ field }) => (
                <>
                  <Avatar
                    {...field}
                    className={errors.profileAvatar ? 'is-invalid' : ''}
                    // value={field.value}
                    onFileChange={(file) => {
                      field.onChange(file);
                    }}
                  />
                  <input type='hidden' {...field} value={field.value} />
                </>
              )}
            />
            {errors.profileAvatar && <div className='invalid-feedback '>請上傳大頭照</div>}
          </div>
          <div className='p-4 mb-4'>
            <label className='mb-4'>個人檔案照片</label>
            <PhotoWall onChange={handleChange} />
          </div>
        </div>
      </form>
      <div className='d-flex flex-column-reverse flex-md-row py-4 justify-content-center align-items-md-center align-items-center w-100'>
        {/* <Link to='/home'> */}
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
            className='btn btn-primary rounded text-white'
            disabled={submitting || formSubmitted}
            onClick={handleSubmit(onSubmit)}
          >
            {submitting ? '正在送出表單...' : '送出表單'}
          </button>
        {/* </Link> */}
      </div>
      <AlertModal message={alertMessage} showModal={showAlertModal} state={AlertStateIcon} />
    </div>
  );
}

export default Info;