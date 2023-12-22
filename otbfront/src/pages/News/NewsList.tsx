import styled from 'styled-components'
import {useEffect, useState} from 'react'
import NewsItem from './NewsItem'
import { SERVER_URL } from '../../server'

interface Article {
  title: string
  description: string
  url: string
  urlToImage: string
}

const NewsListBlock = styled.div`
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

interface Props {
  category: string
}

export default function NewsList({category}: Props) {
  const [articles, setArticles] = useState<Article[] | null>(null)
  const [loading, setLoading] = useState<boolean>(false)
  const [url, setUrl] = useState<string>('')
  useEffect(() => {
    async function fetchData(): Promise<void> {
      setLoading(true)
      try {
        const query = category === 'all' ? '' : `&category=${category}`
        if(query === ''){
          
          setUrl(SERVER_URL+`/news`);
        }else{
          setUrl(SERVER_URL+`/news/${query}`);
        }
        const response = await fetch(url,{method:"GET",headers:{'Content-Type': 'application/json'}})
        console.log(response);
        const data = await response.json()
        setArticles(data.data[0].articles.slice(0, 2))
      } catch (e) {
        console.log(e)
      }
      setLoading(false)
    }
    fetchData()
  }, [category])

  if (loading) {
    return <NewsListBlock>대기 중...</NewsListBlock>
  }

  if (!articles) {
    return null
  }

  return (
    <NewsListBlock>
      {articles.map((article: Article) => (
        <NewsItem key={article.url} article={article} />
      ))}
    </NewsListBlock>
  )
}
