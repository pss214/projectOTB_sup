import styled from 'styled-components'

interface Props {
  article: {
    username: string
    email: string
    password: string
  }
}

const UserBlock = styled.div`
  display: flex;
  .thumbnail {
    margin-right: 1rem;
    img {
      display: block;
      width: 160px;
      height: 100px;
      object-fit: cover;
    }
  }
  .contents {
    h2 {
      margin: 0;
      a {
        color: black;
      }
    }
    p {
      margin: 0;
      line-height: 1.5;
      margin-top: 0.5rem;
      white-space: normal;
    }
  }
  & + & {
    margin-top: 3rem;
  }
`

export default function UserData({article}: Props) {
  const {username, email, password} = article
  return (
    <UserBlock>
      {username && (
        <div className="thumbnail">
          <a href={email} target="_blank" rel="noopener noreferrer">
            <img src={password} alt="thumbnail" />
          </a>
        </div>
      )}
      <div className="contents">
        <h2>
          <a href={email} target="_blank" rel="noopener noreferrer">
            {username}
          </a>
        </h2>
        <p>{password}</p>
      </div>
    </UserBlock>
  )
}
