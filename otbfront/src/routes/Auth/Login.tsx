import type {ChangeEvent} from 'react'
import {useState, useCallback, useEffect} from 'react'
import {Link, useNavigate} from 'react-router-dom'
import {useAuth} from '../../contexts'

type LoginFormType = Record<'username' | 'password', string>
const initialFormState = {username: '', password: ''}

export default function Login() {
  const [{username, password}, setForm] = useState<LoginFormType>(initialFormState)
  const changed = useCallback(
    (key: string) => (e: ChangeEvent<HTMLInputElement>) => {
      setForm(obj => ({...obj, [key]: e.target.value}))
    },
    []
  )

  const navigate = useNavigate()
  const {login} = useAuth()

  const handleOnKeyPress = (e: React.KeyboardEvent<HTMLInputElement>) => {
    if (e.key === 'Enter') {
      loginAccount(); // Enter 입력이 되면 클릭 이벤트 실행
    }
  };
  const loginAccount = useCallback(() => {
    login(username, password, () => navigate('/'))
  }, [username, password, navigate, login])

  return (
    <div className="flex flex-col min-h-screen bg-gray-100 border border-gray-300 shadow-xl rounded-xl">
      <div className="flex flex-col items-center justify-center flex-1 max-w-sm px-2 mx-auto">
        <div className="w-full px-6 py-8 text-black bg-white rounded shadow-md">
          <h1 className="mb-8 text-2xl text-center text-lime-500">로그인</h1>

          <input
            type="text"
            className="w-full p-3 mb-4 input input-primary border-lime-500"
            name="username"
            placeholder="고객은 ID 기사는 차량번호를 입력하세요"
            value={username}
            onChange={changed('username')}
          />

          <input
            type="password"
            className="w-full p-3 mb-4 input input-primary border-lime-500"
            name="password"
            placeholder="비밀번호를 입력해주세요."
            value={password}
            onChange={changed('password')}
            onKeyPress={handleOnKeyPress}
          />

          <button
            type="submit"
            className="w-full btn btn-primary text-white bg-lime-500 border-lime-500"
            onClick={loginAccount}>
            로그인
          </button>
        </div>

        <Link to="/" className="btn btn-link text-lime-500">
          메인 페이지로 이동하기
        </Link>

        <div className="mt-6 text-grey-dark">
          계정이 없으시다면 클릭해주세요.
          <Link className="btn btn-link btn-primary text-lime-500" to="/signup">
            회원 가입
          </Link>
        </div>
      </div>
    </div>
  )
}
