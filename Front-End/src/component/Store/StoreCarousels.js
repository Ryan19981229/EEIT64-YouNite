import React from 'react';
import 'bootstrap/dist/js/bootstrap';
import UnLockVoiceImage from '../../assets/images/storeUnLockVoice.png';
import ImproveMatchingImage from '../../assets/images/storeImproveMatching.png';
import WhoLikeU from '../../assets/images/storeWhoLikeU.png';

function StoreCarousels() {
  return (
    <div id='carouselExampleCaptions' className='carousel slide'>
      <div className='carousel-indicators'>
        <button
          type='button'
          data-bs-target='#carouselExampleCaptions'
          data-bs-slide-to='0'
          className='active col'
          aria-current='true'
          aria-label='Slide 1'
        ></button>
        <button
          type='button'
          data-bs-target='#carouselExampleCaptions'
          data-bs-slide-to='1'
          aria-label='Slide 2'
        ></button>
        <button
          type='button'
          data-bs-target='#carouselExampleCaptions'
          data-bs-slide-to='2'
          aria-label='Slide 3'
        ></button>
      </div>
      <div className='carousel-inner row d-flex'>
        <div className='carousel-item active'>
          <h5 className='store-title'>解鎖聲音</h5>
          <img src={UnLockVoiceImage} className='d-block m-auto' alt='解鎖聲音' />
          <div className='carousel-caption d-none d-md-block'></div>
        </div>
        <div className='carousel-item'>
          <h5 className='store-title'>增加2.5倍配對率</h5>
          <img src={ImproveMatchingImage} className='d-block m-auto' alt='增加2.5倍配對率' />
          <div className='carousel-caption d-none d-md-block'></div>
        </div>
        <div className='carousel-item'>
          <h5 className='store-title'>誰喜歡你</h5>
          <img src={WhoLikeU} className='d-block m-auto' alt='誰喜歡你' />
          <div className='carousel-caption d-none d-md-block'></div>
        </div>
      </div>
      <button
        className='carousel-control-prev'
        type='button'
        data-bs-target='#carouselExampleCaptions'
        data-bs-slide='prev'
      >
        <span className='carousel-control-prev-icon' aria-hidden='true'></span>
        <span className='visually-hidden'>Previous</span>
      </button>
      <button
        className='carousel-control-next'
        type='button'
        data-bs-target='#carouselExampleCaptions'
        data-bs-slide='next'
      >
        <span className='carousel-control-next-icon' aria-hidden='true'></span>
        <span className='visually-hidden'>Next</span>
      </button>
    </div>
  );
}

export default StoreCarousels;
