import {Routes, Route} from 'react-router-dom'
import Layout from './Layout'
import RequireAuth from './Auth/RequireAuth'
import LandingPage from './LandingPage'
import Board from '../pages/Board'
import Signup from './Auth/SignUp'
import Login from './Auth/Login'
import Logout from './Auth/Logout'
import NoMatch from './NoMatch'
import SignUpDriver from './Auth/SignUpDriver'
import NewsPage from '../routes/LandingPage'

import MyPage from '../pages/User/MyPage'
import Reserve from '../pages/Reservation/Reserve'
import BusMain from '../pages/User/BusMain'
export default function RoutesSetup() {
  return (
    <Routes>
      <Route path="/" element={<Layout />}>
        <Route index element={<LandingPage />} />
        <Route
          path="/board"
          element={
            <RequireAuth>
              <Board />
            </RequireAuth>
          }
        />
        <Route path="*" element={<NoMatch />} />
      </Route>
      <Route path="/signup" element={<Signup />} />
      <Route path="/signupdriver" element={<SignUpDriver />} />
      <Route path="/login" element={<Login />} />
      <Route path="/" />
      <Route path="/mypage" element={<MyPage />} />
      <Route path="/busmain" element={<BusMain />} />
      <Route path="/" element={<NewsPage />} />
      <Route path="/:category" element={<NewsPage />} />
      <Route path="/reserve/" element={<Reserve />} />
      <Route
        path="/logout"
        element={
          <RequireAuth>
            <Logout />
          </RequireAuth>
        }
      />
      <Route path="*" element={<NoMatch />} />
    </Routes>
  )
}
