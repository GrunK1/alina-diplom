// src/pages/RouteSheetsPage.js
import React, { useEffect, useState } from "react";
import axios from "axios";
import { useSearchParams } from "react-router-dom";

const priorityMap = {
  Низкий: "LOW",
  Средний: "MEDIUM",
  "Средний+": "MEDIUM_PLUS",
  Высокий: "HIGH",
  Критический: "CRITICAL",
};

const RouteSheetsPage = () => {
  const [searchParams] = useSearchParams();
  const mlNumber = searchParams.get("ml");

  const [routeList, setRouteList] = useState(null);
  const [formOpenFor, setFormOpenFor] = useState(null);
  const [formData, setFormData] = useState({
    pdId: "",
    wtId: "",
    assigneeId: "",
  });
  const [permissionDocs, setPermissionDocs] = useState([]);
  const [workTypes, setWorkTypes] = useState([]);
  const [employees, setEmployees] = useState([]);

  useEffect(() => {
    axios.get("http://localhost:8080/api/route-lists/enriched").then((res) => {
      const found = res.data.find((r) => r.mlNumber.toString() === mlNumber);
      setRouteList(found);
    });
    axios
      .get("http://localhost:8080/api/permission-documents")
      .then((res) => setPermissionDocs(res.data));
    axios
      .get("http://localhost:8080/api/work-types")
      .then((res) => setWorkTypes(res.data));
    axios
      .get("http://localhost:8080/api/employees")
      .then((res) => setEmployees(res.data));
  }, [mlNumber]);

  const handleSubmit = async (serialNumber, priority) => {
    try {
      await axios.post("http://localhost:8080/api/tasks", {
        address: "—",
        comment: "",
        mlNumber: routeList.mlNumber,
        pdId: Number(formData.pdId),
        wtId: Number(formData.wtId),
        assigneeId: Number(formData.assigneeId),
        priorityId: priorityMap[priority],
      });
      alert("Задание создано");
      setFormOpenFor(null);
    } catch (err) {
      console.error("Ошибка создания задания:", err);
      alert("Ошибка создания задания");
    }
  };

  if (!routeList) return <p className="p-8">Загрузка...</p>;

  const formatDate = (date) =>
    date ? new Date(date).toLocaleDateString("ru-RU") : "-";

  return (
    <div className="p-8">
      <h1 className="text-3xl font-bold mb-6">
        Маршрутный лист №{routeList.mlNumber}
      </h1>
      <p className="text-sm text-gray-600 mb-4">
        Период выполнения: {formatDate(routeList.plannedStartDate)} –{" "}
        {formatDate(routeList.plannedEndDate)}
      </p>
      <div className="space-y-4">
        {routeList.puPolls.map((pu) => (
          <div
            key={pu.puSerialNumber}
            className="bg-white p-4 rounded shadow border space-y-2"
          >
            <div className="flex justify-between items-center">
              <div>
                <p className="font-semibold text-sm">ПУ: {pu.puSerialNumber}</p>
                <p className="text-xs text-gray-600">
                  Приоритет: {pu.priority}
                </p>
              </div>
              <button
                className="text-blue-600 text-xs border border-blue-600 px-3 py-1 rounded hover:bg-blue-50"
                onClick={() => setFormOpenFor(pu.puSerialNumber)}
              >
                Создать задание
              </button>
            </div>

            {formOpenFor === pu.puSerialNumber && (
              <div className="mt-4 border-t pt-4 space-y-3">
                <div>
                  <label className="text-xs block mb-1">
                    Разрешающий документ
                  </label>
                  <select
                    className="border rounded w-full px-2 py-1 text-xs"
                    value={formData.pdId}
                    onChange={(e) =>
                      setFormData({ ...formData, pdId: e.target.value })
                    }
                  >
                    <option value="">Выберите</option>
                    {permissionDocs.map((p) => (
                      <option key={p.pdId} value={p.pdId}>
                        {p.pdType}
                      </option>
                    ))}
                  </select>
                </div>
                <div>
                  <label className="text-xs block mb-1">Вид работ</label>
                  <select
                    className="border rounded w-full px-2 py-1 text-xs"
                    value={formData.wtId}
                    onChange={(e) =>
                      setFormData({ ...formData, wtId: e.target.value })
                    }
                  >
                    <option value="">Выберите</option>
                    {workTypes.map((w) => (
                      <option key={w.wtId} value={w.wtId}>
                        {w.wtType}
                      </option>
                    ))}
                  </select>
                </div>
                <div>
                  <label className="text-xs block mb-1">Ответственный</label>
                  <select
                    className="border rounded w-full px-2 py-1 text-xs"
                    value={formData.assigneeId}
                    onChange={(e) =>
                      setFormData({ ...formData, assigneeId: e.target.value })
                    }
                  >
                    <option value="">Выберите</option>
                    {employees.map((e) => (
                      <option key={e.empId} value={e.empId}>
                        {e.name} {e.middleName} {e.secondName}
                      </option>
                    ))}
                  </select>
                </div>

                <div className="flex gap-2 pt-2">
                  <button
                    onClick={() => handleSubmit(pu.puSerialNumber, pu.priority)}
                    className="bg-blue-600 text-white px-4 py-1 text-sm rounded hover:bg-blue-700"
                  >
                    Подтвердить
                  </button>
                  <button
                    onClick={() => setFormOpenFor(null)}
                    className="border px-4 py-1 text-sm rounded"
                  >
                    Отмена
                  </button>
                </div>
              </div>
            )}
          </div>
        ))}
      </div>
    </div>
  );
};

export default RouteSheetsPage;
