import React from 'react';
import { useCallback } from 'react';
import { useNavigate } from 'react-router-dom';

export default function NoMatch() {
  const navigate = useNavigate();

  // 이전 페이지로 돌아가는 함수

  // 홈 페이지로 이동하는 함수
  const goToHome = useCallback(() => {
    // 홈 페이지 경로를 입력하세요
    navigate("/");
  }, [navigate]);

  return (
    <div className="flex flex-col p-4">
      <p className="text-xl text-center alert alert-error">페이지를 찾을 수 없습니다.</p>
      <div className="flex justify-center mt-4">
        {/* goToHome 함수를 클릭 이벤트로 사용 */}
        <button className="ml-4 btn btn-primary btn bg-lime-500 border-lime-500" onClick={goToHome}>
          홈으로 이동
        </button>
      </div>
    </div>
  );
}