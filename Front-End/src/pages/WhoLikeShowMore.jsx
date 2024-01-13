import React, { useState, useEffect } from 'react'
import '../styles/showmore.scss'
import userImge from '../assets/images/sia.png'
import UserModal from '../component/Modal/UserMoadl';
import axios from 'axios';
import FilterModal from '../component/Modal/FilterModal';

const WhoLikeShowMore = () => {
    const [post, setPost] = useState([]);
    const [filteredPost, setFilteredPost] = useState([]);
    const [likedUsers, setLikedUsers] = useState([]);
    const [openModal, setOpenModal] = useState(false); // Model 開關
    const [fadeInModal, setFadeInModal] = useState(false); // 追蹤是否需要淡入
    
    axios.defaults.withCredentials = true;
    useEffect(() => {
      const fetchData = async () => {
        try {
          const response = await axios.get('/users/likesTrackerProfiles');
          const likedUsersResponse = await axios.get('/users/getLikedUsers'); // 獲取使用者的邀請紀錄
          setLikedUsers(likedUsersResponse.data); // 設定使用者的邀請紀錄
          setPost(response.data);
          setFilteredPost(response.data.data);
          console.log(response.data);
        } catch (error) {
          console.error(error);
        }
      };
  
      fetchData();
    }, []);
  
    const handleFilter = (distance, age) => {
      console.log('distance:', distance);
      console.log('age:', age);
    
      const filteredData = post.data.filter((item) => {
        return item.distance <= distance && item.age <= age;
      });
    
      setFilteredPost(filteredData);
      console.log(filteredData);
    };  
  
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
  
    const handleCloseModalFilter = () => {
      setOpenModal(false);
    };
  
    const handleModalClick = () => {
      setOpenModal(true);
      setFadeInModal(true); //設置淡入為 true，觸發淡入效果
    };
  
  
  
    return (
      <>
        {openModal && <FilterModal closeModal={handleCloseModalFilter} fadeIn={fadeInModal} handleFilter={handleFilter}/>}
        <div className='bg-pageTitle d-flex'>
            <h6>你可能喜歡</h6>
            <button
            type='button'
            className='btn btn-primary btn-sm text-white'
            onClick={handleModalClick}
          >
            <i className='fa-solid fa-filter me-1' size='sm'></i>篩選
          </button>
        </div>
        <div className='d-flex' style={{ flexWrap: 'wrap' }}>
        {filteredPost ? (
          filteredPost.map((item, index) => (
            <section 
              key={index}
              className='usersImg ms-3 my-3 me-4' 
              style={{'--bg-images': `url(${item.profileAvatar || userImge})`}}
              onClick={(event) => handleUserButtonClick(event, item)}
            >
              <div className='mt-auto'>
                <div className='d-flex ms-2'>
                  <h5 className='me-2 text-secondary'>{item.name}</h5>
                  <span className='mx-2 text-radio'>{item.age}</span>
                </div>
                <div className='text-nowrap'>
                      {item.interests && item.interests.slice(0, 3).map((interest, i) => (
                        <button key={i} type='button' className='btn btn-outline-radio btn-sm mx-1 mb-1 rounded-pill btn-block'>
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
          {Object.keys(userModalStates).map((userID) => (
            userModalStates[userID].isOpen && (
              <UserModal
                key={userID}
                userID={userID}
                closeModal={() => handleCloseModal(userID)}
                data={userModalStates[userID].data}
                likedUsers={likedUsers} // 傳遞使用者的邀請紀錄
              />
            )
          ))}
      </div>
      </>
    )
  }

export default WhoLikeShowMore
