import type {FC, PropsWithChildren} from 'react'
import {createContext, useContext, useState, useCallback} from 'react'
import * as U from '../utils'
import { SERVER_URL } from '../server/getServer';

export type LoggedUser = {username: string; password: string}
type Callback = () => void

type ContextType = {
  loggedUser?: LoggedUser
  signup: (username:string,email: string, password: string, callback?: Callback) => void
  signupdriver: (
    email: string,
    password: string,
    busnumber: string,
    callback?: Callback
  ) => void
  login: (username: string, password: string, callback?: Callback) => void
  logout: (callback?: Callback) => void
}

export const AuthContext = createContext<ContextType>({
  signup: (username:string,email: string, password: string, callback?: Callback) => {},
  signupdriver: (
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

  const signup = useCallback((username:string, email: string, password: string, callback?: Callback) => {
    const user = {email, password}
    fetch(SERVER_URL+'/api/signup', {
      method: 'POST', // http의 method
      headers:{'Content-Type':'application/json'},
      body: JSON.stringify({ // 기존의 js object를 JSON String의 형태로 변환
        username:username,
        email: email, 
        password: password,
      }),
    })
      .then(response => response.json()) //server에서 보내준 response를 object 형태로 변환
      .then(result => console.log('결과: ', result));
    setLoggedUser(notUsed => ({username, password}))
    U.writeObjectP('user', user).finally(() => callback && callback())
    // callback && callback()
  }, [])
  const signupdriver = useCallback(
    (username: string, password: string, busnumber: string, callback?: Callback) => {
      const user = {username, password, busnumber}
      setLoggedUser(notUsed => ({username, password}))
      U.writeObjectP('user', user).finally(() => callback && callback())
      // callback && callback()
    },
    []
  )
  const login = useCallback((username: string, password: string, callback?: Callback) => {
    fetch(SERVER_URL+'/api/login', {
      method: 'POST', // http의 method
      headers:{'Content-Type':'application/json'},
      body: JSON.stringify({ // 기존의 js object를 JSON String의 형태로 변환
        username:username,
        password: password
      }),
    })
      .then(response => response.json()) //server에서 보내준 response를 object 형태로 변환
      .then(result => console.log('결과: ', result));
    setLoggedUser(notUsed => ({username, password}))
    setLoggedUser(notUsed => ({username, password}))
    callback && callback()
  }, [])
  const logout = useCallback((callback?: Callback) => {
    setLoggedUser(undefined)
    callback && callback()
  }, [])

  const value = {
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
