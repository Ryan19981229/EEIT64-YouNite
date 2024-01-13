import PropTypes from 'prop-types';
import React from 'react';

export const InfoInput = ({ id, labelText, placeholder, register, type, errors, rules, value, onChange }) => {
    const errorMessages = {
      fullName: '請輸入有效的姓名',
      phone: '電話必須是 10 位數字',
      birthday: '請輸入有效的生日 (yyyy-mm-dd)',
    };
  
    return (
      <>
        <label htmlFor={id} className='form-label'>
          {labelText}
        </label>
        <input
          id={id}
          type={type}
          placeholder={placeholder}
          className={`form-control ${errors[id] && 'is-invalid'}`}
          {...register(id, rules)}
          value={value}
          onChange={onChange}
          style={{
            '--placeholder-color': 'rgba(0, 0, 0, 0.2)',
            boxShadow: '0px 4px 4px rgba(0, 0, 0, 0.08)',
          }}
        />
        {errors[id] && <div className='invalid-feedback'>{errorMessages[id]}</div>}
      </>
    );
  };
  
  InfoInput.propTypes = {
    id: PropTypes.string.isRequired,
    labelText: PropTypes.string.isRequired,
    register: PropTypes.func.isRequired,
    type: PropTypes.string.isRequired,
    errors: PropTypes.object.isRequired,
    rules: PropTypes.object.isRequired,
    placeholder: PropTypes.string,
    value: PropTypes.string,
    onChange: PropTypes.func,
  };
  
  export const RadioButtonGroups = ({ name, options, register, errors, rules, checked, onChange }) => {
    return (
      <div>
        <div className='btn-group' role='group' aria-label='Basic radio toggle button group'>
          {options.map((option, index) => (
            <React.Fragment key={index}>
              <input
                type='radio'
                id={`${name}${index}`}
                className={`btn-check ${errors[name] && 'is-invalid'}`}
                {...register(name, rules)}
                value={option.value}
                checked={checked === option.value} 
                onChange={onChange}
              />
              <label
                htmlFor={`${name}${index}`}
                className={`btn btn-outline-radio mt-2 me-4 px-4 rounded shadow-sm bg-white ${
                  errors[name] && 'is-invalid'
                }`}
                style={{zIndex: '0'}}
              >
                {option.label}
              </label>
            </React.Fragment>
          ))}
        </div>
        {errors[name] && (
          <div className='d-block invalid-feedback' style={{ marginTop: '10px' }}>
            {errors[name]?.message}
          </div>
        )}
      </div>
    );
  };
  
  RadioButtonGroups.propTypes = {
    name: PropTypes.string.isRequired,
    options: PropTypes.arrayOf(
      PropTypes.shape({
        value: PropTypes.string.isRequired,
        label: PropTypes.string.isRequired,
      }),
    ).isRequired,
    register: PropTypes.func.isRequired,
    errors: PropTypes.object.isRequired,
    rules: PropTypes.object.isRequired,
    checked: PropTypes.string,
    onChange: PropTypes.func,
  };
  