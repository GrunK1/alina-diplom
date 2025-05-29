import React from "react";
import SidebarSection from "./SidebarSection";

const MainLayoutSection = ({ children }) => {
  return (
    <div className="flex min-h-screen max-h-screen bg-gray-50 overflow-hidden">
      <SidebarSection />
      <main className="flex-1 h-full overflow-auto">{children}</main>
    </div>
  );
};

export default MainLayoutSection;
