import React from "react";

const PriorityIndicatorSection = ({ priority }) => {
  const color =
    priority === "Высокий"
      ? "text-red-500"
      : priority === "Средний"
      ? "text-yellow-500"
      : "text-green-500";

  return <span className={`font-bold ${color}`}>{priority}</span>;
};

export default PriorityIndicatorSection;
