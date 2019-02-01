package com.hkkj.oa.common.annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.hkkj.oa.common.utils.JsonUtil;
import com.hkkj.oa.entity.LogSystem;
import com.hkkj.oa.service.ILogSystemService;

import eu.bitwalker.useragentutils.UserAgent;

@Aspect
@Component
public class LogAopAction {
	private static final Logger log = LogManager.getLogger(LogAopAction.class);

	@Autowired
	private ILogSystemService logSystemService;

	/**
	 * 申明切点，同时配置将要被aop过滤的业务类
	 */
	@Pointcut("execution (* com.hkkj.oa..*.*(..)) && @annotation(com.hkkj.oa.common.annotation.SystemLog)")
	public void pointcut() {
	}

	/**
	 * 环切，记录日志
	 */
	@Around("pointcut()")
	public Object doAround(ProceedingJoinPoint joinPoint)  {
		Object result  = null;
		try {
			long beginTime = System.currentTimeMillis();
			result = joinPoint.proceed();
			long endTime = System.currentTimeMillis();
			int useTime = Long.valueOf(endTime - beginTime).intValue();

			// 获取注解信息
			String methodName = joinPoint.getSignature().getName();
			Class<?> classTarget = joinPoint.getTarget().getClass();
			Class<?>[] par = ((MethodSignature) joinPoint.getSignature()).getParameterTypes();
			Method objMethod = classTarget.getMethod(methodName, par);

			// 手动记录信息
			SystemLog systemLog = objMethod.getAnnotation(SystemLog.class);
			String module = systemLog.module();
			String remark = systemLog.remark();

			// 调用关系
			String callClass = classTarget.getName();
			int callType = 3;// 请求类型【0:Controller,1:Service,2:Dao,3:other】
			Annotation[] annotations = classTarget.getAnnotations();
			for (int i = 0, len = annotations.length; i < len; i++) {
				Annotation tmpAnnotation = annotations[i];
				if (tmpAnnotation instanceof Controller) {
					callType = 0;
					break;
				} else if (tmpAnnotation instanceof Service) {
					callType = 1;
					break;
				} else if (tmpAnnotation instanceof Repository) {
					callType = 2;
					break;
				}
			}

			// 日志入库
			LogSystem logSystem = new LogSystem();
			logSystem.setModule(module);
			logSystem.setRemark(remark);
			logSystem.setCallClass(callClass);
			logSystem.setCallMethod(methodName);
			logSystem.setCallType(callType);
			logSystem.setBeginTime(beginTime);
			logSystem.setEndTime(endTime);
			logSystem.setUseTime(useTime);
			logSystem.setCreateTime(System.currentTimeMillis());

			// web请求信息
			HttpServletRequest request = null;
			ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
			if (null != sra) {
				request = sra.getRequest();
			}
			if (null != request) {
				// 浏览器信息
				UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("User-Agent"));
				String webClientBrowser = userAgent.getBrowser().getName();
				String webClientBrowserVersion = "-";
				if(userAgent.getBrowserVersion()!=null) {
					 webClientBrowserVersion = userAgent.getBrowserVersion().getVersion();
				}
				String webClientOs = userAgent.getOperatingSystem().getName();
				// 客户端IP
				int clientIp = AspectRequestUtil.getIpAddrInt(request);
				// 请求信息
				String webFullPath = request.getRequestURL().toString();
				String webParameter = JsonUtil.toJson(request.getParameterMap());
				String webMethod = request.getMethod();
				String webClientName = request.getLocalName();

				//
				logSystem.setClientIp(clientIp);
				logSystem.setWebFullPath(webFullPath);
				logSystem.setWebParameter(webParameter);
				logSystem.setWebMethod(webMethod);
				logSystem.setWebClientName(webClientName);
				logSystem.setWebClientBrowser(webClientBrowser);
				logSystem.setWebClientBrowserVersion(webClientBrowserVersion);
				logSystem.setWebClientOs(webClientOs);
			}

			int res = logSystemService.saveLogSystem(logSystem);
			log.debug("记录系统日志：[" + (res == 1 ? "success" : "fail") + "]" + logSystem.toString());
			return result;
		} catch (Throwable e) {
			log.error(e.getMessage(),e);
			return result;
		} 
	}

	/*
	 * //@Before("pointcut()") public void doBefore() {
	 * log.debug("doBefore advice"); }
	 * 
	 * //@AfterReturning("pointcut()") public void doAfterReturning() {
	 * log.debug("doAfterReturning advice"); }
	 * 
	 * //@After("pointcut()") public void doAfter() { log.debug("doAfter advice"); }
	 * 
	 * //@AfterThrowing("pointcut()") public void doAfterThrowing(JoinPoint jp) {
	 * log.debug("doAfterThrowing advice"); log.error(jp); }
	 */
}