import {Link} from 'react-router-dom';
import {Div} from '../../components';
import {Button} from '../../theme/daisyui';
import {useCallback, useState} from 'react';
import Categories from '../../pages/News/Categories';
import NewsList from '../../pages/News/NewsList';

export default function Promotion() {
  const [category, setCategory] = useState<string>('all');
  const onSelect = useCallback((category: string) => setCategory(category), []);

  return (
    <section className="w-full mt-4 bg-gray-100 p-8 rounded-lg shadow-md">
      <div className="flex justify-center">
      <Link to="/board">
          <Button className="text-xl font-semibold text-white bg-orange-400 border-orange-500 hover:bg-orange-600 hover:border-orange-600 py-2 px-8">
            서비스 이용하기
          </Button>
        </Link>
      </div>
      <h2 className="text-3xl font-semibold text-center text-gray-800 mb-6">뉴스</h2>
      <div className="mt-6">
        <Categories category={category} onSelect={onSelect} />
        <NewsList category={category} />
      </div>
    </section>
  );
}
