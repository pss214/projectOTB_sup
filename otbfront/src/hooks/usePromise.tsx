import {useEffect, useState} from 'react'

type PromiseCreator = () => Promise<any>

export default function usePromise(promiseCreateor: PromiseCreator, deps: any[]) {
  const [loading, setLoading] = useState(false)
  const [resolved, setResolved] = useState<any>(null)
  const [error, setError] = useState<any>(null)

  useEffect(() => {
    async function process() {
      setLoading(true)
      try {
        const resolved = await promiseCreateor()
        setResolved(resolved)
      } catch (e) {
        setError(e)
      }
      setLoading(false)
    }
    process()
  }, deps)

  return [loading, resolved, error]
}
