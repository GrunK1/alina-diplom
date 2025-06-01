// src/components/SummarySection.js
import React from "react";
import { CircularProgressbar, buildStyles } from "react-circular-progressbar";
import { FaClipboardList } from "react-icons/fa";

const SummarySection = ({ processed = 0, total = 0 }) => {
  const percentage = total ? (processed / total) * 100 : 0;

  return (
    <div className="bg-white rounded-2xl shadow-xl p-6 flex items-center gap-4">
      <div style={{ width: 64, height: 64 }}>
        <CircularProgressbar
          value={percentage}
          text={`${Math.round(percentage)}%`}
          strokeWidth={10}
          styles={buildStyles({
            pathColor: "#3b82f6",
            textColor: "#3b82f6",
            trailColor: "#bfdbfe",
            textSize: "16px",
          })}
        />
      </div>
      <div>
        <div className="text-blue-600 font-bold text-xl mb-1 flex items-center gap-2">
          <FaClipboardList /> Обработано МЛ
        </div>
        <div className="text-sm text-gray-600">
          {processed} из {total}
        </div>
      </div>
    </div>
  );
};

export default SummarySection;
