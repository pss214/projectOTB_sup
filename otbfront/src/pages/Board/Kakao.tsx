import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import BUS_STOP from '../../busStop.'; // busStop.ts 파일에서 BUS_STOP 데이터 가져옴

function Kakao(): JSX.Element {
  const [map, setMap] = useState<any>(null);
  const [markers, setMarkers] = useState<any[]>([]); // 마커들을 배열로 관리
  const [currentLocationMarker, setCurrentLocationMarker] = useState<any>(null); // 내 위치 마커

  useEffect(() => {
    const container = document.getElementById('map');
    const options = {
      center: new (window as any).kakao.maps.LatLng(37.5665, 126.9780), // 초기 위치 설정 (서울)
      level: 3,
    };
    const map = new (window as any).kakao.maps.Map(container, options);
    setMap(map);

    // 마커 생성
    const tempMarkers = BUS_STOP.map((busStop) => {
      const marker = new (window as any).kakao.maps.Marker({
        position: new (window as any).kakao.maps.LatLng(busStop.lat, busStop.lng),
        map,
      });
      // 마커 클릭 이벤트 리스너 등록
      (window as any).kakao.maps.event.addListener(marker, 'click', () => {
        // 마커 클릭 시 이동할 경로 설정
        const path = "/BusReserve"; // 다른 컴포넌트로 이동할 경로를 입력하세요.
        window.location.pathname = path;
      });
      return marker;
    });
    setMarkers(tempMarkers);

    // 내 위치 마커 생성
    const currentLocationMarker = new (window as any).kakao.maps.Marker({
      position: map.getCenter(),
      map,
      title: '내 위치', // 레이블 추가
      zIndex: 9999, // 다른 마커들 보다 항상 위에 표시
      image: new (window as any).kakao.maps.MarkerImage(
        'http://t1.daumcdn.net/localimg/localimages/07/mapapidoc/markerStar.png',
        new (window as any).kakao.maps.Size(24, 35),
        {
          offset: new (window as any).kakao.maps.Point(12, 35),
        }
      ),
    });
    setCurrentLocationMarker(currentLocationMarker);
  }, []);

  // 내 위치로 이동하는 함수
  const moveToMyLocation = () => {
    // Geolocation 사용
    if ('geolocation' in navigator) {
      navigator.geolocation.getCurrentPosition(
        (position) => {
          const lat = position.coords.latitude;
          const lng = position.coords.longitude;
          const moveLatLng = new (window as any).kakao.maps.LatLng(lat, lng);
          map.setCenter(moveLatLng); // 지도의 중심을 실시간 위치로 이동
          currentLocationMarker.setPosition(moveLatLng); // 내 위치 마커 위치 업데이트
        },
        (error) => {
          console.error('위치 정보 오류', error);
        }
      );
    } else {
      console.error('위치 정보를 받아오지 못했습니다.');
    }
  };

  return (
    <div>
      <div id="map" style={{ width: '500px', height: '500px' }}></div>
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
      <Link to="/BusReserve"></Link>
    </div>
  );
}

export default Kakao;