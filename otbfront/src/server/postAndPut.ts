import {SERVER_URL} from './getServer'

const postAndPut =
  (methodName: string) =>
  (path: string, data: object, jwt?: string | null | undefined) => {
    let headers = {'Content-Type': 'application/json'}
    let init: RequestInit = {
      method: methodName,
      body: JSON.stringify(data)
      //cache: 'no-cache',
      //credentials: 'same-origin'
    }
    if (jwt) {
      init = {
        ...init,
        headers: {...headers, Authorization: 'Bearer${jwt}'}
      }
    } else init = {...init, headers}
    return fetch(SERVER_URL, init)
  }
export const post = postAndPut('POST')
export const put = postAndPut('PUT')
