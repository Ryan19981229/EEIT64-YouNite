import axios from 'axios';

function getCookie(name) {
  const value = '; ' + document.cookie;
  const parts = value.split('; ' + name + '=');
  if (parts.length === 2) {
    return parts.pop().split(';').shift();
  }
}

function setToken() {
  const token = getCookie('token');
  axios.defaults.headers.common['Authorization'] = token;
}

export default setToken;
