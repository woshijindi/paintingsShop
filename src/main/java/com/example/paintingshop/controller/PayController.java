package com.example.paintingshop.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.api.CertAlipayRequest;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradePagePayModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.example.paintingshop.enums.DemandStateEnum;
import com.example.paintingshop.enums.EnlistStateEnum;
import com.example.paintingshop.enums.OrderStateEnum;
import com.example.paintingshop.mapper.DemandMapper;
import com.example.paintingshop.mapper.OrderMapper;
import com.example.paintingshop.model.Demand;
import com.example.paintingshop.model.Enlist;
import com.example.paintingshop.model.Order;
import com.example.paintingshop.model.OrderExample;
import com.example.paintingshop.service.DemandService;
import com.example.paintingshop.service.EnlistService;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.Security;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Controller
public class PayController {


    @Autowired
    private DemandMapper demandMapper;
    @Autowired
    private OrderMapper orderMapper;

    @Value("${alipay.appId}")
    private String appId;
    @Value("${app.private-key}")
    private String appPrivateKey;
    @Autowired
    private DemandService demandService;
    @Autowired
    private EnlistService enlistService;


    static{
        try{
            Security.addProvider(new BouncyCastleProvider());
        }catch(Exception e){
            e.printStackTrace();
        }
    }


    @PostMapping(value = "/pay", produces = "text/html; charset=UTF-8")
    public void pay(HttpServletResponse httpResponse,
                    @RequestParam(value = "demandId", required = false) Long demandId,
                    @RequestParam(value = "orderNumber", required = false) String orderNumber,
                    @RequestParam(value = "price", required = false) String price
    ) {

        CertAlipayRequest certAlipayRequest = new CertAlipayRequest();
        certAlipayRequest.setServerUrl("https://openapi.alipaydev.com/gateway.do");
        certAlipayRequest.setAppId(appId);
        certAlipayRequest.setPrivateKey(appPrivateKey);
        certAlipayRequest.setFormat("json");
        certAlipayRequest.setCharset("UTF-8");
        certAlipayRequest.setSignType("RSA2");
        certAlipayRequest.setCertPath("D:/alipay/appCertPublicKey_2016102200737611.crt");
        certAlipayRequest.setAlipayPublicCertPath("D:/alipay/alipayCertPublicKey_RSA2.crt");
        certAlipayRequest.setRootCertPath("D:/alipay/alipayRootCert.crt");
        DefaultAlipayClient alipayClient = null;
        try {
            alipayClient = new DefaultAlipayClient(certAlipayRequest);
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }

        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest(); //创建API对应的request
        alipayRequest.setReturnUrl("http://localhost:8080/payReturn");
        alipayRequest.setNotifyUrl("http://localhost:8080/payNotify"); //在公共参数中设置回跳和通知地址

        Demand demand = demandMapper.selectByPrimaryKey(demandId);


        AlipayTradePagePayModel model = new AlipayTradePagePayModel();
        model.setSubject(demand.getTitle());
        model.setOutTradeNo(orderNumber);
        model.setTimeoutExpress("30m");
        model.setTotalAmount(price);
        model.setProductCode("FAST_INSTANT_TRADE_PAY");
        alipayRequest.setBizModel(model);

        //请求
        String form = null;
        try {
            form = alipayClient.pageExecute(alipayRequest).getBody();
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        httpResponse.setContentType("text/html;charset=utf-8");
        try {
            httpResponse.getWriter().write(form); //直接将完整的表单html输出到页面
            httpResponse.getWriter().flush();
            httpResponse.getWriter().close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @PostMapping(value = "/payCancel", produces = "text/html; charset=UTF-8")
    public String payCancel(@RequestParam(value = "demandId", required = false) Long demandId,
                            @RequestParam(value = "orderNumber", required = false) String orderNumber,
                            @RequestParam(value = "enlistId", required = false) Long enlistId){

        OrderExample example = new OrderExample();
        example.createCriteria()
                .andOrderNumberEqualTo(orderNumber);
        Order upDateOrder = new Order();
        upDateOrder.setState(OrderStateEnum.CANCEL.getId());                //修改订单状态为已取消
        upDateOrder.setGmtModified(System.currentTimeMillis());

        orderMapper.updateByExampleSelective(upDateOrder, example);

        Demand demand = new Demand();
        demand.setId(demandId);
        demand.setState(DemandStateEnum.NOT_ACCEPTED.getId());
        demandService.createOrUpdate(demand);        //修改企划状态为正在招募


        Enlist enlist = new Enlist();
        enlist.setId(enlistId);
        enlist.setState(EnlistStateEnum.NOT_ACCEPTED.getId());
        enlistService.update(enlist);           //修改应征状态为未被接受


        return "redirect:/profile/myOrder";
    }


    @GetMapping("/payReturn")
    public String payReturn(HttpServletRequest request, ModelMap model) {

        System.out.println("支付成功, 进入同步通知接口...");
        //获取支付宝GET过来反馈信息
        Map<String, String> params = new HashMap<String, String>();
        Map<String, String[]> requestParams = request.getParameterMap();
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            try {
                //乱码解决，这段代码在出现乱码时使用
                valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
                params.put(name, valueStr);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }


        boolean signVerified = false; //调用SDK验证签名
        try {
            signVerified = AlipaySignature.rsaCertCheckV1(params, "D:/alipay/alipayCertPublicKey_RSA2.crt", "UTF-8", "RSA2");
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        if (signVerified) {
            String out_trade_no = null;
            try {
                //商户订单号
                out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"), "UTF-8");

                //支付宝交易号
//                String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");

                //交易状态
//                String   = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"),"UTF-8");

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            OrderExample example = new OrderExample();
            example.createCriteria()
                    .andOrderNumberEqualTo(out_trade_no);
            Order upDateOrder = new Order();
            upDateOrder.setState(OrderStateEnum.WAIT_SEND.getId());                //修改订单状态为待发货
            upDateOrder.setGmtModified(System.currentTimeMillis());





            orderMapper.updateByExampleSelective(upDateOrder, example);


            model.addAttribute("order", upDateOrder);

            System.out.println("支付成功！");

        } else {
            System.out.println("支付, 验签失败...");

        }
        return "redirect:/profile/myOrder";

    }

    @RequestMapping(value = "/payNotify", method = RequestMethod.POST)
    @ResponseBody
    public String payNotify(HttpServletRequest request, Model model) {
        System.out.println("支付成功, 进入异步通知接口...");
        //获取支付宝GET过来反馈信息
        Map<String, String> params = new HashMap<String, String>();
        Map<String, String[]> requestParams = request.getParameterMap();
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            try {
                //乱码解决，这段代码在出现乱码时使用
                valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
                params.put(name, valueStr);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        boolean signVerified = false; //调用SDK验证签名
        try {
            signVerified = AlipaySignature.rsaCertCheckV1(params, "D:/alipay/alipayCertPublicKey_RSA2.crt", "UTF-8", "RSA2");
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        if (signVerified) {
            String out_trade_no = null;
            try {
                //商户订单号
                out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"), "UTF-8");

                //支付宝交易号
                String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"), "UTF-8");

                //交易状态
                String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"), "UTF-8");


                if (trade_status.equals("TRADE_FINISHED")) {
                    //判断该笔订单是否在商户网站中已经做过处理
                    //如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
                    //如果有做过处理，不执行商户的业务程序

                    //注意：
                    //退款日期超过可退款期限后（如三个月可退款），支付宝系统发送该交易状态通知
                } else if (trade_status.equals("TRADE_SUCCESS")) {
                    //判断该笔订单是否在商户网站中已经做过处理
                    //如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
                    //如果有做过处理，不执行商户的业务程序

                    //注意：
                    //付款完成后，支付宝系统发送该交易状态通知

                    OrderExample example = new OrderExample();
                    example.createCriteria()
                            .andOrderNumberEqualTo(out_trade_no);
                    List<Order> orderList = orderMapper.selectByExample(example);
                    Order upDateOrder = orderList.get(0);
                    upDateOrder.setState(OrderStateEnum.TO_BE_HARVESTED.getId());
                    upDateOrder.setGmtModified(System.currentTimeMillis());


                    orderMapper.updateByExampleSelective(upDateOrder, example);


                    model.addAttribute("order", upDateOrder);

                    System.out.println("支付成功！");

                }

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }


            System.out.println("异步通知，支付成功！");
        } else {
            System.out.println("支付, 验签失败...");

        }
        return "success";

    }


}

