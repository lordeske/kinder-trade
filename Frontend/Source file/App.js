
import Login from './komponente/Login'; 
import Navbar from './komponente/Navbar'; 

import { BrowserRouter as Router, Route, Routes, Navigate } from 'react-router-dom';  
import ProfilKorisnika from './komponente/ProfilKorisnika';
import './App.css';
import Pocetna from './komponente/Pocetna';
import Figurica from './komponente/Figurica';




function App() {
  return (
    <Router>
      <div className="app-container">
      <Navbar />
        <Routes>
          <Route path="/login" element={<Login />} />
          <Route path="/pocetna" element={<Pocetna />} />
          <Route path="/profil/:korisnickoIme" element={<ProfilKorisnika />} />
          <Route path="/figurica/:idFigurice" element={<Figurica />} />
          <Route path="*" element={<Navigate to="/login" />} />
        </Routes>
      </div>
    </Router>
  );
}

export default App;
