import {useCallback, useState} from 'react'
import Categories from '../../pages/News/Categories'
import NewsList from '../../pages/News/NewsList'

export default function Promotion() {
  const [category, setCategory] = useState<string>('all')
  const onSelect = useCallback((category: string) => setCategory(category), [])

  return (
    <section className="w-full mt-4 ">
      <h2 className="ml-4 text-5xl font-bold text-center"> 뉴스</h2>
      <div className="flex justify-center w-full p-4 "></div>
      {/* <Categories category={category} onSelect={onSelect} />
      <NewsList category={category} /> */}
    </section>
  )
}
{
}
