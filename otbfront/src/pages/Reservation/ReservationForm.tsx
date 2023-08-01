import React, {useState} from 'react'
import axios from 'axios'
import {Link as RRLink} from 'react-router-dom'
import {Link} from '../../components'
import {useAuth} from '../../contexts'
import * as U from '../../utils'
import {SERVER_URL} from '../../server'

interface Bus {
  id: number
  busNumber: string
  capacity: number
  route: string
  destination: string
}

interface ReservationFormProps {
  bus: Bus
  destinationStations: string[]
  startingStation: string
  onReservationSuccess: () => void
}

const ReservationForm: React.FC<ReservationFormProps> = ({
  bus,
  destinationStations,
  startingStation,
  onReservationSuccess
}) => {
  const [jwt, setJwt] = useState<string>('')
  const [name, setName] = useState('')
  const [seats, setSeats] = useState(1)
  const [selectedDestination, setSelectedDestination] = useState('')

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
      busNumber: bus.busNumber,
      route: bus.route,
      destination: selectedDestination,
      name,
      seats
    }
    const jwt = U.readStringP('jwt').then(jwt => {
      setJwt(jwt ?? '')
    })
    // 백서버(예약) 주소 추가하기  //header 추가 // header안에 토큰도 추가 //aplication.json 추가
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
              <h2>버스 예약 - {bus.busNumber}</h2>
              <p>출발 정류장: {startingStation}</p>
              <form onSubmit={handleSubmit}>
                <div>
                  <label htmlFor="name">이름:</label>
                  <input
                    type="text"
                    id="name"
                    value={name}
                    onChange={handleNameChange}
                    required
                  />
                </div>
                <div>
                  <label htmlFor="seats">좌석 수:</label>
                  <input
                    type="number"
                    id="seats"
                    value={seats}
                    min={1}
                    max={bus.capacity}
                    onChange={handleSeatsChange}
                    required
                  />
                </div>
                <div>
                  <label htmlFor="destination">도착정류장 선택:</label>
                  <select
                    id="destination"
                    value={selectedDestination}
                    onChange={handleDestinationChange}
                    required>
                    <option value="" disabled>
                      도착정류장 선택
                    </option>
                    {destinationStations.map(station => (
                      <option key={station} value={station}>
                        {station}
                      </option>
                    ))}
                  </select>
                </div>
                <button type="submit">예약하기</button>
              </form>
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

export default ReservationForm
