
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
import AzurirajFiguricu from './komponente/AzurirajFiguricu';
import KreirajRecenziju from './komponente/KreirajRecenziju';
import ListaRazgovora from './komponente/ListaRazgovora';

import JavniChat from './komponente/JavniChat';
import PrivatniChat from './komponente/PrivatniChat';
import KreirajTrgovinu from './komponente/KreirajTrgovinu';
import MojeTrgovine from './komponente/MojeTrgovine';
import KontraTrgovina from './komponente/KontraTrgovina';





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
                <Route path="/kreiraj-recenziju/:korisnickoIme" element={<KreirajRecenziju />} />
                <Route path="/azuriraj-figuricu/:idFigurice" element={<AzurirajFiguricu />} />
                <Route path="/figurica/:idFigurice" element={<Figurica />} />
                <Route path="/moj-profil" element={<MojProfil />} />
                <Route path="/kreiraj-figuricu" element={<KreirajFiguricu />} />
                <Route path="/azuriraj-profil" element={<AzurirajProfil />} />
                <Route path="/moje-figurice" element={<MojeFigurice />} />
                <Route path="/moje-omiljene" element={<MojeOmiljene />} />
                <Route path="/razgovori" element={<ListaRazgovora />} />
                <Route path="/javniChat" element={<JavniChat />} />
                <Route path="/chat/:sagovornik" element={<PrivatniChat />} />
                <Route path="/kreiraj-trgovinu/:korisnickoIme" element={ <KreirajTrgovinu /> }/>
                <Route path="/moje-trgovine" element={ <MojeTrgovine   /> }/>
                <Route path="/kreiraj-kontra-trgovinu/:idTrgovine/:korisnickoIme" element={ <KontraTrgovina   /> }/>
              </Routes>
            </div>
          }
        />
      </Routes>
    </Router>



  );
}

export default App;
