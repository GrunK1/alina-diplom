import React from "react";

const DashboardViewSection = ({ stats }) => {
  return (
    <div className="grid grid-cols-3 gap-4 mb-6">
      <div className="bg-white p-4 rounded shadow">
        <h3 className="text-sm text-gray-500">Всего заданий</h3>
        <p className="text-2xl font-bold">{stats.total}</p>
      </div>
      <div className="bg-white p-4 rounded shadow">
        <h3 className="text-sm text-gray-500">В процессе</h3>
        <p className="text-2xl font-bold text-blue-600">{stats.inProgress}</p>
      </div>
      <div className="bg-white p-4 rounded shadow">
        <h3 className="text-sm text-gray-500">Завершено</h3>
        <p className="text-2xl font-bold text-green-600">{stats.completed}</p>
      </div>
    </div>
  );
};

export default DashboardViewSection;
