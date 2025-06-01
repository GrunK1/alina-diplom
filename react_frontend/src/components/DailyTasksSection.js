// src/components/DailyTasksSection.js
import React from "react";
import { useNavigate } from "react-router-dom";

const DailyTasksSection = ({ routeSheets = [], onViewAll }) => {
  const navigate = useNavigate();

  return (
    <div className="bg-white shadow-md rounded-lg p-4">
      <div className="flex justify-between items-center mb-4">
        <h2 className="text-xl font-semibold text-blue-800">
          Последние маршрутные листы
        </h2>
        <button className="text-blue-600 text-sm underline" onClick={onViewAll}>
          Посмотреть все
        </button>
      </div>
      <div className="divide-y">
        {routeSheets.map((ml) => (
          <div
            key={ml.mlNumber}
            className="py-2 flex justify-between items-center hover:bg-gray-50 px-2 rounded"
          >
            <div>
              <p className="font-semibold">МЛ №{ml.mlNumber}</p>
              <p className="text-sm text-gray-600">
                Период:{" "}
                {new Date(ml.plannedStartDate).toLocaleDateString("ru-RU")} –{" "}
                {new Date(ml.plannedEndDate).toLocaleDateString("ru-RU")}
              </p>
            </div>
            <button
              onClick={() => navigate(`/route-sheets?ml=${ml.mlNumber}`)}
              className="text-sm text-blue-600 border border-blue-600 px-3 py-1 rounded hover:bg-blue-50"
            >
              Посмотреть
            </button>
          </div>
        ))}
      </div>
    </div>
  );
};

export default DailyTasksSection;
