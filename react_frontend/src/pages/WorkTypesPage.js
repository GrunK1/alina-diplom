import React, { useEffect, useState } from "react";
import axios from "axios";

const WorkTypesPage = () => {
  const [workTypes, setWorkTypes] = useState([]);

  useEffect(() => {
    axios
      .get("http://localhost:8080/api/work-types")
      .then((res) => setWorkTypes(res.data));
  }, []);

  return (
    <div className="p-8">
      <h1 className="text-3xl font-bold mb-6 text-blue-900">
        Каталог видов работ
      </h1>
      <div className="grid grid-cols-2 gap-6 font-semibold text-blue-700 text-sm border-b pb-2 mb-2">
        <div>Вид работ</div>
        <div>Категория</div>
      </div>
      {workTypes.map((w) => (
        <div
          key={w.wtId}
          className="grid grid-cols-2 gap-6 py-2 border-b text-sm text-gray-700"
        >
          <div>{w.wtType}</div>
          <div>{w.wtDescription}</div>
        </div>
      ))}
    </div>
  );
};

export default WorkTypesPage;
