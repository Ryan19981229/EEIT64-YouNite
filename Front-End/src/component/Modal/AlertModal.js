import React from 'react';
import PropTypes from 'prop-types';
import '../../styles/Modal/alertModal.scss';

const AlertModal = ({ state, message, showModal }) => {
  let icon = '';
  console.log(state);
  if (state === 201 || state === 200) {
    icon = (
      <div className='ui ui-success'>
        <svg viewBox='0 0 87 87' version='1.1'>
          <g id='Page-1' stroke='none' strokeWidth='1' fill='none' fillRule='evenodd'>
            <g id='Group-3' transform='translate(2.000000, 2.000000)'>
              <circle
                id='Oval-2'
                stroke='rgba(165, 220, 134, 0.2)'
                strokeWidth='4'
                cx='41.5'
                cy='41.5'
                r='41.5'
              ></circle>
              <circle
                className='ui-success-circle'
                id='Oval-2'
                stroke='#A5DC86'
                strokeWidth='4'
                cx='41.5'
                cy='41.5'
                r='41.5'
              ></circle>
              <polyline
                className='ui-success-path'
                id='Path-2'
                stroke='#A5DC86'
                strokeWidth='4'
                points='19 38.8036813 31.1020744 54.8046875 63.299221 28'
              ></polyline>
            </g>
          </g>
        </svg>
      </div>
    );
  } else if (state === 409 || state === 404 || state === 401) {
    icon = (
      <div className='ui ui-error'>
        <svg viewBox='0 0 87 87' version='1.1'>
          <g id='Page-1' stroke='none' strokeWidth='1' fill='none' fillRule='evenodd'>
            <g id='Group-2' transform='translate(2.000000, 2.000000)'>
              <circle
                id='Oval-2'
                stroke='rgba(252, 191, 191, .5)'
                strokeWidth='4'
                cx='41.5'
                cy='41.5'
                r='41.5'
              ></circle>
              <circle
                className='ui-error-circle'
                stroke='#F74444'
                strokeWidth='4'
                cx='41.5'
                cy='41.5'
                r='41.5'
              ></circle>
              <path
                className='ui-error-line1'
                d='M22.244224,22 L60.4279902,60.1837662'
                id='Line'
                stroke='#F74444'
                strokeWidth='3'
                strokeLinecap='square'
              ></path>
              <path
                className='ui-error-line2'
                d='M60.755776,21 L23.244224,59.8443492'
                id='Line'
                stroke='#F74444'
                strokeWidth='3'
                strokeLinecap='square'
              ></path>
            </g>
          </g>
        </svg>
      </div>
    );
  }

  return (
    <>
      {showModal && (
        <div
          className='alertModal modal fade show'
          tabIndex='-1'
          role='dialog'
          style={{ display: 'block' }}
        >
          <div className='modal-dialog'>
            <div className='modal-content'>
              <div className='modal-body'>
                {icon}
                <h5 className='ms-3 mt-2 '>{message}</h5>
              </div>
            </div>
          </div>
        </div>
      )}
    </>
  );
};

AlertModal.propTypes = {
  state: PropTypes.number.isRequired,
  message: PropTypes.string.isRequired,
  showModal: PropTypes.bool.isRequired,
};

export default AlertModal;
