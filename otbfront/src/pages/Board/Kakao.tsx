import React, { useEffect } from 'react';

export {};

function Kakao(): JSX.Element {
  useEffect(() => {
    const container = document.getElementById('map');
    const options = {
      center: new (window as any).kakao.maps.LatLng(33.45701, 126.570667),
      level: 3,
    };
    const map = new (window as any).kakao.maps.Map(container, options);
  }, []);

  return (
    <div id="map" style={{
      width: '500px',
      height: '500px'
    }}></div>
  );
}

export default Kakao;