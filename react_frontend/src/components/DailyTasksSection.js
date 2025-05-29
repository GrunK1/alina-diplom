// src/components/DailyTasksSection.js
import React from "react";
import { useNavigate } from "react-router-dom";

const DailyTasksSection = ({ routeSheets }) => {
  const navigate = useNavigate();

  return (
    <div className="bg-white rounded-lg shadow p-4">
      <h2 className="text-xl font-semibold mb-4 text-gray-800">
        Маршрутные листы
      </h2>
      <div className="space-y-3">
        {routeSheets.map((sheet) => (
          <div
            key={sheet.mlNumber}
            className="flex items-center justify-between border border-gray-200 rounded p-3"
          >
            <div>
              <div className="font-medium text-gray-800">
                № {sheet.mlNumber}
              </div>
              <div className="text-sm text-gray-500">
                Дата: {new Date(sheet.plannedStartDate).toLocaleDateString()}
              </div>
            </div>
            <button
              onClick={() => navigate(`/route-sheets?ml=${sheet.mlNumber}`)}
              className="text-sm text-blue-600 border border-blue-600 px-3 py-1 rounded hover:bg-blue-50"
            >
              Просмотр
            </button>
          </div>
        ))}
      </div>
    </div>
  );
};

export default DailyTasksSection;
