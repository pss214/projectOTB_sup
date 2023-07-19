import React, { useEffect, useState } from 'react';

function Kakao(): JSX.Element {
  const [map, setMap] = useState<any>(null);

  useEffect(() => {
    const container = document.getElementById('map');
    const options = {
      center: new (window as any).kakao.maps.LatLng(37.5665, 126.9780), // 초기 위치 설정 (서울)
      level: 3,
    };
    const map = new (window as any).kakao.maps.Map(container, options);
    setMap(map);
  }, []);

  // 내 위치로 이동하는 함수
  const moveToMyLocation = () => {
    navigator.geolocation.getCurrentPosition(
      (position) => {
        const lat = position.coords.latitude;
        const lng = position.coords.longitude;
        const moveLatLng = new (window as any).kakao.maps.LatLng(lat, lng);
        map.setCenter(moveLatLng); // 지도의 중심을 실시간 위치로 이동
      },
      (error) => {
        console.error('Error getting geolocation:', error);
      }
    );
  };


  return (
    <div>
    <div
      id="map"
      style={{ width: '500px', height: '500px' }}
    ></div>
    <button
      style={{
        backgroundColor: '#cddc39', // lime-500
        color: '#fff',
        borderRadius: '5px',
        padding: '10px 20px',
        fontSize: '16px',
        border: 'none',
        cursor: 'pointer',
      }}
      onClick={moveToMyLocation}
    >
      내 위치로 이동
    </button>
  </div>
  );
}

export default Kakao;