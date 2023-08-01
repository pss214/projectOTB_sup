import {Link as RRLink} from 'react-router-dom'
import {Link} from '../../components'
import {useAuth} from '../../contexts'

export default function NavigationBar() {
  const {loggedUser} = useAuth()

  return (
    <div className="flex justify-between bg-lime-200">
      {/* <div className="flex p-6 navbar "/> */}
      <div className="flex p-2 ">
        <Link to="/" className="ml-1">
          <img
            src="/img/otblogogogo.png"
            alt="OTB(우비) 로고"
            className="w-12.5 h-12.5 bg-lime-200"
          />
        </Link>

      </div>
      <div className="flex p-6 items-center">
        {!loggedUser && (
          <RRLink
            to="/login"
            className="flex ml-2 btn btn-primary  text-white border-lime-600 bg-lime-600">
            로그인
          </RRLink>
        )}
        {!loggedUser && (
          <RRLink
            to="/signup"
            className="flex ml-2 btn btn-primary text-white border-lime-600 bg-lime-600">
            회원 가입
          </RRLink>
        )}
        {!loggedUser && (
          <RRLink
            to="/signupdriver"
            className="flex ml-2 btn btn-primary  text-white border-lime-600 bg-lime-600">
            버스 기사 회원 가입
          </RRLink>
        )}
        {loggedUser && (
          <RRLink
            to="/mypage"
            className="flex ml-4 mr-4 btn btn-primary text-white  border-lime-600 bg-lime-600">
            회원 정보
          </RRLink>
        )}

        {loggedUser && (
          <RRLink
            to="/logout"
            className="flex ml-4 mr-4 btn btn-primary text-white  border-lime-600 bg-lime-600">
            로그 아웃
          </RRLink>
        )}
      </div>
    </div>
  )
}
