import type {FC, PropsWithChildren} from 'react'
import {createContext, useContext, useState, useCallback, useEffect} from 'react'
import * as U from '../utils'
import {SERVER_URL} from '../server/getServer'
import axios from 'axios'

export type LoggedUser = {username: string; password: string}
export type LoggedDriver = {
  busnumberplate: string
  password: string
  busnumber: string
  personnel: string
}
export type ReservationUser = {username: string; busnumber: string}
type Callback = () => void

type ContextType = {
  jwt?: string
  errorMessage?: string
  loggedUser?: LoggedUser
  loggedDriver?: LoggedDriver
  signup: (username: string, email: string, password: string, callback?: Callback) => void
  signupdriver: (
    busnumberplate: string,
    password: string,
    busnumber: string,
    personnel: string,
    callback?: Callback
  ) => void
  login: (username: string, password: string, callback?: Callback) => void
  logout: (callback?: Callback) => void
  mypage: (jwt: string, callback?: Callback) => void
  reservation: (username: string, busnumber: string, callback?: Callback) => void
}

export const AuthContext = createContext<ContextType>({
  signup: (username: string, email: string, password: string, callback?: Callback) => {},
  signupdriver: (
    busnumberplate: string,
    password: string,
    busnumber: string,
    personnel: string,
    callback?: Callback
  ) => {},
  login: (username: string, password: string, callback?: Callback) => {},
  logout: (callback?: Callback) => {},
  mypage(jwt: string, callback?: Callback) {},
  reservation: (username: string, busnumber: string, callback?: Callback) => {}
})

type AuthProviderProps = {}

export const AuthProvider: FC<PropsWithChildren<AuthProviderProps>> = ({children}) => {
  const [loggedUser, setLoggedUser] = useState<LoggedUser | undefined>(undefined)
  const [loggedDriver, setLoggedDriver] = useState<LoggedDriver | undefined>(undefined)
  const [reservationUser, setReservationUser] = useState<ReservationUser | undefined>(
    undefined
  )
  //서버에서 보내는 json토큰이나 통신장애로 인한 오류 처리
  const [jwt, setJwt] = useState<string>('')
  const [errorMessage, setErrorMessage] = useState<string>('')
  const signup = useCallback(
    async (username: string, email: string, password: string, callback?: Callback) => {
      const anonymous = {}
      await axios({
        method: 'POST',
        timeout: 4000,
        url: SERVER_URL + '/user/signup',
        headers: {'Context-Type': 'application/json'},
        data: {
          username: username,
          password: password,
          email: email
        }
      })
        .then(res => {
          console.log(res.data)
          if (res.data.status == 201) {
            alert('회원가입이 완료되었습니다.')
            U.writeObjectP('anonymous', anonymous).finally(() => callback && callback())
          }
        })
        .catch(e => {
          alert(e.data.message)
          throw new Error(e).message
        })
    },
    []
  )
  const signupdriver = useCallback(
    async (
      busnumberplate: string,
      password: string,
      busnumber: string,
      personnel: string,
      callback?: Callback
    ) => {
      const anonymous = {}
      await axios({
        method: 'POST',
        timeout: 4000,
        url: SERVER_URL + '/user/bussignup',
        headers: {'Context-Type': 'application/json'},
        data: {
          busnumberplate: busnumberplate,
          password: password,
          busnumber: busnumber,
          personnel: personnel
        }
      })
        .then(res => {
          if (res.data.status == 201) {
            alert('회원가입이 완료되었습니다.')
            U.writeObjectP('anonymous', anonymous).finally(() => callback && callback())
          }
        })
        .catch(e => {
          alert(e.data.message)
          throw new Error(e).message
        })
    },
    []
  )
  const login = useCallback(
    async (username: string, password: string, callback?: Callback) => {
      const user = {username, password}
      await axios({
        method: 'POST',
        timeout: 4000,
        url: SERVER_URL + '/user/login',
        headers: {'Context-Type': 'application/json'},
        data: {
          username: username,
          password: password
        }
      })
        .then(res => {
          console.log(res.data)
          if (res.data.status == 200) {
            U.writeStringP('jwt', res.data.data[0].token)
            setJwt(res.data.data[0].token)
            setLoggedUser(notUsed => ({username, password}))
            U.writeObjectP('user', user).finally(() => callback && callback())
          }
        })
        .catch(e => {
          alert(e.data.message)
          throw new Error(e).message
        })
    },
    []
  )
  const reservation = useCallback(
    (username: string, busnumber: string, callback?: Callback) => {
      const user = {username, busnumber}
      fetch(SERVER_URL + '/user/reservation', {
        method: 'POST',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify({
          username: username,
          busnumber: busnumber
        })
      })
        .then(response => response.json()) //server에서 보내준 response를 object 형태로 변환
        // .then(result => console.log('결과: ', result))
        .then((result: {ok: boolean; body?: string; errorMessage?: string}) => {
          const {ok, body} = result
          if (ok) {
            U.writeStringP('jwt', body ?? '').finally(() => {
              setJwt(body ?? '')
              setReservationUser(notUsed => ({username, busnumber}))
              U.writeObjectP('user', user).finally(() => callback && callback())
            })
            //back 서버에서 ok 값이 true일때 setLoggedUser, U.writeObjectP 함수 호출
          }
          console.log('결과: ', result)
        })
        .catch((e: Error) => setErrorMessage(e.message))
      setReservationUser(notUsed => ({username, busnumber}))
      U.writeObjectP('user', user).finally(() => callback && callback())
      callback && callback()
    },
    []
  )
  const logout = useCallback((callback?: Callback) => {
    setJwt(notUsed => '')
    setLoggedUser(undefined)
    callback && callback()
  }, [])

  const mypage = useCallback(async (jwt: string, callback?: Callback) => {
    await axios({
      method: 'GET',
      timeout: 4000,
      url: SERVER_URL + '/member',
      headers: {'Context-Type': 'application/json', Authorization: jwt}
    })
      .then(res => {
        console.log(res.data)
        if (res.data.status == 200) {
          const username = res.data.data[0].username
          const email = res.data.data[0].email

          const user = {username, email}
          U.writeObjectP('user', user).finally(() => callback && callback())
        }
      })
      .catch(e => {
        alert(e.data.message)
        throw new Error(e).message
      })
  }, [])
  //로그인했을때 localStorage에 저장된 jwt값을 읽어 컨텍스트의 jwt상태값 복원
  useEffect(() => {
    U.readStringP('jwt')
      .then(jwt => setJwt(jwt ?? ''))
      .catch(() => {
        /* 오류 무시 */
      })
  }, [errorMessage])

  useEffect(() => {
    const deleteToken = false // localStorage 의 jwt값을 초기화 할때 사용!
    if (deleteToken) {
      U.writeStringP('jwt', '')
        .then(() => {})
        .catch(() => {})
    } else {
      U.readStringP('jwt')
        .then(jwt => setJwt(jwt ?? ''))
        .catch(() => {
          /* 오류 무시*/
        })
    }
  }, [])
  const value = {
    jwt,
    errorMessage,
    loggedUser,
    loggedDriver,
    signup,
    signupdriver,
    login,
    logout,
    reservation,
    mypage
  }
  return <AuthContext.Provider value={value} children={children} />
}

export const useAuth = () => {
  return useContext(AuthContext)
}
