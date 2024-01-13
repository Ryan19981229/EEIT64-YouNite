import React from 'react';
import '../../styles/store.scss';

function StoreCard() {
  return (
    <>
      <div className='store-card col mx-5'>
        <div className='store-white'>
          <h3 className='mt-5 pt-4'>12個月</h3>
          <span className='store-gray'>
            <h4 className='mt-4'>NT$1690</h4>
            <h6 className='text-decoration-line-through'>NT$6720</h6>
            <h6 className='mt-4 pb-5'>
              <span className='store-price '>NT$141/月</span>
            </h6>
          </span>
        </div>
      </div>
    </>
  );
}

export default StoreCard;
