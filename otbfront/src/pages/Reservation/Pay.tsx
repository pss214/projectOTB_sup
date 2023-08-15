import React, {useEffect, useRef, useState} from 'react'
import {loadPaymentWidget, PaymentWidgetInstance} from '@tosspayments/payment-widget-sdk'
import {nanoid} from 'nanoid'
import {Link} from '../../components'
import QRCode from 'qrcode.react'
import {SERVER_URL} from '../../server'
import {Navigate, useLocation, useNavigate} from 'react-router-dom'
import * as U from '../../utils'

const clientKey = 'test_ck_D5GePWvyJnrK0W0k6q8gLzN97Eoq'
const customerKey = 'YbX2HuSlsC9uVJW6NMRMj'

export default function Pay() {
  const paymentWidgetRef = useRef<PaymentWidgetInstance | null>(null)
  const paymentMethodsWidgetRef = useRef<ReturnType<
    PaymentWidgetInstance['renderPaymentMethods']
  > | null>(null)
  const [price, setPrice] = useState(1)
  const [showQRCode, setShowQRCode] = useState(false)
  const [jwt, setJwt] = useState<string>('')
  const [jwtbool, setJwtbool] = useState<boolean>(false)
  const navigate = useNavigate()
  const location = useLocation()
  const reuninum = location.state

  useEffect(() => {
    ;(async () => {
      const paymentWidget = await loadPaymentWidget(clientKey, customerKey)

      const paymentMethodsWidget = paymentWidget.renderPaymentMethods(
        '#payment-widget',
        price
      )

      paymentWidgetRef.current = paymentWidget
      paymentMethodsWidgetRef.current = paymentMethodsWidget
    })()
  }, [])

  useEffect(() => {
    const paymentMethodsWidget = paymentMethodsWidgetRef.current

    if (paymentMethodsWidget == null) {
      return
    }

    paymentMethodsWidget.updateAmount(price, paymentMethodsWidget.UPDATE_REASON.COUPON)
  }, [price])

  useEffect(() => {
    U.readStringP('jwt').then(jwt => {
      setJwt(jwt ?? '')
      setJwtbool(true)
    })
  }, [jwt])

  const sendPayment = () => {
    fetch(SERVER_URL + '/reservation/pay', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        Authorization: `otb ${jwt}`
      },
      body: JSON.stringify({
        rtuinum: reuninum
      })
    }).then(res=>res.json().then(res=>{
      if(res.status==201){
        alert("결제가 완료되었습니다")
        navigate("/mypage")
      }
    }))
  }

  return (
    <div>
      <div className="flex flex-col min-h-screen border-gray-300 rounded-xl shadow-xl bg-gray-100 border">
        <div className="w-full px-6 py-8 text-black bg-white rounded shadow-md">
          <div>
            <h1 className="mb-8 text-4xl text-center text-lime-500">결제</h1>
          </div>
          <div id="payment-widget" />
          <center>
            <button
              className="flex-center ml-4 mr-4 btn btn-primary text-white border-lime-600 bg-lime-600"
              onClick={sendPayment}>
              결제하기
            </button>
            <button
              className="flex-center ml-4 mr-4 btn btn-primary text-white border-lime-600 bg-lime-600"
              onClick={() => setShowQRCode(!showQRCode)}>
              {showQRCode ? 'QR 코드 닫기' : '결제하기 QR'}
            </button>
            {/* 3항 연산자로 QR 코드 렌더링 여닫음 */}
            {showQRCode ? <QRCode value={`금액: ${price}`} /> : null}
            <button
              onClick={() => {
                window.history.back()
              }}
              className="block mt-4 text-lime-500 cursor-pointer">
              버스 목록으로 돌아가기
            </button>
            <Link to="/" className="block mt-4 text-lime-500">
              메인 페이지로 이동하기
            </Link>
          </center>
        </div>
      </div>
    </div>
  )
}
