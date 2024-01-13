import React from 'react';
import { Carousel } from 'react-bootstrap';
import storeImage from '../../assets/images/storeCarousels.png';
import storeImage1 from '../../assets/images/storeCarousels1.png';
import storeImage2 from '../../assets/images/storeCarousels2.png';

function StoreCarousels() {
  return (
    <Carousel>
      <Carousel.Item>
        <img className='d-block ' src={storeImage} alt='Image 1' />
        <Carousel.Caption>
          <h3>Image 1</h3>
        </Carousel.Caption>
      </Carousel.Item>
      <Carousel.Item>
        <img
          className='d-block '
          src={storeImage1}
          alt='Image 2'
          style={{ objectFit: 'contain' }}
        />
        <Carousel.Caption>
          <h3>Image 2</h3>
        </Carousel.Caption>
      </Carousel.Item>
      <Carousel.Item>
        <img
          className='d-block '
          src={storeImage2}
          alt='Image 3'
          style={{ objectFit: 'contain' }}
        />
        <Carousel.Caption>
          <h3>Image 3</h3>
        </Carousel.Caption>
      </Carousel.Item>
    </Carousel>
  );
}

export default StoreCarousels;
