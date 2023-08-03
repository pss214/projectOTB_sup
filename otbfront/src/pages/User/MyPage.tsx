import React, {useState, useCallback, useEffect} from 'react'
import {SERVER_URL} from '../../server/getServer'
import {Link} from '../../components'
import * as U from '../../utils'
import axios from 'axios'
import type {ChangeEvent} from 'react'
import Login from '../../routes/Auth/Login'

const MyPage: React.FC = () => {
  const [user, setUser] = useState<any | null>(null)
  const [jwt, setJwt] = useState<string>('')
  const [loginData, setPassword] = useState({password: ''})
 
  useEffect(()=>{
    U.readStringP('jwt').then(jwt => {
      setJwt(jwt ?? '')
    })
  },[jwt])
  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const {name, value} = e.target
    setPassword(prevData => ({
      ...prevData,
      [name]: value
    }))
  }
  type FormType = Record<'password', string>
  const initialFormState = {password: ''}
  const changed = useCallback(
    (key: string) => (e: ChangeEvent<HTMLInputElement>) => {
      setForm(obj => ({...obj, [key]: e.target.value}))
    },
    []
  )
  const [{password}, setForm] = useState<FormType>(initialFormState)

  const handleSubmit = (e: React.FormEvent<HTMLFormElement>) => {
    axios(SERVER_URL + '/member', {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json',
        Authorization: `otb ${jwt}`
      }
    })
      .then(res => {
        setUser(res.data.data[0])
        console.log(res)
      })
      .catch(error => {
        console.error('정보를 가져오는데 실패했습니다.', error)
      })
    e.preventDefault()
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
              <h1 className="mb-8 text-4xl text-center text-lime-500">마이 페이지</h1>
              {user ? (
                <div className="mb-8 text-2xl text-center text-black-500">
                  <p>Username: {user.username}</p>
                  <p>Email: {user.email}</p>
                  <button className="flex-center ml-4 mr-4 btn btn-primary text-white  border-lime-600 bg-lime-600">
                    수정하기
                  </button>
                  <button className="flex-center ml-4 mr-4 btn btn-primary text-white  border-lime-600 bg-lime-600">
                    회원탈퇴
                  </button>
                </div>
              ) : (
                <form className="text-center" onSubmit={handleSubmit}>
                  {/* <input
                    type="password"
                    name="password"
                    placeholder="비밀번호를 입력해주세요"
                    value={loginData.password}
                    onChange={handleChange}
                  /> */}
                  <button
                    className="btn btn-primary text-white bg-lime-500"
                    type="submit">
                    로그인
                  </button>
                </form>
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

export default MyPage
