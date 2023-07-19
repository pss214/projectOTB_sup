import { Provider as ReduxProvider } from 'react-redux';
import { DndProvider } from 'react-dnd';
import { HTML5Backend } from 'react-dnd-html5-backend';
import { BrowserRouter } from 'react-router-dom';
import { AuthProvider } from './contexts';
import RoutesSetup from './routes/RoutesSetup';
import { useStore } from './store';
import useGeoLocation from './useGeolocation';

export default function App() {
  const store = useStore();
  const location = useGeoLocation();

  return (
    <ReduxProvider store={store}>
      <DndProvider backend={HTML5Backend}>
        <BrowserRouter>
          <AuthProvider>
            <div className="App">
              {location.loaded
                ? JSON.stringify(location)
                : "Location data not available yet."}
            </div>
            <RoutesSetup />
          </AuthProvider>
        </BrowserRouter>
      </DndProvider>
    </ReduxProvider>
  );
}