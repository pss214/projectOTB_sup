import React, {useState, useEffect} from 'react'
import axios from 'axios'

// 버스 도착 정보를 담을 타입 정의
interface BusArrival {
  busNumber: string
  arrivalTime: string
}

const BusArrivalInfo: React.FC = () => {
  const [busArrivals, setBusArrivals] = useState<BusArrival[]>([])

  useEffect(() => {
    // 외부 API의 엔드포인트 URL
    const apiUrl = 'https://api.example.com/bus-arrivals'

    // 외부 API를 호출하여 버스 도착 정보를 가져옵니다.
    axios
      .get<BusArrival[]>(apiUrl) // API 응답의 타입을 지정
      .then(response => {
        // API 호출이 성공하면 결과를 상태에 저장
        setBusArrivals(response.data)
      })
      .catch(error => {
        // API 호출이 실패한 경우 에러 처리
        console.error('Error fetching bus arrivals:', error)
      })
  }, [])

  return (
    <div>
      <h1>버스 도착 정보</h1>
      <ul>
        {busArrivals.map((bus, index) => (
          <li key={index}>
            <strong>버스 번호:</strong> {bus.busNumber}, <strong>도착 예정 시간:</strong>{' '}
            {bus.arrivalTime}
          </li>
        ))}
      </ul>
    </div>
  )
}

export default BusArrivalInfo
