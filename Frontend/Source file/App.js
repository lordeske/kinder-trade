
import Login from './komponente/Login'; 
import { BrowserRouter as Router, Route, Routes, Navigate } from 'react-router-dom';  


function App() {
  return (
    <Router>
      <div className="">
        <Routes>
          <Route path="/login" element={<Login />} />
          <Route path="*" element={<Navigate to="/login" />} />
        </Routes>
      </div>
    </Router>
  );
}

export default App;
