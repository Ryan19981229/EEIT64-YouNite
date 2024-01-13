import React, { useEffect, useState } from 'react';
import PropTypes from 'prop-types';
import SideNav from '../component/SideNav';
import Chat from '../component/Message/Chat';
import Hello from '../component/Message/Hello';
import '../styles/chatroom.scss';
import axios from 'axios';

const ChatRoom = () => {
  const [friendList, setFriendList] = useState([]);
  const [chatRoomInfo, setChatRoomInfo] = useState([]);
  const [userInfo, setUserInfo] = useState();
  const [selectedFriend, setSelectedFriend] = useState(null);

  useEffect(() => {
    const fetchData = async () => {
      try {
        setFriendList([]); // 清空 friendList
        setChatRoomInfo([]); // 清空 chatRoomInfo
        const resUserInfo = await axios.get('/users/profile');
        const resChatRoomInfo = await axios.get('/message/find/32', {
          params: { timestamp: Date.now() }, // 附加獨特的時間戳防止重複渲染
        });
        const resFriendList = await axios.get('/getProfile/friendList', {
          params: { timestamp: Date.now() }, // 附加獨特的時間戳防止重複渲染
        });
        const id = resUserInfo.data.data.userId;
        const friendListsAry = [];
        const roomList = [];

        resFriendList.data.forEach(async (element) => {
          friendListsAry.push({ id: element.userid, key: element.userid });
          let room = await axios.get(`/getProfile/getRoom/${id}/${element.userid}`);
          roomList.push(room);
        });
        console.log('friendListsAry', friendListsAry);
        console.log('roomList', roomList);

        setFriendList(resFriendList.data);
        setChatRoomInfo(resChatRoomInfo.data);
        setUserInfo(resUserInfo.data);
      } catch (error) {
        console.error(error);
      }
    };

    fetchData();
  }, []);

  console.log('friendList', friendList);
  console.log('chatRoomInfo', chatRoomInfo);
  console.log('userInfo', userInfo);

  const handleSelect = (fullName, profileAvatar, userid) => {
    setSelectedFriend({
      fullName: fullName,
      profileAvatar: profileAvatar,
      userid: userid,
    });
  };

  console.log('selectedFriend', selectedFriend);

  return (
    <>
      <SideNav />
      <div className='container-fluid'>
        <div className='row chatroom-main'>
          <div className='col-3 bg-secondary chatroom-list'>
            <div>
              <div className='pt-3'>
                <h3 className='d-grid justify-content-center'>對話</h3>
                <p className='d-grid justify-content-center text-radio'>好友對話:10/25</p>
              </div>
              <div>
                {/* Search */}
                {friendList.length > 0 ? (
                  friendList.map((friend) => (
                    <div
                      className='p-2 d-flex align-items-center gap-2'
                      id='userChat'
                      style={{ cursor: 'pointer' }}
                      key={friend.userid}
                      onClick={() =>
                        handleSelect(friend.fullName, friend.profileAvatar, friend.userid)
                      }
                    >
                      <img
                        className='rounded-circle bg-dark'
                        style={{ height: '50px', width: '50px', objectFit: 'cover' }}
                        src={friend.profileAvatar}
                        alt=''
                      />
                      <div>
                        <span style={{ fontSize: '18px' }}>{friend.fullName}</span>
                        <p style={{ fontSize: '14px', color: '#949494' }}>{friend.lastMessage}</p>
                      </div>
                    </div>
                  ))
                ) : (
                  <p>請選擇朋友</p>
                )}
              </div>
            </div>
          </div>
          <div className='col-9 text-white p-0'>
            {selectedFriend ? (
              <Chat
                friendList={friendList}
                chatRoomInfo={chatRoomInfo}
                selectedFriend={selectedFriend}
                userInfo={userInfo}
              />
            ) : (
              <Hello />
            )}
          </div>
        </div>
      </div>
    </>
  );
};

ChatRoom.propTypes = {
  selectedFriend: PropTypes.object,
};
export default ChatRoom;
