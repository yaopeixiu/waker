package ency;

public class EncyTest {
	/**
	 * 登录
	 */
		/*@Test
		public void testLogin(){

		EncryptUtil encryptUtil =EncryptUtil.getInstance();
		String noexplain =encryptUtil.AESencode("1234567w", ConsantSecrit.LOGIN_PASS_KEY);//密文
		System.out.println("密文--->"+noexplain);
		//解密
		String explain = encryptUtil.AESdecode(noexplain,  ConsantSecrit.LOGIN_PASS_KEY);//明文
		System.out.println("明文--->"+explain);
		String loginTime =System.currentTimeMillis()+"";
		System.out.println(loginTime);

		StringBuffer sb =new StringBuffer("1234567w");
        String result = sb.append(loginTime).append(noexplain).toString();
        System.out.println("result:"+result);
	    String _sgin = encryptUtil.MD5(result, ConsantSecrit.PASS_SIGN);
	    System.out.println("签名--->"+_sgin);

	    //测试登录
	    String url = "http://192.168.0.25:7000/api/user/login";
	    Map<String, String> params =new HashMap<>();
	    params.put("password", noexplain);
	    params.put("device", "test");
	    params.put("type", "0");
	    params.put("sign", _sgin);
	    params.put("loginTime", loginTime);
	    params.put("userAccount", "18100732851");
	    String _result = HttpClientUtil.doPost(url, params, "UTF-8");
	    JSONObject json = JSONObject.parseObject(_result);
	    System.err.println(json.getString("token"));
	}*/
		/**
		 * md5加密
		 */
//		@Test
//		public void testMD5(){
//			System.out.println(MD5.encrypt32("1234567w"));
//		}
//		/**
//		 * 注册步骤1
//		 */
//		@Test
//		public void testRegister1(){
//		String url = "http://192.168.0.25:7000/api/user/register";
//		Map<String, String> params =new HashMap<>();
//		params.put("account", "18100732851");
//	    params.put("loginPass", "A4FB5BB8638E6C7BCC4ABAD8713BA339");
//	    params.put("inviteCode", "xhgj");
//	    params.put("sign", "PKTPrF5RKKQ9e8X4q3/blA==");
//	    params.put("areaCode", "86");
//	    params.put("registTime", "1557990108684");
//	    params.put("step", "1");
//	    String _result = HttpClientUtil.doPost(url, params, "UTF-8");
//	    System.out.println("测试注册第一步结果：="+_result);
//		}
//		/**
//		 * 注册步骤2 -->发送短信
//		 */
//		@Test
//		public void sendCode(){
//			String url = "http://192.168.0.25:7000/api/user/sendCode?account=18100732851&type=0&codeType=0";
//			String _result = HttpClientUtil.doPost(url, null, "UTF-8");
//		    System.out.println("测试注册第二步结果：="+_result);
//		}
//		/**
//		 * 注册步骤3 -->初始化信息
//		 */
//		@Test
//		public void initInfo(){
//			String code ="";
//			String url = "http://192.168.0.25:7000/api/user/register?account=18100732851&step=2&code="+code;
//			Map<String, String> params =new HashMap<>();
//
//			String _result = HttpClientUtil.doPost(url, params, "UTF-8");
//		    System.out.println("测试注册第三步结果：="+_result);
//		}

}
