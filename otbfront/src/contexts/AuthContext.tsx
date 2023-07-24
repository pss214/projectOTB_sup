import type {FC, PropsWithChildren} from 'react'
import {createContext, useContext, useState, useCallback, useEffect} from 'react'
import * as U from '../utils'
import {SERVER_URL} from '../server'
//import {resourceLimits} from 'worker_threads'
//import {post} from '../server'

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
    (username: string, email: string, password: string, callback?: Callback) => {
      const user = {email, password}
      fetch(SERVER_URL + '/user/signup', {
        method: 'POST',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify({
          username: username,
          password: password,
          email: email
        })
      })
        .then(response => response.json()) //server에서 보내준 response를 object 형태로 변환
        .then((result: {ok: boolean; body?: string; errorMessage?: string}) => {
          const {ok, body, errorMessage} = result
          if (ok) {
            U.writeStringP('jwt', body ?? '').finally(() => {
              setJwt(body ?? '')
              setLoggedUser(notUsed => ({username, password}))
              U.writeObjectP('user', user).finally(() => callback && callback())
            })
            //back 서버에서 ok 값이 true일때 setLoggedUser, U.writeObjectP 함수 호출
          }
          console.log('결과: ', result)
        })
        .catch((e: Error) => setErrorMessage(e.message))
      setLoggedUser(notUsed => ({username, password}))
      U.writeObjectP('user', user).finally(() => callback && callback())
      callback && callback()
    },
    []
  )
  const signupdriver = useCallback(
    (
      busnumberplate: string,
      password: string,
      busnumber: string,
      personnel: string,
      callback?: Callback
    ) => {
      const user = {busnumberplate, password, busnumber, personnel}
      fetch(SERVER_URL + '/user/bussignup', {
        method: 'POST',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify({
          // 기존의 js object를 JSON String의 형태로 변환
          busnumberplate: busnumberplate,
          password: password,
          busnumber: busnumber,
          personnel: personnel
        })
      })
        .then(response => response.json()) //server에서 보내준 response를 object 형태로 변환
        // .then(result => console.log('결과: ', result))
        .then((result: {ok: boolean; body?: string; errorMessage?: string}) => {
          const {ok, body, errorMessage} = result
          if (ok) {
            U.writeStringP('jwt', body ?? '').finally(() => {
              setJwt(body ?? '')
              setLoggedDriver(notUsed => ({
                busnumberplate,
                password,
                busnumber,
                personnel
              }))
              U.writeObjectP('driver', user).finally(() => callback && callback())
            })
            //back 서버에서 ok 값이 true일때 setLoggedUser, U.writeObjectP 함수 호출
          }
          console.log('결과: ', result)
        })
        .catch((e: Error) => setErrorMessage(e.message))
      setLoggedDriver(notUsed => ({busnumberplate, password, busnumber, personnel}))
      U.writeObjectP('driver', user).finally(() => callback && callback())
      callback && callback()
    },
    []
  )
  const login = useCallback((username: string, password: string, callback?: Callback) => {
    const user = {username, password}
    fetch(SERVER_URL + '/user/login', {
      method: 'POST',
      headers: {'Content-Type': 'application/json'},
      body: JSON.stringify({
        username: username,
        password: password
      })
    })
      .then(response => response.json()) //server에서 보내준 response를 object 형태로 변환
      // .then(result => console.log('결과: ', result))
      .then((result: {ok: boolean; body?: string; errorMessage?: string}) => {
        const {ok, body, errorMessage} = result
        if (ok) {
          U.writeStringP('jwt', body ?? '').finally(() => {
            setJwt(body ?? '')
            setLoggedUser(notUsed => ({username, password}))
            U.writeObjectP('user', user).finally(() => callback && callback())
          })
          //back 서버에서 ok 값이 true일때 setLoggedUser, U.writeObjectP 함수 호출
        }
        console.log('결과: ', result)
      })
      .catch((e: Error) => setErrorMessage(e.message))
    setLoggedUser(notUsed => ({username, password}))
    U.writeObjectP('user', user).finally(() => callback && callback())
    callback && callback()
  }, [])

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
          const {ok, body, errorMessage} = result
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
    //setLoggedDriver(undefined)
    callback && callback()
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
    reservation
  }
  return <AuthContext.Provider value={value} children={children} />
}

export const useAuth = () => {
  return useContext(AuthContext)
}
