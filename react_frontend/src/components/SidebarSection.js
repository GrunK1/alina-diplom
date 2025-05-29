import React from "react";
import { NavLink } from "react-router-dom";
import {
  FaClipboardList,
  FaMapMarkedAlt,
  FaTasks,
  FaFileAlt,
} from "react-icons/fa";

const SidebarSection = () => {
  const navItems = [
    {
      label: "Общая сводка",
      path: "/",
      icon: <FaClipboardList />,
    },
    {
      label: "Маршрутные листы",
      path: "/route-sheets",
      icon: <FaMapMarkedAlt />,
    },
    {
      label: "Журнал вида работ",
      path: "/work-types",
      icon: <FaTasks />,
    },
    {
      label: "Реестр РД",
      path: "/permission-documents",
      icon: <FaFileAlt />,
    },
  ];

  return (
    <div className="rounded-xl border border-gray-200 bg-white shadow p-4">
      <ul className="space-y-2">
        {navItems.map((item) => (
          <li key={item.path}>
            <NavLink
              to={item.path}
              className={({ isActive }) =>
                `flex items-center gap-3 px-4 py-2 rounded-lg transition ${
                  isActive
                    ? "bg-blue-100 text-blue-600 font-semibold"
                    : "hover:bg-gray-100 text-gray-700"
                }`
              }
            >
              <span className="text-lg">{item.icon}</span>
              <span className="text-sm">{item.label}</span>
            </NavLink>
          </li>
        ))}
      </ul>
    </div>
  );
};

export default SidebarSection;
