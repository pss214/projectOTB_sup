import React, { useState, useEffect } from 'react';
import { Link } from '../../components';
import { useLocation } from 'react-router-dom';
import * as U from '../../utils';
import { SERVER_URL } from '../../server';
import axios from 'axios';

interface Bus {
  rtNm: string;
  busRouteId: string;
  arrmsg1: string;
  arrmsg2: string;
  vehId1 : string;
}

interface BusStop {
  id: number;
  place: string;
  lat: number;
  lng: number;
}
interface busstation{
  arsId : string;
  stationNm : string;

}

interface ReservationFormProps {
  buses: Bus[];
  destinationStations: busstation[];
  startingStation: string;
  onReservationSuccess: () => void;
}

const Reserve: React.FC<ReservationFormProps> = ({
  buses,
  destinationStations,
  startingStation,
  onReservationSuccess,
}) => {
  const [jwt, setJwt] = useState<string>('');
  const [name, setName] = useState('');
  const [seats, setSeats] = useState(1);
  const [selectedBus, setSelectedBus] = useState<Bus | null>(null);
  const [selectedDestination, setSelectedDestination] =useState<busstation|null>(null);
  const [busArrivalInfo, setBusArrivalInfo] = useState<Bus[]>([]);
  const [currentPage, setCurrentPage] = useState(1);
  const itemsPerPage = 6;
  const location = useLocation();
  const queryParams = new URLSearchParams(location.search);
  const lat = queryParams.get('lat');
  const lng = queryParams.get('lng');
  const place = queryParams.get('place');
  const id = queryParams.get('id');
  const selectedMarker = location.state
    ? (location.state as { selectedMarker: BusStop }).selectedMarker
    : null;

  useEffect(() => {
    if (selectedMarker?.id) {
      fetchData();
    }
  }, [selectedMarker]);

  const fetchData = () => {
    try {
      fetch(SERVER_URL + '/bus/information', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({ id: selectedMarker?.id }),
      }).then((res) => {
        res.json().then((res) => {
          console.log(res);
          const data = res.data[0];
          setBusArrivalInfo(data);
        });
      });
    } catch (error) {
      console.error('API 요청 중 오류 발생:', error);
    }
  };

  const goToPreviousPage = () => {
    if (currentPage > 1) {
      setCurrentPage(currentPage - 1);
    }
  };

  const goToNextPage = () => {
    if (indexOfLastItem < busArrivalInfo.length) {
      setCurrentPage(currentPage + 1);
    }
  };

  const indexOfLastItem = currentPage * itemsPerPage;
  const indexOfFirstItem = indexOfLastItem - itemsPerPage;
  const currentBusArrivalInfo = busArrivalInfo.slice(indexOfFirstItem, indexOfLastItem);

  const handleNameChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setName(e.target.value);
  };

  const handleSeatsChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setSeats(Number(e.target.value));
  };

  const handleDestinationChange = (e: React.ChangeEvent<HTMLSelectElement>) => {
    setSelectedDestination(e.target.value);
  };

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();

    const reservationData = {
      startingStation,
      busNumber: selectedBus?.rtNm || '',
      route: selectedBus?.busRouteId || '',
      destination: selectedDestination,
      name,
      seats,
    };

    const jwtPromise = U.readStringP('jwt').then((jwt) => {
      setJwt(jwt ?? '');
    });

    axios
      .post(SERVER_URL + '/reservation', reservationData, {
        headers: {
          'Content-Type': 'application/json',
          Authorization: `otb ${jwt}`,
        },
      })
      .then((response) => {
        console.log('예약 성공:', response.data);
        setName('');
        setSeats(1);
        setSelectedBus(null);
        setSelectedDestination('');
        onReservationSuccess();
      })
      .catch((error) => {
        console.error('예약 실패:', error);
      });
  };

  const handleBusSelection = (bus: Bus) => {
    setSelectedBus(bus);
    fetch(SERVER_URL+'/bus/route-name',{
      method:'POST',
      headers: {'Content-Type': 'application/json'},
      body: JSON.stringify({ id: bus.busRouteId }),
  }).then((res) => {
    res.json().then((res) => {
      console.log(res);
    });
  });
    // 여기에서 선택된 버스에 대한 추가 작업 수행 가능
  };

  return (
    <div>
      <div className="flex justify-between bg-lime-200">
        <div className="flex p-2">
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
              {currentBusArrivalInfo.length > 0 ? (
                <div>
                  <h2 className="mb-4 text-2xl text-lime-500">버스 도착 정보</h2>
                  <ul>
                    {currentBusArrivalInfo.map((bus: any, index: number) => (
                      <li key={index} className="mb-1" style={{ display: 'flex', alignItems: 'center' }}>
                      <button
                        className="btn btn-orange text-xs px-1 py-0.5"
                        onClick={() => handleBusSelection(bus)}
                        style={{
                          margin: '0',
                          lineHeight: '1',
                          width: '16%',
                          height: '11%',
                          display: 'flex',
                          alignItems: 'center',
                          justifyContent: 'center',
                        }}
                      >
                        {bus.rtNm}
                      </button>
                      <span>, {bus.arrmsg1}</span>
                    </li>
                    ))}
                  </ul>
                  <div className="flex justify-center mt-4">
                    <button
                      className="btn btn-orange mr-2"
                      onClick={goToPreviousPage}
                      disabled={currentPage === 1}
                    >
                      이전
                    </button>
                    <button
                      className="btn btn-orange"
                      onClick={goToNextPage}
                      disabled={indexOfLastItem >= busArrivalInfo.length}
                    >
                      다음
                    </button>
                  </div>
                </div>
              ) : (
                <p>버스 도착 정보가 없습니다.</p>
              )}
            </div>
            {selectedBus && (
            <div>
              <label htmlFor="destination">{selectedBus.rtNm}, {selectedBus.busRouteId} 노선 버스 하차지 :</label>
              <select
                id="destination"
                value={selectedDestination}
                onChange={handleDestinationChange}
                required
              >
                <option value="" disabled>
                  정류장 선택
                </option>
                {destinationStations.map((station) => (
                  <option key={station} value={station}>
                    {station}
                  </option>
                ))}
              </select>
            </div>
            )}
            <center>
              <Link to="/pay">
                <button className="flex-center ml-4 mr-4 btn btn-primary text-white border-lime-600 bg-lime-600">
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
