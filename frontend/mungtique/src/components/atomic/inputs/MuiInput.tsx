import React, { ChangeEvent } from "react";
import Input from "@mui/joy/Input";

export interface InputProps {
  value: string;
  placeholder: string;
  onChange: (e: ChangeEvent<HTMLInputElement>) => void;
}

export default function MuiInput({ value, placeholder, onChange }: InputProps) {
  return (
    <Input
      color="primary"
      placeholder={placeholder}
      value={value}
      onChange={onChange}
      sx={{ width: 330 }}
    />
  );
}
