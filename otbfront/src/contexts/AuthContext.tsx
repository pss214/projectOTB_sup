import type {FC, PropsWithChildren} from 'react'
import {createContext, useContext, useState, useCallback, useEffect} from 'react'
import * as U from '../utils'
import {SERVER_URL} from '../server/getServer'
import {resourceLimits} from 'worker_threads'
import {post} from '../server/postAndPut'

export type LoggedUser = {username: string; password: string}
type Callback = () => void

type ContextType = {
  jwt?: string
  errorMessage?: string
  loggedUser?: LoggedUser
  signup: (username: string, email: string, password: string, callback?: Callback) => void
  signupdriver: (
    username: string,
    email: string,
    password: string,
    busnumber: string,
    callback?: Callback
  ) => void
  login: (username: string, password: string, callback?: Callback) => void
  logout: (callback?: Callback) => void
}

export const AuthContext = createContext<ContextType>({
  signup: (username: string, email: string, password: string, callback?: Callback) => {},
  signupdriver: (
    username: string,
    email: string,
    password: string,
    busnumber: string,
    callback?: Callback
  ) => {},
  login: (username: string, password: string, callback?: Callback) => {},
  logout: (callback?: Callback) => {}
})

type AuthProviderProps = {}

export const AuthProvider: FC<PropsWithChildren<AuthProviderProps>> = ({children}) => {
  const [loggedUser, setLoggedUser] = useState<LoggedUser | undefined>(undefined)
  //서버에서 보내는 json토큰이나 통신장애로 인한 오류 처리
  const [jwt, setJwt] = useState<string>('')
  const [errorMessage, setErrorMessage] = useState<string>('')

  const signup = useCallback(
    (username: string, email: string, password: string, callback?: Callback) => {
      const user = {email, password}
      fetch(SERVER_URL + '/api/signup', {
        method: 'POST', // http의 method
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify({
          // 기존의 js object를 JSON String의 형태로 변환
          username: username,
          email: email,
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
      // setLoggedUser(notUsed => ({username, password}))
      // U.writeObjectP('user', user).finally(() => callback && callback())
      // callback && callback()
    },
    []
  )
  const signupdriver = useCallback(
    (
      username: string,
      email: string,
      password: string,
      busnumber: string,
      callback?: Callback
    ) => {
      const user = {username, password, busnumber}
      fetch(SERVER_URL + '/api/signupdriver', {
        method: 'POST', // http의 method
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify({
          // 기존의 js object를 JSON String의 형태로 변환
          username: username,
          email: email,
          password: password,
          busnumber: busnumber
        })
      })
        .then(response => response.json()) //server에서 보내준 response를 object 형태로 변환
        .then(result => console.log('결과: ', result))
      setLoggedUser(notUsed => ({username, password}))
      U.writeObjectP('user', user).finally(() => callback && callback())
      // callback && callback()
    },
    []
  )
  const login = useCallback((username: string, password: string, callback?: Callback) => {
    const user = {username, password}
    U.readStringP('jwt')
      .then(jwt => {
        setJwt(jwt ?? '')
        return post('/api/login', user, jwt)
      })
      .then(res => res.json())
      .then((result: {ok: boolean; errorMessage?: string}) => {
        if (result.ok) {
          setLoggedUser(notUsed => user)
          callback && callback()
        } else {
          setErrorMessage(result.errorMessage ?? '')
        }
      })
      .catch((e: Error) => setErrorMessage(e.message ?? ''))
    // fetch(SERVER_URL + '/api/login', {
    //   method: 'POST', // http의 method
    //   headers: {'Content-Type': 'application/json'},
    //   body: JSON.stringify({
    //     // 기존의 js object를 JSON String의 형태로 변환
    //     username: username,
    //     password: password
    //   })
    // })
    //   .then(response => response.json()) //server에서 보내준 response를 object 형태로 변환
    //   .then(result => console.log('결과: ', result))
    // setLoggedUser(notUsed => ({username, password}))
    // setLoggedUser(notUsed => ({username, password}))
    // callback && callback()
  }, [])
  const logout = useCallback((callback?: Callback) => {
    setJwt(notUsed => '')
    setLoggedUser(undefined)
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
    signup,
    signupdriver,
    login,
    logout
  }
  return <AuthContext.Provider value={value} children={children} />
}

export const useAuth = () => {
  return useContext(AuthContext)
}
