import {Link} from 'react-router-dom';
import {Button} from '../../theme/daisyui';
import {useCallback, useEffect, useState} from 'react';
import Categories from '../../pages/News/Categories';
import NewsList from '../../pages/News/NewsList';
import {useAuth} from '../../contexts';

export default function Promotion() {
  const [category, setCategory] = useState<string>('');
  const onSelect = useCallback((category: string) => setCategory(category), []);
  const {loggedUser} = useAuth();
  const [showPopup, setShowPopup] = useState<boolean>(!loggedUser);
  useEffect(()=>{
    setCategory("all");
  },[])
  return (
    <section className="w-full mt-4 bg-gray-100 p-8 rounded-lg shadow-md">
      <div className="flex justify-center">
        <Link to="/board">
          <Button className="text-xl font-semibold text-white bg-orange-400 border-orange-500 hover:bg-orange-600 hover:border-orange-600 py-2 px-8">
            서비스 이용하기
          </Button>
        </Link>
      </div>
      {/* 팝업창을 표시할지 여부 확인 */}
      {showPopup && (
        <div className="fixed top-0 left-0 w-full h-full flex items-center justify-center bg-gray-800 bg-opacity-75">
          <center>
            <div className="bg-white p-8 rounded-lg shadow-md">
              <p className="text-xl font-semibold text-gray-800 mb-4">
                로그인이 필요한 서비스입니다.
              </p>
              <Link to="/login" className="text-green-600 hover:underline">
                로그인하러 가기
              </Link>
              <button
                className="ml-4 text-gray-500 hover:text-gray-700"
                onClick={() => setShowPopup(false)} // 팝업창 닫기
              >
                닫기
              </button>
            </div>
          </center>
        </div>
      )}
      <h2 className="text-3xl font-semibold text-center text-gray-800 mb-6">뉴스</h2>

      <div className="mt-6">
        <Categories category={category} onSelect={onSelect} />
        <NewsList category={category} />
      </div>
    </section>
  )
}
