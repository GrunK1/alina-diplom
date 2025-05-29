import { Routes, Route, Navigate } from "react-router-dom";
import L from "leaflet";
import "leaflet/dist/leaflet.css";

// Страницы
import HomePage from "./pages/HomePage";
import TasksPage from "./pages/TasksPage";
import MapPage from "./pages/MapPage";
import RouteSheetsPage from "./pages/RouteSheetsPage";
import WorkTypesPage from "./pages/WorkTypesPage";
import RdJournalPage from "./pages/RdJournalPage";

// Layout
import MainLayout from "./layout/MainLayout";

// Настройка иконок Leaflet
delete L.Icon.Default.prototype._getIconUrl;
L.Icon.Default.mergeOptions({
  iconRetinaUrl: require("leaflet/dist/images/marker-icon-2x.png"),
  iconUrl: require("leaflet/dist/images/marker-icon.png"),
  shadowUrl: require("leaflet/dist/images/marker-shadow.png"),
});

function App() {
  return (
    <Routes>
      {/* Перенаправление на /home */}
      <Route path="/" element={<Navigate to="/home" replace />} />

      {/* Все страницы внутри общего layout */}
      <Route path="/" element={<MainLayout />}>
        <Route path="home" element={<HomePage />} />
        <Route path="tasks" element={<TasksPage />} />
        <Route path="map" element={<MapPage />} />
        <Route path="route-sheets" element={<RouteSheetsPage />} />
        <Route path="work-types" element={<WorkTypesPage />} />
        <Route path="permission-documents" element={<RdJournalPage />} />
      </Route>
    </Routes>
  );
}

export default App;
