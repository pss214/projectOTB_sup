import styled from 'styled-components'
import {useEffect, useState} from 'react'
import NewsItem from './NewsItem'

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

  useEffect(() => {
    async function fetchData(): Promise<void> {
      setLoading(true)
      try {
        const query = category === 'all' ? '' : `&category=${category}`
        const url = `https://newsapi.org/v2/top-headlines?country=kr${query}&apiKey=71e24a4b43ac4a689f929182c81dc940`
        const response = await fetch(url)
        const data = await response.json()
        setArticles(data.articles.slice(0, 2))
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
