import React from "react";

export default function Message({ text, sender }) {
  const isUser = sender === "user";
  return (
    <div className={`flex ${isUser ? "justify-end" : "justify-start"}`}>
      <div
        className={`max-w-[75%] px-4 py-2 rounded-2xl shadow-sm mb-1 text-sm whitespace-pre-line
          ${isUser ? "bg-blue-500 text-white rounded-br-md" : "bg-gray-100 text-gray-900 rounded-bl-md"}`}
      >
        {text}
      </div>
    </div>
  );
} 