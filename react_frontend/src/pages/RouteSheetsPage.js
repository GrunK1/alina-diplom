import React, { useEffect, useState } from "react";
import axios from "axios";
import { useSearchParams, useNavigate } from "react-router-dom";

const priorityMap = {
  Низкий: "LOW",
  Средний: "MEDIUM",
  "Средний+": "MEDIUM_PLUS",
  Высокий: "HIGH",
  Критический: "CRITICAL",
};

const RouteSheetsPage = () => {
  const [searchParams] = useSearchParams();
  const navigate = useNavigate();
  const mlNumber = searchParams.get("ml");

  const [allRouteLists, setAllRouteLists] = useState([]);
  const [selectedRouteList, setSelectedRouteList] = useState(null);
  const [tasks, setTasks] = useState([]);
  const [permissionDocs, setPermissionDocs] = useState([]);
  const [workTypes, setWorkTypes] = useState([]);
  const [employees, setEmployees] = useState([]);
  const [formData, setFormData] = useState({
    pdId: "",
    wtId: "",
    assigneeId: "",
  });
  const [formOpenFor, setFormOpenFor] = useState(null);
  const [message, setMessage] = useState("");

  useEffect(() => {
    const fetchAll = async () => {
      try {
        const [mlRes, tasksRes, pdRes, wtRes, empRes] = await Promise.all([
          axios.get("http://localhost:8080/api/route-lists/enriched"),
          axios.get("http://localhost:8080/api/tasks"),
          axios.get("http://localhost:8080/api/permission-documents"),
          axios.get("http://localhost:8080/api/work-types"),
          axios.get("http://localhost:8080/api/employees/inspectors"),
        ]);
        setAllRouteLists(mlRes.data);
        setTasks(tasksRes.data);
        setPermissionDocs(pdRes.data);
        setWorkTypes(wtRes.data);
        setEmployees(empRes.data);

        if (mlNumber) {
          const selected = mlRes.data.find(
            (r) => r.mlNumber.toString() === mlNumber
          );
          setSelectedRouteList(selected || null);
        }
      } catch (err) {
        console.error("Ошибка загрузки:", err);
      }
    };

    fetchAll();
  }, [mlNumber]);

  const formatDate = (date) =>
    date ? new Date(date).toLocaleDateString("ru-RU") : "-";

  const handleSubmit = async (serialNumber, priority) => {
    const alreadyExists = tasks.some(
      (t) =>
        t.mlNumber.toString() === mlNumber && t.puSerialNumber === serialNumber
    );

    if (alreadyExists) {
      setMessage(`Задание по ПУ ${serialNumber} уже создано`);
      return;
    }

    try {
      await axios.post("http://localhost:8080/api/tasks", {
        address: "—",
        comment: "",
        mlNumber: selectedRouteList.mlNumber,
        pdId: Number(formData.pdId),
        wtId: Number(formData.wtId),
        assigneeId: Number(formData.assigneeId),
        priorityId: priorityMap[priority],
        puSerialNumber: serialNumber,
      });

      const updatedTasks = await axios.get("http://localhost:8080/api/tasks");
      setTasks(updatedTasks.data);
      setFormOpenFor(null);
      setMessage("Задание успешно создано");
    } catch (err) {
      console.error("Ошибка создания задания:", err);
      alert("Ошибка при создании задания");
    }
  };

  const isAssigned = (serialNumber) =>
    tasks.some(
      (t) =>
        t.mlNumber.toString() === mlNumber && t.puSerialNumber === serialNumber
    );

  if (mlNumber && !selectedRouteList) return <p className="p-8">Загрузка...</p>;

  return (
    <div className="p-8">
      {!mlNumber && (
        <>
          <h1 className="text-3xl font-bold mb-6">Все маршрутные листы</h1>
          <div className="space-y-4">
            {allRouteLists.map((ml) => (
              <div
                key={ml.mlNumber}
                className="p-4 bg-white rounded shadow border"
              >
                <div className="flex justify-between items-center">
                  <div>
                    <p className="text-sm font-semibold">
                      Маршрутный лист №{ml.mlNumber}
                    </p>
                    <p className="text-xs text-gray-500">
                      Период: {formatDate(ml.plannedStartDate)} –{" "}
                      {formatDate(ml.plannedEndDate)}
                    </p>
                  </div>
                  <button
                    className="text-blue-600 border border-blue-600 px-3 py-1 rounded text-xs hover:bg-blue-50"
                    onClick={() => navigate(`/route-sheets?ml=${ml.mlNumber}`)}
                  >
                    Посмотреть
                  </button>
                </div>
              </div>
            ))}
          </div>
        </>
      )}

      {mlNumber && selectedRouteList && (
        <>
          <h1 className="text-3xl font-bold mb-6">
            Маршрутный лист №{selectedRouteList.mlNumber}
          </h1>
          <p className="text-sm text-gray-600 mb-4">
            Период выполнения: {formatDate(selectedRouteList.plannedStartDate)}{" "}
            – {formatDate(selectedRouteList.plannedEndDate)}
          </p>

          {message && (
            <div className="bg-yellow-100 text-yellow-800 px-4 py-2 rounded mb-4">
              {message}
            </div>
          )}

          <div className="space-y-4">
            {selectedRouteList.puPolls.map((pu) => (
              <div
                key={pu.puSerialNumber}
                className="bg-white p-4 rounded shadow border space-y-2"
              >
                <div className="flex justify-between items-center">
                  <div>
                    <p className="font-semibold text-sm">
                      ПУ: {pu.puSerialNumber}
                    </p>
                    <p className="text-xs text-gray-600">
                      Приоритет: {pu.priority}
                    </p>
                  </div>
                  {isAssigned(pu.puSerialNumber) ? (
                    <span className="text-xs text-gray-500 italic">
                      Задание уже создано
                    </span>
                  ) : (
                    <button
                      className="text-blue-600 text-xs border border-blue-600 px-3 py-1 rounded hover:bg-blue-50"
                      onClick={() => {
                        setMessage("");
                        setFormOpenFor(pu.puSerialNumber);
                      }}
                    >
                      Создать задание
                    </button>
                  )}
                </div>

                {formOpenFor === pu.puSerialNumber &&
                  !isAssigned(pu.puSerialNumber) && (
                    <div className="mt-4 border-t pt-4 space-y-3">
                      <div>
                        <label className="text-xs block mb-1">
                          Разрешающий документ
                        </label>
                        <select
                          className="border rounded w-full px-2 py-1 text-xs"
                          value={formData.pdId}
                          onChange={(e) =>
                            setFormData({
                              ...formData,
                              pdId: e.target.value,
                            })
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
                            setFormData({
                              ...formData,
                              wtId: e.target.value,
                            })
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
                        <label className="text-xs block mb-1">
                          Ответственный
                        </label>
                        <select
                          className="border rounded w-full px-2 py-1 text-xs"
                          value={formData.assigneeId}
                          onChange={(e) =>
                            setFormData({
                              ...formData,
                              assigneeId: e.target.value,
                            })
                          }
                        >
                          <option value="">Выберите</option>
                          {employees.map((e) => (
                            <option key={e.empId} value={e.empId}>
                              {e.second_name} {e.name} {e.middle_name}
                            </option>
                          ))}
                        </select>
                      </div>

                      <div className="flex gap-2 pt-2">
                        <button
                          onClick={() =>
                            handleSubmit(pu.puSerialNumber, pu.priority)
                          }
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
        </>
      )}
    </div>
  );
};

export default RouteSheetsPage;
