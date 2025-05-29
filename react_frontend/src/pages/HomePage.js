import React, { useEffect, useState } from "react";
import NavigationBarSection from "../components/NavigationBarSection";
import SidebarSection from "../components/SidebarSection";
import SummarySection from "../components/SummarySection";
import TaskListSection from "../components/TaskListSection";
import DailyTasksSection from "../components/DailyTasksSection";
import InspectorListSection from "../components/InspectorListSection";
import axios from "axios";
import { InfoIcon } from "lucide-react";
import { Card, CardContent } from "../components/card";

const HomePage = () => {
  const [tasks, setTasks] = useState([]);
  const [routeSheets, setRouteSheets] = useState([]);
  const [inspectors, setInspectors] = useState([]);

  useEffect(() => {
    const fetchData = async () => {
      try {
        const [tasksRes, routeSheetsRes, inspectorsRes] = await Promise.all([
          axios.get("http://localhost:8080/api/tasks"),
          axios.get("http://localhost:8080/api/route-lists/enriched"),
          axios.get("http://localhost:8080/api/employees/inspectors"),
        ]);
        setTasks(tasksRes.data);
        setRouteSheets(routeSheetsRes.data);
        setInspectors(inspectorsRes.data);
      } catch (err) {
        console.error("Ошибка загрузки:", err);
      }
    };

    fetchData();
  }, []);

  const expiredTasks = tasks.filter(
    (t) => t.status?.toLowerCase() === "просрочено"
  );

  return (
    <div className="flex flex-col min-h-screen bg-gray-100">
      {/* Верхняя панель */}
      <NavigationBarSection />

      {/* Основная часть */}
      <div className="flex flex-1">
        {/* Сайдбар слева */}
        <aside className="w-[260px] bg-white shadow-lg p-4">
          <SidebarSection />
        </aside>

        {/* Центральный контент */}
        <main className="flex-1 p-8">
          {/* Заголовок */}
          <h1 className="text-4xl font-bold text-gray-800 mb-6">
            Текущее состояние по филиалу
          </h1>

          {/* Верхняя часть: Статистика и инспекторы в ряд */}
          <div className="grid grid-cols-3 gap-6 mb-8">
            {/* Статистика слева и по центру */}
            <div className="col-span-2 grid grid-cols-2 gap-6">
              <SummarySection
                processed={routeSheets.length}
                total={routeSheets.length}
              />
              <TaskListSection
                totalTasks={tasks.length}
                expiredTasks={expiredTasks.length}
              />
            </div>
            {/* Список инспекторов справа */}
            <div className="col-span-1">
              <InspectorListSection inspectors={inspectors} />
            </div>
          </div>

          {/* Таблица маршрутных листов */}
          <div className="mb-4">
            <DailyTasksSection routeSheets={routeSheets} />
          </div>

          {/* Подпись под таблицей */}
          <div>
            <Card className="bg-gray-200 rounded-lg border-none">
              <CardContent className="flex items-center p-3">
                <InfoIcon className="w-5 h-5 mr-3 text-gray-500" />
                <span className="text-gray-700 text-base">
                  Список отсортирован по приоритету
                </span>
              </CardContent>
            </Card>
          </div>
        </main>
      </div>
    </div>
  );
};

export default HomePage;
