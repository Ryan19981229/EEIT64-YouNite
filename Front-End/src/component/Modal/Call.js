import React, { useState } from 'react';
import PropTypes from 'prop-types';
import Draggable from 'react-draggable';
import '../../styles/Modal/call.scss';

function Call({ closeModal, selectedFriend }) {
  const [position, setPosition] = useState({ x: 500, y: 100 });

  const handleDrag = (e, ui) => {
    const { x, y } = position;
    const { deltaX, deltaY } = ui;

    // 調整速度限制
    const speedFactor = 0.2; // 調整此數值以改變速度
    const newPosition = {
      x: x + deltaX * speedFactor,
      y: y + deltaY * speedFactor,
    };

    setPosition(newPosition);
  };

  return (
    <>
      <Draggable handle='.userImg' onDrag={handleDrag}>
        <section
          className='userImg container-fluid'
          style={{
            '--bg-image': `url(${selectedFriend.profileAvatar})`,
            top: position.y,
            left: position.x,
          }}
        >
          <div className='modalContainer row p-3'>
            {/* <div className='col-12 d-flex p-0'>
              <button onClick={() => closeModal(false)} className='position-absolute top-0 end-0 me-4 mt-4 bg-transparent'>
                X
              </button>
            </div> */}
            <h4 className='col-12 text-light'>{selectedFriend.fullName}</h4>
            <div className='d-flex justify-content-around'>
              {/* <p>this is text zone</p> */}
              <button className='button1 rounded-circle'>
                <i className='fa-solid fa-microphone-slash text-light'></i>
              </button>
              <button className='button1 rounded-circle'>
                <i className='fa-solid fa-video text-light'></i>
              </button>
              <button className='button1 rounded-circle'>
                <i className='fa-solid fa-volume-high text-light'></i>
              </button>
            </div>
            <h6>通話中...</h6>
            <div>
              <button className='button2 rounded-circle' onClick={() => closeModal(false)}>
                <i className='fa-solid fa-phone-slash text-light'></i>
              </button>
            </div>
          </div>
        </section>
      </Draggable>
    </>
  );
}

Call.propTypes = {
  closeModal: PropTypes.func.isRequired,
  selectedFriend: PropTypes.object,
};

export default Call;
