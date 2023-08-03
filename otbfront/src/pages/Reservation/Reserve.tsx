import React from 'react';
import { Link } from 'react-router-dom';
import { useLocation } from 'react-router-dom';

function ReservePage() {
  const location = useLocation();
  const queryParams = new URLSearchParams(location.search);

  const lat = queryParams.get('lat');
  const lng = queryParams.get('lng');
  const id = queryParams.get('id');
  const place = queryParams.get('place');

  return (
    <div>
      <div className="flex justify-between bg-lime-200 p-2">
        <div className="flex">
          <Link to="/" className="ml-1">
            <img
              src="/img/otblogogogo.png"
              alt="OTB(우비) 로고"
              className="w-12.5 h-12.5 bg-lime-200"
            />
          </Link>
        </div>
      </div>
      <div className="flex flex-col min-h-screen border rounded-xl shadow-xl bg-gray-100 border-gray-300">
        <div className="flex flex-col items-center flex-1 max-w-sm px-2 mx-auto">
          <div className="w-full px-6 py-8 text-black bg-white rounded shadow-md">
            <div>
              <h1 className="mb-8 text-4xl text-center text-lime-500">정류장</h1>
            </div>
            <div>
              <h2 className="mb-4 text-2xl text-lime-500">정류장 정보</h2>
              <p className="mb-1">정류장 이름: {place}</p>
              <p className="mb-1">정류장 ID: {id}</p>
              <p className="mb-1">위도: {lat}</p>
              <p className="mb-4">경도: {lng}</p>
            </div>
            <Link to="/" className="block mt-4 text-lime-500 hover:underline">
              메인 페이지로 이동하기
            </Link>
          </div>
        </div>
      </div>
    </div>
  );
}

export default ReservePage;
