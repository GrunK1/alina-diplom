import React, { useEffect, useState } from "react";
import axios from "axios";
import NavigationBarSection from "../components/NavigationBarSection";
import PriorityIndicatorSection from "../components/PriorityIndicatorSection";
import DashboardViewSection from "../components/DashboardViewSection";

const priorityLabelMap = {
  LOW: "Низкий",
  MEDIUM: "Средний",
  MEDIUM_PLUS: "Средний+",
  HIGH: "Высокий",
  CRITICAL: "Критический",
};

const TasksPage = () => {
  const [tasks, setTasks] = useState([]);
  const [filteredTasks, setFilteredTasks] = useState([]);
  const [filters, setFilters] = useState({
    id: "",
    assignee: "",
    priority: "",
    status: "",
    startDateFrom: "",
    startDateTo: "",
    endDateFrom: "",
    endDateTo: "",
    createdFrom: "",
    createdTo: "",
  });

  useEffect(() => {
    const fetchAll = async () => {
      try {
        const [tasksRes, routeListsRes] = await Promise.all([
          axios.get("http://localhost:8080/api/tasks"),
          axios.get("http://localhost:8080/api/route-lists/enriched"),
        ]);

        const routeLists = routeListsRes.data;

        const enrichedTasks = tasksRes.data.map((task) => {
          const routeList = routeLists.find((rl) =>
            rl.puPolls?.some((pu) => pu.puSerialNumber === task.puSerialNumber)
          );

          const pu =
            routeList?.puPolls?.find(
              (pu) => pu.puSerialNumber === task.puSerialNumber
            ) || {};

          return {
            ...task,
            startDate: routeList?.plannedStartDate || null,
            endDate: routeList?.plannedEndDate || null,
            priority: pu.priority || task.priorityId,
            status: task.status || "Запланировано",
          };
        });

        setTasks(enrichedTasks);
        setFilteredTasks(enrichedTasks);
      } catch (err) {
        console.error("Ошибка загрузки задач:", err);
      }
    };

    fetchAll();
  }, []);

  const handleFilter = () => {
    const filtered = tasks.filter((task) => {
      const assigneeFullName = task.assignee
        ? `${task.assignee.second_name ?? ""} ${task.assignee.name ?? ""} ${
            task.assignee.middle_name ?? ""
          }`.toLowerCase()
        : "";

      return (
        (filters.id === "" ||
          task.taskNumber?.toString().includes(filters.id)) &&
        (filters.assignee === "" ||
          assigneeFullName.includes(filters.assignee.toLowerCase())) &&
        (filters.priority === "" ||
          priorityLabelMap[task.priorityId]?.toLowerCase() ===
            filters.priority.toLowerCase()) &&
        (filters.status === "" ||
          task.status?.toLowerCase() === filters.status.toLowerCase()) &&
        (!filters.startDateFrom ||
          new Date(task.startDate) >= new Date(filters.startDateFrom)) &&
        (!filters.startDateTo ||
          new Date(task.startDate) <= new Date(filters.startDateTo)) &&
        (!filters.endDateFrom ||
          new Date(task.endDate) >= new Date(filters.endDateFrom)) &&
        (!filters.endDateTo ||
          new Date(task.endDate) <= new Date(filters.endDateTo)) &&
        (!filters.createdFrom ||
          new Date(task.dateOfCreation) >= new Date(filters.createdFrom)) &&
        (!filters.createdTo ||
          new Date(task.dateOfCreation) <= new Date(filters.createdTo))
      );
    });

    setFilteredTasks(filtered);
  };

  const handleClear = () => {
    setFilters({
      id: "",
      assignee: "",
      priority: "",
      status: "",
      startDateFrom: "",
      startDateTo: "",
      endDateFrom: "",
      endDateTo: "",
      createdFrom: "",
      createdTo: "",
    });
    setFilteredTasks(tasks);
  };

  const stats = {
    total: tasks.length,
    inProgress: tasks.filter((t) => t.status === "В работе").length,
    completed: tasks.filter((t) => t.status === "Выполнено").length,
  };

  const formatDate = (dateStr) =>
    dateStr ? new Date(dateStr).toLocaleDateString("ru-RU") : "-";

  return (
    <>
      <NavigationBarSection />

      <div className="p-6">
        <h1 className="text-4xl font-bold mb-6">Задания</h1>
        <DashboardViewSection stats={stats} />

        {/* Фильтры */}
        <div className="p-4 bg-gray-100 rounded-lg mb-4">
          <h2 className="text-xl font-semibold mb-2">Фильтры</h2>
          <div className="grid grid-cols-2 md:grid-cols-4 gap-4">
            <div>
              <label className="text-sm block mb-1">Номер задания</label>
              <input
                type="text"
                className="border p-2 rounded w-full"
                value={filters.id}
                onChange={(e) => setFilters({ ...filters, id: e.target.value })}
              />
            </div>
            <div>
              <label className="text-sm block mb-1">Исполнитель</label>
              <input
                type="text"
                className="border p-2 rounded w-full"
                value={filters.assignee}
                onChange={(e) =>
                  setFilters({ ...filters, assignee: e.target.value })
                }
              />
            </div>
            <div>
              <label className="text-sm block mb-1">Приоритет</label>
              <input
                type="text"
                className="border p-2 rounded w-full"
                value={filters.priority}
                onChange={(e) =>
                  setFilters({ ...filters, priority: e.target.value })
                }
              />
            </div>
            <div>
              <label className="text-sm block mb-1">Статус</label>
              <input
                type="text"
                className="border p-2 rounded w-full"
                value={filters.status}
                onChange={(e) =>
                  setFilters({ ...filters, status: e.target.value })
                }
              />
            </div>
            <div>
              <label className="text-sm block mb-1">Дата начала (с)</label>
              <input
                type="date"
                className="border p-2 rounded w-full"
                value={filters.startDateFrom}
                onChange={(e) =>
                  setFilters({ ...filters, startDateFrom: e.target.value })
                }
              />
            </div>
            <div>
              <label className="text-sm block mb-1">Дата начала (до)</label>
              <input
                type="date"
                className="border p-2 rounded w-full"
                value={filters.startDateTo}
                onChange={(e) =>
                  setFilters({ ...filters, startDateTo: e.target.value })
                }
              />
            </div>
            <div>
              <label className="text-sm block mb-1">Дата окончания (с)</label>
              <input
                type="date"
                className="border p-2 rounded w-full"
                value={filters.endDateFrom}
                onChange={(e) =>
                  setFilters({ ...filters, endDateFrom: e.target.value })
                }
              />
            </div>
            <div>
              <label className="text-sm block mb-1">Дата окончания (до)</label>
              <input
                type="date"
                className="border p-2 rounded w-full"
                value={filters.endDateTo}
                onChange={(e) =>
                  setFilters({ ...filters, endDateTo: e.target.value })
                }
              />
            </div>
            <div>
              <label className="text-sm block mb-1">Дата создания (с)</label>
              <input
                type="date"
                className="border p-2 rounded w-full"
                value={filters.createdFrom}
                onChange={(e) =>
                  setFilters({ ...filters, createdFrom: e.target.value })
                }
              />
            </div>
            <div>
              <label className="text-sm block mb-1">Дата создания (до)</label>
              <input
                type="date"
                className="border p-2 rounded w-full"
                value={filters.createdTo}
                onChange={(e) =>
                  setFilters({ ...filters, createdTo: e.target.value })
                }
              />
            </div>
          </div>

          <div className="mt-4 flex gap-4">
            <button
              onClick={handleFilter}
              className="bg-blue-500 text-white px-4 py-2 rounded"
            >
              Применить
            </button>
            <button onClick={handleClear} className="border px-4 py-2 rounded">
              Очистить
            </button>
          </div>
        </div>

        {/* Заголовки таблицы */}
        <div className="grid grid-cols-7 gap-4 px-4 py-2 bg-blue-50 text-blue-900 font-semibold text-sm rounded-t">
          <div>Номер задания</div>
          <div>ПУ</div>
          <div>Приоритет</div>
          <div>Исполнитель</div>
          <div>Дата начала</div>
          <div>Дата окончания</div>
          <div>Статус</div>
        </div>

        {/* Список заданий */}
        <div className="bg-white shadow rounded-b divide-y">
          {filteredTasks.map((task) => (
            <div
              key={task.taskNumber ?? task.id}
              className="grid grid-cols-7 gap-4 px-4 py-2 text-sm text-gray-800 items-center hover:bg-gray-50"
            >
              <div>{task.taskNumber ?? task.id}</div>
              <div>{task.puSerialNumber || "-"}</div>
              <div>
                <PriorityIndicatorSection
                  priority={priorityLabelMap[task.priority] || task.priority}
                />
              </div>
              <div>
                {task.assignee
                  ? `${task.assignee.second_name ?? ""} ${
                      task.assignee.name ?? ""
                    } ${task.assignee.middle_name ?? ""}`
                  : "-"}
              </div>
              <div>{formatDate(task.startDate)}</div>
              <div>{formatDate(task.endDate)}</div>
              <div>{task.status || "Запланировано"}</div>
            </div>
          ))}
        </div>
      </div>
    </>
  );
};

export default TasksPage;
