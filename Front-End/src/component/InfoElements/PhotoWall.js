import React, { useState, useEffect } from 'react';
import { PlusOutlined } from '@ant-design/icons';
import { Modal, Upload, message } from 'antd';
import PropTypes from 'prop-types';

const getBase64 = (file) =>
  new Promise((resolve, reject) => {
    const reader = new FileReader();
    reader.readAsDataURL(file);
    reader.onload = () => resolve(reader.result);
    reader.onerror = (error) => reject(error);
  });

const PhotoWall = ({ onChange }) => {
  const [previewOpen, setPreviewOpen] = useState(false);
  const [previewImage, setPreviewImage] = useState('');
  const [previewTitle, setPreviewTitle] = useState('');
  const [fileList, setFileList] = useState([
    // {
    //   uid: '-1',
    //   name: 'image.png',
    //   status: 'done',
    //   url: 'https://images.unsplash.com/photo-1524250502761-1ac6f2e30d43?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=388&q=80',
    // },
    // {
    //   uid: '-2',
    //   name: 'image.png',
    //   status: 'done',
    //   url: 'https://images.unsplash.com/photo-1560727749-cc261b23794c?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=387&q=80',
    // },
  ]);
  const handleCancel = () => setPreviewOpen(false);
  const handlePreview = async (file) => {
    if (!file.url && !file.preview) {
      file.preview = await getBase64(file.originFileObj);
    }
    setPreviewImage(file.url || file.preview);
    setPreviewOpen(true);
    setPreviewTitle(file.name || file.url.substring(file.url.lastIndexOf('/') + 1));
  };
  useEffect(() => {
    onChange(fileList); // 監聽 fileList 變化，觸發 onChange 回調函式
  }, [fileList, onChange]);
  const handleChange = ({ fileList: newFileList }) => setFileList(newFileList);
  const uploadButton = (
    <div>
      <PlusOutlined />
      <div
        style={{
          marginTop: 8,
        }}
      >
        Upload
      </div>
    </div>
  );

  const handleBeforeUpload = (file) => {
    const isImage = file.type.startsWith('image/');
    const isLt2M = file.size / 1024 / 1024 < 2;

    if (!isImage) {
      message.error('只能上传图片文件！');
    }

    if (!isLt2M) {
      message.error('图片文件大小不能超过2MB！');
    }

    return isImage && isLt2M;
  };

  const handleCustomRequest = (options) => {
    const {  onSuccess, onProgress } = options;

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
    <>
      <Upload
        beforeUpload={handleBeforeUpload}
        customRequest={handleCustomRequest}
        listType='picture-card'
        fileList={fileList}
        onPreview={handlePreview}
        onChange={handleChange}
      >
        {fileList.length >= 8 ? null : uploadButton}
      </Upload>
      <Modal open={previewOpen} title={previewTitle} footer={null} onCancel={handleCancel}>
        <img
          alt='example'
          style={{
            width: '100%',
          }}
          src={previewImage}
        />
      </Modal>
    </>
  );
};

PhotoWall.propTypes = {
  onChange: PropTypes.func.isRequired,
};

export default PhotoWall;
