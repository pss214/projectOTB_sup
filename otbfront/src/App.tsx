import {Provider as ReduxProvider} from 'react-redux'
import {DndProvider} from 'react-dnd'
import {HTML5Backend} from 'react-dnd-html5-backend'
import {BrowserRouter} from 'react-router-dom'
import {AuthProvider} from './contexts'
import RoutesSetup from './routes/RoutesSetup'
import useGeoLocation from './useGeolocation'
import {useStore} from './store'

export default function App() {
  const store = useStore()
  const location = useGeoLocation()

  return (
    <ReduxProvider store={store}>
      <DndProvider backend={HTML5Backend}>
        <BrowserRouter>
          <AuthProvider>
            <div className="App">
              {location.loaded
                ? JSON.stringify(location)
                : 'Location data not available yet.'}
              : "위치를 찾을 수 없습니다."
            </div>
            <RoutesSetup />
            <footer>
              <div className="text-center p-4 bg-lime-700 text-primary-100 fixed-bottom">
                <p className="text-white">
                  OTB(우비) 프런트 : 김동욱, 안장훈 / 백 : 박성수, 강선혁 Git : //
                  https://github.com/DwKmm3/projectOTB
                </p>
              </div>
            </footer>
          </AuthProvider>
        </BrowserRouter>
      </DndProvider>
    </ReduxProvider>
  )
}

