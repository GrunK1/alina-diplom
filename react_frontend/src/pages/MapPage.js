// pages/MapPage.js
import React, { useEffect, useState } from "react";
import { MapContainer, TileLayer, Marker } from "react-leaflet";
import L from "leaflet";
import "leaflet/dist/leaflet.css";
import axios from "axios";

const createColoredIcon = (color) =>
  new L.Icon({
    iconUrl: require(`leaflet/dist/images/marker-icon.png`),
    iconSize: [25, 41],
    iconAnchor: [12, 41],
    popupAnchor: [1, -34],
    shadowUrl: require("leaflet/dist/images/marker-shadow.png"),
    shadowSize: [41, 41],
    className: `leaflet-marker-icon-${color}`,
  });

const MapPage = () => {
  const [devices, setDevices] = useState([]);
  const [filters, setFilters] = useState({
    critical: true,
    medium: true,
    low: true,
  });
  const [selectedDevice, setSelectedDevice] = useState(null);

  const BASE_URL = "http://localhost:8080/api";

  useEffect(() => {
    const fetchData = async () => {
      const res = await axios.get(`${BASE_URL}/record-devices`);
      const rawDevices = res.data;

      const enrichedDevices = await Promise.all(
        rawDevices.map(async (device) => {
          try {
            const pollRes = await axios.get(
              `${BASE_URL}/poll-registries/latest`,
              {
                params: { serial: device.puSerialNumber },
              }
            );
            const pollDate = pollRes.data.pollDate;
            const priority = getPriorityByPollDate(pollDate);
            return { ...device, pollDate, priority };
          } catch (e) {
            return { ...device, pollDate: null, priority: "Низкий" };
          }
        })
      );

      setDevices(enrichedDevices);
    };

    fetchData();
  }, []);

  const getPriorityByPollDate = (pollDateStr) => {
    if (!pollDateStr) return "Низкий";
    const now = new Date();
    const pollDate = new Date(pollDateStr);
    const diffDays = Math.floor((now - pollDate) / (1000 * 60 * 60 * 24));

    if (diffDays <= 6) return "Низкий";
    if (diffDays <= 28) return "Средний";
    return "Критический";
  };

  const parseCoordinates = (coordStr) => {
    const [lat, lng] = coordStr.split(",").map(Number);
    return [lat, lng];
  };

  const toggleFilter = (key) => {
    setFilters((prev) => ({ ...prev, [key]: !prev[key] }));
  };

  const filteredDevices = devices.filter((device) => {
    if (device.priority === "Критический" && filters.critical) return true;
    if (device.priority === "Средний" && filters.medium) return true;
    if (device.priority === "Низкий" && filters.low) return true;
    return false;
  });

  const getMarkerIcon = (priority) => {
    if (priority === "Критический") return createColoredIcon("red");
    if (priority === "Средний") return createColoredIcon("yellow");
    return createColoredIcon("green");
  };

  return (
    <div style={{ display: "flex", height: "100vh", width: "100vw" }}>
      {/* Левая часть */}
      <div style={{ flex: 1, position: "relative" }}>
        <div
          style={{
            position: "absolute",
            top: 16,
            left: 16,
            zIndex: 1000,
            background: "white",
            padding: 16,
            borderRadius: 8,
            boxShadow: "0 2px 6px rgba(0,0,0,0.15)",
          }}
        >
          <h3 style={{ fontWeight: "bold", marginBottom: 8 }}>Фильтры</h3>
          <div style={{ display: "flex", flexDirection: "column", gap: 8 }}>
            <label>
              <input
                type="checkbox"
                checked={filters.critical}
                onChange={() => toggleFilter("critical")}
              />{" "}
              Критический
            </label>
            <label>
              <input
                type="checkbox"
                checked={filters.medium}
                onChange={() => toggleFilter("medium")}
              />{" "}
              Средний
            </label>
            <label>
              <input
                type="checkbox"
                checked={filters.low}
                onChange={() => toggleFilter("low")}
              />{" "}
              Низкий
            </label>
          </div>
        </div>

        <MapContainer
          center={[55.76, 37.64]}
          zoom={10}
          style={{ height: "100vh", width: "100%" }}
        >
          <TileLayer
            attribution='&copy; <a href="https://www.openstreetmap.org/">OpenStreetMap</a>'
            url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
          />

          {filteredDevices.map((device) => {
            const [lat, lng] = parseCoordinates(device.puCoordinates);
            return (
              <Marker
                key={device.puSerialNumber}
                position={[lat, lng]}
                icon={getMarkerIcon(device.priority)}
                eventHandlers={{
                  click: () => setSelectedDevice(device),
                }}
              />
            );
          })}
        </MapContainer>
      </div>

      {/* Правая панель */}
      {selectedDevice && (
        <div
          style={{
            width: 400,
            height: "100vh",
            background: "white",
            padding: 16,
            overflowY: "auto",
            boxShadow: "-2px 0 6px rgba(0,0,0,0.15)",
          }}
        >
          <div
            style={{
              display: "flex",
              justifyContent: "space-between",
              alignItems: "center",
              marginBottom: 16,
            }}
          >
            <h2 style={{ fontSize: 20, fontWeight: "bold" }}>
              Информация о приборе
            </h2>
            <button
              onClick={() => setSelectedDevice(null)}
              style={{ fontSize: 18, color: "gray" }}
            >
              ×
            </button>
          </div>
          <div style={{ fontSize: 14, lineHeight: 1.5 }}>
            <p>
              <strong>Серийный №:</strong> {selectedDevice.puSerialNumber}
            </p>
            <p>
              <strong>Адрес:</strong> {selectedDevice.puAddress}
            </p>
            <p>
              <strong>Координаты:</strong> {selectedDevice.puCoordinates}
            </p>
            <p>
              <strong>Последний опрос:</strong> {selectedDevice.pollDate || "—"}
            </p>
            <p>
              <strong>Приоритет:</strong> {selectedDevice.priority}
            </p>
          </div>
        </div>
      )}
    </div>
  );
};

export default MapPage;
