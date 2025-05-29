import React, { useEffect, useState } from "react";
import axios from "axios";

const RdJournalPage = () => {
  const [docs, setDocs] = useState([]);

  useEffect(() => {
    axios
      .get("http://localhost:8080/api/permission-documents")
      .then((res) => setDocs(res.data));
  }, []);

  return (
    <div className="p-8">
      <h1 className="text-3xl font-bold mb-6 text-blue-900">
        Реестр разрешающих документов
      </h1>
      <div className="grid grid-cols-2 gap-6 font-semibold text-blue-700 text-sm border-b pb-2 mb-2">
        <div>Тип РД</div>
        <div>Описание</div>
      </div>
      {docs.map((doc) => (
        <div
          key={doc.pdId}
          className="grid grid-cols-2 gap-6 py-2 border-b text-sm text-gray-700"
        >
          <div>{doc.pdType}</div>
          <div>{doc.pdDescription}</div>
        </div>
      ))}
    </div>
  );
};

export default RdJournalPage;
