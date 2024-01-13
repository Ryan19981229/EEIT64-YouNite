import React, { useState, useEffect } from 'react';
import PropTypes from 'prop-types';
import Draggable from 'react-draggable';
import '../../styles/Modal/draggableModal.scss';

function DraggableModal({ closeModal }) {
  // 移動視窗 程式碼 START
  const [position, setPosition] = useState({ x: undefined, y: undefined });
  const [fadeIn, setFadeIn] = useState(false); // 追蹤是否需要淡入
  useEffect(() => {
    const screenWidth = window.innerWidth;
    const screenHeight = window.innerHeight;
    const modalWidth = 500; // 假設 modal 寬度為 500px
    const modalHeight = 300; // 假設 modal 高度為 300px

    const initialX = (screenWidth - modalWidth) / 2;
    const initialY = (screenHeight - modalHeight) / 2;

    setPosition({ x: initialX, y: initialY });
    setFadeIn(true); // 設置淡入為 true，觸發淡入效果
  }, []);

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
      {/* 宣告 Draggable handle 選擇抓取的物件範圍 */}
      <Draggable handle='.modal-bg' onDrag={handleDrag}>
        <section
          className={`modal-bg container-fluid p-0 ${fadeIn ? 'fade-in' : ''}`}
          style={{ top: position.y, left: position.x }}
        >
          <div className='row p-3'>
            <div className='col-12 d-flex justify-content-end'>
              <button
                type='button'
                className='btn-close'
                onClick={() => closeModal(false)}
              ></button>
            </div>
            {/* ↓↓↓ 下面可以隨意更改，區塊直接用 col 來寫  ↓↓↓*/}
            <h1 className='col-12'>Hi Wow</h1>
            <p>this is text zone</p>
            <div className='footer d-flex'>
              <button className='col-5'>確定</button>
              <button className='col-5 ' onClick={() => closeModal(false)}>
                取消
              </button>
            </div>
            {/* ↑↑↑ 上面可以隨意更改，區塊直接用 col 來寫 ↑↑↑ */}
          </div>
        </section>
      </Draggable>
    </>
  );
}

DraggableModal.propTypes = {
  closeModal: PropTypes.func.isRequired,
};

export default DraggableModal;
