import PropTypes from 'prop-types';
import React from 'react';

export const CheckboxRadio = ({ id, labelText, register, type, errors, rules, value, name }) => {
  return (
    <>
      <div className='form-check'>
        <input
          className={`form-check-input ${errors[name] && 'is-invalid'}`}
          type={type}
          name={name}
          id={id}
          value={value}
          {...register(name, rules)}
        />
        {/* Radio 使用 Name 欄位 */}
        <label className='form-check-label' htmlFor={id}>
          {labelText}
        </label>
        {errors[name] && <div className='invalid-feedback'>{errors[name]?.message}</div>}
      </div>
    </>
  );
};

CheckboxRadio.propTypes = {
  id: PropTypes.string.isRequired,
  labelText: PropTypes.string.isRequired,
  register: PropTypes.func.isRequired,
  type: PropTypes.string.isRequired,
  errors: PropTypes.object.isRequired,
  rules: PropTypes.object.isRequired,
  value: PropTypes.string,
  name: PropTypes.string.isRequired,
};

export const Input = ({ id, labelText, placeholder, register, type, errors, rules }) => {
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
        style={{
          '--placeholder-color': 'rgba(0, 0, 0, 0.2)',
          boxShadow: '0px 4px 4px rgba(0, 0, 0, 0.08)',
        }}
      />
      {errors[id] && <div className='invalid-feedback'>{errors[id]?.message}</div>}
    </>
  );
};

Input.propTypes = {
  id: PropTypes.string.isRequired,
  labelText: PropTypes.string.isRequired,
  register: PropTypes.func.isRequired,
  type: PropTypes.string.isRequired,
  errors: PropTypes.object.isRequired,
  rules: PropTypes.object.isRequired,
  placeholder: PropTypes.string,
};

export const RadioButtonGroup = ({ name, options, register, errors, rules }) => {
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

RadioButtonGroup.propTypes = {
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
};
