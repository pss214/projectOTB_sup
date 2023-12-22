import { useEffect, useState } from "react"
import { SERVER_URL } from "../../../server/getServer"
import { Button } from "../../../theme/daisyui"

interface user{
    id:number,
    username:string,
    email:string,
    rule:string,
    cd:Date,
    md:Date
}
export default function ReservationTable({ jwt }: { jwt: string }){
    const [jsonData, setJsonData] = useState([])
    
    useEffect(()=>{
        userfetch(jwt)
    },[])
    
    const userfetch= async(jwt:any)=>{
        await fetch(SERVER_URL+"/admin/reservationlist",{
            method:"GET",
            headers: {
            'Content-Type': 'application/json',
            Authorization: `otb ${jwt}`
        },
        })
        .then(res=>res.json().then(res=>{            
            if(res.status==200){
                console.log(res.data[0])
                setJsonData(res.data[0])         
            }else {
                console.error('가져오기 실패', res.data[0])
            }
        }
        )) 
    }
    const userdelete= () =>{
        if (window.confirm('삭제하시겠습니까?')) {
            alert("개발중입니다...")
            // fetch(SERVER_URL + '/admin/user/'+{}, {
            //     headers: {
            //       'Content-Type': 'application/json',
            //       Authorization: `otb ${jwt}`,
            //     },
            //   })
            //   .then(res=>res.json().then(res=>{            
            //     if(res.status===201){       
            //         alert("삭제되었습니다")
            //     }else {
            //         console.error('가져오기 실패', res.data[0])
            //     }
            // }
            // )) 
          } else {
            // 삭제 취소
          }
    }
    const DisplayData = jsonData.map(
        (info: any)=>{
            return(
                <tr>
                    <td>{info.rt_id}</td>
                    <td>{info.username}</td>
                    <td>{info.rtuinum}</td>
                    <td>{info.depart_station}</td>
                    <td>{info.arrive_station}</td>
                    <td>{info.busnumber}</td>
                    <td>{info.busnumberplate}</td>
                    <td><Button className="bg-red-600 border-red-100 " onClick={userdelete}>삭제</Button></td>
                    <td></td>
                </tr>
            )
        }
    )
    return(
        <>
            <table className="table table-striped">
                <thead>
                    <tr>
                        <th>rt_id</th>
                        <th>username</th>
                        <th>rtuinum</th>
                        <th>depart_station</th>
                        <th>arrive_station</th>
                        <th>busnumber</th>
                        <th>busnumberplate</th>
                        <th></th>
                        <th></th>
                    </tr>
                </thead>
                <tbody>
                    {DisplayData}
                </tbody>
            </table>
        </>
    )

}