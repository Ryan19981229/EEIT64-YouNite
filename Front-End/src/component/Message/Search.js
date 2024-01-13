// 搜尋框
import React, { useState } from 'react'
import '../../styles/all.scss'
import { SearchOutlined } from '@ant-design/icons';
import { Input } from 'antd';
import { ChatDatas } from './ChatDatas';

const Search = () => {
  const [username, setUsername] = useState("");
  const [user, setUser] = useState(null);
  const [err, setErr] = useState(false);

  const handleSearch = async () => {
    const filteredChats = ChatDatas.filter(
      (chat) => chat.userInfo.displayName === username
    );

    if (filteredChats.length > 0) {
      setUser(filteredChats[0].userInfo);
    } else {
      setUser(null);
      setErr(true);
    }
  };

  const handleKey = (e) => {
    if (e.code === "Enter") {
      handleSearch();
    }
  };

  const handleSelect = async () => {
    // 在這裡處理選擇使用者的邏輯
    setUser(null);
    setUsername("");
  };

  return (
    <div className="border-bottom">
      <div className="mx-3">
        <Input 
          size="large" 
          placeholder="搜尋" 
          prefix={<SearchOutlined />}  
          className='border-0 rounded-pill' 
          style={{outline: 'none', height:'40px'}}
          onKeyDown={handleKey}
          onChange={(e) => setUsername(e.target.value)}
          value={username}
        />
      </div>
      {err && <span>User not found!</span>}
      {user && (
        <div className='mt-2 p-2 d-flex align-items-center gap-2 ' id='userChat' style={{ cursor: 'pointer' }} onClick={handleSelect}>
          <img className='rounded-circle bg-dark' style={{height: '50px', width:'50px' , objectFit: 'cover'}} src={user.photoURL} alt="" />
          <div>
              <span style={{fontSize:'18px'}}>{user.displayName}</span>
          </div>
        </div>
        )}
    </div>
  )
}

export default Search
