import { Button } from "@mui/material";

export interface ButtonProps {
  value: string;
  onClick?: (e: React.MouseEvent<HTMLButtonElement>) => void;
  type: "button" | "submit" | "reset";
  variant: "text" | "outlined" | "contained";
  color:
    | "inherit"
    | "primary"
    | "secondary"
    | "success"
    | "error"
    | "info"
    | "warning";
}

export default function MuiButton({
  onClick,
  variant,
  color,
  value,
  type,
}: ButtonProps) {
  return (
    <Button variant={variant} color={color} onClick={onClick} type={type}>
      {value}
    </Button>
  );
}
