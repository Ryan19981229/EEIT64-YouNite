import React from 'react'
import '../styles/component/bdGradient.scss';
import '../styles/all.scss';
import { Outlet } from 'react-router-dom';

const ShowMore = () => {
  return (
    <section className='container'>
        <section className='bg-pageMain row d-flex'>
            {/* <div className='bg-pageTitle d-flex'> */}
              <Outlet />
            {/* </div> */}
        </section>
    </section>
  )
}

export default ShowMore
