import {Link} from '../../components'
import Kakao from '../Board/Kakao'
import React, {useState, useEffect} from 'react'
import axios from 'axios'

// 버스 도착 정보를 담을 타입 정의
interface BusArrival {
  busNumber: string
  arrivalTime: string
}

const Reserve: React.FC = () => {
  const [busArrivals, setBusArrivals] = useState<BusArrival[]>([])

  useEffect(() => {
    // 외부 API의 엔드포인트 URL //
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
      <div className="flex justify-between bg-lime-200">
        <div className="flex p-2 ">
          <Link to="/" className="ml-1">
            <img
              src="/img/otblogogogo.png"
              alt="OTB(우비) 로고"
              className="w-12.5 h-12.5 bg-lime-200"
            />
          </Link>
        </div>
      </div>
      <div className="flex flex-col min-h-screen border-gray-300 rounded-xl shadow-xl bg-gray-100 border">
        <div className="flex flex-col items-center  flex-1 max-w-sm px-2 mx-auto">
          <div className="w-full px-6 py-8 text-black bg-white rounded shadow-md">
            <div>
              <h1 className="mb-8 text-4xl text-center text-black">예약 페이지</h1>
              <ul>
                {busArrivals.map((bus, index) => (
                  <li key={index}>
                    <strong>버스 번호:</strong> {bus.busNumber},{' '}
                    <strong>도착 예정 시간:</strong> {bus.arrivalTime}
                  </li>
                ))}
              </ul>
              <button className="ml-4 mr-4 btn btn-primary text-white  border-lime-600 bg-lime-600">
                예약 하기
              </button>
            </div>
            {/*값 기재 하기*/}
            <Link to="/" className="btn btn-link text-lime-500">
              메인 페이지로 이동하기
            </Link>
          </div>
        </div>
      </div>
    </div>
  )
}
export default Reserve
