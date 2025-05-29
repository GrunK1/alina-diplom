import React from "react";
import { Outlet } from "react-router-dom";

const MainLayout = () => {
  return (
    <div className="flex h-screen w-screen overflow-hidden">
      {/* Sidebar отключён */}
      {/* <aside className="w-64 bg-blue-800 text-white flex flex-col p-4 space-y-4">
        // Удалено всё содержимое
      </aside> */}

      {/* Main Content */}
      <main className="flex-1 overflow-y-auto bg-gray-50">
        <Outlet />
      </main>
    </div>
  );
};

export default MainLayout;
