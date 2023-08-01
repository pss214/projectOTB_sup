import type {ChangeEvent} from 'react'
import {useState, useCallback} from 'react'
import {Link, useNavigate} from 'react-router-dom'
import {useAuth} from '../../contexts'

type SignUpFormType = Record<
  'email' | 'username' | 'password' | 'confirmPassword',
  string
>
const emailRegex = /^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}$/

const initialFormState = {email: '', username: '', password: '', confirmPassword: ''}

export default function SignUp() {
  const [{email, username, password, confirmPassword}, setForm] =
    useState<SignUpFormType>(initialFormState)
  const changed = useCallback(
    (key: string) => (e: ChangeEvent<HTMLInputElement>) => {
      setForm(obj => ({...obj, [key]: e.target.value}))
    },
    []
  )

  const navigate = useNavigate()
  const {signup} = useAuth()

  const validateEmail = (email: string) => {
    return emailRegex.test(email)
  }

  const createAccount = useCallback(() => {
    console.log(email, password, confirmPassword)
    if (!validateEmail(email)) {
      alert('올바른 이메일 형식을 입력해주세요.')
      return
    }
    if (password === confirmPassword) {
      signup(username, email, password, () => navigate('/'))
    } else alert('비밀번호가 일치하여야 합니다.')
  }, [email, password, confirmPassword, navigate, signup])

  return (
    <div className="flex flex-col min-h-screen border-gray-300 rounded-xl shadow-xl bg-gray-100 border">
      <div className="flex flex-col items-center justify-center flex-1 max-w-sm px-2 mx-auto">
        <div className="w-full px-6 py-8 text-black bg-white rounded shadow-md">
          <h1 className="mb-8 text-2xl text-center text-lime-500">회원 가입</h1>

          <input
            type="ID"
            className="w-full p-3 mb-4 input input-primary border-lime-500"
            name="username"
            placeholder="ID를 입력해주세요."
            value={username}
            onChange={changed('username')}
          />

          <input
            type="email"
            className="w-full p-3 mb-4 input input-primary border-lime-500"
            name="email"
            placeholder="email을 입력해주세요."
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
          <input
            type="password"
            className="w-full p-3 mb-4 input input-primary border-lime-500"
            name="confirm_password"
            placeholder="비밀번호 확인"
            value={confirmPassword}
            onChange={changed('confirmPassword')}
          />

          <button
            type="submit"
            className="w-full btn btn-primary text-white bg-lime-500"
            onClick={createAccount}>
            계정 생성하기
          </button>
        </div>

        <Link to="/" className="btn btn-link text-lime-500">
          메인 페이지로 이동하기
        </Link>

        <div className="mt-6 text-grey-dark">
          이미 계정이 있으신가요?
          <Link className="btn btn-link btn-primary text-lime-500" to="/login/">
            로그인하기
          </Link>
        </div>
      </div>
    </div>
  )
}
