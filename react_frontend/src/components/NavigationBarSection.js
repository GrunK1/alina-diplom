import React from "react";
import { NavLink } from "react-router-dom";
import { FaCarSide } from "react-icons/fa";

const NavigationBarSection = () => {
  const linkClasses = ({ isActive }) =>
    `px-4 py-2 font-medium transition ${
      isActive
        ? "border-b-2 border-blue-600 text-blue-600"
        : "text-gray-600 hover:text-blue-600"
    }`;

  return (
    <nav className="flex items-center justify-between px-6 py-4 bg-white shadow z-[1100]">
      <div className="flex items-center space-x-4">
        <FaCarSide className="text-blue-600 text-2xl mr-2" />
        <NavLink to="/" className={linkClasses}>
          Главная
        </NavLink>
        <NavLink to="/tasks" className={linkClasses}>
          Задания
        </NavLink>
        <NavLink to="/map" className={linkClasses}>
          Карта
        </NavLink>
      </div>
    </nav>
  );
};

export default NavigationBarSection;
