import React, {useState, useEffect} from 'react'
import axios from 'axios'
import ReservationForm from './ReservationForm'
import {Link as RRLink} from 'react-router-dom'
import {Link} from '../../components'
import {useAuth} from '../../contexts'

interface Bus {
  id: number
  busNumber: string
  capacity: number
  route: string
  destination: string
}

const BusListByStationId: React.FC = () => {
  const [stationId, setStationId] = useState('')
  const [buses, setBuses] = useState<Bus[]>([])
  const [selectedBus, setSelectedBus] = useState<Bus | null>(null)
  const [destinationStations, setDestinationStations] = useState<string[]>([])

  useEffect(() => {
    if (stationId.trim() === '') return

    const apiUrl = `http://ws.bus.go.kr/api/rest/stationinfo/getRouteByStation?
    ServiceKey=au774mPDNO37gAJrlTNvjrymn07a%2Ff739RcICwnifiDnut1ekKDvSB8VpIbxYugjR0bPwIe1TM7uTzYk3yjsiw%3D%3D&arsId=${stationId}` // api 주소 추가하기

    axios
      .get<Bus[]>(apiUrl)
      .then(response => {
        setBuses(response.data)
        const destinationStations = Array.from(
          new Set(response.data.map(bus => bus.destination))
        )
        setDestinationStations(destinationStations)
      })
      .catch(error => {
        console.error('옳지않은 정류장 ID입니다.:', error)
      })
  }, [stationId])

  const handleBusSelect = (bus: Bus) => {
    setSelectedBus(bus)
  }

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
              <h1 className="mb-8 text-4xl text-center text-lime-500">예약 하기</h1>
              <h1>출발 정류장 선택</h1>
              <input
                type="text"
                value={stationId}
                onChange={e => setStationId(e.target.value)}
                placeholder="출발 정류장 ID를 입력하세요"
              />

              {stationId.trim() !== '' && (
                <>
                  <h2>정류장 {stationId} 도착 버스 목록</h2>
                  <ul>
                    {buses.map(bus => (
                      <li key={bus.id}>
                        <span>{bus.busNumber}</span>
                        <button onClick={() => handleBusSelect(bus)}>예약하기</button>
                      </li>
                    ))}
                  </ul>
                </>
              )}
              {selectedBus && (
                <div>
                  <h2>선택한 버스 노선 정보</h2>
                  <p>노선 이름: {selectedBus.route}</p>
                  <p>도착정류장: {selectedBus.destination}</p>
                  <ReservationForm
                    bus={selectedBus}
                    destinationStations={destinationStations}
                    startingStation={`정류장 ${stationId}`}
                    onReservationSuccess={function (): void {
                      throw new Error('문제 발생')
                    }}
                  />
                </div>
              )}
            </div>
            <Link to="/" className="btn btn-link text-lime-500">
              메인 페이지로 이동하기
            </Link>
          </div>
        </div>
      </div>
    </div>
  )
}

export default BusListByStationId
