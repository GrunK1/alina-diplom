import React, { useEffect, useState } from "react";
import { fetchTasks } from "../services/tasksApi";
import NavigationBarSection from "../components/NavigationBarSection";
import PriorityIndicatorSection from "../components/PriorityIndicatorSection";
import DashboardViewSection from "../components/DashboardViewSection";

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
  });

  useEffect(() => {
    fetchTasks().then((data) => {
      setTasks(data);
      setFilteredTasks(data);
    });
  }, []);

  const handleFilter = () => {
    const filtered = tasks.filter((task) => {
      const assigneeFullName = task.assignee
        ? `${task.assignee.second_name ?? ""} ${task.assignee.name ?? ""} ${
            task.assignee.middle_name ?? ""
          }`.toLowerCase()
        : "";

      const matchId =
        filters.id === "" || task.id.toString().includes(filters.id);
      const matchAssignee =
        filters.assignee === "" ||
        assigneeFullName.includes(filters.assignee.toLowerCase());
      const matchPriority =
        filters.priority === "" ||
        (task.priority &&
          task.priority.toLowerCase() === filters.priority.toLowerCase());
      const matchStatus =
        filters.status === "" ||
        (task.status &&
          task.status.toLowerCase() === filters.status.toLowerCase());
      const matchStartDateFrom =
        !filters.startDateFrom ||
        new Date(task.startDate) >= new Date(filters.startDateFrom);
      const matchStartDateTo =
        !filters.startDateTo ||
        new Date(task.startDate) <= new Date(filters.startDateTo);
      const matchEndDateFrom =
        !filters.endDateFrom ||
        new Date(task.endDate) >= new Date(filters.endDateFrom);
      const matchEndDateTo =
        !filters.endDateTo ||
        new Date(task.endDate) <= new Date(filters.endDateTo);

      return (
        matchId &&
        matchAssignee &&
        matchPriority &&
        matchStatus &&
        matchStartDateFrom &&
        matchStartDateTo &&
        matchEndDateFrom &&
        matchEndDateTo
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
    });
    setFilteredTasks(tasks);
  };

  const stats = {
    total: tasks.length,
    inProgress: tasks.filter((t) => t.status === "В процессе").length,
    completed: tasks.filter((t) => t.status === "Завершено").length,
  };

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
            <input
              type="text"
              placeholder="Номер задания"
              className="border p-2 rounded"
              value={filters.id}
              onChange={(e) => setFilters({ ...filters, id: e.target.value })}
            />
            <input
              type="text"
              placeholder="Исполнитель"
              className="border p-2 rounded"
              value={filters.assignee}
              onChange={(e) =>
                setFilters({ ...filters, assignee: e.target.value })
              }
            />
            <input
              type="text"
              placeholder="Приоритет"
              className="border p-2 rounded"
              value={filters.priority}
              onChange={(e) =>
                setFilters({ ...filters, priority: e.target.value })
              }
            />
            <input
              type="text"
              placeholder="Статус"
              className="border p-2 rounded"
              value={filters.status}
              onChange={(e) =>
                setFilters({ ...filters, status: e.target.value })
              }
            />
            <input
              type="date"
              className="border p-2 rounded"
              value={filters.startDateFrom}
              onChange={(e) =>
                setFilters({ ...filters, startDateFrom: e.target.value })
              }
            />
            <input
              type="date"
              className="border p-2 rounded"
              value={filters.startDateTo}
              onChange={(e) =>
                setFilters({ ...filters, startDateTo: e.target.value })
              }
            />
            <input
              type="date"
              className="border p-2 rounded"
              value={filters.endDateFrom}
              onChange={(e) =>
                setFilters({ ...filters, endDateFrom: e.target.value })
              }
            />
            <input
              type="date"
              className="border p-2 rounded"
              value={filters.endDateTo}
              onChange={(e) =>
                setFilters({ ...filters, endDateTo: e.target.value })
              }
            />
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
          <div>Номер</div>
          <div>Приоритет</div>
          <div>Назначил</div>
          <div>Исполнитель</div>
          <div>Начало</div>
          <div>Окончание</div>
          <div>Статус</div>
        </div>

        {/* Список заданий */}
        <div className="bg-white shadow rounded-b divide-y">
          {filteredTasks.map((task) => (
            <div
              key={task.id}
              className="grid grid-cols-7 gap-4 px-4 py-2 text-sm text-gray-800 items-center hover:bg-gray-50"
            >
              <div>{task.id}</div>
              <div>
                <PriorityIndicatorSection priority={task.priority} />
              </div>
              <div>
                {task.assignor
                  ? `${task.assignor.second_name ?? ""} ${
                      task.assignor.name ?? ""
                    } ${task.assignor.middle_name ?? ""}`
                  : "-"}
              </div>
              <div>
                {task.assignee
                  ? `${task.assignee.second_name ?? ""} ${
                      task.assignee.name ?? ""
                    } ${task.assignee.middle_name ?? ""}`
                  : "-"}
              </div>
              <div>{task.startDate}</div>
              <div>{task.endDate}</div>
              <div>{task.status}</div>
            </div>
          ))}
        </div>
      </div>
    </>
  );
};

export default TasksPage;
