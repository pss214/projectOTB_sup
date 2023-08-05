import React, {useEffect, useState} from 'react'
import {Link} from 'react-router-dom'
import BUS_STOP from '../../busStop.' // busStop.ts 파일에서 BUS_STOP 데이터 가져옴

function Kakao(): JSX.Element {
  const [map, setMap] = useState<any>(null)
  const [markers, setMarkers] = useState<any[]>([]) // 마커들을 배열로 관리
  const [currentLocationMarker, setCurrentLocationMarker] = useState<any>(null) // 내 위치 마커
  const [showTrafficOverlay, setShowTrafficOverlay] = useState<boolean>(false) // 교통정보 보기/숨기기 상태

  const toggleTrafficOverlay = () => {
    // 교통정보를 토글하는 로직을 작성합니다.
    // 지도에 교통정보 오버레이를 추가 또는 제거하여 보이거나 숨기는 기능을 구현합니다.
    if (map) {
      if (showTrafficOverlay) {
        map.removeOverlayMapTypeId((window as any).kakao.maps.MapTypeId.TRAFFIC)
      } else {
        map.addOverlayMapTypeId((window as any).kakao.maps.MapTypeId.TRAFFIC)
      }
      setShowTrafficOverlay(!showTrafficOverlay)
    }
  }

  useEffect(() => {
    const container = document.getElementById('map')
    const options = {
      center: new (window as any).kakao.maps.LatLng(37.5665, 126.978), // 초기 위치 설정 (서울)
      level: 3
    }
    const map = new (window as any).kakao.maps.Map(container, options)
    setMap(map)

    // 마커 생성
    const getUserPosition = () => {
      return new Promise<GeolocationPosition>((resolve, reject) => {
        navigator.geolocation.getCurrentPosition(resolve, reject)
      })
    }

    // 두 지점 사이의 거리를 계산하는 함수
    const calculateDistance = (
      lat1: number,
      lng1: number,
      lat2: number,
      lng2: number
    ) => {
      const radlat1 = (Math.PI * lat1) / 180 // 위도를 라디안 단위로 변환
      const radlat2 = (Math.PI * lat2) / 180 // 위도를 라디안 단위로 변환
      const theta = lng1 - lng2 // 두 경도의 차이를 계산
      const radtheta = (Math.PI * theta) / 180 // 경도를 라디안 단위로 변환

      // 'Haversine formula'를 이용해 두 지점의 직선 거리를 계산
      let dist =
        Math.sin(radlat1) * Math.sin(radlat2) +
        Math.cos(radlat1) * Math.cos(radlat2) * Math.cos(radtheta)
      dist = Math.acos(dist) // 코사인 역함수인 아크코사인(acos)을 적용하여 라디안 단위의 각도를 구함
      dist = (dist * 180) / Math.PI // 라디안 단위의 각도를 도(degree)로 변환
      dist = dist * 60 * 1.1515 * 1609.344 // 마일을 미터로 변환 (1마일 = 1609.344미터)

      return dist // 두 지점 사이의 거리를 반환 (미터 단위)
    }

    const loadMarkers = async () => {
      try {
        // 사용자의 현재 위치를 얻어옴
        const userPosition = await getUserPosition()
        const userLat = userPosition.coords.latitude
        const userLng = userPosition.coords.longitude

        // 사용자 위치에서 200m 이내의 버스 정류장만 필터링
        const filteredMarkers = BUS_STOP.filter(busStop => {
          const distance = calculateDistance(userLat, userLng, busStop.lat, busStop.lng)
          return distance <= 500
        })

        // 필터링된 버스 정류장에 대한 마커 생성
        const tempMarkers = filteredMarkers.map(busStop => {
          const marker = new (window as any).kakao.maps.Marker({
            position: new (window as any).kakao.maps.LatLng(busStop.lat, busStop.lng),
            map
          })

          // 마커 클릭 이벤트 리스너 등록
          ;(window as any).kakao.maps.event.addListener(marker, 'click', () => {
            // 클릭된 마커의 정보를 쿼리 파라미터로 전달하여 다음 페이지로 이동
            const path = '/reserve' // 다음 페이지 경로
            const queryParams = `lat=${busStop.lat}&lng=${busStop.lng}&id=${busStop.id}&place=${busStop.place}`
            window.location.href = `${path}?${queryParams}`
          })

          return marker
        })
        setMarkers(tempMarkers)
      } catch (error) {
        console.error('사용자 위치 가져오기 오류:', error)
      }
    }

    // 지도에 교통정보 오버레이를 추가하는 함수
    const addTrafficOverlay = () => {
      map.addOverlayMapTypeId((window as any).kakao.maps.MapTypeId.TRAFFIC)
      setShowTrafficOverlay(true)
    }

    // 지도에 교통정보 오버레이를 제거하는 함수
    const removeTrafficOverlay = () => {
      map.removeOverlayMapTypeId((window as any).kakao.maps.MapTypeId.TRAFFIC)
      setShowTrafficOverlay(false)
    }

    if (showTrafficOverlay) {
      addTrafficOverlay()
    } else {
      removeTrafficOverlay()
    }

    loadMarkers()

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
          offset: new (window as any).kakao.maps.Point(12, 35)
        }
      )
    })
    setCurrentLocationMarker(currentLocationMarker)

    // 지도 확대 축소를 제어할 수 있는 줌 컨트롤 생성
    const zoomControl = new (window as any).kakao.maps.ZoomControl()
    map.addControl(zoomControl, (window as any).kakao.maps.ControlPosition.RIGHT)

    // 지도가 확대 또는 축소되면 마지막 파라미터로 넘어온 함수를 호출하는 이벤트 등록
    ;(window as any).kakao.maps.event.addListener(map, 'zoom_changed', function () {
      // 지도의 현재 레벨을 얻어옵니다
      const level = map.getLevel()

      // 결과를 HTML 요소에 출력
      const resultDiv = document.getElementById('result')
      if (resultDiv) {
        resultDiv.innerHTML = `현재 지도 레벨은 ${level} 입니다`
      }
    })
  }, [])

  // 내 위치로 이동하는 함수
  const moveToMyLocation = () => {
    // Geolocation 사용
    if ('geolocation' in navigator) {
      navigator.geolocation.getCurrentPosition(
        (position: GeolocationPosition) => {
          const lat = position.coords.latitude
          const lng = position.coords.longitude
          const moveLatLng = new (window as any).kakao.maps.LatLng(lat, lng)
          map.setCenter(moveLatLng) // 지도의 중심을 실시간 위치로 이동
          currentLocationMarker.setPosition(moveLatLng) // 내 위치 마커 위치 업데이트
        },
        error => {
          console.error('위치 정보 오류', error)
        }
      )
    } else {
      console.error('위치 정보를 받아오지 못했습니다.')
    }
  }

  return (
    <div>
      <div id="map" style={{width: '500px', height: '500px'}}></div>
      <button
        style={{
          backgroundColor: '#cddc39', // lime-500
          color: '#fff',
          borderRadius: '5px',
          padding: '10px 20px',
          fontSize: '16px',
          border: 'none',
          cursor: 'pointer',
          marginRight: '10px' // 오른쪽 여백 추가
        }}
        onClick={moveToMyLocation}>
        내 위치로 이동
      </button>
      <button
        style={{
          backgroundColor: '#cddc39', // lime-500
          color: '#fff',
          borderRadius: '5px',
          padding: '10px 20px',
          fontSize: '16px',
          border: 'none',
          cursor: 'pointer'
        }}
        onClick={toggleTrafficOverlay}>
        {showTrafficOverlay ? '교통정보 숨기기' : '교통정보 표시하기'}
      </button>
      {/* 지도 레벨을 출력할 HTML 요소 */}
      <div id="result"></div>
      <Link to="/reserve"></Link>
    </div>
  )
}

export default Kakao
