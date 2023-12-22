import { Button } from "../../theme/daisyui";
import {Link as RRLink, useNavigate} from 'react-router-dom'
import { useAuth } from "../../contexts";
import { useEffect, useState } from "react";
import * as U from '../../utils'
import UserTable from "./table/usertable";
import ReservationTable from "./table/reservationtable";

export default function AdminPage() {
    const {loggedUser} = useAuth()
    const navigate = useNavigate()
    const [jwt,setJwt] = useState<string>("")
    const [jwtbool,setJwtbool] = useState<boolean>(false)
    const [userapi,setUserapi] = useState<any>()
    const [value,setValue] = useState(0)
    useEffect(()=>{
        if(loggedUser){
            if(loggedUser.username!=="admin"){
                navigate("/")
            }
        }else{
            navigate('/admin')
        }
        if(!jwt){
            U.readStringP('jwt').then(jwt => {
                setJwt(jwt ?? '')
                setJwtbool(true)
              })
        } else{
        }
    },[jwt])
    const btn_user = ()=>{
        setValue(1)
    }
    const btn_bus = ()=>{
        setValue(2)
    }
    const btn_reservation = ()=>{
        setValue(3)
    }
    const btn_station = ()=>{
        setValue(4)
    }
    const btn_busnum = ()=>{
        setValue(5)
    }
    return(
        <>
            <>    
                <div className="flex justify-between items-center bg-lime-200">
                    <>
                        <p className="w-40 h-12.5" style={{ fontSize: "20px" }}>관리자 페이지</p>
                    </>
                    <div className="flex p-6 flex p-6 navbar justify-center">
                        <div className="flex p-6">
                            <Button onClick={btn_user}>유저</Button>
                        </div>
                        <div className="flex p-6">
                            <Button onClick={btn_bus}>버스</Button>
                        </div>
                        <div className="flex p-6">
                            <Button onClick={btn_reservation}>예약</Button>
                        </div>
                        <div className="flex p-6">
                            <Button onClick={btn_station}>버스정류장</Button>
                        </div>
                        <div className="flex p-6">
                            <Button onClick={btn_busnum}>버스번호</Button>
                        </div>
                    </div>
                    <RRLink
                        to="/logout"
                        className="flex ml-4 mr-4 btn btn-primary text-white border-lime-600 bg-lime-600">
                        로그 아웃
                    </RRLink>
                </div>
            </>
            <>
                <div className="flex justify-center">
                    {value === 0 && (
                        <>버튼을 눌러 선택해주세요.</>
                    )}
                    {value === 1 && (
                        <UserTable jwt={jwt} />
                    )}
                    {value === 2 && (
                        <>버스 테이블</>
                        
                    )}
                    {value === 3 && (
                        <ReservationTable jwt={jwt} />
                    )}
                    {value === 4 && (
                        <>버스정류장 테이블</>
                    )}
                    {value === 5 && (
                        <>버스번호 테이블</>
                    )}
                </div>
            </>
        </>
        
    )
}
