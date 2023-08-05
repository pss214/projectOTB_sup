import {useEffect, useRef, useState} from 'react'
import {loadPaymentWidget, PaymentWidgetInstance} from '@tosspayments/payment-widget-sdk'
import {nanoid} from 'nanoid'
import {Link} from '../../components'

const clientKey = 'test_ck_D5GePWvyJnrK0W0k6q8gLzN97Eoq'
const customerKey = 'YbX2HuSlsC9uVJW6NMRMj'

export default function Pay() {
  const paymentWidgetRef = useRef<PaymentWidgetInstance | null>(null)
  const paymentMethodsWidgetRef = useRef<ReturnType<
    PaymentWidgetInstance['renderPaymentMethods']
  > | null>(null)
  const [price, setPrice] = useState(1)

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
        <div className=" w-full px-6 py-8 text-black bg-white rounded shadow-md">
          <div>
            <h1 className="mb-8 text-4xl text-center text-lime-500">결제</h1>
          </div>
          <div id="payment-widget" />
          <center>
            <button
              className="flex-center ml-4 mr-4 btn btn-primary text-white  border-lime-600 bg-lime-600"
              onClick={() => {
                const paymentWidget = paymentWidgetRef.current
                paymentWidget?.requestPayment({
                  orderId: nanoid(),
                  orderName: '버스 예약',
                  customerName: '김xx',
                  customerEmail: 'customer123@gmail.com',
                  successUrl: `${window.location.origin}/success`,
                  failUrl: `${window.location.origin}/fail`
                })
              }}>
              결제하기
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
