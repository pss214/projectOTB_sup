import { useState, useCallback, ChangeEvent } from 'react';

type UseInputReturnType = [string, (e: ChangeEvent<HTMLInputElement>) => void, React.Dispatch<React.SetStateAction<string>>];

const useInput = (initialValue: string = ''): UseInputReturnType => {
  const [value, setValue] = useState(initialValue);

  const onChange = useCallback((e: ChangeEvent<HTMLInputElement>) => {
    setValue(e.target.value);
  }, []);

  return [value, onChange, setValue];
};

export default useInput;

export {}