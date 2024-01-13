import React, { useState, forwardRef, useRef } from 'react';
import { PlusOutlined } from '@ant-design/icons';
import { Button, Divider, Input, Select, Space } from 'antd';

let index = 0;

const Hobbies = forwardRef(() => {
  const [items, setItems] = useState(['游泳', '爬山']);
  const [name, setName] = useState('');
  const inputRef = useRef(null);

  const onNameChange = (event) => {
    setName(event.target.value);
  };

  const addItem = (e) => {
    e.preventDefault();
    setItems([...items, name || `New item ${index++}`]);
    setName('');
    setTimeout(() => {
      inputRef.current?.focus();
    }, 0);
  };

  return (
    <Select
      mode="multiple"
      style={{
        width: 300,
      }}
      placeholder="新增興趣"
      dropdownRender={(menu) => (
        <>
          {menu}
          <Divider
            style={{
              margin: '8px 0',
            }}
          />
          <Space
            style={{
              padding: '0 8px 4px',
            }}
          >
            <Input
              placeholder="輸入興趣"
              ref={inputRef}
              value={name}
              onChange={onNameChange}
            />
            <Button type="text" icon={<PlusOutlined />} onClick={addItem}>
              新增
            </Button>
          </Space>
        </>
      )}
      options={items.map((item) => ({
        label: item,
        value: item,
      }))}
    />
  );
});

Hobbies.displayName = 'Hobbies';

export default Hobbies;
