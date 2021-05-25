package cn.kgc.config;

import java.io.FileWriter;
import java.io.IOException;

/* *
 *类名：AlipayConfig
 *功能：基础配置类
 *详细：设置帐户有关信息及返回路径
 *修改日期：2017-04-05
 *说明：  ksfxhw3818@sandbox.com   111111
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 *该代码仅供学习和研究支付宝接口使用，只是提供一个参考。
 */

public class AlipayConfig {

//↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓

	// 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号
	public static String app_id = "2021000117653098";

	// 商户私钥，您的PKCS8格式RSA2私钥
    public static String merchant_private_key = "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCsgWjQP8KtgUgmkrjImJfylI4ZIa2qDygGLf3t0yns9qe+DY3If6c8+84ynCi44BW8a+KKP0X6DiDnjmL/VluF2gXt9yeKElM4UACv5y+Im7P3mmb/DL4cYrdX2fnxOHU3RNjblyAKIUO0RnWuB27rioAnH1y9Ii7w5i5NAmUJQ/3+C1pxpXEqSS7WXggX48uL6XS3eZ81ktR1xPfJ23S9cusfqkjVdNVzHi7IXb1z2d/KggWQqM9snFD+MxbVmxpNYE3uAVOd7SSX/7dCCZFWJQwaR+cyWwIXAB5udXW2GLFfcD7as26rkdgq1X3co6yddC+CECShDzUxbv+uDD8rAgMBAAECggEAGQo71aa7soXGnFrF2dBUlhC8Q6eg1sOWcrcwWdpCBrSTCsJa51SoSPDgMHZ8bsGQRiZ+3sRqeu6cFiCT/C5j4J0SKUWsBp28WLs8LkxnmPPI/tL5ymHmIqatqGHqpvokAZ7Lv6xDKVVEx6FGqblIf4Y+Le+efZG9sJgo9mK3mc14x0loMxuj2hMNpPGZrtzeravj03X3NzLzf5VTrN+dcqPbAoSAklED8geqbqGC7P9sk1kvH+JObsdmImbYTPQzijjEZTwxsO+T3p08cG1/Jnr8ML5KSEPys+/TLg2PFno/ttrfj42Lxx192zS8YpyWswACu9SWuFXg6K+9g+bnqQKBgQDV8l4M0s1RncehW7uj7t9rDDyjuhGabRpYO1EEOkXwaUDHqzYpOV40FyaIJaQR8qLbK3lt96klm+RjU5sLHU0rBAzbMdVarzV9fqjAj1fkkIAa319hBa6e5U5fz8Ine2rK6HQILQqhiqDKcWAJi+aYI4nk/AMF8UBfQX+v611VhwKBgQDOacBQ3OCZ5FFWJqq3dnpIzYn0Le5/1y+saSW+Dqy7aGymxK/jr1BK5Pq7stihBdj6KL3EpOGtLUSzJC0jYTVaIwYk/Yxr+LiLx5UnCdUFEO+1ak/OXEyBjWQoiwksX/D7quE87+IjhUil8Elb5e5s8TeqhI//ePmd69OyJ7GyPQKBgAXUlw+OZx4ESL5y+4gaKGiIatajaXtl4cPpyeltlR5aH/PRDKbO82xD/T7Ul8YNcLa8VpDSoP9vYif6zUzePY4gjCElGIViJPkIjJzCy0hlT7hrwfOo7ftSkHI3/TiKOnzRHOhkIdgJKRybPlzEPUKEJWviGkr9Kh3ucQZgSajdAoGAFdOU5HgWv+8msqdwEepTT/N8DPf2akpE3kRYG1rN5RtLse7GcpsNMH46DTMCOTS3FuzL9ElDG97XjuHabA1RaPCfK7Yr7Rbl3NID8ZbWkaySzGorejTEWkarOqZJGAolH1eYbasoDH2Bixp3h2Iu7hIrwro1x9vw+UQApKwR3DkCgYAJtF8lcEmkLX17zl76r20GecJxnO8eNhSorRnXzoJDvmi/JzZn2QG8IATlSGtFfEonpijpUybTq5F5jHu0JACs26JRBk5KXga8c/42yJrblQnV1l5FYLFGrUm8RZyOle7EjkCRHFEnD7EJhMZKUBO6Br//sa+uJ2cIEanPidk7qw==";

	// 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
    public static String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEArIFo0D/CrYFIJpK4yJiX8pSOGSGtqg8oBi397dMp7Panvg2NyH+nPPvOMpwouOAVvGviij9F+g4g545i/1ZbhdoF7fcnihJTOFAAr+cviJuz95pm/wy+HGK3V9n58Th1N0TY25cgCiFDtEZ1rgdu64qAJx9cvSIu8OYuTQJlCUP9/gtacaVxKkku1l4IF+PLi+l0t3mfNZLUdcT3ydt0vXLrH6pI1XTVcx4uyF29c9nfyoIFkKjPbJxQ/jMW1ZsaTWBN7gFTne0kl/+3QgmRViUMGkfnMlsCFwAebnV1thixX3A+2rNuq5HYKtV93KOsnXQvghAkoQ81MW7/rgw/KwIDAQAB";

	// 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	public static String notify_url = "http://工程公网访问地址/alipay.trade.page.pay-JAVA-UTF-8/notify_url.jsp";

	// 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    //回调路径：当支付完成之后，回调到自己的项目路径中
	public static String return_url = "http://localhost:8080/orders/afterordersPay";

	// 签名方式
	public static String sign_type = "RSA2";

	// 字符编码格式
	public static String charset = "utf-8";

	// 支付宝网关
	public static String gatewayUrl = "https://openapi.alipaydev.com/gateway.do";

	// 支付宝网关
	public static String log_path = "C:\\";


//↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

    /**
     * 写日志，方便测试（看网站需求，也可以改成把记录存入数据库）
     * @param sWord 要写入日志里的文本内容
     */
    public static void logResult(String sWord) {
        FileWriter writer = null;
        try {
            writer = new FileWriter(log_path + "alipay_log_" + System.currentTimeMillis()+".txt");
            writer.write(sWord);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

