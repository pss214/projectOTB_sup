import React, {useState} from 'react'
import {Link} from '../../components'
import {useAuth} from '../../contexts'
import BUS_STOP, {BusStop} from '../../busStop.' // BusStop 타입과 busStops 데이터를 가져옴

const Reserve: React.FC = () => {
  const [selectedStation, setSelectedStation] = useState<BusStop | null>(null)

  const handleMarkerClick = (station: BusStop) => {
    setSelectedStation(station)
  }

  const handleListItemClick = (station: BusStop) => {
    handleMarkerClick(station)
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
            </div>
            {selectedStation ? (
              <div>
                <h2 className="mb-4 text-2xl text-lime-500">정류장 정보</h2>
                <p>정류장 이름: {selectedStation.place}</p>
                <p>정류장 ID: {selectedStation.id}</p>
                <p>위도: {selectedStation.lat}</p>
                <p>경도: {selectedStation.lng}</p>
              </div>
            ) : (
              <p className="text-center">정류장을 선택해주세요.</p>
            )}

            <ul>
              {BUS_STOP.map(station => (
                <li key={station.id} onClick={() => handleListItemClick(station)}>
                  {station.place} - 위도: {station.lat}, 경도: {station.lng}
                </li>
              )).splice(0, 2)}
            </ul>
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
