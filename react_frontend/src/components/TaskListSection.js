import React from "react";
import { CircularProgressbar, buildStyles } from "react-circular-progressbar";
import "react-circular-progressbar/dist/styles.css";
import { FaExclamationTriangle } from "react-icons/fa";

const TaskListSection = ({ totalTasks = 20, expiredTasks = 6 }) => {
  const percentage = totalTasks ? (expiredTasks / totalTasks) * 100 : 0;

  return (
    <div className="bg-white rounded-2xl shadow-xl p-6 flex items-center gap-4">
      <div style={{ width: 64, height: 64 }}>
        <CircularProgressbar
          value={percentage}
          text={`${Math.round(percentage)}%`}
          strokeWidth={10}
          styles={buildStyles({
            pathColor: "#dc2626",
            textColor: "#dc2626",
            trailColor: "#fca5a5",
            textSize: "16px",
          })}
        />
      </div>
      <div>
        <div className="text-red-600 font-bold text-xl mb-1 flex items-center gap-2">
          <FaExclamationTriangle /> Просроченные задания
        </div>
        <div className="text-sm text-gray-600">
          {expiredTasks} из {totalTasks}
        </div>
      </div>
    </div>
  );
};

export default TaskListSection;
