package com.example.demo.configuration;

public class ConstantsConfiguration {
	public final static String CLIENT_ID = "f86653550f3eb2fa76fbb665ad16ac03";
	public final static String CLIENT_SECRET = "7oKeCCmY3MQvMz1f1TPeKypdiGxGLCqN";
	public final static String AUTH_URL = "https://kauth.kakao.com//oauth/authorize?client_id=%s&redirect_uri=%s&response_type=code";
	public final static String OAUTH_URL = "https://kauth.kakao.com/oauth/token";
	public final static String MESSAGE_SEND_URL = "https://kapi.kakao.com/v2/api/talk/memo/default/send";
	public final static String REDIRECT_URL = "http://127.0.0.1:8080/callback";
	public final static String LINE_TOKEN = "EECRyCRi8CpRPFsMvh7ctsOybIbu39KmiqLlVKXjklo";
	public final static String LINE_AUTH_URL = "https: //notify-bot.line.me/oauth/authorize";
	public final static String LINE_OAUTH_URL = "https://notify-bot.line.me/oauth/token";
	public final static String LINE_NOTIFY_URL = "https://notify-api.line.me/api/notify?message=%s";
}
