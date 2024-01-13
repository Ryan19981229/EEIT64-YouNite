import React, { useState, useEffect, useRef } from 'react';
import { Modal } from 'react-bootstrap';
import { Map, GoogleApiWrapper, Marker } from 'google-maps-react';
import PropTypes from 'prop-types';

const LocationModal = (props) => {
  const [selectedLocation, setSelectedLocation] = useState(null);
  const [searchValue, setSearchValue] = useState('');

  const mapRef = useRef(null);

  const handleMapClick = (mapProps, map, clickEvent) => {
    const { latLng } = clickEvent;
    setSelectedLocation({
      lat: latLng.lat(),
      lng: latLng.lng()
    });
  };

  const handleModalOk = () => {
    console.log('選擇的位置:', selectedLocation);
    props.onOk(selectedLocation);
  };

  const handleSearchChange = (event) => {
    setSearchValue(event.target.value);
  };

  const handleSearchSubmit = (event) => {
    event.preventDefault();
    if (searchValue.trim() !== '') {
      searchLocation();
    }
  };

  const searchLocation = () => {
    const geocoder = new props.google.maps.Geocoder();
    geocoder.geocode({ address: searchValue }, (results, status) => {
      if (status === props.google.maps.GeocoderStatus.OK) {
        const { lat, lng } = results[0].geometry.location;
        const newLocation = {
          lat: lat(),
          lng: lng()
        };
        setSelectedLocation(newLocation);
        if (mapRef.current) {
          mapRef.current.map.panTo(newLocation);
        }
      } else {
        console.log('無法找到該地點');
      }
    });
  };

  useEffect(() => {
    if (navigator.geolocation) {
      navigator.geolocation.getCurrentPosition(updateLocation);
    }
  }, []);

  const updateLocation = (position) => {
    setSelectedLocation({
      lat: position.coords.latitude,
      lng: position.coords.longitude
    });
    if (mapRef.current) {
      mapRef.current.map.panTo({
        lat: position.coords.latitude,
        lng: position.coords.longitude
      });
    }
  };

  return (
    <Modal show={props.visible} onHide={props.onCancel} className='w-30'>
      <Modal.Header closeButton>
        <Modal.Title>選擇位置</Modal.Title>
      </Modal.Header>
      <Modal.Body>
        <div style={{ height: '60vh', marginBottom: '20px' }} className='ms-4'>
          <form onSubmit={handleSearchSubmit}>
            <div className="input-group mb-3">
              <input
                type="text"
                className="form-control"
                placeholder="搜尋地點"
                value={searchValue}
                onChange={handleSearchChange}
              />
              <button className="btn" type="submit" style={{backgroundColor: '#E7ADAC'}}>
                搜尋
              </button>
            </div>
          </form>
          <div style={{ position: 'relative', zIndex: 0 }}>
          <Map
            google={props.google}
            onClick={handleMapClick}
            ref={mapRef}
            initialCenter={selectedLocation || {
              lat: 25.0329694, // 初始中心點緯度
              lng: 121.5654177 // 初始中心點經度
            }}
            zoom={14}
            style={{ width: '28vw', height: '52vh' }}
          >
            {selectedLocation && (
              <Marker position={selectedLocation} />
            )}
          </Map>
          </div>
        </div>
      </Modal.Body>
      <Modal.Footer>
        <button className="btn btn-secondary" onClick={props.onCancel}>
          取消
        </button>
        <button className="btn" onClick={handleModalOk} style={{backgroundColor: '#E7ADAC'}}>
          確定
        </button>
      </Modal.Footer>
    </Modal>
  );
};

LocationModal.propTypes = {
  visible: PropTypes.bool.isRequired,
  onCancel: PropTypes.func.isRequired,
  onOk: PropTypes.func.isRequired,
  google: PropTypes.object // 或者根據需要的屬性進行精確的驗證
};

export default GoogleApiWrapper({
  apiKey: process.env.REACT_APP_GOOLGE_MAP,
})(LocationModal); 
