import React, {useState, useEffect} from 'react'
import {SERVER_URL} from '../../server/getServer'
import {Link} from '../../components'
import {useAuth} from '../../contexts'
import axios from 'axios'
import styled from 'styled-components'
import UserData from './UserData'

interface Article {
  email: string
  username: string
  password: string
}
const UserBlock = styled.div`
  box-sizing: border-box;
  padding-bottom: 3rem;
  width: 768px;
  margin: 0 auto;
  margin-top: 2rem;
  @media screen and (max-width: 768px) {
    width: 100%;
    padding-left: 1rem;
    padding-right: 1rem;
  }
`

const MyPage = () => {
  const [articles, setArticles] = useState<Article[] | null>(null)
  const [loading, setLoading] = useState<boolean>(false)
  useEffect(() => {
    async function fetchData(): Promise<void> {
      setLoading(true)
      try {
        const response = await axios.get(SERVER_URL + '/member')
        setArticles(response.data.articles.sli)
      } catch (e) {
        console.log(e)
      }
      setLoading(false)
    }
    fetchData()
  })

  return (
    <div>
      <div className="flex justify-between bg-lime-200">
        <div className="flex p-2 ">
          <Link to="/" className="ml-1">
            <img
              src="/img/otblogogogo.png"
              alt="OTB(우비) 로고"
              className="w-12.5 h-12.5 bg-lime-200"
            />
          </Link>
        </div>
      </div>
      <div className="flex flex-col min-h-screen border-gray-300 rounded-xl shadow-xl bg-gray-100 border">
        <div className="w-full px-6 py-8 text-black bg-white rounded shadow-md">
          <h1 className="mb-8 text-2xl text-center text-lime-500">회원 정보</h1>
        </div>
        <UserBlock>
          {articles?.map((article: Article) => (
            <UserData key={article.username} article={article} />
          ))}
        </UserBlock>
        <Link to="/" className="btn btn-link text-lime-500">
          메인 페이지로 이동하기
        </Link>
      </div>
    </div>
  )
}
export default MyPage
