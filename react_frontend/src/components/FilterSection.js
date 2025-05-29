import React from "react";

const FilterSection = () => {
  return (
    <div className="p-4 bg-gray-100 rounded-lg mb-4">
      <h2 className="text-xl font-semibold mb-2">Фильтры</h2>
      <div className="flex flex-wrap gap-4">
        <input
          type="text"
          placeholder="Номер задания"
          className="border p-2 rounded w-40"
        />
        <input
          type="text"
          placeholder="Исполнитель"
          className="border p-2 rounded w-40"
        />
        <input type="date" className="border p-2 rounded w-40" />
        <input type="date" className="border p-2 rounded w-40" />
        <button className="bg-blue-500 text-white px-4 py-2 rounded">
          Применить
        </button>
        <button className="border px-4 py-2 rounded">Очистить</button>
      </div>
    </div>
  );
};

export default FilterSection;
