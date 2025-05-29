// components/StatusOverviewSection.js
import React from "react";
import { NavLink } from "react-router-dom";

const StatusOverviewSection = () => {
  return (
    <header className="w-full bg-white shadow-md px-8 py-4 border-b flex items-center justify-between">
      <h1 className="text-xl font-bold text-blue-900">Система учёта ПУ</h1>
      <nav className="flex gap-6">
        <NavLink
          to="/"
          className={({ isActive }) =>
            `text-sm font-semibold ${
              isActive ? "text-blue-600" : "text-gray-500"
            }`
          }
        >
          Главная
        </NavLink>
        <NavLink
          to="/map"
          className={({ isActive }) =>
            `text-sm font-semibold ${
              isActive ? "text-blue-600" : "text-gray-500"
            }`
          }
        >
          Карта
        </NavLink>
        <NavLink
          to="/tasks"
          className={({ isActive }) =>
            `text-sm font-semibold ${
              isActive ? "text-blue-600" : "text-gray-500"
            }`
          }
        >
          Задания
        </NavLink>
      </nav>
    </header>
  );
};

export default StatusOverviewSection;
