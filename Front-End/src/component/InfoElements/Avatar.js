import React, { useState, useEffect } from 'react';
import { Upload, message } from 'antd';
import ImgCrop from 'antd-img-crop';
import PropTypes from 'prop-types';
import axios from 'axios';

const Avatar = ({ onFileChange }) => {
  const [formData, setFormData] = useState();
  const [fileList, setFileList] = useState([]);

  useEffect(() => {
    axios.defaults.withCredentials = true;
    axios
      .get('http://localhost:8080/users/profile')
      .then((response) => {
        const userData = response.data;
        setFormData(userData.data.profileAvatar);
      })
      .catch((error) => {
        console.error(error);
      });
  }, []);

  useEffect(() => {
    if (formData) {
      setFileList([{ url: formData }]);
    }
  }, [formData]);

  const onChange = ({ fileList: newFileList }) => {
    // 檢查新的檔案列表長度
    if (newFileList.length > 1) {
      // 提示錯誤訊息
      message.error('只能上傳一張照片');
      // 刪除多餘的檔案，只保留最新上傳的一張
      newFileList.splice(0, newFileList.length - 1);
    }

    setFileList(newFileList);
    onFileChange(newFileList);
  };

  const onPreview = async (file) => {
    let src = file.url;
    if (!src) {
      src = await new Promise((resolve) => {
        const reader = new FileReader();
        reader.readAsDataURL(file.originFileObj);
        reader.onload = () => resolve(reader.result);
      });
    }
    const image = new Image();
    image.src = src;
    const imgWindow = window.open(src);
    imgWindow?.document.write(image.outerHTML);
  };

  const handleBeforeUpload = (file) => {
    const isImage = file.type.startsWith('image/');
    const isLt2M = file.size / 1024 / 1024 < 2;

    if (!isImage) {
      message.error('只能上傳圖片文件！');
    }

    if (!isLt2M) {
      message.error('圖片文件大小不能超過2MB！');
    }

    return isImage && isLt2M;
  };

  const handleCustomRequest = (options) => {
    const { onSuccess, onProgress } = options;

    // 自定义上传逻辑
    // 将文件上传到指定位置，并在上传过程中更新进度和状态

    // 示例：模拟上传进度
    const total = 100;
    let uploaded = 0;

    const uploadInterval = setInterval(() => {
      uploaded += 10;
      onProgress({ percent: uploaded });

      if (uploaded >= total) {
        clearInterval(uploadInterval);
        onSuccess(); // 上传成功
      }
    }, 500);

    // 示例：模拟上传失败
    // setTimeout(() => {
    //   clearInterval(uploadInterval);
    //   onError(new Error('上传失败'));
    // }, 2000);
  };

  return (
    <ImgCrop rotationSlider>
      <Upload
        beforeUpload={handleBeforeUpload}
        customRequest={handleCustomRequest}
        listType='picture-circle'
        fileList={fileList}
        onChange={onChange}
        onPreview={onPreview}
      >
        {fileList.length === 0 && '+ Upload'}
      </Upload>
    </ImgCrop>
  );
};

Avatar.propTypes = {
  onFileChange: PropTypes.func.isRequired,
};

export default Avatar;
