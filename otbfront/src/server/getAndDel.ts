import {SERVER_URL} from './getServer'

const getAndDel =
  (methodName: string, jwt?: string | null | undefined) =>
  (path: string, jwt?: string | null | undefined) => {
    let headers = {'Content-Type': 'application/json'}
    let init: RequestInit = {
      method: methodName
    }
    if (jwt) {
      init = {
        ...init,
        headers: {...headers, Authorization: 'Bearer${jwt}'}
      }
    } else init = {...init, headers}
    return fetch(SERVER_URL, init)
  }
export const get = () => fetch(SERVER_URL)
export const del = () => fetch(SERVER_URL, {method: 'DELETE'})
