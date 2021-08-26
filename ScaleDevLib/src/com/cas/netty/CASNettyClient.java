package com.cas.netty;

import com.cas.code.CommonDefines;
import com.cas.code.ResultReceive;
import com.cas.netty.CASNettyClient;
import com.cas.netty.NettyProperty;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CASNettyClient {
	private static final Logger LOGGER = LoggerFactory.getLogger(CASNettyClient.class);

	private EventLoopGroup group;

	private Map<String, Integer> retryMap;

	private Map<String, ChannelFuture> channelFutures;

	private NettyProperty nettyProperty;

	private Bootstrap b;

	public CASNettyClient() {
		loadProperties();
		this.retryMap = new ConcurrentHashMap<>();
		this.channelFutures = new HashMap<>();
	}

	public CASNettyClient(String propFilePath) {
		loadProperties(propFilePath);
		this.retryMap = new ConcurrentHashMap<>();
		this.channelFutures = new HashMap<>();
	}

	public void loadProperties() {
		this.nettyProperty = new NettyProperty(2, 3000);
	}

	public void loadProperties(String propFilePath) {
		this.nettyProperty = new NettyProperty(2, 3000);
	}

	public void connectScale(Map<String, String> scaleEnv, Object sendData, Map<String, String> scaleInfo, ResultReceive resultReceive) {
		this.group = (EventLoopGroup) new NioEventLoopGroup(2);
		String ip = scaleInfo.get("Ip");
		int port = Integer.parseInt(scaleInfo.get("Port"));
		String scaleModel = scaleInfo.get("Model");
		if (scaleModel == null) {
			LOGGER.error("scaleModel is null,can't do anything,return directly");
			return;
		}
		try {
			this.retryMap.put(ip, Integer.valueOf(1));
			this.b = new Bootstrap();
			((Bootstrap) ((Bootstrap) ((Bootstrap) ((Bootstrap) ((Bootstrap) this.b.group(this.group)).channel(NioSocketChannel.class))
					.handler((ChannelHandler) new LoggingHandler(LogLevel.INFO))).option(ChannelOption.TCP_NODELAY, Boolean.valueOf(true))).option(ChannelOption.CONNECT_TIMEOUT_MILLIS,
							Integer.valueOf(this.nettyProperty.getConnectTimeOut()))).handler(new CASNettyClientHandler(scaleEnv, sendData, scaleInfo, resultReceive));
			ChannelFuture f = this.b.connect(ip, port);
			f.addListener(new ChannelFutureListener() {
				public void operationComplete(ChannelFuture f) {
					if (!f.isSuccess()) {
						Integer connectCnt = (Integer) CASNettyClient.this.retryMap.get(ip);
						CASNettyClient.this.retryMap.put(ip, Integer.valueOf(connectCnt.intValue() + 1));
						connectCnt = Integer.valueOf(connectCnt.intValue() + 1);
						if (connectCnt.intValue() <= CASNettyClient.this.nettyProperty.getConnectRetryTimes()) {
							try {
								CASNettyClient.this.b.connect(ip, port).addListener(this);
							} catch (Exception e) {
								LOGGER.error("Occur an exception when retry to connect for ip:{}, times{},exception:{}", new Object[] { ip, connectCnt, e });
							}
						} else {
							CASNettyClient.this.returnConnectFailResp(scaleEnv, sendData, scaleInfo, resultReceive);
						}
					}
				}
			});
			this.channelFutures.put(ip, f);
		} catch (Exception e) {
			LOGGER.error("Occur an Exception when connectScale()", e);
			returnConnectFailResp(scaleEnv, sendData, scaleInfo, resultReceive);
		}
	}

	private void returnConnectFailResp(Map<String, String> scaleEnv, Object sendData, Map<String, String> scaleInfo, ResultReceive resultReceive) {
		List<Map<String, String>> resultList = new ArrayList<>();
		resultReceive.setResultData(scaleEnv, CommonDefines.CommState.CONNECTION_ERROR.getStateCode(), resultList, sendData, scaleInfo);
	}

	public boolean checkHealthChannel(String ip) {
		if (this.channelFutures.get(ip) != null && ((ChannelFuture) this.channelFutures.get(ip)).channel().isRegistered()) {
			return false;
		}
		return true;
	}

	public boolean checkAllHealthChannel() {
		for (Map.Entry<String, ChannelFuture> entry : this.channelFutures.entrySet()) {
			if (((ChannelFuture) entry.getValue()).channel().isRegistered()) {
				return false;
			}
			if (((ChannelFuture) entry.getValue()).channel().isActive()) {
				return false;
			}
		}
		return true;
	}

	public void quit() {
		this.group.shutdownGracefully();
	}

	public boolean exitChannel(String ip) {
		if (this.channelFutures.get(ip) == null) {
			return false;
		}
		((ChannelFuture) this.channelFutures.get(ip)).channel().close();
		((ChannelFuture) this.channelFutures.get(ip)).channel().deregister();
		return true;
	}

	public void allKillChannel() {
		for (Map.Entry<String, ChannelFuture> entry : this.channelFutures.entrySet()) {
			((ChannelFuture) entry.getValue()).channel().close();
			((ChannelFuture) entry.getValue()).channel().deregister();
		}
	}

	public int channelCount() {
		return this.channelFutures.size();
	}
}
