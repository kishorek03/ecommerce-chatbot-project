import React from "react";
import ChatWindow from "./components/ChatWindow";
import "./App.css";

function App() {
  return (
    <div className="min-h-screen bg-gradient-to-br from-blue-50 to-blue-200 flex flex-col items-center justify-center">
      <div className="w-full max-w-2xl shadow-xl rounded-2xl bg-white/90 p-0 md:p-6">
        <ChatWindow />
      </div>
    </div>
  );
}

export default App;
