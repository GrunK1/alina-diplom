import React from "react";
import { FaUserTie } from "react-icons/fa";

const InspectorListSection = ({ inspectors = [] }) => {
  const getFullName = (emp) =>
    `${emp.second_name ?? ""} ${emp.name ?? ""} ${
      emp.middle_name ?? ""
    }`.trim();

  return (
    <div className="bg-white rounded-2xl shadow-xl p-6">
      <h2 className="font-bold text-xl mb-4 flex items-center gap-2 text-blue-900">
        <FaUserTie /> Инспектора
      </h2>
      <ul className="space-y-3">
        {inspectors.map((emp) => (
          <li key={emp.empId} className="text-gray-800 text-sm border-b pb-1">
            {getFullName(emp)}
          </li>
        ))}
      </ul>
    </div>
  );
};

export default InspectorListSection;
