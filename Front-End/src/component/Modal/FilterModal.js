import React, { useState, useEffect } from 'react';
import PropTypes from 'prop-types';
import Draggable from 'react-draggable';
import '../../styles/Modal/filterModal.scss';

function FilterModal({ closeModal, handleFilter }) {
  // 移動視窗 程式碼 START
  const [position, setPosition] = useState({ x: undefined, y: undefined });
  const [fadeIn, setFadeIn] = useState(false); // 追蹤是否需要淡入
  useEffect(() => {
    const screenWidth = window.innerWidth;
    const screenHeight = window.innerHeight;
    const modalWidth = 400; // 假設 modal 寬度為 500px
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

  //控制距離
  const [rangeValDistance, setRangeValDistance] = useState(0); // 距離的 state 變數
  const [rangeValAge, setRangeValAge] = useState(0); // 年齡的 state 變數

  const updateRangeDistance = (e) => {
    setRangeValDistance(e.target.value);
  };

  const updateRangeAge = (e) => {
    setRangeValAge(e.target.value);
  };

  const handleAgeInputChange = (e) => {
    const value = parseInt(e.target.value, 10);
    setRangeValAge(value);
  };

  const handleApplyFilter = () => {
    closeModal();
    handleFilter(rangeValDistance, rangeValAge); // 呼叫父元件傳遞的函數，並傳遞選擇的值
  };

  return (
    <>
      {/* 宣告 Draggable handle 選擇抓取的物件範圍 */}
      <Draggable handle='.filter-modal-bg' cancel='input' onDrag={handleDrag}>
        <section
          className={`filter-modal-bg container-fluid p-0 ${fadeIn ? 'fade-in' : ''}`}
          style={{ top: position.y, left: position.x }}
        >
          <div className='row p-3 justify-content-center '>
            <div className='filter-container'>
              <div className='col-12 d-flex justify-content-end'>
                <button
                  type='button'
                  className='btn-close'
                  onClick={() => closeModal(false)}
                ></button>
              </div>
              {/* ↓↓↓ 下面可以隨意更改，區塊直接用 col 來寫  ↓↓↓*/}
              <div className='col d-flex align-items-center'>
                <label htmlFor='customRange1' className='form-label mb-0'>
                  距離
                </label>
                <span id='output' className='bg-lightblue text-primary rounded px-1 mx-3'>
                  <input
                    type='number'
                    className='form-control text-primary'
                    value={rangeValDistance}
                    min='0'
                    max='100'
                    step='10'
                    onChange={updateRangeDistance}
                  />
                </span>
                公里
              </div>
              <input
                type='range'
                className='form-range mt-3'
                id='customRange1'
                value={rangeValDistance}
                min='0'
                max='999'
                step='0'
                onChange={updateRangeDistance}
              />
              <hr className='mx-1' />
              <div className='col d-flex align-items-center'>
                <label htmlFor='customRange2' className='form-label mb-0'>
                  年齡
                </label>
                <span id='output' className='bg-lightblue text-primary rounded px-1 mx-3'>
                  <input
                    type='number'
                    className='form-control text-primary'
                    value={rangeValAge}
                    min='0'
                    max='100'
                    step='1'
                    onChange={handleAgeInputChange}
                  />
                </span>
                歲
              </div>
              <input
                type='range'
                className='form-range mt-3'
                id='customRange2'
                value={rangeValAge}
                min='0'
                max='100'
                step='1'
                onChange={updateRangeAge}
              />

              {/* ↑↑↑ 上面可以隨意更改，區塊直接用 col 來寫 ↑↑↑ */}
            </div>
            <button type='button' className='btn btn-primary btn-sm text-white' onClick={handleApplyFilter}>
              提交
            </button>
          </div>
        </section>
      </Draggable>
    </>
  );
}

FilterModal.propTypes = {
  closeModal: PropTypes.func.isRequired,
  handleFilter: PropTypes.func.isRequired,
};

export default FilterModal;
