import React, { ChangeEvent } from "react";
import Input from "@mui/joy/Input";

export interface InputProps {
  name: string;
  value: string;
  type?: string;
  placeholder: string;
  onChange: (e: ChangeEvent<HTMLInputElement>) => void;
}

export default function MuiInput({
  name,
  value,
  placeholder,
  type,
  onChange,
}: InputProps) {
  return (
    <Input
      name={name}
      color="primary"
      placeholder={placeholder}
      value={value}
      type={type}
      onChange={onChange}
      sx={{ width: 330 }}
    />
  );
}
