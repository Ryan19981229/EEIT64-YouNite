import React from 'react';
import { Outlet } from 'react-router-dom';
import SideNav from '../component/SideNav';

function HomeLayout() {
  return (
    <>
      <SideNav />
      <main className='bg-page-gradient'>
        <Outlet />
      </main>
    </>
  );
}

export default HomeLayout;
