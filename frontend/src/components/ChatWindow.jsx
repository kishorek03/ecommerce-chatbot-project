import React, { useState, useRef, useEffect } from "react";
import Message from "./Message";
import InputBox from "./InputBox";

const SUGGESTIONS = [
  "Show me the top 5 sold products",
  "What is the status of order ID 12345?",
  "How many Classic T-Shirts are left in stock?",
  "List all available product categories"
];

const API_URL = "http://localhost:8080/api/chatbot/query";

export default function ChatWindow() {
  const [messages, setMessages] = useState([
    { sender: "bot", text: "Hi! 👋 I'm your e-commerce assistant. How can I help you today?" }
  ]);
  const [loading, setLoading] = useState(false);
  const [typing, setTyping] = useState(false);
  const chatEndRef = useRef(null);

  useEffect(() => {
    chatEndRef.current?.scrollIntoView({ behavior: "smooth" });
  }, [messages, loading]);

  const sendMessage = async (userText) => {
    if (!userText.trim()) return;
    setMessages((msgs) => [...msgs, { sender: "user", text: userText }]);
    setLoading(true);
    setTyping(true);
    try {
      const res = await fetch(API_URL, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ question: userText })
      });
      const data = await res.json();
      setMessages((msgs) => [
        ...msgs,
        { sender: "bot", text: data.message || "Sorry, I didn't understand that." }
      ]);
    } catch (e) {
      setMessages((msgs) => [
        ...msgs,
        { sender: "bot", text: "Network error. Please try again." }
      ]);
    }
    setLoading(false);
    setTyping(false);
  };

  return (
    <div className="flex flex-col h-[70vh] md:h-[32rem]">
      {/* Chat log */}
      <div className="flex-1 overflow-y-auto px-2 py-4 space-y-2 bg-white rounded-t-2xl border-b border-gray-200">
        {messages.map((msg, idx) => (
          <Message key={idx} text={msg.text} sender={msg.sender} />
        ))}
        {typing && (
          <Message text={<TypingIndicator />} sender="bot" />
        )}
        <div ref={chatEndRef} />
      </div>
      {/* Suggestions */}
      <div className="flex flex-wrap gap-2 px-2 py-2 bg-gray-50 border-b border-gray-200">
        {SUGGESTIONS.map((s, i) => (
          <button
            key={i}
            className="px-3 py-1 bg-blue-100 text-blue-700 rounded-full text-xs hover:bg-blue-200 transition"
            onClick={() => sendMessage(s)}
          >
            {s}
          </button>
        ))}
      </div>
      {/* Input */}
      <InputBox onSend={sendMessage} loading={loading} />
    </div>
  );
}

function TypingIndicator() {
  return (
    <span className="flex items-center gap-1">
      <span className="w-2 h-2 bg-gray-400 rounded-full animate-bounce" style={{ animationDelay: '0ms' }}></span>
      <span className="w-2 h-2 bg-gray-400 rounded-full animate-bounce" style={{ animationDelay: '100ms' }}></span>
      <span className="w-2 h-2 bg-gray-400 rounded-full animate-bounce" style={{ animationDelay: '200ms' }}></span>
      <span className="ml-2 text-xs text-gray-400">Bot is typing...</span>
    </span>
  );
} 