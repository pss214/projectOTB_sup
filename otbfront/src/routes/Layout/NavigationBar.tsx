import {Link as RRLink} from 'react-router-dom'
import {Link} from '../../components'
import {useAuth} from '../../contexts'

export default function NavigationBar() {
  const {loggedUser} = useAuth()

  return (
    <div className="flex justify-between bg-base-300">
      <div className="flex p-6 navbar ">
        <Link to="/" className="ml-4 text-5xl font-bold btn btn-link">
          OTB(우비)
        </Link>
        
      </div>
      <div className="flex p-2 items-center">
        {!loggedUser && (
          <RRLink to="/login" className="btn btn-sm btn-primary">
            로그인
          </RRLink>
        )}
        {!loggedUser && (
          <RRLink to="/signup" className="ml-4 btn btn-sm btn-outline btn-primary">
            회원 가입
          </RRLink>
        )}
        {loggedUser && (
          <RRLink to="/logout" className="ml-4 mr-4 btn btn-sm btn-link">
            로그 아웃
          </RRLink>
        )}
      </div>
    </div>
  )
}
