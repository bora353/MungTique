import React from "react";

export interface OptionSelectProps {
  name: string;
  value: string;
  onChange: (e: React.ChangeEvent<HTMLSelectElement>) => void;
  defaultOptionText: string;
  options: string[];
}

export default function OptionSelect({
  name,
  value,
  onChange,
  defaultOptionText,
  options,
}: OptionSelectProps) {
  return (
    <select
      name={name}
      value={value}
      onChange={onChange}
      style={{
        width: 330,
        padding: "8px",
        fontSize: "16px",
        borderRadius: "8px",
        border: "1px solid skyblue",
      }}
    >
      <option value="" disabled>
        {defaultOptionText}
      </option>
      {options.map((option, index) => (
        <option key={index} value={option}>
          {option}
        </option>
      ))}
    </select>
  );
}
