import { useState } from "react";
import MuiInput from "../../../components/atomic/inputs/MuiInput";
import { useNavigate } from "react-router-dom";
import { MyMung } from "../../../shared/types/mungjoin.interface";
import MuiSnackbar from "../../../components/atomic/snackbar/MuiSnackbar";
import MuiButton from "../../../components/atomic/buttons/MuiButton";
import OptionSelect from "../../../components/atomic/inputs/OptionSelect";

interface MyMungJoinProps {
  onsubmit: (mungJoinDTO: MyMung) => void;
}

type SnackbarSeverity = "error" | "warning" | "info" | "success";

export default function MyMungJoinForm({ onsubmit }: MyMungJoinProps) {
  const navigate = useNavigate();
  const [openSnackbar, setOpenSnackbar] = useState(false);
  const [snackbarSeverity, setSnackbarSeverity] =
    useState<SnackbarSeverity>("error");

  const [snackbarMessage, setSnackbarMessage] = useState("");

  const [mungJoinForm, setMungJoinForm] = useState({
    mungName: "",
    breed: "",
    weight: "",
    age: "",
    gender: "",
    fixed: "",
  });

  const handleChange = (
    e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement>
  ) => {
    const { name, value } = e.target;
    setMungJoinForm({ ...mungJoinForm, [name]: value });
  };

  const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();

    const mungJoinDTO = {
      mungName: mungJoinForm.mungName,
      breed: mungJoinForm.breed,
      weight: Number(mungJoinForm.weight),
      age: Number(mungJoinForm.age),
      gender: mungJoinForm.gender,
      fixed: mungJoinForm.fixed,
      userId: Number(localStorage.getItem("userId")),
    };

    if (
      !(
        mungJoinDTO.mungName &&
        mungJoinForm.breed &&
        mungJoinForm.weight &&
        mungJoinForm.age &&
        mungJoinForm.gender &&
        mungJoinForm.fixed
      )
    ) {
      setSnackbarMessage("모든 값을 입력해주세요");
      setSnackbarSeverity("error");
      setOpenSnackbar(true);
      return;
    }

    try {
      console.log("mungJoinDTO", mungJoinDTO);
      onsubmit(mungJoinDTO);
      setSnackbarMessage("Mung 등록 완료하였습니다. 메인페이지로 이동합니다");
      setSnackbarSeverity("success");
      setOpenSnackbar(true);
      setTimeout(() => {
        navigate("/");
      }, 3000);
    } catch (error) {
      setSnackbarMessage("Mung 등록에 실패했습니다.");
      setSnackbarSeverity("error");
      setOpenSnackbar(true);
    }
  };

  return (
    <div>
      <form onSubmit={handleSubmit}>
        <p>첨부파일로 강아지 이미지 등록도 가능하게!</p>
        <MuiInput
          name="mungName"
          placeholder="반려뭉 이름"
          value={mungJoinForm.mungName}
          onChange={handleChange}
        />
        <MuiInput
          name="breed"
          placeholder="품종"
          value={mungJoinForm.breed}
          onChange={handleChange}
        />
        <MuiInput
          name="weight"
          placeholder="몸무게(kg)"
          value={mungJoinForm.weight}
          onChange={handleChange}
        />
        <MuiInput
          name="age"
          type="number"
          placeholder="나이"
          value={mungJoinForm.age}
          onChange={handleChange}
        />
        <OptionSelect
          name="gender"
          value={mungJoinForm.gender}
          onChange={handleChange}
          defaultOptionText="성별"
          options={["Male", "Female"]}
        />
        <div>
          <OptionSelect
            name="fixed"
            value={mungJoinForm.fixed}
            onChange={handleChange}
            defaultOptionText="중성화 여부"
            options={["Yes", "No"]}
          />
        </div>

        <div className="m-3">
          <MuiButton
            value="완료"
            variant={"contained"}
            color={"primary"}
            type={"submit"}
          />
        </div>
      </form>
      <MuiSnackbar
        message={snackbarMessage}
        severity={snackbarSeverity}
        open={openSnackbar}
        onClose={() => setOpenSnackbar(false)}
      />
    </div>
  );
}
