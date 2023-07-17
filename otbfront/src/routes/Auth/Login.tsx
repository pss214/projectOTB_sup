import type {ChangeEvent} from 'react'
import {useState, useCallback, useEffect} from 'react'
import {Link, useNavigate} from 'react-router-dom'
import {useAuth} from '../../contexts'
import * as U from '../../utils'

type LoginFormType = Record<'email' | 'password', string>
const initialFormState = {email: '', password: ''}

export default function Login() {
  const [{email, password}, setForm] = useState<LoginFormType>(initialFormState)
  const changed = useCallback(
    (key: string) => (e: ChangeEvent<HTMLInputElement>) => {
      setForm(obj => ({...obj, [key]: e.target.value}))
    },
    []
  )

  const navigate = useNavigate()
  const {login} = useAuth()

  const loginAccount = useCallback(() => {
    login(email, password, () => navigate('/'))
  }, [email, password, navigate, login])

  useEffect(() => {
    U.readObjectP<LoginFormType>('user')
      .then(user => {
        if (user) setForm(user)
      })
      .catch(e => {
        /* ignore */
      })
  }, [])

  return (
    <div className="flex flex-col min-h-screen bg-gray-100 border border-gray-300 shadow-xl rounded-xl">
      <div className="flex flex-col items-center justify-center flex-1 max-w-sm px-2 mx-auto">
        <div className="w-full px-6 py-8 text-black bg-white rounded shadow-md">
          <h1 className="mb-8 text-2xl text-center text-lime-500">로그인</h1>

          <input
            type="text"
            className="w-full p-3 mb-4 input input-primary border-lime-500"
            name="email"
            placeholder="Email을 입력해주세요."
            value={email}
            onChange={changed('email')}
          />

          <input
            type="password"
            className="w-full p-3 mb-4 input input-primary border-lime-500"
            name="password"
            placeholder="비밀번호를 입력해주세요."
            value={password}
            onChange={changed('password')}
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
        <div className="mt-6 text-grey-dark">
          "버스 기사"님 이시면 클릭해주세요.
          <Link className="btn btn-link btn-primary text-lime-500" to="/signup">
            회원 가입
          </Link>
        </div>
      </div>
    </div>
  )
}
