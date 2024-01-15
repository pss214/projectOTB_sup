import 'dart:developer' as dev;
import 'package:flutter/material.dart';
import 'package:toss_payment/toss_payment.dart';

class PaymentRequest {
  String productName;
  int amount;

  PaymentRequest({required this.productName, required this.amount});

  String get json {
    return '{"product_name": "$productName", "amount": $amount}';
  }

  Uri get url {
    return Uri.http("localhost:8080", "payment", {"data": json});
  }
}

class Product {
  final String name;
  final int price;

  Product({required this.name, required this.price});
}

class OrderWidget extends StatelessWidget {
  final String title;
  final Product product;
  final VoidCallback onTap;
  final String payBy;

  const OrderWidget({
    Key? key,
    required this.title,
    required this.product,
    required this.onTap,
    required this.payBy,
  }) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return GestureDetector(
      onTap: onTap,
      child: Container(
        margin: const EdgeInsets.all(4),
        decoration: BoxDecoration(
          border: Border.all(color: Colors.grey.shade300),
          borderRadius: const BorderRadius.all(
            Radius.circular(8),
          ),
        ),
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            Text(
              title,
              style: Theme.of(context).textTheme.headline6,
            ),
            const SizedBox(height: 8),
            Text(
              '${product.name}\n${product.price}원',
              style: Theme.of(context).textTheme.bodyText2,
              textAlign: TextAlign.center,
            ),
            const SizedBox(height: 8),
            Text(
              '$payBy 결제',
              style: Theme.of(context).textTheme.caption,
            ),
          ],
        ),
      ),
    );
  }
}

class ProductWidget extends StatelessWidget {
  final Product product;

  const ProductWidget({Key? key, required this.product}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Container(
      margin: const EdgeInsets.all(16),
      child: Column(
        mainAxisAlignment: MainAxisAlignment.center,
        children: [
          Text(
            product.name,
            style: Theme.of(context).textTheme.headline4,
          ),
          const SizedBox(height: 8),
          Text(
            '${product.price}원',
            style: Theme.of(context).textTheme.subtitle1,
          ),
        ],
      ),
    );
  }
}

class PayMenu extends StatelessWidget {
  const PayMenu({Key? key}) : super(key: key);

  //_showPayment 메소드를 static으로 변경
  static Future<void> showPayment(BuildContext context, PaymentRequest request) async {
    var ret = await showModalBottomSheet(
      context: context,
      isScrollControlled: true,
      backgroundColor: Colors.transparent,
      enableDrag: false,
      isDismissible: false,
      builder: (context) {
        bool success = false;
        return Container(
          margin: const EdgeInsets.only(top: 110),
          child: PaymentWebView(
            title: request.productName,
            paymentRequestUrl: request.url,
            onPageStarted: (url) {
              dev.log('onPageStarted.url = $url', name: "PaymentWebView");
            },
            onPageFinished: (url) {
              dev.log('onPageFinished.url = $url', name: "PaymentWebView");
              success = url.contains('success');
            },
            onDisposed: () {},
            onTapCloseButton: () {
              Navigator.of(context).pop(success);
            },
          ),
        );
      },
    );
    dev.log('ret = $ret', name: '_showPayment');
  }

  @override
  Widget build(BuildContext context) {
    final Product _product = Product(price: 1450, name: '버스 이용 비용');

    return MaterialApp(
      title: 'Toss Payment Demo',
      theme: ThemeData(
        primarySwatch: Colors.indigo,
      ),
      home: Scaffold(
        appBar: AppBar(
          title: const Text('결제창'),
          backgroundColor: Colors.orangeAccent,
        ),
        body: SafeArea(
          child: Column(
            children: [
              Expanded(
                child: Center(
                  child: ProductWidget(
                    product: _product,
                  ),
                ),
              ),
              GridView.count(
                shrinkWrap: true,
                padding: const EdgeInsets.all(4),
                crossAxisCount: 3,
                children: List.generate(8, (index) {
                  Widget ret = Container(
                    margin: const EdgeInsets.all(4),
                    decoration: BoxDecoration(
                      border: Border.all(color: Colors.grey.shade300),
                      borderRadius: const BorderRadius.all(
                        Radius.circular(8),
                      ),
                    ),
                  );

                  switch (index) {
                    case 0:
                      ret = OrderWidget(
                        title: '카드',
                        product: _product,
                        onTap: () {
                          //static으로 변경한 _showPayment 메소드 호출
                          showPayment(context, PaymentRequest(
                            productName: _product.name,
                            amount: _product.price,
                          ));
                        },
                        payBy: '카드',
                      );
                      break;

                    case 1:
                      ret = OrderWidget(
                        title: '가상계좌',
                        product: _product,
                        onTap: () {
                          //static으로 변경한 _showPayment 메소드 호출
                          showPayment(context, PaymentRequest(
                            productName: _product.name,
                            amount: _product.price,
                          ));
                        },
                        payBy: '가상계좌',
                      );
                      break;

                    case 2:
                      ret = OrderWidget(
                        title: '토스결제',
                        product: _product,
                        onTap: () {
                          //static으로 변경한 _showPayment 메소드 호출
                          showPayment(context, PaymentRequest(
                            productName: _product.name,
                            amount: _product.price,
                          ));
                        },
                        payBy: '토스결제',
                      );
                      break;
                  }
                  return ret;
                }),
              ),
            ],
          ),
        ),
      ),
    );
  }
}
