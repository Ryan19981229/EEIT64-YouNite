import React, { useState, useRef, useEffect } from 'react';
import '../styles/component/bdGradient.scss';
import '../styles/all.scss';
import Select from 'react-select';
import { useForm, Controller } from 'react-hook-form';
import { RadioButtonGroups, InfoInput } from '../component/InfoElements/FromElement';
import { Button, Divider } from 'antd';
import { useNavigate } from 'react-router-dom';
import Avatar from '../component/InfoElements/Avatar';
import PhotoWall from '../component/InfoElements/PhotoWall';
import LocationModal from '../component/InfoElements/Location';
import InfoModal from '../component/Modal/InfoModal';
import axios from 'axios';
import AlertModal from '../component/Modal/AlertModal';

function SettingPersonal() {
  const [submitting, setSubmitting] = useState(false);
  const [showAlertModal, setShowAlertModal] = useState(false);
  const [alertMessage, setAlertMessage] = useState('');
  const [AlertStateIcon, setAlertStateIcon] = useState('');
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
    setAlertMessage(response.data.message);
    setAlertStateIcon(response.data.state);
    setShowAlertModal(true);

    setTimeout(() => {
      setShowAlertModal(false);
    }, 2500);
  };

  const onSubmit = async (data) => {
    try {
      data.photoWall = photoWallValue.map((file) => file.originFileObj);
      const avatarData = data.profileAvatar ? (data.profileAvatar[0].originFileObj || data.profileAvatar) : null;
      setSubmitting(true);
      await submitForm(data);
      // console.log(data.photoWall);
      console.log(data);
      console.log(avatarData);
      let str = [];
      for (let i = 0; i < data.hobbies.length; i++) {
        str.push(data.hobbies[i].label);
      }
      const formData = new FormData();
      formData.append('fullName', data.fullName);
      formData.append('gender', data.gender);
      formData.append('sexualOrientation', data.sexualOrientation);
      formData.append('location', JSON.stringify(data.location));
      formData.append('selfIntro', data.selfIntro);
      formData.append('preferredGender', data.preferredGender);
      formData.append('datingGoal', data.datingGoal);
      formData.append('avatar', avatarData);
      formData.append('birthday', data.birthday);
      formData.append('professions', data.professions.label);
      formData.append('phone', data.phone);
      formData.append('photos', data.photoWall);
      formData.append('hobbies', str);

      axios.defaults.withCredentials = true;
      const response = await axios.put('/users/profile', formData, {
        headers: {
          'Content-Type': 'multipart/form-data; boundary=<calculated when request is sent>',
        },
      });
      console.log(response.data);

      if (response.data.state == 204) {
        handleAlertRes(response);
        setTimeout(() => {
          navigate('/home');
        }, 2500);
      } else {
        handleAlertRes(response);
        setTimeout(() => {
          navigate('/setting');
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
      }, 2000);
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
    setValue('location', selectedLocation); // 使用setValue更新Controller的值
  };

  const handleDialogOk1 = (selectedButtonLabel) => {
    // 传入选中按钮的标签
    setInfoModalVisible(false);
    setSelectedButtonLabel(selectedButtonLabel); // 更新选中按钮的标签
    setValue('datingGoal', selectedButtonLabel); // 使用setValue更新datingGoal字段的值
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

  const [formDatas, setformDatas] = useState({
    fullName: '',
    phone: '',
    birthday: '',
    gender: '',
    sexualOrientation: '',
    preferredGender: '',
    professions: '',
    datingGoal: '',
    location: '',
    selfIntro: '',
  });

  const [defaultValue, setDefaultValue] = useState(formDatas.professions
    ? profession.find((option) => option.label === formDatas.professions)
    : profession[0]);
  const [birthdayDate, setBirthdayDate] = useState(formDatas.birthday.split('T')[0]);

  const fetchData = () => {
    axios
      .get('/users/profile')
      .then((response) => {
        const userData = response.data;
        setformDatas(userData.data);
        console.log(userData.data);
        
        setBirthdayDate(userData.data.birthday.split('T')[0]);
        setDefaultValue(userData.data.professions
          ? profession.find((option) => option.label === userData.data.professions)
          : profession[0]);
        
        setValue('fullName', userData.data.fullName);
        setValue('phone', userData.data.phone);
        setValue('birthday', userData.data.birthday.split('T')[0]);
        setValue('gender', userData.data.gender);
        setValue('sexualOrientation', userData.data.sexualOrientation);
        setValue('preferredGender', userData.data.preferredGender);
        setValue('professions', profession.find((option) => option.label === userData.data.professions));
        setValue('datingGoal', userData.data.datingGoal);
        setValue('location', userData.data.location);
        setValue('profileAvatar', userData.data.profileAvatar);
        setValue('selfIntro', userData.data.selfIntro);
        setSelectedButtonLabel(userData.data.datingGoal);
        setSelectedLocation(userData.data.location);
        
        axios.defaults.withCredentials = true;
      })
      .catch((error) => {
        console.error(error);
      });
  };  

  const [hobbyOptions, setHobbyOptions] = useState({
    hobbies: ''
  });

  const fetchData2 = () => {
    axios
      .get('/users/interest')
      .then((response) => {
        const hobbyArray = response.data.data.map((item) => item.trim());
  
        const options = hobbyArray.map((item) => {
          const matchedHobby = hobbies.find((hobby) => hobby.label === item);
          if (matchedHobby) {
            return { value: matchedHobby.value, label: matchedHobby.label };
          }
          return null;
        });
  
        setHobbyOptions(options);
        console.log(options);
        setValue('hobbies', options);
      })
      .catch((error) => {
        console.error(error);
      });
  };  
  
  useEffect(() => {
    fetchData();
    fetchData2();
  }, []);
  
  const handleChanges = (e) => {
    const { name, value } = e.target;
    setformDatas((prevData) => ({
      ...prevData,
      [name]: value,
    }));
  };

  return (
    <>
      <section className='container'>
        <section className='bg-pageMain row d-flex'>
          <div className='bg-pageTitle d-flex'>
            <div className='container'>
              <form className='row justify-content-center flex-md-row flex-column-reverse'>
                <div className='col-md-5'>
                  <div className='pb-4 w-50'>
                    <i className='fa-solid fa-user text-black' />
                    <span className='text-danger p-1'>*</span>
                    <InfoInput
                      id='fullName'
                      labelText='姓名'
                      type='text'
                      placeholder='輸入姓名'
                      errors={errors}
                      register={register}
                      // rules={{
                      //   required: '姓名為必填',
                      // }}
                      value={formDatas.fullName}
                      onChange={handleChanges}
                    ></InfoInput>
                  </div>
                  <div className='pb-4 w-50'>
                    <i className='fa-solid fa-phone text-black' />
                    <span className='text-danger p-1'>*</span>
                    <InfoInput
                      id='phone'
                      labelText='電話'
                      type='text'
                      placeholder='輸入手機號碼'
                      errors={errors}
                      register={register}
                      rules={{
                        // required: '電話為必填',
                        pattern: {
                          value: /^[0-9]{10}$/,
                          errorMessages: '電話必須是 10 位數字',
                        },
                      }}
                      value={formDatas.phone}
                      onChange={handleChanges}
                    />
                  </div>
                  <div className='pb-4 w-50'>
                    <i className='fa-solid fa-cake-candles text-black'></i>
                    <span className='text-danger p-1'>*</span>
                    {/* <label htmlFor="">生日</label>
                    <br /> */}
                    <InfoInput
                      id='birthday'
                      labelText='生日'
                      type='text'
                      placeholder='輸入生日 (yyyy-mm-dd)'
                      errors={errors}
                      register={register}
                      rules={{
                        // required: '生日為必填',
                        pattern: {
                          value: /^\d{4}-\d{2}-\d{2}$/,
                          message: '請輸入有效的生日格式 (yyyy-mm-dd)',
                        },
                      }}
                      value={birthdayDate}
                      onChange={(e) => {
                        const updatedValue = e.target.value; // 获取输入框的值
                        setBirthdayDate(updatedValue); // 更新生日状态值
                        setValue('birthday', updatedValue); // 更新表单值
                      }}
                    />
                  </div>
                  <div className='pb-4'>
                    <i className='fa-solid fa-briefcase text-black'></i>
                    <span className='text-danger p-1'>*</span>
                    <label className='mb-2'>職業</label>
                    <br />
                    <Controller
                      name='professions'
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
                          value={defaultValue}
                          onChange={(selectedOption) => {
                            field.onChange(selectedOption); // 更新表單值
                            setDefaultValue(selectedOption); // 更新狀態值
                          }}
                        />
                      )}
                    />
                    {errors.profession && (
                      <div
                        className='error-message text-danger mt-2'
                        style={{ fontSize: '0.9rem' }}
                      >
                        {errors.profession.message}
                      </div>
                    )}
                  </div>
                  <div className='pb-4'>
                    <i className='fa-solid fa-venus-mars text-black'></i>
                    <span className='text-danger p-1'>*</span>
                    <label>性別</label>
                    <br />
                    <RadioButtonGroups
                      name='gender'
                      options={radioGender}
                      register={register}
                      errors={errors}
                      rules={{
                        required: '請選擇性別',
                      }}
                      checked={formDatas.gender}
                      onChange={handleChanges}
                    />
                  </div>
                  <div className='pb-4'>
                    <i className='fa-solid fa-transgender text-black'></i>
                    <span className='text-danger p-1'>*</span>
                    <label>性向</label>
                    <br />
                    <RadioButtonGroups
                      name='sexualOrientation'
                      options={radioSO}
                      register={register}
                      errors={errors}
                      rules={{
                        required: '請選擇性向',
                      }}
                      checked={formDatas.sexualOrientation}
                      onChange={handleChanges}
                    />
                  </div>
                  <div className='pb-4'>
                    <i className='fa-solid fa-users text-black'></i>
                    <span className='text-danger p-1'>*</span>
                    <label>顯示給我看</label>
                    <br />
                    <RadioButtonGroups
                      name='preferredGender'
                      options={radioShow}
                      register={register}
                      errors={errors}
                      rules={{
                        required: '顯示給我看為必填',
                      }}
                      checked={formDatas.preferredGender}
                      onChange={handleChanges}
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
                      defaultValue={hobbyOptions}
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
                          value={hobbyOptions}
                          onChange={(selectedOptions) => {
                            field.onChange(selectedOptions); // 更新表單值
                            setHobbyOptions(selectedOptions); // 更新狀態值
                          }}
                        />
                      )}
                    />
                    {errors.hobbies && (
                      <div
                        className='error-message text-danger mt-2'
                        style={{ fontSize: '0.9rem' }}
                      >
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
                        name='datingGoal'
                        rules={{ required: true }}
                        render={({ field }) => (
                          <Button
                            type='dashed'
                            {...field}
                            className={` ${errors.datingGoal && 'is-invalid'}`}
                            onClick={handleInfoModalButtonClick}
                            onChange={handleChanges}
                          >
                            +新增交友目標
                          </Button>
                        )}
                      />
                      {errors.datingGoal && !selectedButtonLabel && (
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
                      {selectedButtonLabel && (
                        <div className='completed-tag'>{selectedButtonLabel}</div>
                      )}
                    </div>
                    <div className='mb-2 ps-5'>
                      <i className='fa-solid fa-location-dot text-black'></i>
                      <span className='text-danger p-1'>*</span>
                      <label className='mb-2'>地點</label>
                      <br />
                      <Controller
                        control={control}
                        name='location'
                        rules={{ required: true }}
                        render={({ field }) => (
                          <Button
                            type='dashed'
                            {...field}
                            className={` ${errors.location && 'is-invalid'}`}
                            onClick={handleLocationModalButtonClick}
                            onChange={handleChanges}
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
                      {errors.location && !isLocationSelected && (
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
                    <label htmlFor='selfIntro' className='form-label mb-2'>
                      文字自我介紹
                    </label>
                    <textarea
                      className='form-control'
                      id='selfIntro'
                      rows='3'
                      maxLength={40}
                      {...register('selfIntro')}
                      value={formDatas.selfIntro}
                      onChange={handleChanges}
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
                            value={field.value}
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
                <button
                  type='submit'
                  className='btn btn-primary rounded text-white'
                  disabled={submitting}
                  onClick={handleSubmit(onSubmit)}
                >
                  {submitting ? '正在送出表單...' : '送出表單'}
                </button>
              </div>
            </div>
          </div>
        </section>
      </section>
      <AlertModal message={alertMessage} showModal={showAlertModal} state={AlertStateIcon} />
    </>
  );
}
export default SettingPersonal;
