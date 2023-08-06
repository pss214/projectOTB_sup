import React, {useState} from 'react'
import {Link} from '../../components'
import {useAuth} from '../../contexts'
import BUS_STOP from '../../busStop.' // BusStop 타입과 busStops 데이터를 가져옴
import {useLocation} from 'react-router-dom'
import * as U from '../../utils'
import {SERVER_URL} from '../../server'
import axios from 'axios'

interface Bus {
  id: number
  busNumber: string
  capacity: number
  route: string
  destination: string
}

interface BusStop {
  id: number
  place: string
  lat: number
  lng: number
}

interface ReservationFormProps {
  buses: Bus[]
  destinationStations: string[]
  startingStation: string
  onReservationSuccess: () => void
}

const Reserve: React.FC<ReservationFormProps> = ({
  buses,
  destinationStations,
  startingStation,
  onReservationSuccess
}) => {
  const [jwt, setJwt] = useState<string>('')
  const [name, setName] = useState('')
  const [seats, setSeats] = useState(1)
  const [selectedBus, setSelectedBus] = useState<Bus | null>(null)
  const [selectedDestination, setSelectedDestination] = useState('')

  const location = useLocation()
  const selectedMarker = location.state
    ? (location.state as {selectedMarker: BusStop}).selectedMarker
    : null

  const handleNameChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setName(e.target.value)
  }

  const handleSeatsChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setSeats(Number(e.target.value))
  }

  const handleDestinationChange = (e: React.ChangeEvent<HTMLSelectElement>) => {
    setSelectedDestination(e.target.value)
  }

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault()

    const reservationData = {
      startingStation,
      busNumber: selectedBus?.busNumber || '',
      route: selectedBus?.route || '',
      destination: selectedDestination,
      name,
      seats
    }

    const jwt = U.readStringP('jwt').then(jwt => {
      setJwt(jwt ?? '')
    })

    // 서버로 예약 데이터 전송
    axios
      .post(SERVER_URL + '/reservation', reservationData, {
        headers: {
          'Content-Type': 'application/json',
          Authorization: `otb ${jwt}`
        }
      })
      .then(response => {
        console.log('예약 성공:', response.data)
        setName('')
        setSeats(1)
        setSelectedBus(null)
        setSelectedDestination('')
        onReservationSuccess() // 예약 성공 후 알림
      })
      .catch(error => {
        console.error('예약 실패:', error)
      })
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
              <h1 className="mb-8 text-4xl text-center text-lime-500">정류장</h1>
              {selectedMarker && (
                <ul>
                  <h2>정류장 정보</h2>
                  <p>정류장 이름: {selectedMarker.place}</p>
                  <p>정류장 ID: {selectedMarker.id}</p>
                  <p>위도: {selectedMarker.lat}</p>
                  <p>경도: {selectedMarker.lng}</p>
                </ul>
              )}
            </div>

            <div>
              <label htmlFor="bus">버스 선택:</label>
              <select
                id="bus"
                value={selectedBus ? selectedBus.id : ''}
                onChange={e => {
                  const selectedBusId = parseInt(e.target.value)
                  const selectedBus = buses.find(bus => bus.id === selectedBusId)
                  setSelectedBus(selectedBus || null)
                  setSelectedDestination('') // 새로운 버스 선택 시 도착 정류장 선택 초기화
                }}>
                <option value="" disabled>
                  버스 선택
                </option>
                {buses.map(bus => (
                  <option key={bus.id} value={bus.id}>
                    {bus.busNumber}
                  </option>
                ))}
              </select>
            </div>
            {selectedBus && (
              <div>
                <label htmlFor="destination">도착 정류장 선택:</label>
                <select
                  id="destination"
                  value={selectedDestination}
                  onChange={handleDestinationChange}
                  required>
                  <option value="" disabled>
                    도착 정류장 선택
                  </option>
                  {destinationStations.map(station => (
                    <option key={station} value={station}>
                      {station}
                    </option>
                  ))}
                </select>
              </div>
            )}
            <center>
              <Link to="/pay">
                <button className="flex-center ml-4 mr-4 btn btn-primary text-white  border-lime-600 bg-lime-600">
                  결제하기
                </button>
              </Link>
              <Link to="/" className="block mt-4 text-lime-500">
                메인 페이지로 이동하기
              </Link>
            </center>
          </div>
        </div>
      </div>
    </div>
  )
}

export default Reserve
