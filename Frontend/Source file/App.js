
import Login from './komponente/Login'; 
import { BrowserRouter as Router, Route, Routes, Navigate } from 'react-router-dom';  
import ProfilKorisnika from './komponente/ProfilKorisnika';
import './App.css';




function App() {
  return (
    <Router>
      <div className="app-container">
        <Routes>
          <Route path="/login" element={<Login />} />
          <Route path="/profil/:imeKorisnika" element={<ProfilKorisnika />} />
          <Route path="*" element={<Navigate to="/login" />} />
        </Routes>
      </div>
    </Router>
  );
}

export default App;
