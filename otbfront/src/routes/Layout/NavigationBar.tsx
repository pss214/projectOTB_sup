import {Link as RRLink} from 'react-router-dom'
import {Link} from '../../components'
import {useAuth} from '../../contexts'

export default function NavigationBar() {
  const {loggedUser} = useAuth()

  return (
    <div className="flex justify-between bg-lime-200">
      <div className="flex p-6 navbar ">
      <Link to="/" className="ml-1">
  <img
    src="/img/otblogogogo.png"
    alt="OTB(우비) 로고"
    className="w-12.5 h-12.5 bg-lime-200"
  />
</Link>
        {loggedUser && (
          <Link to="/board" className="btn btn-link ml-4 font-bold text-lime-700">
            버스 이용하기
          </Link>
        )}
      </div>
      <div className="flex p-2 items-center">
        {!loggedUser && (
          <RRLink to="/login" className="btn btn-sm text-white border-lime-600 bg-lime-600">
            로그인
          </RRLink>
        )}
        {!loggedUser && (
          <RRLink to="/signup" className="ml-4 btn btn-sm btn-outline text-white border-lime-600 bg-lime-600">
            회원 가입
          </RRLink>
        )}
        {loggedUser && (
          <RRLink to="/logout" className="ml-4 mr-4 btn btn-sm btn-link text-lime-700">
            로그 아웃
          </RRLink>
        )}
      </div>
    </div>
  )
}
