import React, {useState} from 'react'
import {SERVER_URL} from '../../server'
import {Link} from '../../components'
import {response} from 'express'

const MyPage: React.FC = () => {
  const [user, setUser] = useState<any | null>(null)
  const [loginData, setLoginData] = useState({username: '', password: ''})

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const {name, value} = e.target
    setLoginData(prevData => ({
      ...prevData,
      [name]: value
    }))
  }

  const handleSubmit = (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault()
    fetch(SERVER_URL + '/user/login', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(loginData)
    })
      .then(response => response.json())
      .then(data => {
        const {token} = data
        getUserInfo(token)
      })
      .catch(error => {
        console.error('로그인에 실패했습니다.', error)
      })
  }

  const getUserInfo = (token: string) => {
    fetch(SERVER_URL + '/member', {
      method: 'GET',
      headers: {'Content-Type': 'application/json', Authorization: `${token}`}
    })
      .then(response => response.json())
      .then(data => {
        setUser(data)
      })
      .catch(error => {
        console.error('정보를 가져오는데 실패했습니다.', error)
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
        <div>
          <h1 className="mb-8 text-4xl text-center text-lime-500">마이 페이지</h1>
          {user ? (
            <div className="mb-8 text-2xl text-center text-black-500">
              <p>Username: {user.username}</p>
              <p>Email: {user.email}</p>
              <p>Password: {user.password}</p>
            </div>
          ) : (
            <form onSubmit={handleSubmit}>
              <input
                type="username"
                name="username"
                placeholder="사용자명"
                value={loginData.username}
                onChange={handleChange}
              />
              <input
                type="password"
                name="password"
                placeholder="비밀번호"
                value={loginData.password}
                onChange={handleChange}
              />
              <button className="btn btn-primary text-white bg-lime-500" type="submit">
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
  )
}

export default MyPage
