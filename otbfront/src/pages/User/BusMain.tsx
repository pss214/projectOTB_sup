import React, {useState, useCallback, useEffect} from 'react'
import {SERVER_URL} from '../../server/getServer'
import {Link} from '../../components'
import QrReader from 'react-qr-scanner'
import * as U from '../../utils'
import {userInfo} from 'os'
import ReservationForm from '../Reservation/ReservationForm'

interface Reservelist {
  username: string
  stNm: string
  rtNm: number
  plainNo1: string
  station_in: boolean
  station_out: boolean
  payment: boolean
}
const BusMain: React.FC = () => {
  const [qrData, setQrData] = useState('')
  const [qrScannerVisible, setQrScannerVisible] = useState(false) // QR 스캐너 표시 여부 상태 변수 추가
  const [boardingInfoVisible, setBoardingInfoVisible] = useState(false) // 승하차 정보 보기 상태 변수 추가
  const [reservations, setReservations] = useState<Reservelist[]>([])
  const [jwt, setJwt] = useState<string>('')
  const [jwtbool, setJwtbool] = useState<boolean>(false)

  useEffect(() => {
    U.readStringP('jwt').then(jwt => {
      setJwt(jwt ?? '')
      setJwtbool(true)
    })
    // if (jwtbool) {

    // }
  }, [jwt])

  const handleScan = (data: {text: string} | null) => {
    if (data && data.text) {
      setQrData(data.text) // text 필드 추출하여 저장
    }
  }

  const handleError = (error: any) => {
    console.error(error)
  }

  const handleLogout = () => {
    // 인증 정보 제거
    localStorage.removeItem('authToken')

    // 로그아웃 후 리다이렉션
    window.location.href = '/' // 메인 페이지로 리다이렉션 (필요에 따라 경로 변경)
  }
  const handleReserveList = async (e: React.FormEvent) => {
    e.preventDefault()
    try {
      const response = await fetch(SERVER_URL + '/bus-driver/get-inout', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
          Authorization: `otb ${jwt}`
        },
        body: JSON.stringify(U.readObjectP)
      })
        .then(res => res.json())
        .then(res => {
          setReservations(res.data)
          console.log(res)
        })
    } catch (error) {
      console.error('오류 발생', error)
    }
  }
  return (
    <div>
      <div className="flex justify-between bg-lime-200">
        <div className="flex p-2 ">
          <img
            src="/img/otblogogogo.png"
            alt="OTB(우비) 로고"
            className="w-12.5 h-12.5 bg-lime-200"
          />
        </div>
      </div>
      <div className="flex flex-col min-h-screen border-gray-300 rounded-xl shadow-xl bg-gray-100 border">
        <div className="flex flex-col items-center  flex-1 max-w-sm px-2 mx-auto">
          <h1 className="mb-8 text-4xl text-center text-lime-500">
            발급 받은 QR 코드를 화면에 대주세요.
          </h1>
          <div className="w-full px-6 py-8 text-black bg-white rounded shadow-md">
            <div className="flex justify-center space-x-4 mb-4">
              <button
                className="btn btn-link text-lime-500"
                onClick={() => setQrScannerVisible(!qrScannerVisible)} // 삼항 연산자로 열고 닫기
              >
                {qrScannerVisible ? 'QR 스캐너 닫기' : 'QR 인식'}
              </button>
              <button
                className="btn btn-link text-lime-500"
                onClick={() => {
                  setBoardingInfoVisible(!boardingInfoVisible)
                }} // 승하차 정보 버튼 열고 닫기
              >
                {boardingInfoVisible ? '승하차 정보 닫기' : '승하차 정보'}
              </button>
              <button className="btn btn-link text-lime-500" onClick={handleReserveList}>
                정보가져오기
              </button>
            </div>
            {qrScannerVisible && ( // QR 스캐너와 스캔된 데이터의 조건부 렌더링
              <>
                <QrReader
                  delay={300}
                  onError={handleError}
                  onScan={handleScan}
                  style={{width: '100%'}}
                />
                <div className="mt-4">
                  {qrData && <p className="text-lime-500">스캔된 데이터: {qrData}</p>}
                </div>
              </>
            )}
            <center>
              {boardingInfoVisible && (
                <div className="mt-4">
                  <p className="text-lime-500">승하차 정보</p>
                  {reservations.map((reservation, index) => (
                    <div key={index} className="border p-2 mt-2 bg-white rounded shadow">
                      <p>예약자명:{reservation.username}</p>
                      <p>출발 정류장:{reservation.station_in}</p>
                      <p>도착 정류장:{reservation.station_out}</p>
                    </div>
                  ))}
                </div>
              )}
            </center>
            <center>
              <button className="btn btn-link text-lime-500" onClick={handleLogout}>
                로그아웃
              </button>
            </center>
          </div>
        </div>
      </div>
    </div>
  )
}

export default BusMain
