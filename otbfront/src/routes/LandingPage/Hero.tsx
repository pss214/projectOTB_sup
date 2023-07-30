import {Link} from 'react-router-dom'
import {Div} from '../../components'
import {Button} from '../../theme/daisyui'
//import * as D from '../../data'

export default function Hero() {
  return (
    <div className="flex items-center p-4">
      <Div minWidth="30rem" width="30rem" maxWidth="30rem">
        <div className="flex flex-col justify-center p-4 font-bold">
          <div className="flex items-center justify-between mt-4">
            <Link to="/board">
              <Button className="btn-primary text-3xl font-bold btn-primary-lime-500 text-white bg-lime-500 border-lime-500">
                user 클릭
              </Button>
            </Link>
            <Link to="/">
              <Button className="btn-primary text-3xl font-bold btn-primary-lime-500 text-white bg-lime-500 border-lime-500">
                driver 클릭
              </Button>
            </Link>
          </div>
          <div></div>
        </div>
      </Div>
    </div>
  )
}
