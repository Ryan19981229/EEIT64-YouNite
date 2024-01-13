import React, { useState, useEffect } from 'react';
import '../styles/component/bdGradient.scss';
import '../styles/home.scss';
// import PeoplePhoto from '../component/User/PeoplePhoto';
import UserCard from '../component/User/UserCard';
import { Link } from 'react-router-dom';
import axios from 'axios';
import UserModal from '../component/Modal/UserMoadl';
import userImge from '../assets/images/sia.png';

function Home() {
  const [post1, setPost1] = useState([]);
  const [post2, setPost2] = useState([]);
  const [post3, setPost3] = useState([]);
  const [post4, setPost4] = useState([]);
  const [unlocked, setUnlocked] = useState(false); // 確認是否解鎖
  const [likedUsers, setLikedUsers] = useState([]);

  useEffect(() => {
    const fetchData = async () => {
      try {
        const response1 = await axios.get('/users/mutualInterests');
        const response2 = await axios.get('/users/profiles/profession');
        const likedUsersResponse = await axios.get('/users/getLikedUsers'); // 獲取使用者的邀請紀錄
        const response3 = await axios.get('/users/recommendation/');
        const response4 = await axios.get('/users/getUser');
        const response5 = await axios.get('/users/likesTrackerProfiles');
        setPost1(response1.data);
        setPost2(response2.data);
        setPost3(response3.data);
        setUnlocked(response4.data.data.unlocked); // 設定解鎖狀態
        console.log(response4.data.data.unlocked); // 設定解鎖狀態
        setPost4(response5.data);
        setLikedUsers(likedUsersResponse.data); // 設定使用者的邀請紀錄
      } catch (error) {
        console.error(error);
      }
    };

    fetchData();
  }, []);

  const [userModalStates, setUserModalStates] = useState(false);

  const handleUserButtonClick = (event, item) => {
    event.preventDefault();
    setUserModalStates((prevState) => ({
      ...prevState,
      [item.userID]: {
        isOpen: true,
        data: item,
      },
    }));
  };

  const handleCloseModal = () => {
    setUserModalStates(false);
  };

  return (
    <>
      <section className='container mt-4'>
        <section className='bg-pageMain row d-flex'>
          <div className='bg-pageTitle d-flex'>
            <h6>你可能喜歡</h6>
          </div>
          <div className='bg-pagePhoto d-flex mt-3 mb-3'>
            {post3.data ? (
              post3.data.slice(0, 5).map((item, index) => (
                <section
                  key={index}
                  className='usersImg ms-3 my-3 me-4'
                  style={{ '--bg-images': `url(${item.profileAvatar || userImge})` }}
                  onClick={(event) => handleUserButtonClick(event, item)}
                >
                  <div className='mt-auto'>
                    <div className='d-flex ms-2'>
                      <h5 className='me-2 text-secondary'>{item.name}</h5>
                      <span className='mx-2 text-radio'>{item.age}</span>
                    </div>
                    <div className='text-nowrap'>
                      {item.interests &&
                        item.interests.slice(0, 3).map((interest, i) => (
                          <button
                            key={i}
                            type='button'
                            className='btn btn-outline-radio btn-sm mx-1 mb-1 rounded-pill btn-block'
                          >
                            #{interest}
                          </button>
                        ))}
                    </div>
                  </div>
                </section>
              ))
            ) : (
              <p>loading</p>
            )}
          </div>
          <div className='bg-pageMore d-flex'>
            <Link to='/show/like'>顯示更多...</Link>
          </div>
        </section>
        <section className='row justify-content-between mt-3'>
          <div className='bg-pageMain col me-3'>
            <h6>共同興趣</h6>
            <>
              {post1.data ? (
                post1.data.slice(0, 2).map((item, index) => (
                  <div
                    key={index}
                    className='user-card px-2 py-2 row'
                    onClick={(event) => handleUserButtonClick(event, item)}
                  >
                    <div className='col d-flex align-items-center'>
                      <img
                        src={item.profileAvatar || userImge}
                        alt='Your Picture'
                        className='mb-1 user-card-image'
                      />
                      <h6 className='ms-3'>{item.name}</h6>
                      <h6 className='ms-2 text-radio'>{item.age}</h6>
                    </div>
                    <div className='row mt-1 text-nowrap'>
                      {item.interests &&
                        item.interests.slice(0, 3).map((interest, i) => (
                          <button
                            key={i}
                            type='button'
                            className='btn btn-outline-primary btn-sm col-3 m-1 rounded-pill btn-block'
                          >
                            #{interest}
                          </button>
                        ))}
                    </div>
                  </div>
                ))
              ) : (
                <>
                  <UserCard />
                  <UserCard />
                </>
              )}
            </>
            <div className='bg-pageMore d-flex mt-3'>
              <Link to='/show/interest'>顯示更多...</Link>
            </div>
          </div>
          <div className='bg-pageMain col'>
            {unlocked ? (
              <>
                <h6>誰喜歡你</h6>
                {post4.data &&
                  post4.data.map((item, index) => (
                    <div
                      key={index}
                      className='user-card px-2 py-2 row'
                      onClick={(event) => handleUserButtonClick(event, item)}
                    >
                      <div className='col d-flex align-items-center'>
                        <img
                          src={item.profileAvatar || userImge}
                          alt='Your Picture'
                          className='mb-1 user-card-image'
                        />
                        <h6 className='ms-3'>{item.name}</h6>
                        <h6 className='ms-2 text-radio'>{item.age}</h6>
                      </div>
                      <div className='row mt-1 text-nowrap'>
                        {item.interests &&
                          item.interests.slice(0, 3).map((interest, i) => (
                            <button
                              key={i}
                              type='button'
                              className='btn btn-outline-primary btn-sm col-3 m-1 rounded-pill btn-block'
                            >
                              #{interest}
                            </button>
                          ))}
                      </div>
                    </div>
                  ))}
                {/* : ( */}
                <>
                  <UserCard />
                  <UserCard />
                </>
                {/* ) */}
              </>
            ) : (
              <>
                <Link to='/store'>
                  <i className='fa-solid fa-user-lock' style={{ color: '#82898D' }}></i>
                </Link>
                <h6>誰喜歡你</h6>
                <div className='user-lock'>
                  <UserCard />
                </div>
              </>
            )}
            <div className='bg-pageMore d-flex mt-3'>
              <Link to='/show/wholike'>顯示更多...</Link>
            </div>
          </div>
          <div className='bg-pageMain col ms-3'>
            <h6>共同職業</h6>
            <>
              {post2.data ? (
                post2.data.slice(0, 2).map((item, index) => (
                  <div
                    key={index}
                    className='user-card px-2 py-2 row'
                    onClick={(event) => handleUserButtonClick(event, item)}
                  >
                    <div className='col d-flex align-items-center'>
                      <img
                        src={item.avatar || userImge}
                        alt='Your Picture'
                        className='mb-1 user-card-image'
                      />
                      <h6 className='ms-3'>{item.name}</h6>
                      <h6 className='ms-2 text-radio'>{item.age}</h6>
                    </div>
                    <div className='row mt-1 text-nowrap'>
                      {item.interests &&
                        item.interests.slice(0, 3).map((interest, i) => (
                          <button
                            key={i}
                            type='button'
                            className='btn btn-outline-primary btn-sm col-3 m-1 rounded-pill btn-block'
                          >
                            #{interest}
                          </button>
                        ))}
                    </div>
                  </div>
                ))
              ) : (
                <>
                  <UserCard />
                  <UserCard />
                </>
              )}
            </>
            <div className='bg-pageMore d-flex mt-3'>
              <Link to='/show/profession'>顯示更多...</Link>
            </div>
          </div>
        </section>
        {Object.keys(userModalStates).map(
          (userID) =>
            userModalStates[userID].isOpen && (
              <UserModal
                key={userID}
                userID={userID}
                closeModal={() => handleCloseModal(userID)}
                data={userModalStates[userID].data}
                likedUsers={likedUsers} // 傳遞使用者的邀請紀錄
              />
            ),
        )}
      </section>
    </>
  );
}

export default Home;
