import React from 'react';
import '../../styles/component/userCard.scss';

function UserCard() {
  return (
    <>
      <div className='user-card px-2 py-2 row'>
        <div className='col d-flex align-items-center placeholder-glow'>
          <div className='mb-1 user-card-image placeholder' />
          <h6 className='ms-3 placeholder'></h6>
          <h6 className='ms-2 placeholder'></h6>
        </div>

        <div className='row mt-1 placeholder-glow'>
          <button type='button' className='btn placeholder btn-sm col-3 m-1 rounded-pill btn-block'>
            
          </button>
          <button type='button' className='btn placeholder btn-sm col-3 m-1 rounded-pill btn-block'>
            
          </button>
          <button type='button' className='btn placeholder btn-sm col-3 m-1 rounded-pill btn-block'>
            
          </button>
          
        </div>
      </div>
    </>
  );
}

export default UserCard;
