
import Login from './komponente/Login'; 
import Navbar from './komponente/Navbar'; 

import { BrowserRouter as Router, Route, Routes, Navigate } from 'react-router-dom';  
import ProfilKorisnika from './komponente/ProfilKorisnika';
import './App.css';
import Pocetna from './komponente/Pocetna';
import Figurica from './komponente/Figurica';
import LoginStrana from './komponente/LoginStrana';
import MojProfil from './komponente/MojProfil';
import KreirajFiguricu from './komponente/KreirajFiguricu';
import AzurirajProfil from './komponente/AzurirajProfil';
import MojeFigurice from './komponente/MojeFigurice';
import MojeOmiljene from './komponente/MojeOmiljene';




function App() {
  return (
    <Router>
  <Routes>
   
    <Route path="/login" element={<Login />} />
    <Route path="/loginStrana" element={<LoginStrana />} />

    
    <Route
      path="/*"
      element={
        <div className="app-container">
          <Navbar />
          <Routes>
            <Route path="/pocetna" element={<Pocetna />} />
            <Route path="/profil/:korisnickoIme" element={<ProfilKorisnika />} />
            <Route path="/figurica/:idFigurice" element={<Figurica />} />
            <Route path="/moj-profil" element={<MojProfil />} />
            <Route path="/kreiraj-figuricu" element={<KreirajFiguricu />} />
            <Route path="/azuriraj-profil" element={<AzurirajProfil />} />
            <Route path="/moje-figurice" element={<MojeFigurice />} />
            <Route path="/moje-omiljene" element={<MojeOmiljene />} />
            <Route path="*" element={<Navigate to="/login" />} />

          </Routes>
        </div>
      }
    />
  </Routes>
</Router>

  );
}

export default App;
