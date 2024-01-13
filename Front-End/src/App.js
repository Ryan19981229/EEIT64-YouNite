import React from 'react';
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import LandingPage from './pages/LandingPage';
import Register from './pages/Register';
import Login from './pages/Login';
import PersonalInfo from './pages/PersonalInfo';
import HomeLayout from './component/HomeLayout';
import Home from './pages/Home';
import SettingPersonal from './pages/SettingPersonal';
import ChatRoom from './pages/ChatRoom';
import Store from './pages/Store';
import ShowMore from './component/ShowMore';
import LikeShowMore from './pages/LikeShowMore';
import InterestsShowMore from './pages/InterestsShowMore';
import ProfessionShowMore from './pages/ProfessionShowMore';
import WhoLikeShowMore from './pages/WhoLikeShowMore';
import ForgetPassword from './component/ForgetPasword';

function App() {
  return (
    <>
      <BrowserRouter>
        <Routes>
          <Route path='/' element={<LandingPage />}></Route>
          <Route path='/register' element={<Register />}></Route>
          <Route path='/login' element={<Login />}></Route>
          <Route path='/personal' element={<PersonalInfo />}></Route>
          <Route path='/chatroom' element={<ChatRoom />}></Route>
          <Route path='/forget' element={<ForgetPassword />}></Route>
          <Route path='/' element={<HomeLayout />}>
            <Route path='/home' element={<Home />}></Route>
            <Route path='/show' element={<ShowMore />}>
              <Route path='/show/like' element={<LikeShowMore />}></Route>
              <Route path='/show/interest' element={<InterestsShowMore />}></Route>
              <Route path='/show/profession' element={<ProfessionShowMore />}></Route>
              <Route path='/show/wholike' element={<WhoLikeShowMore />}></Route>
            </Route>
            <Route path='/setting' element={<SettingPersonal />}></Route>
            <Route path='/store' element={<Store />}></Route>
          </Route>
        </Routes>
      </BrowserRouter>
    </>
  );
}

export default App;
