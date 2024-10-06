import './App.css';
import {RouterProvider} from "react-router-dom"
import router from "./routes/routing"
import { AuthProvider } from './pages/Login/AuthContext';
import ProtectedRoute from './pages/Login/ProtectedRoute';

const App = () => {
  return (
          <AuthProvider>
            <RouterProvider router={router} />
          </AuthProvider>
  );
}

export default App;
