// src/pages/MapPage.js

import React, { useEffect, useState } from "react";
import { MapContainer, TileLayer, Marker } from "react-leaflet";
import L from "leaflet";
import "leaflet/dist/leaflet.css";
import axios from "axios";
import NavigationBarSection from "../components/NavigationBarSection";

const MapPage = () => {
  const [devices, setDevices] = useState([]);
  const [tasks, setTasks] = useState([]);
  const [filters, setFilters] = useState({
    critical: true,
    high: true,
    medium_plus: true,
    medium: true,
    low: true,
    hasMl: false,
  });
  const [selectedDevice, setSelectedDevice] = useState(null);
  const [permissionDocs, setPermissionDocs] = useState([]);
  const [workTypes, setWorkTypes] = useState([]);
  const [employees, setEmployees] = useState([]);
  const [formData, setFormData] = useState({
    pdId: "",
    wtId: "",
    assigneeId: "",
  });
  const [message, setMessage] = useState("");

  const BASE_URL = "http://localhost:8080/api";

  useEffect(() => {
    const fetchData = async () => {
      try {
        const [devRes, taskRes, pdRes, wtRes, empRes, mlRes] =
          await Promise.all([
            axios.get(`${BASE_URL}/record-devices`),
            axios.get(`${BASE_URL}/tasks`),
            axios.get(`${BASE_URL}/permission-documents`),
            axios.get(`${BASE_URL}/work-types`),
            axios.get(`${BASE_URL}/employees/inspectors`),
            axios.get(`${BASE_URL}/route-lists/enriched`),
          ]);

        setTasks(taskRes.data);
        setPermissionDocs(pdRes.data);
        setWorkTypes(wtRes.data);
        setEmployees(empRes.data);

        const mlPuMap = {};
        mlRes.data.forEach((ml) => {
          ml.puPolls.forEach((poll) => {
            mlPuMap[poll.puSerialNumber] = ml.mlNumber;
          });
        });

        const enriched = await Promise.all(
          devRes.data.map(async (device) => {
            try {
              const pollRes = await axios.get(
                `${BASE_URL}/poll-registries/latest`,
                { params: { serial: device.puSerialNumber } }
              );
              const pollDate = pollRes.data.pollDate;
              const priority = getPriorityByPollDate(pollDate);
              return {
                ...device,
                pollDate,
                priority,
                mlNumber: mlPuMap[device.puSerialNumber] || null,
              };
            } catch {
              return {
                ...device,
                pollDate: null,
                priority: "Нет приоритета",
                mlNumber: mlPuMap[device.puSerialNumber] || null,
              };
            }
          })
        );

        setDevices(enriched);
      } catch (error) {
        console.error("Ошибка загрузки данных:", error);
      }
    };

    fetchData();
  }, []);

  const getPriorityByPollDate = (dateStr) => {
    if (!dateStr) return "Нет приоритета";
    const now = new Date();
    const d = new Date(dateStr);
    const diff = Math.floor((now - d) / (1000 * 60 * 60 * 24));
    if (diff >= 46) return "Критический";
    if (diff >= 29) return "Высокий";
    if (diff >= 15) return "Средний+";
    if (diff >= 7) return "Средний";
    if (diff >= 1) return "Низкий";
    return "Нет приоритета";
  };

  const getMarkerIcon = (priority) => {
    const colorMap = {
      Критический: "black",
      Высокий: "violet",
      "Средний+": "red",
      Средний: "orange",
      Низкий: "yellow",
      "Нет приоритета": "gray",
    };

    const color = colorMap[priority] || "gray";

    return new L.Icon({
      iconUrl: require(`../assets/icons/marker-${color}.png`),
      shadowUrl: require("leaflet/dist/images/marker-shadow.png"),
      iconSize: [25, 41],
      iconAnchor: [12, 41],
      popupAnchor: [1, -34],
      shadowSize: [41, 41],
    });
  };

  const toggleFilter = (key) => {
    setFilters({ ...filters, [key]: !filters[key] });
  };

  const filteredDevices = devices.filter((d) => {
    const priorityMap = {
      Критический: "critical",
      Высокий: "high",
      "Средний+": "medium_plus",
      Средний: "medium",
      Низкий: "low",
    };

    const priorityKey = priorityMap[d.priority];
    const matchesPriority = priorityKey ? filters[priorityKey] : true;
    const matchesML = !filters.hasMl || d.mlNumber !== null;

    return matchesPriority && matchesML;
  });

  const handleSubmit = async () => {
    const alreadyExists = tasks.some(
      (t) => t.puSerialNumber === selectedDevice.puSerialNumber
    );
    if (alreadyExists) {
      setMessage("Задание уже создано");
      return;
    }

    try {
      await axios.post(`${BASE_URL}/tasks`, {
        address: selectedDevice.puAddress,
        comment: "",
        mlNumber: selectedDevice.mlNumber || 1,
        pdId: Number(formData.pdId),
        wtId: Number(formData.wtId),
        assigneeId: Number(formData.assigneeId),
        priorityId: priorityToEnum(selectedDevice.priority),
        puSerialNumber: selectedDevice.puSerialNumber,
      });
      setMessage("Задание успешно создано");
    } catch (e) {
      setMessage("Ошибка при создании задания");
    }
  };

  const priorityToEnum = (priority) => {
    return {
      Низкий: "LOW",
      Средний: "MEDIUM",
      "Средний+": "MEDIUM_PLUS",
      Высокий: "HIGH",
      Критический: "CRITICAL",
    }[priority];
  };

  const parseCoordinates = (str) =>
    str?.includes(",") ? str.split(",").map(Number) : [0, 0];

  return (
    <div className="relative h-screen w-screen overflow-hidden">
      <NavigationBarSection />

      <div className="pt-[80px] relative h-full w-full">
        <div className="absolute z-[999] top-0 left-4 bg-white p-4 rounded shadow-md w-64">
          <h3 className="font-bold mb-2">Фильтры</h3>
          {[
            ["Критический", "critical"],
            ["Высокий", "high"],
            ["Средний+", "medium_plus"],
            ["Средний", "medium"],
            ["Низкий", "low"],
          ].map(([label, key]) => (
            <label key={key} className="block text-sm">
              <input
                type="checkbox"
                checked={filters[key]}
                onChange={() => toggleFilter(key)}
              />{" "}
              {label}
            </label>
          ))}
          <label className="block mt-2 text-sm">
            <input
              type="checkbox"
              checked={filters.hasMl}
              onChange={() => toggleFilter("hasMl")}
            />{" "}
            Только с маршрутным листом
          </label>
        </div>

        <MapContainer
          center={[55.76, 37.64]}
          zoom={11}
          className="h-full w-full z-0"
        >
          <TileLayer
            attribution=""
            url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
          />
          {filteredDevices.map((device) => {
            const [lat, lng] = parseCoordinates(device.puCoordinates);
            return (
              <Marker
                key={device.puSerialNumber}
                position={[lat, lng]}
                icon={getMarkerIcon(device.priority)}
                eventHandlers={{ click: () => setSelectedDevice(device) }}
              />
            );
          })}
        </MapContainer>

        {selectedDevice && (
          <div className="absolute top-0 right-0 w-[400px] h-full bg-white shadow-lg p-6 overflow-y-auto z-[1000]">
            <h2 className="text-lg font-bold mb-2">
              Создание задания для {selectedDevice.puSerialNumber}
            </h2>
            <p className="text-sm mb-1">Адрес: {selectedDevice.puAddress}</p>
            <p className="text-sm mb-1">
              Координаты: {selectedDevice.puCoordinates}
            </p>
            <p className="text-sm mb-1">
              Приоритет:{" "}
              <span className="font-medium">{selectedDevice.priority}</span>
            </p>
            <p className="text-sm mb-1">
              Маршрутный лист: {selectedDevice.mlNumber ?? "—"}
            </p>
            <div className="mt-4 space-y-3">
              <div>
                <label className="text-xs block mb-1">Вид работ</label>
                <select
                  className="border w-full px-2 py-1 rounded text-sm"
                  value={formData.wtId}
                  onChange={(e) =>
                    setFormData({ ...formData, wtId: e.target.value })
                  }
                >
                  <option value="">Выберите</option>
                  {workTypes.map((wt) => (
                    <option key={wt.wtId} value={wt.wtId}>
                      {wt.wtType}
                    </option>
                  ))}
                </select>
              </div>
              <div>
                <label className="text-xs block mb-1">
                  Разрешающий документ
                </label>
                <select
                  className="border w-full px-2 py-1 rounded text-sm"
                  value={formData.pdId}
                  onChange={(e) =>
                    setFormData({ ...formData, pdId: e.target.value })
                  }
                >
                  <option value="">Выберите</option>
                  {permissionDocs.map((pd) => (
                    <option key={pd.pdId} value={pd.pdId}>
                      {pd.pdType}
                    </option>
                  ))}
                </select>
              </div>
              <div>
                <label className="text-xs block mb-1">Ответственный</label>
                <select
                  className="border w-full px-2 py-1 rounded text-sm"
                  value={formData.assigneeId}
                  onChange={(e) =>
                    setFormData({ ...formData, assigneeId: e.target.value })
                  }
                >
                  <option value="">Выберите</option>
                  {employees.map((e) => (
                    <option key={e.empId} value={e.empId}>
                      {`${e.second_name ?? ""} ${e.name ?? ""} ${
                        e.middle_name ?? ""
                      }`}
                    </option>
                  ))}
                </select>
              </div>
              <div className="flex justify-between mt-4">
                <button
                  onClick={handleSubmit}
                  className="bg-blue-600 text-white px-4 py-2 rounded hover:bg-blue-700"
                >
                  + Создать задание
                </button>
                <button
                  onClick={() => setSelectedDevice(null)}
                  className="px-4 py-2 border rounded"
                >
                  Отмена
                </button>
              </div>
              {message && (
                <p className="text-sm text-green-600 mt-2">{message}</p>
              )}
            </div>
          </div>
        )}
      </div>
    </div>
  );
};

export default MapPage;
