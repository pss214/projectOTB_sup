import {useCallback} from 'react'
import {useNavigate} from 'react-router-dom'

export default function NoMatch() {
  const navigate = useNavigate()

  const goBack = useCallback(() => {
    navigate(-1)
  }, [navigate])
  return (
    <div className="flex flex-col p-4">
      <p className="text-xl text-center alert alert-error">페이지를 찾을 수 없습니다.</p>
      <div className="flex justify-center mt-4">
        <button className="ml-4 btn btn-primary btn-xs" onClick={goBack}>
          돌아가기
        </button>
      </div>
    </div>
  )
}
