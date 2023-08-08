import React, {useState, useCallback} from 'react'
import {SERVER_URL} from '../../server/getServer'
import {Link} from '../../components'

const BusMain: React.FC = () => {
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
          <h1 className="mb-8 text-4xl text-center text-lime-500">Driver Main</h1>
          <div className="w-full px-6 py-8 text-black bg-white rounded shadow-md">
            <div></div>
            <Link to="/" className="btn btn-link text-lime-500">
              메인 페이지로 이동
            </Link>
          </div>
        </div>
      </div>
    </div>
  )
}
export default BusMain
